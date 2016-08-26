package commands.player
import utils.GenericSyntaxParser/* Copyright 2008 Matthew Corby-Eaglen
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

import static utils.WordUtils.*
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
def all = false
def itemName = argstr

GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, " ")

def itemsToGet = parser.subjectObject.findObjectInContainer(source)

stream = source.getTerminalOutput()

if(itemsToGet instanceof Set){
	def copy = new HashSet(itemsToGet)
	copy.each{
		hashSet -> 
			hashSet.each{
				item ->
					source.getCurrentContainer().addMudObject(item)
					sendDropMessage(item);
			}
	}
}else{
	if(itemsToGet != null){
	    source.getCurrentContainer().addMudObject(itemsToGet)	
	    sendDropMessage(itemsToGet)
	}else{
	    stream.writeln("Cannot find ${itemName} to drop.")
	}
}

def sendDropMessage(item){
	def scopeMsg = "${source.getName()} drops ${affixIndefiniteArticle(item)}"
	def sourceMsg = "You drop ${affixIndefiniteArticle(item)}"
	sendMessageToRoom(source, sourceMsg, scopeMsg);
}