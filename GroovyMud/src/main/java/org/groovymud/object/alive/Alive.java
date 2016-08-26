package org.groovymud.object.alive;

import org.groovymud.engine.event.HeartBeatListener;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.room.Exit;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;

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
 * 
 * An Alive object has a terminalio object and can move about.
 * It is also a container, has a heartbeat so it can do stuff.
 */
public interface Alive extends MudObject, Container {

	public ExtendedTerminalIO getTerminalOutput();

	public void setTerminalOutput(ExtendedTerminalIO terminalIO);

	public void addStatus(String name);

	public void removeStatus(String name);

	public String getArrivalMessage(Exit direction);

	public String getDepartureMessage(Exit direction);

}
