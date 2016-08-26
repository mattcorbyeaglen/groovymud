package org.groovymud.engine.event.system;

import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.object.Container;
import org.groovymud.object.ObjectLocation;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.room.Exit;

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
public class MovementEvent implements IScopedEvent {

	private Container source;
	private ObjectLocation roomLocation;
	private Alive movingObject;
	private Exit sourceExit;
	private Container foundRoom;
	private EventScope scope;

	public MovementEvent(Container sourceRoom, ObjectLocation location, Alive movingObject, Exit exit) {
		super();
		this.sourceExit = exit;
		this.source = sourceRoom;
		this.movingObject = movingObject;
		this.roomLocation = location;
		scope = EventScope.GLOBAL_SCOPE;
	}

	public EventScope getScope() {
		return scope;
	}

	public void setScope(EventScope scope) {
		this.scope = scope;
	}

	public void setSource(Object object) {
		this.source = (Container) object;
	}

	public ObjectLocation getRoomLocation() {
		return roomLocation;
	}

	public void setRoomLocation(ObjectLocation roomLocation) {
		this.roomLocation = roomLocation;
	}

	public Object getSource() {
		return source;
	}

	public Exit getSourceExit() {
		return sourceExit;
	}

	public void setSourceExit(Exit sourceExit) {
		this.sourceExit = sourceExit;
	}

	public Alive getMovingObject() {
		return movingObject;
	}

	public void setMovingObject(Alive movingObject) {
		this.movingObject = movingObject;
	}

	public Container getFoundRoom() {
		return foundRoom;
	}

	public void setFoundRoom(Container foundRoom) {
		this.foundRoom = foundRoom;
	}

}
