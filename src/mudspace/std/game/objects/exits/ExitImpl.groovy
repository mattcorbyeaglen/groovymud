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

import org.groovymud.object.room.Exitimport org.groovymud.object.ObjectLocationimport org.groovymud.object.views.Viewimport org.groovymud.shell.command.CommandInterpreterimport org.groovymud.object.alive.Aliveimport org.groovymud.engine.event.IScopedEventimport org.groovymud.engine.event.system.MovementEventimport org.groovymud.object.room.Roomimport org.groovymud.object.room.Roomimport org.groovymud.engine.event.messages.MessageEventimport org.groovymud.engine.event.observer.Observableimport std.game.objects.MudObjectImpl
import org.groovymud.engine.event.EventScopeimport org.groovymud.engine.event.HeartBeatListener
/**
 * @author corbym
 *
 */
class ExitImpl extends MudObjectImpl implements Exit{
	
	String direction;
	String arrivalDirection;
	
	ObjectLocation destination;
	
	transient CommandInterpreter commandInterpreter;
	transient View view
	
	void go(Alive object) {
		MovementEvent event = new MovementEvent(object.getCurrentContainer(), destination, object, this)
		object.fireEvent(event);
	}
	
	void doEvent(IScopedEvent event) {
		super.doEvent(event)
		if (event instanceof MovementEvent) {
			MovementEvent movement = (MovementEvent) event;
			if (movement.getSourceExit().equals(this)) {
				Room sourceRoom = (Room) movement.getSource();
				Room foundRoom = (Room) movement.getFoundRoom();
				Alive movingObject = movement.getMovingObject();
				doMove(sourceRoom, foundRoom, movingObject);
			}
		}
	}
	
	void doMove(def sourceRoom, def foundRoom, def movingObject) {
		if (foundRoom != null) {
			MessageEvent event = createMessageEvent();
			event.setScope(EventScope.ROOM_SCOPE);
			event.setSource(movingObject);
			event.setSourceMessage("You go ${direction}.");
			event.setScopeMessage(movingObject.getDepartureMessage(this));
			movingObject.fireEvent(event);
			
			movingObject.setContainerLocation(new ObjectLocation(getDestination()));
			
			foundRoom.addMudObject(movingObject);
			
			MessageEvent arriveEvent = createMessageEvent();
			arriveEvent.setScope(EventScope.ROOM_SCOPE);
			arriveEvent.setSource(movingObject);
			arriveEvent.setSourceMessage(null);
			arriveEvent.setScopeMessage(movingObject.getArrivalMessage(this));
			movingObject.fireEvent(arriveEvent);
			
			
			getCommandInterpreter().doCommand("look", null, movingObject);
	
		}
	}
	
	MessageEvent createMessageEvent() {
		return new MessageEvent(EventScope.ROOM_SCOPE);
	}


	
}
