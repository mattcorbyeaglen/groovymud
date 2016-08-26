package std.game.objects.bodyparts;
import std.game.objects.trappings.AbstractWeapon;
import std.game.objects.trappings.Holdable;
import std.game.objects.trappings.Wearable;


import org.groovymud.object.MudObject;

import std.game.races.Statistics
import std.game.objects.containers.ContainerImpl
import org.groovymud.object.registry.InventoryHandler;
/** * Copyright 2008 Matthew Corby-Eaglen *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not * use this file except in compliance with the License. You may obtain a copy of * the License at *  * http://www.apache.org/licenses/LICENSE-2.0 *  * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the * License for the specific language governing permissions and limitations under * the License. */ class BodyPart extends ContainerImpl {
	
	boolean canHold = false
	boolean holding = false
	
    Set defences; // defences are cumulative
	Set attacks; // attacks that you are not using but
	// still exist if unwielding items
	
	InventoryHandler linkedParts // for a pair of arms, legs, etc -
	// could be more than one linked!!
	InventoryHandler heldItem
		String partShortName
	
	public boolean checkCanAddItem(MudObject object) {
		if (object instanceof Wearable) {
			Wearable wearable = (Wearable) object;
			if (wearable.getNumberOfBodyPartsRequired() > 1 && linkedParts.size() < wearable.getNumberOfBodyPartsRequired() - 1) {
				return false;
			}
			// if the body part's names matched the items bodypartfor class
			// object then say yes
			if (wearable.getBodyPartNameFor().equals(getName())) {
				int containsAmount = getInventoryHandler().getMudObject(wearable.getBodyPartNameFor()).size();
				if (containsAmount < wearable.getMaxWearable()) {
					return superCheckCanAddItem(object);
				}
			}
		}
		return false;
	}
	
	public void addItem(MudObject object) {
		Wearable item = (Wearable) object;
		if (item.getNumberOfBodyPartsRequired() > 1) {
			HashSet<MudObject> linkedParts = getLinkedParts().getMudObjects();
			int amount = item.getNumberOfBodyPartsRequired();
			Iterator i = linkedParts.iterator();
			while (i.hasNext() && amount > 0) {
				BodyPart obj = (BodyPart) i.next();
				obj.addItem(object);
				amount--;
			}
		}
		super.addItem(object);
	}
	
	public MudObject removeItem(MudObject object) {
		HashSet<MudObject> linkedParts = getLinkedParts().getMudObjects();
		Iterator i = linkedParts.iterator();
		while (i.hasNext()) {
			BodyPart obj = (BodyPart) i.next();
			obj.removeItem(object);
		}
		return super.removeItem(object);
	}
	
	public boolean holdItem(Holdable item) {
		if (holding || !canHold) {
			return false;
		}
		
		heldItem.addMudObject((MudObject)item);
		holding = true;
		return holding; // make sure we return the old item, can only hold one
		// thing at a time in this part
	}
	
	public MudObject lowerItem(Holdable item) {
		item = (Holdable) heldItem.removeMudObject(item);
		holding = false;
		return item;
	}
	
	boolean superCheckCanAddItem(MudObject object) {
		return super.checkCanAddItem(object);
	}
	
	boolean canHold() {
		return canHold;
	}
	
	void setCanHold(boolean holdable) {
		this.canHold = holdable;
	}
	
	Set getDefences() {
		Set defences = this.defences;
		if (holding) {
			Holdable item = getHeldItem();
			defences.addAll(item.getDefences());
		}
		Set wearing = getInventoryHandler().getItemsOfClass(Wearable.class);
		for (MudObject obj : wearing) {
			Wearable wearable = (Wearable) obj;
			defences.addAll(wearable.getDefences());
		}
		return defences;
	}
	
	
	Set getAttacks() {
		if (holding) {
			Holdable item = getHeldItem();
			if (item instanceof AbstractWeapon) {
				AbstractWeapon weapon = (AbstractWeapon) item;
				return weapon.getAttacks();
			}
			// holding none weapons renders the limb useless
			return new HashSet();
		}
		return attacks;
	}
	
	Holdable getHeldItem() {
		return ((Holdable) heldItem.getMudObjects().toArray()[0]);
	}
	
	/**	 * defers modding of character stats to worn/held items	 * 	 * @param moddedStats	 */
	public void modifyStatistics(Statistics moddedStats) {
		if (holding) {
			Holdable item = getHeldItem();
			item.modifyStatistics(moddedStats);
		}
		for (MudObject obj : getInventoryHandler().getMudObjects()) {
			obj.modifyStatistics(moddedStats);
		}
	}
	
	public InventoryHandler getLinkedParts() {
		if (linkedParts == null) {
			linkedParts = new InventoryHandler();
		}
		return linkedParts;
	}
	
	public void setLinkedParts(Set<BodyPart> linkedParts) {
		for (BodyPart part : linkedParts) {
			getLinkedParts().addMudObject(part);
		}
	}
	
}
