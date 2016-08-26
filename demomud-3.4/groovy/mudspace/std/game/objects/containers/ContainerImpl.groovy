/**
 * 
 */
package std.game.objects.containers

import org.groovymud.object.MudObject
import std.game.objects.Weighted
import org.groovymud.engine.event.EventScope
import org.groovymud.object.registry.InventoryHandler
import org.groovymud.object.views.View
import org.groovymud.object.registry.Registry
import org.groovymud.object.registry.MudObjectAttendant
import org.groovymud.shell.command.CommandInterpreter
import org.groovymud.object.ObjectLocation
import org.groovymud.engine.event.observer.Observable
import org.groovymud.engine.event.ScopedEvent
import org.groovymud.object.Container
import std.game.objects.MudObjectImpl
import org.groovymud.engine.event.observer.Observer
import org.apache.log4j.Logger
import std.game.behaviours.Behaviour
import groovy.lang.MissingMethodException
import org.groovymud.object.alive.Player
import org.groovymud.object.alive.Alive
import org.groovymud.engine.event.MudEventListenerimport org.groovymud.engine.event.messages.MessageEventimport org.groovymud.utils.MessengerUtils/**
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
class ContainerImpl extends MudObjectImpl implements Container, Weighted, Observer{
	
	InventoryHandler inventoryHandler;	
	EventScope scope
	
	boolean checkCanAddItem(MudObject obj){
		if (obj instanceof std.game.objects.alive.MOBImpl) {
			return false;
		}
		//double currentFullness = calculateCurrentFullness();
		return true //(currentFullness + obj.getCapacityAlpha()) <= maxFullness;
	}
	/**
	 * calculates the "fullness" of the container based on a function of the
	 * weight
	 * 
	 * @return
	 */
	long calculateCurrentFullness() {
		long fullness = 0;
		def set = getMudObjects();
		
		set.each{ it -> fulness += it.getCapacityAlpha(); }
		
		return fullness;
	}
	/**
	 * containers do commands on their contents
	 * 
	 */
	boolean doCommand(Alive player, String name, String args){
		boolean done = super.doCommand(player, name, args)
		getMudObjects()?.each{ it->
			if(!done && !(it instanceof Alive)){
				if(it.respondsTo("${name}", Alive, String)){
					done = it."${name}"(player, args)
				}
			}
		}
		return done
	}
	
	void setItems(Set items) {
		items.each{object ->  addMudObject(object) }
	}
	
	void setItemInInventory(Set items, InventoryHandler inv) {
		for (MudObject object : items) {
			addItemToInventoryHandler(object, inv);
		}
	}
	
	void addMudObject(MudObject object) {
		addItemToInventoryHandler(object, getInventoryHandler());
	}
	/**
	 * adds an item to inventory handler
	 * remember to call checkCanAddItem BEFORE you call this
	 * 
	 *  @param object object ot add
	 *  @param handler inventory handler
	 */
	void addItemToInventoryHandler(MudObject object, InventoryHandler handler) {
		// remove it from its old container if it exists
		if (object.getCurrentContainer() != null) {
			Container oldContainer = object.getCurrentContainer()
			object.deleteObserver(oldContainer);
			oldContainer.removeMudObject(object);		
		}
		object.addObserver(this);
		object.setCurrentContainer(this);				
		handler.addMudObject(object);
	}
	
	
	MudObject removeMudObject(MudObject object) {
		return removeItemFromInventoryHandler(object, getInventoryHandler());
	}
	
	MudObject removeItemFromInventoryHandler(MudObject object, InventoryHandler handler) {
		if (object instanceof Observable) {
			object.deleteObserver(this);
		}
		return handler?.removeMudObject(object);
	}
	
	Set getMudObjects() {
		return getItemsFromInventoryHandler(getInventoryHandler());
	}
	
	Set getItemsFromInventoryHandler(InventoryHandler handler) {
		return handler?.getMudObjects();
	}
	
	/**
	 * 
	 * @returns map containing the name, hashset(object) mapping for the
	 *          inventory handler's contents
	 */
	Map getMudObjectsMap() {
		return getMudObjectsMapFromInventoryHandler(getInventoryHandler());
	}
	
	Map getMudObjectsMapFromInventoryHandler(InventoryHandler handler) {
		return handler.getMudObjectsMap();
	}
	
	Set getMudObjects(String key) {
		return getItemsFromInventoryHandler(key, getInventoryHandler());
	}
	
	Set getItemsFromInventoryHandler(String key, InventoryHandler handler) {
		return handler.getMudObjects(key);
	}
	
	def getMudObject(String name){
	    return inventoryHandler.getMudObject(name)
	}
	
	void update(Observable o, ScopedEvent event) {
		if (!event.getScope().isObjectInScope((Observer) this)) {
			setChanged();
			notifyObservers(event); // pass event upwards
		} else {			
			doEventInCollection(event, getMudObjects());
		}
	}
	
	void doEventInCollection(ScopedEvent event, collection) {
		collection.each(){ it ->			
			if(it instanceof MudEventListener){
				if(!event.isConsumed()) {
					it.onBeforeMudEvent(event);
					if(!event.isConsumed()){
						it.onMudEvent(event);
					}
					if(!event.isConsumed()){
						it.onAfterMudEvent(event);
					}    			
				}
			}
		}
	}
	
	double getCapacityAlpha() {
		return (weight + calculateCurrentFullness()) / 10;
	}
	

}
