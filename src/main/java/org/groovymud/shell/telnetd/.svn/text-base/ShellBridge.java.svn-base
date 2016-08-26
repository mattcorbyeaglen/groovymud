package org.groovymud.shell.telnetd;

import java.io.IOException;

import net.wimpi.telnetd.net.Connection;
import net.wimpi.telnetd.net.ConnectionEvent;
import net.wimpi.telnetd.shell.Shell;

import org.apache.log4j.Logger;
import org.groovymud.object.registry.Registry;
import org.springframework.context.ApplicationContext;

/**
 * horrible fudge to make spring and telnetd more compatible function here
 * 
 * the shell bridge basically enables spring to be used by telnetd.
 * 
 */
public abstract class ShellBridge implements Shell {

	private final static Logger logger = Logger.getLogger(ShellBridge.class);

	private static ApplicationContext context;
	private ExtendedTerminalIO extendedTerminalIO;
	private Connection connection;
	private Registry objectRegistry;

	public static void setApplicationContext(ApplicationContext context) {
		ShellBridge.context = context;
	}

	public static ApplicationContext getApplicationContext() {
		return ShellBridge.context;
	}

	public static void setShell(LoginShell shell) {
		throw new UnsupportedOperationException();
	}

	public void connectionIdle(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_IDLE");

			getExtendedTerminalIO().flush();
		} catch (IOException e) {
			logger.error(e, e);
		}
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
	}

	public void connectionLogoutRequest(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_LOGOUTREQUEST");
			getExtendedTerminalIO().flush();
		} catch (IOException e) {
			logger.error(e, e);
		}

	}

	public void connectionSentBreak(ConnectionEvent ce) {
		try {
			getExtendedTerminalIO().write("CONNECTION_BREAK");
			getExtendedTerminalIO().flush();
		} catch (IOException e) {
			logger.error(e, e);
		}

	}

	public void run(Connection con) {
		throw new UnsupportedOperationException();
	}

	public ExtendedTerminalIO getExtendedTerminalIO() {
		return extendedTerminalIO;
	}

	public void setExtendedTerminalIO(ExtendedTerminalIO terminalIO) {
		this.extendedTerminalIO = terminalIO;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setObjectRegistry(Registry objectRegistry) {
		this.objectRegistry = objectRegistry;
	}

	public Registry getObjectRegistry() {
		return objectRegistry;
	}

}
