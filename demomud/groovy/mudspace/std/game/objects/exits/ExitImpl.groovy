/**
 * Copyright 2008 Matthew Corby-Eaglen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package std.game.objects.exits

import org.groovymud.object.room.Exit
import org.groovymud.object.ObjectLocation
import org.groovymud.object.views.View
import org.groovymud.shell.command.CommandInterpreter
import org.groovymud.object.alive.Alive
import org.groovymud.engine.event.ScopedEvent
import org.groovymud.engine.event.system.MovementEvent
import org.groovymud.object.room.Room
import org.groovymud.object.room.Room
import org.groovymud.engine.event.messages.MessageEvent
import std.game.objects.exits.events.ArrivalEvent
import std.game.objects.GroovyMudObject
import org.groovymud.engine.event.EventScope
import org.groovymud.engine.event.HeartBeatListener
/**
 * @author corbym
 *
 */
class ExitImpl extends GroovyMudObject implements Exit{
	
	String direction;
	String arrivalDirection;
	
	ObjectLocation destination;
	
	transient View view
	
	void go(object) {
		def event = new MovementEvent(object.currentContainer, destination, object as Alive, this)
		object.fireEvent(event);
		
	}
	
	void onMudEvent(ScopedEvent event) {
		super.onMudEvent(event)
		if (event instanceof MovementEvent) {
			MovementEvent movement = (MovementEvent) event;
			if (movement.getSourceExit() == this) {
				def movingObject = movement.getMovingObject();
				def foundRoom = movement.getFoundRoom();
				doMove(foundRoom, movingObject);
			}
		}
	}
	
	void doMove(def foundRoom, def movingObject) {
		if (foundRoom != null) {
			MessageEvent event = createMessageEvent();
			event.setScope(EventScope.ROOM_SCOPE);
			event.setSource(movingObject);
			event.setSourceMessage("You go ${direction}.");
			event.setScopeMessage(movingObject.getDepartureMessage(this));
			movingObject.fireEvent(event);
			
			movingObject.setContainerLocation(new ObjectLocation(getDestination()));
			
			foundRoom.addMudObject(movingObject)

			def otherExit = foundRoom.getExit(arrivalDirection)			
			ArrivalEvent arriveEvent = createArrivalEvent(otherExit)
			arriveEvent.setSource(movingObject)
			arriveEvent.setSourceMessage(null)		
			arriveEvent.setScopeMessage(movingObject.getArrivalMessage(otherExit))
			movingObject.fireEvent(arriveEvent)
						
			interpreter.doCommand("look", null, movingObject);
	
		}
	}
	
	MessageEvent createMessageEvent() {
		return new MessageEvent(scope: EventScope.ROOM_SCOPE);
	}

	ArrivalEvent createArrivalEvent(otherExit){
		new ArrivalEvent(scope: EventScope.ROOM_SCOPE, direction: otherExit)
	}
	
}
