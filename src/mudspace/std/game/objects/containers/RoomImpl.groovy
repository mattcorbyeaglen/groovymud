package std.game.objects.containers

import std.game.behaviours.*
import org.groovymud.object.MudObjectimport org.groovymud.engine.event.EventScopeimport org.groovymud.object.registry.InventoryHandlerimport org.groovymud.object.views.Viewimport org.groovymud.object.alive.Alive
import org.groovymud.object.room.Room
import org.groovymud.object.room.Exit
import org.groovymud.object.Container
import org.groovymud.engine.event.IScopedEvent

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
 
class RoomImpl extends ContainerImpl implements Room{
	
	def calendar
	InventoryHandler exitInventory;
	View view
	/**
	 * in a room, the command is done on the contents first
	 * then in the player's content
	 */
	boolean doCommand(Alive player, String name, String args) {
		boolean done = super.doCommand(player, name, args)
		if(!done && this != player){
			done = player.doCommand(player, name, args)
		}
		return done
	}
	boolean checkCanAddItem(MudObject object) {
		return true;
	}
	
	MudObject removeExit(Exit object) {
		return removeItemFromInventoryHandler(object, getExitInventory());
	}
	
	void addExit(Exit exit) {
		addItemToInventoryHandler(exit, getExitInventory());
	}
	
	Exit getExit(String direction) {
		return (Exit) getExitInventory().getMudObject(direction);
	}
	
	Set getExits(){
		return getExitInventory().getMudObjects()
	}
	
	void setExits(Set exits) {
		getExitInventory().clear();
		exits.each{ exit -> addExit(exit) }
	}

	Set getMudObjects() {
		Set objs = [] 
		objs.addAll(super.getMudObjects())
		objs.addAll(getExits())
		return objs
	}
}
