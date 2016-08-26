package org.groovymud.object.registry;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.action.DestroyEvent;
import org.groovymud.engine.event.action.SaveEvent;
import org.groovymud.engine.event.messages.MessageEvent;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.engine.event.observer.Observer;
import org.groovymud.engine.event.system.MovementEvent;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.ObjectLocation;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Room;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import org.groovymud.shell.telnetd.LoggingExtendedTerminalIO;
import org.groovymud.shell.telnetd.LoginShell;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
/**
 * The registry is the "root" container for all mud objects all global scoped
 * events pass through this object, and in turn are (if necessary) passed down
 * to the child rooms and objects.
 * 
 * spring injects mudEngine, mudObjectAttendant, commandIterpreter
 * 
 * this object is spring applicationcontextaware and this will get injected by
 * spring automatically.
 */
public class Registry implements Container, Observer, ApplicationContextAware {

	private final static Logger logger = Logger.getLogger(Registry.class);

	private Map<LoginShell, Player> activePlayerHandles;

	protected InventoryHandler inventoryHandler;

	private MudObjectAttendant mudObjectAttendant;

	private ApplicationContext applicationContext;

	public Registry(InventoryHandler handler) {
		this.inventoryHandler = handler;
	}

	public void register(MudObject obj) {

		if (isContainerInstance(obj)) {
			Container con = castToContainer(obj);
			con.getInventoryHandler().relinkContent(con);
		}

		if (obj.getId() != null) {
			MudObject old = getMudObject(obj.getId());
			if (old != null) {
				old.dest(false);
			}
		}
		if (isAliveInstance(obj)) {
			Alive alive = castToAlive(obj);
			if (alive.getTerminalOutput() == null) {
				alive.setTerminalOutput(new LoggingExtendedTerminalIO(null));
				LoggingExtendedTerminalIO io = (LoggingExtendedTerminalIO) alive.getTerminalOutput();
				if (io != null) {
					io.setObject(alive);
				}
			}
		}
		addMudObject(obj.getId() == null ? obj.getName() : obj.getId(), obj);
		addMudObject(obj);

	}

	public void update(MudObject object) {
		if (getMudObjects().contains(object)) {
			removeMudObject(object);
			addMudObject(object);
		} else {
			throw new IllegalArgumentException("cannot update registry for " + object);
		}
	}

	protected Alive castToAlive(MudObject obj) {
		// TODO Auto-generated method stub
		return (Alive) obj;
	}

	protected boolean isAliveInstance(MudObject obj) {
		// TODO Auto-generated method stub
		return obj instanceof Alive;
	}

	protected boolean isContainerInstance(MudObject obj) {
		return obj instanceof Container;
	}

	public void addMudObject(String key, MudObject object) {
		getInventoryHandler().addMudObject(key, object);
		addMudObject(object);
		if (object instanceof Room) {
			object.setCurrentContainer(this);
			castToObservable(object).addObserver(this);
		}
	}

	protected IObservable castToObservable(MudObject object) {
		return ((IObservable) object);
	}

	public void addActivePlayer(LoginShell shell, Player player) {
		addActivePlayerHandle(shell, player);
		if (logger.isDebugEnabled()) {
			logger.debug("There are " + getActivePlayerHandles().size() + " connections active and " + (Runtime.getRuntime().freeMemory() / 1000) + " kBytes free");
		}
	}

	public void removeaActivePlayer(Player player) {
		getActivePlayerHandlesMap().values().remove(player);
	}

	public MudObject removeMudObject(MudObject object) {
		if (object instanceof Player) {
			removeaActivePlayer((Player) object);
		}
		return getInventoryHandler().removeMudObject(object);
	}

	public void addActivePlayerHandle(LoginShell shell, Player player) {
		getActivePlayerHandlesMap().put(shell, player);
	}

	public Player getPlayerByHandle(LoginShell handle) {
		return getActivePlayerHandlesMap().get(handle);
	}

	protected Map<LoginShell, Player> getActivePlayerHandlesMap() {
		if (activePlayerHandles == null) {
			Map<LoginShell, Player> synchronizedMap = Collections.synchronizedMap(new WeakHashMap<LoginShell, Player>());
			activePlayerHandles = synchronizedMap;
		}
		return activePlayerHandles;
	}

	protected void notifyContents(IObservable observable, IScopedEvent event) {
		Iterator<MudObject> contentIterator = getMudObjects().iterator();
		while (contentIterator.hasNext()) {
			MudObject o = contentIterator.next();
			if (o instanceof Observer) {
				castToObserver(o).update(observable, event);
			}
		}
	}

	protected Observer castToObserver(Object o) {
		return ((Observer) o);
	}

	public void update(IObservable object, IScopedEvent arg) {
		doUpdate(object, arg);
	}

	public void doUpdate(IObservable o, IScopedEvent arg) {
		if (arg instanceof MessageEvent) {
			notifyAll(arg);
		}
		if (arg instanceof MovementEvent) {
			doMovementEvent(arg);
		}
		if (arg instanceof DestroyEvent) {
			doDestroyEvent(arg);
		}
		if (arg instanceof SaveEvent) {
			doSaveEvent(arg);
		}

	}

