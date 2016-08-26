package std.game.objects.exits

import std.game.behaviours.ChanceBehaviourimport org.groovymud.engine.event.EventScopeimport org.groovymud.object.ObjectLocationimport org.groovymud.object.registry.Registryimport org.groovymud.object.registry.MudObjectAttendantimport org.groovymud.engine.event.HeartBeatListenerimport std.game.behaviours.Behaviour/* Copyright 2008 Matthew Corby-Eaglen
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
import org.groovymud.object.MudObject
import org.groovymud.object.room.Room
import org.groovymud.engine.event.IScopedEvent
import sun.security.action.GetLongAction

import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import static org.groovymud.utils.MessengerUtils.sendMessageToPlayer
import org.groovymud.utils.Dice

import std.game.objects.exits.DoorEvent


/**
 * an example door: can be opened and closed.
 * 
 * fires a DoorEvent which can be used by other objects to detect an event
 * 
 * @author corbym
 *
 */
class DoorImpl extends ExitImpl implements HeartBeatListener{
	def objectAttendant
	boolean open
	def otherDoor = null;
	List behaviours
	def emotes		
	void initialise(){
		open = false;
		def chanceSwing = new ChanceBehaviour(chance: 0..10, numDice: 1, sides: 100);
		emotes = [(0..5) : "The ${getName()} creaks a little.",
						(6..10): "The ${getName()} swings on its hinges, as if a slight wind has caught it."];
		
		chanceSwing.behaviour = [doBehaviour : {obj ->  
				sendMessageToRoom obj, "", emotes[1.d100.sum]
			} 
		] as Behaviour
		shortNames << "$name door".toString()
		behaviours = [chanceSwing]
		println "objectAttendant: $objectAttendant"
		super.initialise()
	}
	
	def open(Alive obj, String args){		
		boolean consume = shortNames.contains(args)
		if(!open && consume){
			sendMessageToRoom(obj, "You open the ${getDirection()} door.", "${obj.name} opens the ${getDirection()} door.")			
			doorEvent(obj, true)
		}else if(consume){
			sendMessageToPlayer(obj, "You try to open the ${getDirection()} door but it is already open.")
		}        
		return consume
	}
	
	private def doorEvent(obj, boolean open){
		this.open = open
		if(otherDoor == null){
			otherDoor = findOtherDoor(arrivalDirection, destination)
		}
		if(otherDoor != null){
			otherDoor.open = open
		}       
		fireDoorEvent(obj)
	}
	
	def close(Alive obj, String args){
		boolean consume = shortNames.contains(args)
		if(open && consume){
			sendMessageToRoom(obj, "You close the ${getDirection()} door.", "${obj.name} closes the ${getDirection()} door.")
			doorEvent(obj, false)
		}else if(consume){
			sendMessageToPlayer(obj, "You try to close the ${getDirection()} door but it is already closed.")
		}
		return consume
	}

	
	def findOtherDoor(String direction, ObjectLocation location){
		def otherRoom = objectAttendant.load(location)
		if(otherRoom != null){
			def otherExit = otherRoom.getExit(direction)
			if(otherExit instanceof DoorImpl){
				otherDoor = otherExit
				otherDoor.open  = this.open
			}
		}
		return otherDoor
	}
	
	void fireDoorEvent(player){
		DoorEvent openEvent = new DoorEvent(source:this, targetRoomLocation: destination, targetDirection: arrivalDirection, open: this.open)
		fireEvent(openEvent)
	}
	
	void heartBeat() {
		//println "baddum door"
		if(open){          
			behaviours?.each{ it ->
				it.doBehaviour(this);
			}
		}        
	}
	
	void go(Alive player) {
		if(isOpen()){
			super.go(player)
			if(otherDoor == null){
				otherDoor = findOtherDoor(arrivalDirection, destination)
			}
		}else{
			sendMessageToRoom(player, "You try to go ${getDirection()} but the door is closed!", "${player.getName()} tries to go ${getDirection()} but the door is closed!")            
		}
	}
}
