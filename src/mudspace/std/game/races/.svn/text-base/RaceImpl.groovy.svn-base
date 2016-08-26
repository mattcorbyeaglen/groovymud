package std.game.races;

import java.util.HashSet
import java.util.List
import java.util.Set

import std.game.objects.bodyparts.BodyPart
import org.groovymud.object.alive.Alive
import org.groovymud.object.MudObject
import std.game.objects.alive.MOBImpl

import org.groovymud.object.registry.InventoryHandler

import org.groovymud.utils.MessengerUtils
import org.groovymud.utils.WordUtils

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
 

public class RaceImpl {
	
	static final int STRENGTH_CAPACITY_MULTIPLIER = 100;
	
	String race
	Map bodyParts
	Statistics baseStats
	boolean agressive
	def raceCommands

	public Statistics getStatistics() {
		Statistics moddedStats = new Statistics(baseStats);
		
		for (BodyPart part : getBodyParts()) {
			part.modifyStatistics(moddedStats);
		}
		
		return moddedStats;
	}
	
	public void doWear(Alive wearer, def item) {
		if (item.canWear(this)) {
			Set bodyParts = getBodyPartsByName(item.getBodyPartNameFor());
			for (BodyPart part : bodyParts) {
				if (addItemToBodyPart(item, part)) {
					sendMessage(wearer, "wear", item);
					return;
				}
			}
			sendMessageToPlayer(wearer, "You have nowhere to wear that.");
		} else {
			sendMessageToPlayer(wearer, "You cannot wear that.");
		}
	}
	
	protected void sendMessageToPlayer(Alive wearer, String string) {
		MessengerUtils.sendMessageToPlayer(wearer, string);
	}
	
	protected void sendMessage(Alive subject, String action, MudObject mudObject) {
		String name = (mudObject.requiresArticle() ? WordUtils.affixIndefiniteArticle(mudObject.getName()) : mudObject.getName());
		String roomMessage = WordUtils.affixDefiniteArticle(subject) + " " + WordUtils.pluralize(action) + " " + name;
		String sourceMessage = "You ${action} ${name}";
		MessengerUtils.sendMessageToRoom(subject, sourceMessage, roomMessage);
	}
	
	protected boolean addItemToBodyPart(def item, BodyPart part) {
		if (part.checkCanAddItem(item)) {
			part.addItem(item);
			return true;
		}
		return false;
	}
	
	public void doRemove(Alive wearer, def item) {
		// get from any inventory collection
		Set bodyParts = getBodyParts();
		for (BodyPart part : bodyParts) {
			// find first available slot and remove it
			MudObject remove = part.removeItem(item);
			if (remove != null) {
				wearer.getInventoryHandler().addMudObject((MudObject) remove);
				sendMessage(wearer, "remove", item);
				return;
			}
		}
		sendMessageToPlayer(wearer, "Nothing to remove.");
	}
	
	public void doHold(Alive holdee, def item) {
		if (item.canHold(this)) {
			Set bodyParts = getBodyParts();
			for (BodyPart part : bodyParts) {
				// find first available slot and hold it there
				if (part.holdItem(item)) {
					sendMessage(holdee, "hold", item);
					return;
				}
			}
			sendMessageToPlayer(holdee, "You cannot hold that because you are holding something else. Try lowering it?");
		} else {
			sendMessageToPlayer(holdee, "You cannot hold that.");
		}
	}
	
	public void doLower(Alive holder, def item) {
		Set bodyParts = getBodyParts();
		for (BodyPart part : bodyParts) {
			if (part.getHeldItem().equals(item)) {
				holder.getInventoryHandler().addMudObject(part.lowerItem(item));
				sendMessage(holder, "lower", item);
				return;
			}
		}
		sendMessageToPlayer(holder, "You cannot lower that.");
	}
	
	public Set getAttacks() {
		Set attacks = new HashSet();
		for (BodyPart part : getBodyParts()) {
			attacks.addAll(part.getAttacks());
		}
		return attacks;
	}
	
	/**
	 * returns all worn items. not backed by the inventory
	 * 
	 * @return
	 */
	public InventoryHandler getWearingInventory() {
		InventoryHandler allWorn = new InventoryHandler();
		for (BodyPart part : getBodyParts()) {
			allWorn.addAll(part.getInventoryHandler());
		}
		return allWorn;
	}
	
	/**
	 * returns all held items. not backed by the inventory
	 * 
	 * @return
	 */
	public InventoryHandler getHoldingInventory() {
		InventoryHandler allHeld = new InventoryHandler();
		for (BodyPart part : getBodyParts()) {
			allHeld.addAll(part.getHeldInventory());
		}
		return allHeld;
	}
	
	public Set getDefences() {
		Set defences = new HashSet();
		for (BodyPart parts : getBodyParts()) {
			defences.addAll(parts.getDefences());
		}
		return defences;
	}
}
