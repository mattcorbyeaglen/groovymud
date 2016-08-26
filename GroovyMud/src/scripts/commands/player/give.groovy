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
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import utils.MatchedObject
import utils.GenericSyntaxParser
import org.groovymud.object.Containerimport std.game.objects.alive.MOBImplimport static org.groovymud.utils.WordUtils.*

def stream = source.getTerminalOutput()
 
GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, "to")

if(parser.subjectObject != null){
	
	if(parser.prepositionObject != null){
		// is the object in the player?
		obj = parser.subjectObject.findObjectInContainer(source)
		if(obj != null){
		    mob = parser.prepositionObject.findObjectInContainer(source.getCurrentContainer())
		    if(mob instanceof MOBImpl){
		        if(obj instanceof HashSet){			    
				    def map = source.getMudObjectsMap().clone()
				    def remove = [] as Set
				    source.removeMudObject(source, map)
				    actualObjs.each{
					    obj ->
					       if(mob.checkCanAddItem(obj)){
								mob.addItem(obj)
						    }else{
								stream.writeln("You cannot give ${affixDefiniteArticle(obj)} to ${affixDefiniteArticle(mob)}.")							
								source.removeMudObject(obj, map);
						    }
					    
					}			 
				    contentsDescription = source.getContentsDescription(map, source, false)
				}else{
				    contentsDescription = affixDefiniteArticle(obj);
				     
				    if(mob.checkCanAddItem(obj)){
						mob.addItem obj
				    }
				    else{
						stream.writeln "You cannot give $contentsDescription to ${affixDefiniteArticle(mob)}"
						return 
				    }
				}
				if(contentsDescription.trim() != ""){
	    		    def scopeMsg = "${source.getName()} gives ${contentsDescription} to ${affixIndefiniteArticle(mob)}."
	    		    def sourceMsg = "You give $contentsDescription to ${affixDefiniteArticle(mob)}."
	    		    sendMessageToRoom(source, sourceMsg, scopeMsg)
	    		    return
				}
		    }
		}
	}
	   
}
stream.println("You must give something to someone!")

