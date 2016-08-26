package std.game.objects.exits

import std.game.behaviours.Behaviour;
import std.game.behaviours.ChanceBehaviour

import org.groovymud.object.ObjectLocation
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

import org.groovymud.object.alive.Alive
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.engine.event.ScopedEvent

import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import static org.groovymud.utils.MessengerUtils.sendMessageToPlayer

import std.game.objects.exits.DoorEvent


/**
 * an example door: can be opened and closed.
 * 
 * fires a DoorEvent which can be used by other objects to detect an event
 * 
 * @author corbym
 *
 */
class DoorImpl extends ExitImpl {
	MudObjectAttendant objectAttendant
	volatile boolean open = false
	DoorImpl otherDoor = null;
	
	def emotes		
	void initialise(){
		def chanceSwing = new ChanceBehaviour(chance: 0..10, numDice: 1, sides: 100);
		emotes = [(0..5) : "The ${getName()} creaks a little.",
				(6..10): "The ${getName()} swings on its hinges, as if a slight wind has caught it."];
		
		chanceSwing.behaviour = [doBehaviour : {obj, arg ->
			def roll = 1.d100.sum
			emotes.keys.each(){
				if(it.contains(roll)){
					sendMessageToRoom obj, "", delegate.emotes[roll]
				}
			}
		} 
		] as Behaviour
		shortNames << "$name door".toString()
		heartbeatBehaviours << chanceSwing
		super.initialise()
	}

	def open(obj, String args){		
		boolean consume = shortNames.contains(args)
		if(!open && consume){
			sendMessageToRoom(obj, "You open the ${direction} door.", "${obj.name} opens the ${direction} door.")			
			doorEvent(obj, true)
		}else if(consume){
			sendMessageToPlayer(obj, "You try to open the ${direction} door but it is already open.")
		}        
		return consume
	}
	
	private def doorEvent(obj, boolean op){
		this.setOpen op
		if(otherDoor == null){
			DoorImpl od = findOtherDoor(arrivalDirection, destination)
			od.setOpen op
			if(!od.otherDoor){
				od.setOtherDoor this
				this.setOtherDoor od
			}			
			od = null
		}
		sendMessageToRoom(otherDoor, "", "The ${otherDoor.direction} door ${op ? 'opens' : 'closes'}.")
		
		fireDoorEvent(obj)
	}
	
	def close(obj, String args){
		boolean consume = shortNames.contains(args)
		if(open && consume){
			sendMessageToRoom(obj, "You close the ${direction} door.", "${obj.name} closes the ${direction} door.")
			doorEvent(obj, false)
		}else if(consume){
			sendMessageToPlayer(obj, "You try to close the ${direction} door but it is already closed.")
		}
		return consume
	}
	
	
	DoorImpl findOtherDoor(String direction, ObjectLocation location){
		def otherRoom = objectAttendant.findOrClone(location)
		def od = null
		if(otherRoom != null){
			def otherExit = otherRoom.getExit(direction)
			if(otherExit instanceof DoorImpl){
				od = otherExit				
			}
		}
		return od
	}
	
	void fireDoorEvent(player){
		DoorEvent openEvent = new DoorEvent(source:this, targetRoomLocation: destination, targetDirection: arrivalDirection, open: this.open)
		fireEvent(openEvent)
	}
	
	
	void go(Alive player) {
		if(getOpen()){
			super.go(player)
			if(otherDoor == null){
				otherDoor = findOtherDoor(arrivalDirection, destination)
			}
		}else{
			sendMessageToRoom(player, "You try to go ${getDirection()} but the door is closed!", "${player.getName()} tries to go ${getDirection()} but the door is closed!")            
		}
	}
}
