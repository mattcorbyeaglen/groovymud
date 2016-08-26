package org.groovymud.shell.security;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.groovymud.object.alive.Player;

public class MudLoginModuleTest extends TestCase {

	protected boolean existingPlayerCalled;
	protected boolean doLoadPlayerCallbackCalled;
	protected boolean checkPasswordValidCalled;
	protected boolean confirmDestIfDupeExistedCalled;

	public void testLogin() throws IOException, UnsupportedCallbackException, LoginException {
		final String prompt = "username: ";
		final NameCallback mockNameCallback = new NameCallback(prompt);
		mockNameCallback.setName("userx");
		final Callback[] callbacks = new Callback[1];
		callbacks[0] = mockNameCallback;
		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();

		final Set mockPlayerPrincipals = new HashSet();

		final MudPrincipal mockPrincipal = new MudPrincipal("mock");

		MudLoginModule mockModule = new MudLoginModule() {

			@Override
			protected NameCallback createNameCallback(String promptx) {
				assertEquals(prompt, promptx);
				return mockNameCallback;
			}

			@Override
			protected Callback[] createCallbackArray(Callback... callback) {
				return callbacks;
			}

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}

			@Override
			protected void retrieveExistingPlayer() throws IOException, UnsupportedCallbackException, FailedLoginException {
				// TODO Auto-generated method stub
				existingPlayerCalled = true;
			}

			@Override
			protected void doLoadPlayerCallback() throws IOException, UnsupportedCallbackException {
				// TODO Auto-generated method stub
				doLoadPlayerCallbackCalled = true;
			}

			@Override
			protected void checkPasswordValid(Player player) throws IOException, UnsupportedCallbackException, FailedLoginException {
				// TODO Auto-generated method stub
				checkPasswordValidCalled = true;
			}

			@Override
			protected void confirmDestIfDupeExisted() throws IOException, UnsupportedCallbackException, FailedLoginException {
				// TODO Auto-generated method stub
				confirmDestIfDupeExistedCalled = true;
			}

			@Override
			public Set<Principal> getPlayerPrincipals() {
				// TODO Auto-generated method stub
				return mockPlayerPrincipals;
			}

			@Override
			protected MudPrincipal createPrincipal(String principal) {
				// TODO Auto-generated method stub
				return mockPrincipal;
			}
		};

