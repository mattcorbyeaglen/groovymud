package commands.player
import org.groovymud.object.views.ContentsHelper/* Copyright 2008 Matthew Corby-Eaglen
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
import org.groovymud.object.alive.Alive

import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import utils.MatchedObject
import utils.GenericSyntaxParser

/*
 * Syntax: get [all] [item [index] | items] [from [container [index] | containers]
 * 
 * eg get bread from sack
 * 	  get bread 2 from sack 1
 * 	  get all from sack
 *    get all monkeys from sacks
 *    get monkeys from sacks
 *    get all green monkeys from dirty sacks
 */
stream = source.getTerminalOutput()

parser = new GenericSyntaxParser()
parser.parse(argstr, "from")

if(parser.subjectObject != null){
	
	def fromObject = source.getCurrentContainer() // get from the room
	if(parser.prepositionObject != null){ // unless the prep object is populated
		// is the object in the player?
		fromObject = parser.prepositionObject.findObjectInContainer(source)
		if(fromObject == null){
			// or the room
			fromObject = parser.prepositionObject.findObjectInContainer(source.getCurrentContainer())			
		}
	}
	if(fromObject != null && !(fromObject instanceof Alive)){
		def actualObjs = parser.subjectObject.findObjectInContainer(fromObject)
		getFrom(actualObjs, fromObject)
	}else{	  
	    if(fromObject != null && fromObject instanceof Alive){
	        stream.writeln "You cannot get things from ${affixDefiniteArticle(fromObject)}, they have to give it to you!" 
	    }else{
	        stream.writeln "Cannot find ${parser.prepositionObject.objectName} to get anything from."
	    }
	}
}else{
	stream.writeln "Syntax: get [all] [item [index] | items] [from [container [index] | containers]"
}

 
 def getFrom(def actualObjs, def fromObject){
		def contentsDescription = "";
		//put the objects into the source object (the player)
		if(actualObjs != null){
			if(actualObjs instanceof Set){			    
			    def map = fromObject.getMudObjectsMap().clone()
			    def remove = [] as Set
			    def contentsHelper = new ContentsHelper()
			    removeAlive(map, contentsHelper)
			    actualObjs.clone().each{
				    obj ->
				       if(source.checkCanAddItem(obj)){
							source.addMudObject(obj)
					    }else{
							stream.writeln("You cannot get ${affixIndefiniteArticle(obj)}.")							
							contentsHelper.removeMudObject(obj, map);
					    }
				    
				}			 
			    
			    contentsDescription = contentsHelper.getContentsDescription(map, source, false, false)
			}else{
			    contentsDescription = affixIndefiniteArticle(actualObjs);
			    boolean canAdd = source.checkCanAddItem(actualObjs)
			    if(canAdd){
					source.addMudObject actualObjs 
			    }
			    else{
					stream.writeln "You cannot get $contentsDescription."
					return 
			    }
			}
			if(contentsDescription.trim() != ""){
    		    def scopeMsg = "${affixDefiniteArticle(source)} gets ${contentsDescription} from ${affixDefiniteArticle(fromObject)}."
    		    def sourceMsg = "You get $contentsDescription from the ${fromObject.getName()}."
    		    sendMessageToRoom(source, sourceMsg, scopeMsg)
			}
		}else{
		    if(parser.subjectObject == null || parser.subjectObject.objectName == "null"){
		        stream.writeln "You need to get something."
		    }else{
		        stream.writeln "Cannot find ${parser.prepositionObject.objectName} to get."
		    }
		}
 }
 
 def removeAlive(map, contentsHelper){
     new HashSet(map.values()).each{ set -> 
         set.each{
             if(it instanceof Alive){
                 contentsHelper.removeMudObject(it, map)
             }
         }         
     }
 }
