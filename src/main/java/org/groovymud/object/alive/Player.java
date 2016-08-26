package org.groovymud.object.alive;

import javax.security.auth.Subject;

import org.groovymud.object.ObjectLocation;
import org.groovymud.shell.security.PlayerCredentials;

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
 * The standard player interface. Players have an subject object
 * used by the JAAS system and also credentials like username and password
 * Players also have a recorder player location, so when they are persisted
 * their position is persisted with them.
 * 
 * @author matt
 */
public interface Player extends Alive {

	public Subject getSubject();

	public void setSubject(Subject subject);

	public PlayerCredentials getPlayerCredentials();

	public void setPlayerCredentials(PlayerCredentials credentials);

	// location that the player is in (the room)
	public ObjectLocation getObjectLocation();
}