		assertTrue(mockModule.login());
		callbackHandlerControl.verify();
		assertTrue(existingPlayerCalled);
		assertTrue(doLoadPlayerCallbackCalled);
		assertTrue(checkPasswordValidCalled);
		assertTrue(confirmDestIfDupeExistedCalled);
		assertTrue(mockPlayerPrincipals.contains(mockPrincipal));
	}

	public void testDoLoadPlayerCallback() throws IOException, UnsupportedCallbackException {
		String username = "test";
		String action = "wombat";
		MockControl playerCtrl = MockControl.createNiceControl(Player.class);
		Player obj = (Player) playerCtrl.getMock();
		final ActionCallback mockCallback = new ActionCallback(username, action);
		mockCallback.setMudObject(obj);
		final Callback[] callbacks = new Callback[1];
		callbacks[0] = mockCallback;
		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();

		MudLoginModule mockLoginModule = new MudLoginModule() {

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}

			@Override
			protected ActionCallback createActionCallback(String username, String action) {
				// TODO Auto-generated method stub
				return mockCallback;
			}

			@Override
			protected Callback[] createCallbackArray(Callback... callback) {
				// TODO Auto-generated method stub
				return callbacks;
			}
		};
		mockLoginModule.doLoadPlayerCallback();
		assertSame(obj, mockLoginModule.getPlayer());
		callbackHandlerControl.verify();
	}

	protected boolean checkSamePasswordCalled;

	public void testCheckPasswordValidExistingPlayer() throws IOException, UnsupportedCallbackException, FailedLoginException {
		final String password = "x123456";
		final String username = "blow";
		Subject mockSubject = new Subject();
		mockSubject.getPrivateCredentials().add(new PlayerCredentials(username, password));
		MockControl playerCtrl = MockControl.createNiceControl(Player.class);
		Player player = (Player) playerCtrl.getMock();
		player.getSubject();
		playerCtrl.setReturnValue(mockSubject);
		playerCtrl.replay();
		final PasswordCallback mockCallback = new PasswordCallback("password: ", false);
		mockCallback.setPassword(password.toCharArray());
		final Callback[] callbacks = new Callback[1];
		callbacks[0] = mockCallback;
		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();
		MudLoginModule mockLogin = new MudLoginModule() {

			@Override
			protected PasswordCallback createPasswordCallback(String prompt, boolean echo) {
				// TODO Auto-generated method stub
				assertEquals("password: ", prompt);
				assertEquals(false, echo);
				return mockCallback;
			}

			@Override
			protected Callback[] createCallbackArray(Callback... callback) {
				// TODO Auto-generated method stub
				assertTrue(callbacks[0].equals(mockCallback));
				return callbacks;
			}

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return "blow";
			}
		};
		mockLogin.checkPasswordValid(player);
		callbackHandlerControl.verify();
		playerCtrl.verify();
	}

	public void testCheckPasswordValidExistingPlayerFails() throws IOException, UnsupportedCallbackException, FailedLoginException {
		final String password = "x123456";
		MockControl playerCtrl = MockControl.createNiceControl(Player.class);
		Player player = (Player) playerCtrl.getMock();
		player.getSubject();
		playerCtrl.setDefaultReturnValue(new Subject());
		playerCtrl.replay();
		final PasswordCallback mockCallback = new PasswordCallback("password: ", false);
		mockCallback.setPassword(password.toCharArray());
		final Callback[] callbacks = new Callback[1];
		callbacks[0] = mockCallback;
		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();
		MudLoginModule mockLogin = new MudLoginModule() {

			@Override
			protected PasswordCallback createPasswordCallback(String prompt, boolean echo) {
				// TODO Auto-generated method stub
				assertEquals("password: ", prompt);
				assertEquals(false, echo);
				return mockCallback;
			}

			@Override
			protected Callback[] createCallbackArray(Callback... callback) {
				// TODO Auto-generated method stub
				assertTrue(callbacks[0].equals(mockCallback));
				return callbacks;
			}

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}
		};
		try {
			mockLogin.checkPasswordValid(player);
			fail("should rethrow exception");
		} catch (FailedLoginException e) {
			callbackHandlerControl.verify();
			playerCtrl.verify();
		}
	}

	protected boolean doCreatePlayerCallbackCalled;

	public void testCheckPasswordValidNewPlayer() throws IOException, UnsupportedCallbackException, FailedLoginException {
		final String password = "x123456";
		Player player = null;
		final PasswordCallback mockCallback1 = new PasswordCallback("password: ", false);
		final PasswordCallback mockCallback2 = new PasswordCallback("again: ", false);
		mockCallback1.setPassword(password.toCharArray());
		mockCallback2.setPassword(password.toCharArray());

		final Callback[] callbacks = new Callback[2];
		callbacks[0] = mockCallback1;
		callbacks[0] = mockCallback2;

		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();
		MudLoginModule mockLogin = new MudLoginModule() {

			@Override
			protected boolean checkSamePassword(char[] passwordx, char[] passwordOther) {
				int x = 0;
				for (char character : password.toCharArray()) {
					assertTrue(character == passwordx[x]);
					x++;
				}
				checkSamePasswordCalled = true;
				return true;
			}

			@Override
			protected void doCreatePlayerCallback() throws IOException, UnsupportedCallbackException {
				// TODO Auto-generated method stub
				doCreatePlayerCallbackCalled = true;
			}

			@Override
			protected PasswordCallback createPasswordCallback(String prompt, boolean echo) {
				// TODO Auto-generated method stub
				if (prompt.equals("again: ")) {
					return mockCallback2;
				}
				return mockCallback1;
			}

			@Override
			protected Callback[] createCallbackArray(Callback... callback) {
				// TODO Auto-generated method stub
				return callbacks;
			}

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}
		};

		mockLogin.checkPasswordValid(player);
		assertTrue(checkSamePasswordCalled);
		assertTrue(doCreatePlayerCallbackCalled);
		callbackHandlerControl.verify();

	}

	public void testCheckPasswordValidNewPlayerFailed() throws IOException, UnsupportedCallbackException, FailedLoginException {
		final String password = "x123456";
		Player player = null;
		final PasswordCallback mockCallback1 = new PasswordCallback("password: ", false);
		final PasswordCallback mockCallback2 = new PasswordCallback("again: ", false);
		mockCallback1.setPassword(password.toCharArray());
		mockCallback2.setPassword(password.toCharArray());

		final Callback[] callbacks = new Callback[2];
		callbacks[0] = mockCallback1;
		callbacks[0] = mockCallback2;

		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();
		MudLoginModule mockLogin = new MudLoginModule() {

			@Override
			protected boolean checkSamePassword(char[] passwordx, char[] passwordOther) {

				checkSamePasswordCalled = true;
				return false;
			}

			@Override
			protected void doCreatePlayerCallback() throws IOException, UnsupportedCallbackException {
				doCreatePlayerCallbackCalled = true;
			}

			@Override
			protected PasswordCallback createPasswordCallback(String prompt, boolean echo) {
				if (prompt.equals("again: ")) {
					return mockCallback2;
				}
				return mockCallback1;
			}

			@Override
			protected Callback[] createCallbackArray(Callback... callback) {
				return callbacks;
			}

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}
		};
		try {
			mockLogin.checkPasswordValid(player);
			fail("should rethrow exception");
		} catch (FailedLoginException e) {
			assertTrue(checkSamePasswordCalled);
			assertFalse(doCreatePlayerCallbackCalled);
			callbackHandlerControl.verify();
		}
	}

	public void testRetrieveExistingPlayer() throws IOException, UnsupportedCallbackException, FailedLoginException {
		final String username = "user";

		final ActionCallback mockActionCallback = new ActionCallback(username, ActionCallback.EXISTS_ACTION);
		MockControl playerCtrl = MockControl.createNiceControl(Player.class);
		Player player = (Player) playerCtrl.getMock();

		mockActionCallback.setMudObject(player);
		final Callback[] callbacks = new Callback[2];
		callbacks[0] = mockActionCallback;

		MockControl callbackHandlerControl = MockControl.createControl(CallbackHandler.class);
		final CallbackHandler cbHandler = (CallbackHandler) callbackHandlerControl.getMock();
		cbHandler.handle(callbacks);
		callbackHandlerControl.setDefaultVoidCallable();
		callbackHandlerControl.replay();
		MudLoginModule mudLoginModule = new MudLoginModule() {

			@Override
			public CallbackHandler getCallbackHandler() {
				// TODO Auto-generated method stub
				return cbHandler;
			}

			@Override
			protected ActionCallback createActionCallback(String usernamex, String action) {
				// TODO Auto-generated method stub
				assertEquals(username, usernamex);
				return mockActionCallback;
			}
		};
		mudLoginModule.setUsername(username);
		mudLoginModule.retrieveExistingPlayer();
		assertEquals(player, mudLoginModule.getDupe());
		callbackHandlerControl.verify();
	}

	public void testCheckSamePassword() throws FailedLoginException {
		String password1 = "password";
		String password2 = "password";
		MudLoginModule module = new MudLoginModule();

		assertTrue(module.checkSamePassword(password1.toCharArray(), password2.toCharArray()));

	}

	public void testCheckNotSamePassword() throws FailedLoginException {
		String password1 = "password";
		String password2 = "pissword";
		MudLoginModule module = new MudLoginModule();

		assertFalse(module.checkSamePassword(password1.toCharArray(), password2.toCharArray()));

	}

	public void testCheckFailEmptyPassword() throws FailedLoginException {
		String password1 = "";
		String password2 = "";
		MudLoginModule module = new MudLoginModule();

		try {
			module.checkSamePassword(password1.toCharArray(), password2.toCharArray());
			fail("should throw exception");
		} catch (FailedLoginException e) {

		}

	}

	boolean cleanupCalled = false;

	public void testCommit() throws LoginException {
		final String password = "x12345";
		final String username = "blarg";
		PlayerCredentials mockCredentials = new PlayerCredentials(username, password);

		Set<Principal> subjectPrincipals = new HashSet<Principal>();
		final Set<Principal> playerPrincipals = new HashSet<Principal>();

		final Subject mockSubject = new Subject();
		mockSubject.getPrincipals().addAll(subjectPrincipals);
		Principal playerPrincipal = new MudPrincipal("principal");
		mockSubject.getPrincipals().add(playerPrincipal);

		final Subject playerSubject = new Subject();
		playerSubject.getPrincipals().addAll(playerPrincipals);

		MockControl playerControl = MockControl.createControl(Player.class);
		final Player mockPlayer = (Player) playerControl.getMock();
		mockPlayer.getSubject();
		playerControl.setDefaultReturnValue(playerSubject);
		mockPlayer.setSubject(mockSubject);
		playerControl.setDefaultVoidCallable();
		mockPlayer.setSubject(mockSubject);
		playerControl.setDefaultVoidCallable();
		mockPlayer.setPlayerCredentials(mockCredentials);
		playerControl.setDefaultVoidCallable();
		playerControl.replay();

		MudLoginModule mockMod = new MudLoginModule() {

			@Override
			public char[] getPassword() {
				// TODO Auto-generated method stub
				return password.toCharArray();
			}

			@Override
			public Set<Principal> getPlayerPrincipals() {
				// TODO Auto-generated method stub
				return playerPrincipals;
			}

			@Override
			public Subject getSubject() {
				// TODO Auto-generated method stub
				return mockSubject;
			}

			protected void cleanup() {
				assertTrue(hasSucceeded());
				cleanupCalled = true;
			}

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return username;
			}

			@Override
			public Player getPlayer() {
				// TODO Auto-generated method stub
				return mockPlayer;
			}
		};
		mockMod.setSucceeded(true);
		mockMod.commit();
		mockPlayer.getSubject().getPrincipals().contains(playerPrincipal);
		mockPlayer.getSubject().getPrincipals().contains(new MudPrincipal("player"));
		mockPlayer.getSubject().getPrincipals().contains(new MudPrincipal("guest"));

		assertTrue(mockMod.hasCommitSucceeded());
		playerControl.verify();
	}
}
