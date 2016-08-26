package org.groovymud.shell.command;

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
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.security.auth.Subject;

import org.apache.log4j.Logger;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Player;
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.object.registry.Registry;
import org.groovymud.shell.security.MudPermission;

public class CommandInterpreter {

	private GroovyScriptEngine groovyScriptEngine;

	private static final Logger logger = Logger.getLogger(CommandInterpreter.class);

	private Registry objectRegistry;
	private MudObjectAttendant mudObjectAttendant;
	private String mudSpace;
	private String scriptSpace;

	private Map<String, String> commandAliases;

	class ArgHolder {

		String command;
		String args;

		public void parse(String nextCommand, MudObject object) {
			nextCommand = resolveAlias(nextCommand, object);
			if (nextCommand.indexOf(' ') != -1) {
				// fill the args string
				args = nextCommand.substring(nextCommand.indexOf(' ') + 1);
				// return the next command as the first arg
				command = nextCommand.substring(0, nextCommand.indexOf(' '));
			} else {
				// or we return the resolved command
				command = nextCommand;
			}
			command = resolveAlias(command, object);
		}
	}

	public void doShellCommand(String command, final Player player) {
		if (command == null) {
			throw new IllegalArgumentException("command cannot be null");
		}

		StringTokenizer all = new StringTokenizer(command, ";");
		while (all.hasMoreTokens()) {
			String nextCommand = all.nextToken();

			final ArgHolder argHolder = new ArgHolder();

			argHolder.parse(nextCommand, player);

			Container container = player.getCurrentContainer();
			boolean commandRun = false;
			MudObject containerMO = (MudObject) container;
			if (containerMO != null) {
				// the object means to do the action with the container, it
				// holds a list of
				// local commands that need to be run.
				commandRun = containerMO.doCommand(player, argHolder.command, argHolder.args);
			}

			if (!commandRun) {
				// run through a privileged action so we don't access stuff we
				// shouldn't
				Subject subject = player.getSubject();
				Subject.doAsPrivileged(subject, new PrivilegedAction<Object>() {

					public Object run() {
						boolean error = false;
						try {
							Class<?> clz = getGroovyScriptEngine().loadScriptByName(argHolder.command);

							MudPermission permission = new MudPermission(clz.getName());
							AccessController.checkPermission(permission);
							return doCommand(argHolder.command, argHolder.args, player);
						} catch (Exception e) {
							logger.error(e, e);
							error = true;
						} finally {
							if (error) {
								try {
									player.getTerminalOutput().writeln("You cannot do that.");
								} catch (IOException e) {
									logger.error(e, e);
								}
							}
						}
						return null;

					}
				}, null);
			}

		}
	}

	protected String resolveAlias(String nextCommand, MudObject object) {
		// check the player's list of aliases for commands
		// check the standard set of aliases for commands
		String alias = (String) getCommandAliases().get(nextCommand);
		return alias == null ? nextCommand : alias;
	}

	public Object doCommand(final String command, String argsAsString, MudObject performingObject) throws ResourceException, ScriptException {

		if (getGroovyScriptEngine() != null) {
			List<String> argsAsList = null;

			if (argsAsString != null) {
				argsAsList = Arrays.asList(argsAsString.split(" "));
			}

			Map<String, Object> bindingMap = new HashMap<String, Object>();
			bindingMap.put("mudSpace", getMudSpace());
			bindingMap.put("scriptSpace", getScriptSpace());
			bindingMap.put("source", performingObject);
			bindingMap.put("args", argsAsList);
			bindingMap.put("argstr", argsAsString);
			bindingMap.put("globalRegistry", objectRegistry);
			bindingMap.put("attendant", mudObjectAttendant);
			bindingMap.put("interpreter", this);
			Binding binding = new Binding(bindingMap);

			return getGroovyScriptEngine().run(command + ".groovy", binding);

		}

		return null;
	}

	public Registry getObjectRegistry() {
		return objectRegistry;
	}

	public void setObjectRegistry(Registry objectRegistry) {
		this.objectRegistry = objectRegistry;
	}

	public GroovyScriptEngine getGroovyScriptEngine() {
		return groovyScriptEngine;
	}

	public void setGroovyScriptEngine(GroovyScriptEngine groovyScriptEngine) {
		this.groovyScriptEngine = groovyScriptEngine;
	}

	public Map<String, String> getCommandAliases() {
		return commandAliases;
	}

	public void setCommandAliases(Map<String, String> commandAliases) {
		this.commandAliases = new HashMap<String, String>(commandAliases);
	}

	public MudObjectAttendant getMudObjectAttendant() {
		return mudObjectAttendant;
	}

	public void setMudObjectAttendant(MudObjectAttendant mudObjectAttendant) {
		this.mudObjectAttendant = mudObjectAttendant;
	}

	public String getMudSpace() {
		return mudSpace;
	}

	public void setMudSpace(String mudSpace) {
		this.mudSpace = mudSpace;
	}

	public void setScriptSpace(String scriptSpace) {
		this.scriptSpace = scriptSpace;
	}

	public String getScriptSpace() {
		return scriptSpace;
	}

}
