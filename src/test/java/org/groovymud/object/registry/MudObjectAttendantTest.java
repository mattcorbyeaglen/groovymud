package org.groovymud.object.registry;

import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.ObjectLocation;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Room;
import org.groovymud.shell.command.CommandInterpreter;
import org.groovymud.shell.telnetd.LoggingExtendedTerminalIO;
import org.springframework.context.ApplicationContext;

import com.thoughtworks.xstream.XStream;

public class MudObjectAttendantTest extends TestCase {

	boolean methodCalled;
	protected boolean methodCalled1;
	protected boolean methodCalled2;
	MockControl ctrl;

	@Override
	protected void setUp() throws Exception {
		ctrl = MockControl.createControl(MudObject.class);
		Logger.getRootLogger().setLevel(Level.OFF);
	}

	/*
	 * Test method for
	 * 'org.groovymud.object.registry.MudObjectAttendant.load(String, boolean)'
	 */
	public void testLoadToRegistry() throws ResourceException, ScriptException, InstantiationException, IllegalAccessException, CompilationFailedException, FileNotFoundException, MalformedURLException {
		final String scriptName = "/mockScriptName.groovy";
		final MudObject mudObject = (MudObject) ctrl.getMock();

		MockControl ctxCtrl = MockControl.createControl(ApplicationContext.class);

		final ApplicationContext mockApplicationContext = (ApplicationContext) ctxCtrl.getMock();
		mockApplicationContext.containsBean("beanId");
		ctxCtrl.setDefaultReturnValue(false);
		ctxCtrl.replay();

		MockControl regCtrl = MockClassControl.createControl(Registry.class);
		final Registry reg = (Registry) regCtrl.getMock();
		reg.getMudObject("beanId");
		regCtrl.setDefaultReturnValue(mudObject);
		
		regCtrl.replay();
		MudObjectAttendant attnd = new MudObjectAttendant() {
			@Override
			protected void loadDefinition(String scriptLocation) {
				assertEquals(scriptName, scriptLocation);
			}
			
			@Override
			public ApplicationContext getApplicationContext() {
				// TODO Auto-generated method stub
				return mockApplicationContext;
			}
            @Override
            public Registry getObjectRegistry() {
            	// TODO Auto-generated method stub
            	return reg;
            }

		};

		ObjectLocation location = new ObjectLocation();
		location.setDefinition(scriptName);
		location.setBeanId("beanId");
		MudObject myObj = attnd.load(location);

		assertEquals(mudObject.getClass(), myObj.getClass());
		ctxCtrl.verify();
		regCtrl.verify();
	}
	/*
	 * Test method for
	 * 'org.groovymud.object.registry.MudObjectAttendant.load(String, boolean)'
	 */
	public void testLoadAsBean() throws ResourceException, ScriptException, InstantiationException, IllegalAccessException, CompilationFailedException, FileNotFoundException, MalformedURLException {
		final String scriptName = "/mockScriptName.groovy";
		final MudObject mudObject = (MudObject) ctrl.getMock();

		MockControl ctxCtrl = MockControl.createControl(ApplicationContext.class);

		final ApplicationContext mockApplicationContext = (ApplicationContext) ctxCtrl.getMock();
		mockApplicationContext.containsBean("beanId");
		ctxCtrl.setReturnValue(false);
		mockApplicationContext.getBean("beanId");
		ctxCtrl.setDefaultReturnValue(mudObject);
		
		mockApplicationContext.containsBean("beanId");
		ctxCtrl.setReturnValue(true);
		ctxCtrl.replay();

		MockControl regCtrl = MockClassControl.createControl(Registry.class);
		final Registry reg = (Registry) regCtrl.getMock();
		reg.getMudObject("beanId");
		regCtrl.setDefaultReturnValue(null);
		
		regCtrl.replay();
		MudObjectAttendant attnd = new MudObjectAttendant() {
			@Override
			protected void loadDefinition(String scriptLocation) {
				assertEquals(scriptName, scriptLocation);
			}
			
			@Override
			public ApplicationContext getApplicationContext() {
				// TODO Auto-generated method stub
				return mockApplicationContext;
			}
            @Override
            public Registry getObjectRegistry() {
            	// TODO Auto-generated method stub
            	return reg;
            }

		};

		ObjectLocation location = new ObjectLocation();
		location.setDefinition(scriptName);
		location.setBeanId("beanId");
		MudObject myObj = attnd.load(location);

		assertEquals(mudObject.getClass(), myObj.getClass());
		ctxCtrl.verify();
		regCtrl.verify();
	}
	/*
	 * Test method for
	 * 'org.groovymud.object.registry.MudObjectAttendant.loadPlayerData(String)'
	 */
	public void testLoadPlayerData() throws CompilationFailedException, FileNotFoundException, ResourceException, ScriptException {
		final GroovyClassLoader mockClassLoader = new GroovyClassLoader() {

		};
		MockControl playerCtrl = MockControl.createControl(Player.class);
		final Player player = (Player) playerCtrl.getMock();
		final InputStream inputStream = new ByteArrayInputStream(new byte[] { '<', 'x', 'm', 'l', '/', '>' });

		final XStream mockXStream = new XStream() {

			@Override
			public Object fromXML(InputStream input) {
				// TODO Auto-generated method stub
				assertEquals(inputStream, input);
				methodCalled1 = true;
				return player;
			}
		};
		MockControl scriptCtrl = MockClassControl.createControl(GroovyScriptEngine.class, new Class[] { String.class }, new String[] { new String("file://") });
		final GroovyScriptEngine eng = (GroovyScriptEngine) scriptCtrl.getMock();
		eng.getParentClassLoader();
		scriptCtrl.setDefaultReturnValue(null);
		eng.loadScriptByName("player.Impl");
		scriptCtrl.setReturnValue(player.getClass());
		scriptCtrl.replay();
		MudObjectAttendant attend = new MudObjectAttendant() {

			protected InputStream createFileInputStream(File playerFile) throws FileNotFoundException {
				return inputStream;
			}

			@Override
			public ObjectLocation getPlayerImpl() {
				ObjectLocation loc = new ObjectLocation();
				loc.setBeanId("player.impl");
				loc.setDefinition("loc/def");
				return loc;
			}

			@Override
			protected File createFile(String username) {
				// TODO Auto-generated method stub
				return new File(username) {

					@Override
					public boolean exists() {
						// TODO Auto-generated method stub
						return true;
					}
				};
			}

			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return mockXStream;
			}

			

			

			@Override
			public GroovyScriptEngine getGroovyScriptEngine() {
				// TODO Auto-generated method stub
				return eng;
			}

		
		};

		attend.loadPlayerData("player");

		assertTrue(methodCalled1);
		assertTrue(methodCalled2);
	}

	public void testCreateNewPlayer() throws CompilationFailedException, FileNotFoundException, InstantiationException {
		String username = "wombat";
		String upperuname = username.substring(0, 1).toUpperCase() + username.substring(1);
		String password = "x";
		MockControl mockCtrl = MockControl.createControl(Player.class);
		final Player mockPlayer = (Player) mockCtrl.getMock();
		mockPlayer.setName(username);
		mockCtrl.setVoidCallable();

		mockPlayer.setName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.setName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.getName();
		mockCtrl.setDefaultReturnValue(upperuname);
		mockPlayer.addShortName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.addShortName(upperuname);
		mockCtrl.setVoidCallable();
		mockPlayer.initialise();
		mockCtrl.setVoidCallable();
		MudObjectAttendant attnd = new MudObjectAttendant() {

			@Override
			public MudObject load(ObjectLocation loc) {
				return mockPlayer;
			}

		
			@Override
			public XStream getXStream() {
				// TODO Auto-generated method stub
				return null;
			}

		
		};

		attnd.createNewPlayer(username);
		assertTrue(methodCalled);
	}

}
