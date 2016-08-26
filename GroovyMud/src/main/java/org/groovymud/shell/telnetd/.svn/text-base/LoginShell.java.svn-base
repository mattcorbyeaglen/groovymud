package org.groovymud.shell.telnetd;

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
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.ConfirmationCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import net.wimpi.telnetd.io.BasicTerminalIO;
import net.wimpi.telnetd.net.Connection;
import net.wimpi.telnetd.net.ConnectionEvent;
import net.wimpi.telnetd.shell.Shell;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.object.alive.Player;
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.shell.SplashLoader;
import org.groovymud.shell.security.ActionCallback;
import org.groovymud.shell.security.PasswordException;
import org.groovymud.shell.security.PasswordService;

/**
 *  specific instance of a player connects through this shell
 *  
 *  provides JAAS authentication callback functions
 *  see http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/JAASRefGuide.html#Callback
 */

public class LoginShell extends ShellBridge implements CallbackHandler {

	private static final String BEAN_NAME = "loginShell";

	private final static Logger logger = Logger.getLogger(LoginShell.class);

	private int loginAttempts;
	private MudObjectAttendant objectLoader;
	private PasswordService passwordService;

	private SplashLoader splashLoader;

	Player player = null;

	public void run(Connection connection) {

		setConnection(connection);
		ExtendedTerminalIO io = getExtendedTerminalIO();
		io.setBasicTerminalIO(connection.getTerminalIO());
		connection.addConnectionListener(this);

		// clear the screen and start from zero

		LoginContext lc = null;
		try {
			io.eraseScreen();
			io.homeCursor();
			io.setBackgroundColor(BasicTerminalIO.BLACK);

			String splashPage = getSplashLoader().loadMudSplash();
			io.write(splashPage);
			io.write("");
			io.writeln("term type:" + getConnection().getConnectionData().getNegotiatedTerminalType());

			try {
				lc = new LoginContext("Mud", this);
			} catch (SecurityException e) {
				logger.error(e, e);
			} catch (LoginException e) {
				logger.error(e, e);
			}

			int logins = 0;
			for (; logins < 3; logins++) {
				try {
					lc.login();
					break;
				} catch (LoginException e) {
					logger.error(e, e);

				}
			}

			if (logins < 3) {
				if (player != null) {

					getObjectRegistry().addActivePlayer(this, player);
					player.setTerminalOutput(io);
					try {
						doMovePlayer(io, player);
					} catch (Exception e) {
						io.writeln("Eeek you fell into the void!");
						getObjectLoader().movePlayerToVoid(player);
						logger.error(e,e);
					}
					getConnection().getConnectionData().getEnvironment().put("player", player);
					getConnection().getConnectionData().getEnvironment().put("registry", getObjectRegistry());
					getConnection().getConnectionData().getEnvironment().put("loginContext", lc);
					getConnection().getConnectionData().getEnvironment().put("terminalIO", getExtendedTerminalIO());
					getConnection().setNextShell(MudShell.BEAN_NAME);
					getConnection().removeConnectionListener(this);
				}
			} else {
				io.writeln("Sorry too many login attempts.");
			}

		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	private void doMovePlayer(ExtendedTerminalIO io, Player player) throws FileNotFoundException, IOException {
		boolean error = false;
		try {
			getObjectLoader().movePlayerToLocation(player);
		} catch (CompilationFailedException e) {
			error = true;
			logger.error(e, e);
		} catch (InstantiationException e) {
			logger.error(e, e);
		} finally {
			if (error) {
				io.writeln("Aarrrrggh you've fallen into the void!!!1! Quick! Get a cre!!");
				getObjectLoader().movePlayerToVoid(player);
			}
		}
	}

	protected String requestUsername(NameCallback nc) throws IOException {
		ExtendedTerminalIO io = getExtendedTerminalIO();
		io.write(nc.getPrompt());
		io.flush();
		String username = io.readln(false);

		return username;
	}

	protected Player doRegisterNewPlayer(String username) {
		try {
			return getObjectLoader().createNewPlayer(username);
		} catch (CompilationFailedException e) {
			logger.error(e, e);
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		} catch (InstantiationException e) {
			logger.error(e, e);
		}
		return null;
	}

	protected String retrievePassword(PasswordCallback pc) throws IOException {
		ExtendedTerminalIO io = getExtendedTerminalIO();
		io.write(pc.getPrompt());
		io.flush();
		return io.readln(pc.isEchoOn());
	}

	private Player getLoggedInPlayer(String loggingIn) {
		return (Player) getObjectRegistry().getMudObject(loggingIn);
	}

	protected Player loadPlayer(String username) throws CompilationFailedException, FileNotFoundException, ResourceException, ScriptException, InstantiationException {
		return getObjectLoader().loadPlayerData(username);
	}

	public Player tryToloadPlayer(String username) {
		Player player = null;
		try {
			player = loadPlayer(username);
		} catch (CompilationFailedException e) {
			logger.error(e, e);
		} catch (InstantiationException e) {
			logger.error(e, e);
		} catch (ResourceException e) {
			logger.error(e, e);
		} catch (ScriptException e) {
			logger.error(e, e);
		} catch (FileNotFoundException e) {
			logger.error(e, e);
		}
		return player;
	}

	public static Shell createShell() {
		return (Shell) ShellBridge.getApplicationContext().getBean(BEAN_NAME);
	}

	/**
	 * handles callbacks from the JAAS login module
	 * 
	 * can handle NameCallback, PasswordCallback, ConfirmationCallback,
	 * ActionCallback and TextOutputCallback
	 * 
	 * @param callbacks
	 *            - a array of callback objects to handle
	 * 
	 */
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		String username = null;

		for (int x = 0; x < callbacks.length; x++) {
			if (callbacks[x] instanceof NameCallback) {
				NameCallback nc = (NameCallback) callbacks[x];
				username = requestUsername(nc);
				nc.setName(username);
			} else if (callbacks[x] instanceof PasswordCallback) {
				PasswordCallback pc = (PasswordCallback) callbacks[x];
				try {
					String password = retrievePassword(pc);
					pc.setPassword(getPasswordService().encrypt(password).toCharArray());
				} catch (PasswordException e) {
					logger.error(e, e);
				}
			} else if (callbacks[x] instanceof ConfirmationCallback) {
				ConfirmationCallback conf = (ConfirmationCallback) callbacks[x];
				if (conf.getMessageType() != ConfirmationCallback.YES_NO_OPTION) {
					throw new UnsupportedCallbackException(conf, "cannot handle none YN questions yet");
				}
				confirmNewPlayer(conf);
			} else if (callbacks[x] instanceof ActionCallback) {
				ActionCallback cp = (ActionCallback) callbacks[x];
				if (cp.getAction().equals(ActionCallback.LOAD_ACTION)) {
					this.setPlayer(tryToloadPlayer(cp.getObjectName()));
					cp.setMudObject(player);
					if (player != null) {
						player.getSubject().getPrivateCredentials().add(player.getPlayerCredentials());
					}
				}
				if (cp.getAction().equals(ActionCallback.CREATE_ACTION)) {
					this.setPlayer(this.doRegisterNewPlayer(cp.getObjectName()));
					cp.setMudObject(player);
				}
				if (cp.getAction().equals(ActionCallback.EXISTS_ACTION)) {
					cp.setMudObject(getLoggedInPlayer(cp.getObjectName()));
				}
			} else if (callbacks[x] instanceof TextOutputCallback) {
				TextOutputCallback cb = (TextOutputCallback) callbacks[x];
				getExtendedTerminalIO().writeln(cb.getMessage());
			} else {
				throw new UnsupportedCallbackException(callbacks[x]);
			}
		}

	}

	protected void confirmNewPlayer(ConfirmationCallback conf) throws IOException {
		ExtendedTerminalIO io = getExtendedTerminalIO();

		io.writeln(conf.getPrompt());
		String response = io.readln(false);

		if (response.substring(0, 1).equalsIgnoreCase("y")) {
			conf.setSelectedIndex(ConfirmationCallback.YES);
		} else {
			conf.setSelectedIndex(ConfirmationCallback.NO);
		}

	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public MudObjectAttendant getObjectLoader() {
		return objectLoader;
	}

	public void setObjectLoader(MudObjectAttendant objectLoader) {
		this.objectLoader = objectLoader;
	}

	// this implements the ConnectionListener!
	public void connectionTimedOut(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_TIMEDOUT");
			getExtendedTerminalIO().flush();
			// close connection
			getConnection().close();
		} catch (Exception ex) {
			logger.error("connectionTimedOut()", ex);
		}
	}// connectionTimedOut

	public void connectionIdle(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_IDLE");

			getExtendedTerminalIO().flush();
		} catch (IOException e) {
			logger.error(e, e);
		}
	}// connectionIdle

	public void connectionLogoutRequest(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_LOGOUTREQUEST");
			getExtendedTerminalIO().flush();
		} catch (IOException e) {
			logger.error(e, e);
		}

	}// connectionLogout

	public void connectionSentBreak(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_BREAK");
			getExtendedTerminalIO().flush();
		} catch (IOException e) {
			logger.error(e, e);
		}

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PasswordService getPasswordService() {
		return passwordService;
	}

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public SplashLoader getSplashLoader() {
		return splashLoader;
	}

	public void setSplashLoader(SplashLoader splashLoader) {
		this.splashLoader = splashLoader;
	}
}
