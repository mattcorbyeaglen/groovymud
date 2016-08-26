package org.groovymud.engine;

/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import net.wimpi.telnetd.TelnetD;

import org.apache.log4j.Logger;
import org.groovymud.engine.event.HeartBeatListener;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Player;
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.object.registry.Registry;
import org.groovymud.object.room.Room;
import org.groovymud.shell.telnetd.LoginShell;
import org.groovymud.shell.telnetd.ShellBridge;
import org.groovymud.utils.CountingMap;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The mud engine is responsible for starting up the mud, shutting it down,
 * distributing heartbeats, resetting rooms and registering player states.
 * 
 * @author matt
 * 
 */
public abstract class JMudEngine extends Thread {

	private static final int HEARTBEATS_UNTIL_AUTOSAVE = 60;
	private static final int HEARTBEATS_UNTIL_RESET = 1200;

	private static final String NET_DEAD_STATUS = "net dead";

	protected static final int DEFAULT_MUD_HEARTBEAT_LENGTH_MS = 1000;

	private static TelnetD telnetDaemon;

	private Registry objectRegistry;

	private volatile boolean running;

	private final static Logger logger = Logger.getLogger(JMudEngine.class);

	private static AbstractApplicationContext context;

	private MudObjectAttendant objectAttendant;

	private CountingMap netDeadPlayers;

	private boolean shutdownRequested = false;

	private ExecutorService executor;

	public JMudEngine() {
		running = true;
	}

	public static void main(String[] args) {
		System.out.println("workspace.loc:" + System.getProperty("workspace.loc"));
		System.out.println("mavenrepo:" + System.getProperty("maven.repo"));
		System.out.println("mudspace:" + System.getProperty("mudspace"));

		context = new ClassPathXmlApplicationContext(new String[] { "org/groovymud/telnetdContext.xml", "org/groovymud/applicationContext.xml", "org/groovymud/commandAliases.xml" });
		context.registerShutdownHook();
		ShellBridge.setApplicationContext(context);

		JMudEngine engine = (JMudEngine) context.getBean("mudEngine");
		engine.setPriority(Thread.MAX_PRIORITY);
		engine.getExecutor().execute(engine);

		telnetDaemon.start();
	}

	public void run() {
		logger.info("GroovyMud up and running");
		int saveTime = 0;
		int resetTime = 0;
		while (isRunning()) {
			try {
				doHeartBeat();
				handleTheNetDead();
				checkPlayerHandles();

				if (saveTime++ == HEARTBEATS_UNTIL_AUTOSAVE) {
					savePlayers();
					saveTime = 0;
				}
				if (resetTime++ == HEARTBEATS_UNTIL_RESET) {
					resetRooms();
					resetTime = 0;
				}
				if (isShutdownRequested()) {
					createShutdownBehaviour().handleShutdown();
				}
				Thread.sleep(DEFAULT_MUD_HEARTBEAT_LENGTH_MS);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}

	}

	protected void resetRooms() {
		logger.info("Resetting rooms");
		Iterator<MudObject> i = getObjectRegistry().getMudObjects().iterator();
		while (i.hasNext()) {
			MudObject o = i.next();

			if (o instanceof Room) {
				((Room) o).initialise();
			}
			Thread.yield();
		}
		getContext().refresh();
	}

	protected void savePlayers() {
		logger.info("auto saving..");
		Iterator<LoginShell> i = getObjectRegistry().getActivePlayerHandles().iterator();
		while (i.hasNext()) {
			LoginShell o = i.next();
			Player p = o.getPlayer();
			if (getObjectRegistry().getMudObject(p.getName()) != null) {
				getObjectAttendant().savePlayerData(p);
				try {
					o.getPlayer().getTerminalOutput().writeln("\r\nAuto Saving...");
				} catch (IOException e) {
					logger.error(e, e);
				}
			}
		}
	}

	protected void checkPlayerHandles() {
		CountingMap netDead = getNetDeadPlayers();
		Set<Player> totallyDead = netDead.getKeysAbove(30);

		for (Player p : totallyDead) {
			p.removeStatus(NET_DEAD_STATUS);
			p.dest(false);
		}

	}

	protected void doHeartBeat() {
		Set<MudObject> objects = getObjectRegistry().getMudObjects();
		Iterator<MudObject> i = objects.iterator();
		while (i.hasNext()) {
			MudObject o = i.next();

			if (o instanceof HeartBeatListener) {
				try {
					((HeartBeatListener) o).heartBeat();
				} catch (Exception e) {
					// ^^ ick but we don't want to break the whole hearbeat system because of 
					// one object's error now do we?
					logger.error(e, e);
				}
			}
			Thread.yield();
		}

	}

	protected void handleTheNetDead() {

		Iterator<LoginShell> x = new HashSet<LoginShell>(getObjectRegistry().getActivePlayerHandles()).iterator();
		CountingMap netDead = getNetDeadPlayers();
		while (x.hasNext()) {
			LoginShell shell = x.next();
			if (!shell.getConnection().isActive()) {
				Player p = getObjectRegistry().getPlayerByHandle(shell);
				if (!netDead.containsKey(p)) {
					// not a good way to do it.. but how.. ?
					logger.info(p.getName() + " is net dead..");
					p.addStatus(NET_DEAD_STATUS);
				}
				netDead.increment(p);
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void requestShutDown(Player requester) {
		logger.info("shutdown requested!!!");

		shutdownRequested = true;

	}

	public static AbstractApplicationContext getContext() {
		return context;
	}

	public Registry getObjectRegistry() {
		return objectRegistry;
	}

	public void setObjectRegistry(Registry registry) {
		this.objectRegistry = registry;
	}

	public MudObjectAttendant getObjectAttendant() {
		return objectAttendant;
	}

	public void setObjectAttendant(MudObjectAttendant objectAttendant) {
		this.objectAttendant = objectAttendant;
	}

	public TelnetD getTelnetDaemon() {
		return telnetDaemon;
	}

	public void setTelnetDaemon(TelnetD telnetDeamon) {
		JMudEngine.telnetDaemon = telnetDeamon;
	}

	public CountingMap getNetDeadPlayers() {
		return netDeadPlayers;
	}

	public void setNetDeadPlayers(CountingMap netDeadPlayers) {
		this.netDeadPlayers = netDeadPlayers;
	}

	public void setShutdownRequested(boolean shutdownRequested) {
		this.shutdownRequested = shutdownRequested;
	}

	public boolean isShutdownRequested() {
		return shutdownRequested;
	}

	protected void shutdownNow() {
		logger.info("shutdownNow called");
		destAllPlayers();
		getTelnetDaemon().stop();
		this.setRunning(false);
		logger.info("shudownNow complete");
		System.exit(0);
	}

	protected void destAllPlayers() {
		Iterator<LoginShell> x = new HashSet<LoginShell>(getObjectRegistry().getActivePlayerHandles()).iterator();
		while (x.hasNext()) {
			LoginShell shell = x.next();
			if (shell.getConnection().isActive()) {
				Player p = getObjectRegistry().getPlayerByHandle(shell);
				p.dest(false);
			}
		}
	}

	public abstract ShutdownBehaviour createShutdownBehaviour();

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

}
