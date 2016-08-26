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
package std.game.objects.views

import org.groovymud.object.views.ContentsHelper
import org.groovymud.shell.telnetd.ExtendedTerminalIO
import org.groovymud.object.alive.Alive
import org.groovymud.object.MudObject

import org.groovymud.object.views.ContainerView
import org.groovymud.object.registry.InventoryHandler
/**
 * view object for a MOB
 * 
 * @author matt
 *
 */
public class MOBView extends ContainerView{
	private static final String CARRYING_DESC = "carrying";
	private static final String HOLDING_DESC = "holding";
	private static final String WEARING_DESC = "wearing";
	
	public void writeLookHeader(Alive looker, MudObject object) {
	    ExtendedTerminalIO stream = looker.getTerminalOutput();
		if (object.equals(looker)) {
			stream.writeln("Looking at yourself? How vain!");
			stream.writeln("You look like:");
		} else {
			stream.writeln("You see ${object.getName()}") //+ " the " + object.getGuildBehaviour().getGuildTitle());
		}
		writeDynamicText(looker, object, object.description)
	}
	
	public void writeLookInventory(Alive looker){
		writeLookInventory(CARRYING_DESC, looker.getInventoryHandler(), looker, looker);
	}
	 
	public void writeLookInventory(String invTypeAction, InventoryHandler handler, Alive looker, MudObject obj) {
	    ExtendedTerminalIO stream = looker.getTerminalOutput();
		if (looker.equals(obj)) {
			stream.writeln("You are $invTypeAction: ");
		} else {
			stream.writeln("${obj.getName()} is $invTypeAction: ");
		}
		ContentsHelper helper = new ContentsHelper();
		String wearing = helper.getContentsDescription(handler.getMudObjectsMap(), obj, false);
		stream.writeln(wearing.equals("") ? "Nothing." : wearing);
	}
	
	void writeLookFooter(ExtendedTerminalIO stream) throws IOException {
	}
	
	void writeLookBody(Alive looker, MudObject object){
		//writeLookInventory(WEARING_DESC, obj.getRaceBehaviour().getWearingInventory(),  obj, looker);
		//writeLookInventory(HOLDING_DESC, obj.getRaceBehaviour().getHoldingInventory(), obj, looker);
		writeLookInventory(CARRYING_DESC, object.getInventoryHandler(), looker, object);
	}
	
}
