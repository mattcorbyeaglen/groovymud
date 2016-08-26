package commands.player
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
import org.groovymud.object.MudObject
import org.groovymud.object.Container
import org.groovymud.engine.event.messages.MessageEvent
import org.groovymud.engine.event.EventScope

import static org.groovymud.utils.WordUtils.*
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
def all = false
itemName = args[0]

if(itemName == 'all'){
	all = true	
	itemName =args[1]
}

itemName = depluralize(itemName);

def itemsToGet = ((Container)source).getItems(itemName);
stream = source.getTerminalOutput()

if(all == true){
	itemsToGet.each{
		hashSet -> 
			hashSet.each{
				item ->
					source.removeItem(item)
					source.getCurrentContainer().addItem(item)
					sendDropMessage(item);
			}
	}
}else{
	// put the first object into the player as long as they do not weigh too much
	// and they can be picked up
	def item = itemsToGet.toArray()[0]
	source.removeItem(item);
	source.getCurrentContainer().addItem(item)
	
	sendDropMessage(item)
}

def sendDropMessage(item){
	def scopeMsg = "${source.getName()} drops ${affixIndefiniteArticle(item)}"
	def sourceMsg = "You drop ${affixIndefiniteArticle(item)}"
	sendMessageToRoom(source, sourceMsg, scopeMsg);
}