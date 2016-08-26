package org.groovymud.object.registry;

import grails.spring.BeanBuilder;
import groovy.lang.Binding;
import groovy.lang.GroovyObjectSupport;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.engine.JMudEngine;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.ObjectLocation;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Room;
import org.groovymud.shell.command.CommandInterpreter;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import org.groovymud.shell.telnetd.LoggingExtendedTerminalIO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thoughtworks.xstream.XStream;

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
 * The MudObjectAttendant is responsible for loading, saving, registering and
 * configuring all mud objects in the game.
 * 
 * The object can have an id
 * 
 */
public class MudObjectAttendant implements ApplicationContextAware {

	private ObjectLocation theVoid;

	private ObjectLocation playerImpl;

	private JMudEngine mudEngine;

	private GroovyScriptEngine groovyScriptEngine;

	private String mudSpacePlayerLocation;

	private static final Logger logger = Logger.getLogger(MudObjectAttendant.class);

	private Registry objectRegistry;

	private XStream xStream;

	private ApplicationContext applicationContext;

	/**
	 * this method loads all the script files that contain beans into the
	 * context when the attendand is loaded
	 * 
	 * filename for the script must end with "MudBeans.groovy"
	 * 
	 * e.g. /mudspace/my/weapons/WeaponMudBeans.groovy
	 */
	public void initialize() {
		BeanBuilder builder = new BeanBuilder(getApplicationContext(), getGroovyScriptEngine().getGroovyClassLoader());
		try {
			builder.loadBeans("classpath*:**/*MudBeans.groovy");
		} catch (IOException e) {
			logger.error(e, e);
		}
		setApplicationContext(builder.createApplicationContext());
	}

	/**
	 * loads a mudobject object and initialise it using a location object
	 * 
	 * if the object isnt in the context, the definition script is loaded and
	 * then the context is checked again. if the context doesn't contain the
	 * object then the registry is checked
	 * 
	 * scripts that require the attendand to use a new context should create the
	 * context with a beanbuilder and set the attendants context.
	 * 
	 * make sure you set the parent context first on the builder
	 * @param ObjectLocation
	 *            location - representing where the object can be found in the
	 *            spring container
	 */
	public MudObject load(ObjectLocation location) {
		MudObject obj = null;
		if (!getApplicationContext().containsBean(location.getBeanId())) {
			loadDefinition(location.getDefinition());
		}
		String beanId = location.getBeanId();
		if (!getApplicationContext().containsBean(location.getBeanId())) {
			// check the registry
			obj = getObjectRegistry().getMudObject(beanId);
		} else {
			// otherwise check the application context
			obj = (MudObject) getApplicationContext().getBean(beanId);
		}

		return obj;
	}

	/**
	 * loads the groovy object definition file
	 * 
	 * objects could be instantiated straight into the registry, or can be added
	 * to the context for future reference
	 * 
	 * provides the object definition script with three bindings:
	 * 
	 * builder - a bean builder parentContext - the spring context, in case we
	 * want to pass it on registry - in case we want to add the objects directly
	 * to the mud
	 * 
	 * @param scriptLocation
	 */
	protected void loadDefinition(String scriptLocation) {
		Binding binding = new Binding();

		binding.setVariable("parentContext", getApplicationContext());
		binding.setVariable("registry", getObjectRegistry());
		binding.setVariable("attendant", this);
		binding.setVariable("gse", getGroovyScriptEngine());
		try {
			getGroovyScriptEngine().run(scriptLocation, binding);
		} catch (ResourceException e) {
			logger.error(e, e);
		} catch (ScriptException e) {
			logger.error(e, e);
		}
	}

	public Player loadPlayerData(String username) throws CompilationFailedException, ResourceException, ScriptException, FileNotFoundException {
		Player player = null;
		XStream xstream = getXStream();
		load(playerImpl);
		File playerFile = createFile(username);
		if (playerFile.exists()) {
			InputStream xmlIn = createFileInputStream(playerFile);
			player = (Player) xstream.fromXML(xmlIn);
			getObjectRegistry().register(player);
		}
		return player;
	}

	protected InputStream createFileInputStream(File playerFile) throws FileNotFoundException {
		return new FileInputStream(playerFile);
	}

	protected File createFile(String username) {
		return new File(mudSpacePlayerLocation + username + ".xml");
	}

	public void movePlayerToLocation(Player player) throws InstantiationException, FileNotFoundException, CompilationFailedException {
		Container room;
		ObjectLocation location = player.getContainerLocation();

		room = (Container) getObjectRegistry().getMudObject(location.getBeanId());
		if (room == null) {
			room = (Container) load(location);
		}
		if (room == null) {
			throw new InstantiationException("room was null!");
		}
		room.addMudObject(player);
	}

	public void movePlayerToVoid(Player player) throws IOException, FileNotFoundException {
		try {
			player.setContainerLocation(getTheVoid());
			movePlayerToLocation(player);
		} catch (CompilationFailedException e) {
			logger.error(e, e);
		} catch (InstantiationException e) {
			logger.error(e, e);
		}
	}

	public Player createNewPlayer(String username) throws InstantiationException, FileNotFoundException, CompilationFailedException {
		Player player = (Player) load(getPlayerImpl());
		String upperName = username.substring(0, 1).toUpperCase() + username.substring(1);

		player.setName(upperName);
		player.addShortName(upperName);
		player.addShortName(username);
		getObjectRegistry().register(player);

		return player;
	}

	public void savePlayerData(Player player) {
		XStream xstream = getXStream();
		try {
			xstream.omitField(GroovyObjectSupport.class, "metaClass");
			xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
			FileOutputStream out = new FileOutputStream(createFile(player.getPlayerCredentials().getUsername()));
			xstream.toXML(player, out);

		} catch (FileNotFoundException e) {
			try {
				player.getTerminalOutput().writeln(e.getMessage());
			} catch (IOException e1) {
				logger.error(e1, e1);
			}

			logger.error(e, e);
		}
	}

	public Registry getObjectRegistry() {
		return objectRegistry;
	}

	public void setObjectRegistry(Registry objectRegistry) {
		this.objectRegistry = objectRegistry;
	}

	public String getMudSpacePlayerLocation() {
		return mudSpacePlayerLocation;
	}

	public void setMudSpacePlayerLocation(String mudSpacePlayerLocation) {
		this.mudSpacePlayerLocation = mudSpacePlayerLocation.trim();
	}

	public GroovyScriptEngine getGroovyScriptEngine() {
		return groovyScriptEngine;
	}

	public void setGroovyScriptEngine(GroovyScriptEngine groovyScriptEngine) {
		this.groovyScriptEngine = groovyScriptEngine;
	}

	public JMudEngine getMudEngine() {
		return mudEngine;
	}

	public void setMudEngine(JMudEngine mudEngine) {
		this.mudEngine = mudEngine;
	}

	public ObjectLocation getTheVoid() {
		return theVoid;
	}

	public void setTheVoid(ObjectLocation theVoid) {
		this.theVoid = theVoid;
	}

	public ObjectLocation getPlayerImpl() {
		return playerImpl;
	}

	public void setPlayerImpl(ObjectLocation playerImpl) {
		this.playerImpl = playerImpl;
	}

	public XStream getXStream() {
		return xStream;
	}

	public void setXStream(XStream stream) {
		xStream = stream;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
