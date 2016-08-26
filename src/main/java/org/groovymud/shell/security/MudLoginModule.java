package org.groovymud.shell.security;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.ConfirmationCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;
import org.groovymud.object.alive.Player;

/**
 * this little beast is the implementation of a JAAS login module
 * 
 * see http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/JAASRefGuide.html
 * 
 * using JAAS should allow us to plug in modules a bit more easily by using a
 * standard framework we should be able to use platform login modules too if
 * required.
 * 
 * @author matt
 * 
 */
public class MudLoginModule implements LoginModule {

	private static final String GUEST_USER = "guest";
	private static final String PLAYER_PRINCIPAL = "player";
	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map sharedState;
	private Map options;

	// configurable option
	private boolean debug = false;

	// the authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	// username and password
	private String username;
	private char[] password;

	Player player = null;
	Player dupe = null;

	private Set<Principal> playerPrincipals;

	private final static Logger logger = Logger.getLogger(MudLoginModule.class);

	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
		playerPrincipals = new HashSet<Principal>();
		getPlayerPrincipals().add(createPrincipal(GUEST_USER));
		// initialize any configured options
		debug = "true".equalsIgnoreCase((String) options.get("debug"));

	}

	protected MudPrincipal createPrincipal(String principal) {
		return new MudPrincipal(principal);
	}

	public boolean login() throws LoginException {
		try {
			NameCallback nameCallback = createNameCallback("username: ");
			Callback[] cb = createCallbackArray(nameCallback);

			getCallbackHandler().handle(cb);
			setUsername(nameCallback.getName());
			retrieveExistingPlayer(); // populate dupe player if there is one
			doLoadPlayerCallback(); // load the player
			boolean confirmed = false;

			if (getPlayer() == null) {
				ConfirmationCallback confirm = new ConfirmationCallback("Your name is not in our annuls:" + nameCallback.getName() + "..  Did you type it correctly?", ConfirmationCallback.INFORMATION, ConfirmationCallback.YES_NO_OPTION, ConfirmationCallback.YES);
				cb = createCallbackArray(confirm);
				getCallbackHandler().handle(cb);
				confirmed = (confirm.getSelectedIndex() == ConfirmationCallback.YES);
			}
			if (confirmed || getPlayer() != null) {

				if (!getUsername().equals(GUEST_USER)) {
					getPlayerPrincipals().add(createPrincipal(PLAYER_PRINCIPAL));
					checkPasswordValid(player);
					confirmDestIfDupeExisted();

				} else {
					// always create guests
					doCreatePlayerCallback();
				}
				// successful log in
				setSucceeded(true);
				return true;
			} else {
				TextOutputCallback toc = new TextOutputCallback(TextOutputCallback.WARNING, "Ok, try again then..");
				cb = createCallbackArray(toc);
				getCallbackHandler().handle(cb);
				return false;
			}
		} catch (IOException e) {
			logger.error(e, e);
			throw new LoginException(e.getMessage());
		} catch (UnsupportedCallbackException e) {
			logger.error(e, e);
			throw new LoginException(e.getMessage());
		}
	}

	protected Callback[] createCallbackArray(Callback... callback) {
		return callback;
	}

	protected NameCallback createNameCallback(String prompt) {
		return new NameCallback(prompt);
	}

	protected void doLoadPlayerCallback() throws IOException, UnsupportedCallbackException {
		Callback[] cb;
		ActionCallback loadCallback = createActionCallback(username, ActionCallback.LOAD_ACTION);
		cb = createCallbackArray(loadCallback);
		getCallbackHandler().handle(cb);
		setPlayer((Player) loadCallback.getMudObject());
	}

	protected ActionCallback createActionCallback(String username, String action) {
		return new ActionCallback(username, action);
	}

	protected void confirmDestIfDupeExisted() throws IOException, UnsupportedCallbackException, FailedLoginException {
		Callback[] cb;
		Player dupe = getDupe();
		if (dupe != null) {
			// we need to dest this player if they say yes
			ConfirmationCallback alreadyPlaying = new ConfirmationCallback("You are already playing. Throw the other copy out?", ConfirmationCallback.INFORMATION, ConfirmationCallback.YES_NO_OPTION, ConfirmationCallback.NO);
			cb = createCallbackArray(alreadyPlaying);
			getCallbackHandler().handle(cb);
			if (alreadyPlaying.getSelectedIndex() == ConfirmationCallback.YES) {
				dupe.dest(true);
			} else {
				throw new FailedLoginException("cannot log in, already logged in!");
			}
		}
	}

	protected void checkPasswordValid(Player player) throws IOException, UnsupportedCallbackException, FailedLoginException {
		Callback[] cb;
		PasswordCallback passwordCallback = null;
		PasswordCallback checkPassword = null;

		if (player == null) {

			checkPassword = createPasswordCallback("again:", false);
			passwordCallback = createPasswordCallback("enter a password:", false);

			cb = createCallbackArray(passwordCallback, checkPassword);
		} else {
			passwordCallback = createPasswordCallback("password: ", false);
			cb = createCallbackArray(passwordCallback);
		}
		getCallbackHandler().handle(cb);
		setPassword(passwordCallback.getPassword()); // set the password
		boolean passwordValid = false;
		if (checkPassword != null) {
			passwordValid = checkSamePassword(getPassword(), checkPassword.getPassword());
		} else {
			PlayerCredentials testCredentials = new PlayerCredentials(getUsername(), String.valueOf(getPassword()));
			passwordValid = player.getSubject().getPrivateCredentials().contains(testCredentials);
		}
		if (!passwordValid) {
			throw new FailedLoginException("passwords do not match.");
		}
		if (checkPassword != null) {
			doCreatePlayerCallback();
		}
	}

	protected PasswordCallback createPasswordCallback(String prompt, boolean echo) {
		return new PasswordCallback(prompt, echo);
	}

	protected void doCreatePlayerCallback() throws IOException, UnsupportedCallbackException {
		Callback[] cb;
		ActionCallback createCallback;
		createCallback = new ActionCallback(getUsername(), ActionCallback.CREATE_ACTION);
		cb = createCallbackArray(createCallback);
		getCallbackHandler().handle(cb);
		setPlayer((Player) createCallback.getMudObject());

	}

	protected void retrieveExistingPlayer() throws IOException, UnsupportedCallbackException, FailedLoginException {
		Callback[] cb;
		ActionCallback checkIfLoggedIn = createActionCallback(getUsername(), ActionCallback.EXISTS_ACTION);
		cb = createCallbackArray(checkIfLoggedIn);
		getCallbackHandler().handle(cb);
		setDupe((Player) checkIfLoggedIn.getMudObject());
	}

	protected boolean checkSamePassword(char[] password, char[] passwordOther) throws FailedLoginException {
		boolean passwordValid = true;
		if (passwordOther.length == 0) {
			throw new FailedLoginException("password cannot be 0 length");
		}
		for (int x = 0; x < passwordOther.length; x++) {
			if (passwordOther[x] != password[x]) {
				passwordValid = false;
			}
		}
		return passwordValid;
	}

	public boolean abort() throws LoginException {
		if (!hasSucceeded()) {
			return false;
		}
		if (hasSucceeded() && hasCommitSucceeded()) {
			logout();
		} else {
			setSucceeded(false);
		}
		return true;
	}

	public boolean logout() throws LoginException {
		getSubject().getPrincipals().removeAll(getPlayerPrincipals());
		cleanup();
		player = null;
		playerPrincipals = null;
		setSucceeded(setCommitSucceeded(false));
		return true;
	}

	public boolean commit() throws LoginException {
		if (!hasSucceeded()) {
			cleanup();
			return false;
		}
		Player player = getPlayer();
		if (player != null) {
			getPlayerPrincipals().addAll(player.getSubject().getPrincipals());
			logger.info("player principals:");
			for (Principal p : getPlayerPrincipals()) {
				logger.info(p.getName());
			}
			getSubject().getPrincipals().addAll(getPlayerPrincipals());
			player.setSubject(getSubject()); // update the players principals
			PlayerCredentials playerCredentials = new PlayerCredentials(getUsername(), String.valueOf(getPassword()));
			getSubject().getPrivateCredentials().add(playerCredentials);
			player.setPlayerCredentials(playerCredentials);
			cleanup();
			setCommitSucceeded(true);

		} else {
			throw new LoginException("player was null");
		}
		return true;
	}

	protected void cleanup() {
		username = null;
		password = null;
		dupe = null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public CallbackHandler getCallbackHandler() {
		return callbackHandler;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Player getDupe() {
		return dupe;
	}

	public void setDupe(Player dupe) {
		this.dupe = dupe;
	}

	public void setCallbackHandler(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}

	public Set<Principal> getPlayerPrincipals() {
		return playerPrincipals;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	public boolean hasSucceeded() {
		return succeeded;
	}

	public boolean setCommitSucceeded(boolean commitSucceeded) {
		this.commitSucceeded = commitSucceeded;
		return commitSucceeded;
	}

	public boolean hasCommitSucceeded() {
		return commitSucceeded;
	}

}