	protected void doSaveEvent(IScopedEvent arg) {
		SaveEvent ev = (SaveEvent) arg;
		getMudObjectAttendant().savePlayerData((Player) ev.getSource());
	}

	protected void notifyAll(IScopedEvent arg) {
		MessageEvent mEvent = (MessageEvent) arg;
		Map<String, Set<MudObject>> everyone = getInventoryHandler().getMudObjectsMap(true);
		Iterator<Set<MudObject>> all = everyone.values().iterator();

		while (all.hasNext()) {
			Set<MudObject> nextSet = all.next();
			Iterator<MudObject> setIterator = nextSet.iterator();
			while (setIterator.hasNext()) {
				Alive obj = (Alive) setIterator.next();
				ExtendedTerminalIO term = obj.getTerminalOutput();
				if (term == null) {
					continue;
				}
				if (mEvent.getSource() == obj) {
					try {
						term.writeln(mEvent.getSourceMessage());
					} catch (IOException e) {
						logger.error(e, e);
					}
				} else {
					try {
						if (mEvent.getScopeMessage() != null) {
							term.writeln(mEvent.getScopeMessage());
						}
						if (mEvent.getTargets().contains(obj)) {
							term.writeln(mEvent.getTargetMessage());
						}
					} catch (IOException e) {
						logger.error(e, e);
					}
				}
			}
		}

	}

	protected void doDestroyEvent(IScopedEvent arg) {
		DestroyEvent ev = (DestroyEvent) arg;
		MudObject object = (MudObject) ev.getSource();
		if (object instanceof Player) {
			fireLeavingMessage((Player) object);
		}
		if (object instanceof Container) {
			unregisterContents(((Container) object));
		}
		removeMudObject(object);
		object.getCurrentContainer().removeMudObject(object);
		if (object instanceof Player) {
			try {
				((Player) object).getTerminalOutput().close();
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
	}

	/**
	 * removes all objects from any container and sub containers
	 * 
	 * eg. "dest" them
	 * 
	 * @param container
	 */
	public void unregisterContents(Container container) {
		for (MudObject obj : container.getInventoryHandler().getMudObjects()) {
			// remove objects from the game, but not from the container
			if (obj instanceof Container) {
				unregisterContents(castToContainer(obj));
			}
			removeMudObject(obj);
		}
	}

	private Container castToContainer(MudObject obj) {
		return ((Container) obj);
	}

	protected void fireLeavingMessage(Player player) {
		MessageEvent leaves = new MessageEvent(EventScope.GLOBAL_SCOPE);
		leaves.setScopeMessage("[" + player.getName() + " leaves GroovyMud]");
		leaves.setSource(player);
		leaves.setSourceMessage("Thanks for visiting!!");
		((Observable) player).fireEvent(leaves);
	}

	protected synchronized void doMovementEvent(IScopedEvent arg) {
		MovementEvent event = (MovementEvent) arg;
		Alive movingObject = event.getMovingObject();
		Container foundRoom = (Container) getMudObject(event.getRoomLocation().getBeanId());
		if (foundRoom == null) {
			Exception anException = null;
			try {
				foundRoom = (Container) getMudObjectAttendant().load(event.getRoomLocation());
			} catch (CompilationFailedException e) {
				logger.error(e, e);
				anException = e;
			} finally {
				if (anException != null) {
					try {
						movingObject.getTerminalOutput().writeln(anException.getMessage());
					} catch (IOException e) {
						logger.error(e, e);
					}
				}
			}
		}
		event.setFoundRoom(foundRoom);
		// pass the event back to the room, with an altered scope
		event.setScope(EventScope.CONTAINER_SCOPE);
		((Observable) event.getSourceExit()).doEvent(event);
	}

	public MudObjectAttendant getMudObjectAttendant() {
		return mudObjectAttendant;
	}

	public void setMudObjectAttendant(MudObjectAttendant mudObjectAttendant) {
		this.mudObjectAttendant = mudObjectAttendant;
	}

	public Map<String, Set<MudObject>> getMudObjectsMap() {
		return getInventoryHandler().getMudObjectsMap();
	}

	public HashSet<LoginShell> getActivePlayerHandles() {
		return new HashSet<LoginShell>(getActivePlayerHandlesMap().keySet());
	}

	public void addMudObject(MudObject object) {
		getInventoryHandler().addMudObject(object);
	}

	public MudObject getMudObject(String name) {
		return getInventoryHandler().getMudObject(name);
	}

	public Set<MudObject> getMudObjects(String name) {
		return getInventoryHandler().getMudObjects(name);
	}

	public Set<MudObject> getMudObjects() {
		return getInventoryHandler().getMudObjects();
	}

	public void setInventoryHandler(InventoryHandler inventoryHandler) {
		this.inventoryHandler = inventoryHandler;
	}

	public InventoryHandler getInventoryHandler() {
		return inventoryHandler;
	}

	public EventScope getScope() {
		return EventScope.GLOBAL_SCOPE;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ObjectLocation getObjectLocation() {
		return null;
	}

	public void setScope(EventScope scope) {

	}

}
