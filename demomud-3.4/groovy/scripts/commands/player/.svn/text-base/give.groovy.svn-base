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
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom
import utils.MatchedObject
import utils.GenericSyntaxParser
import org.groovymud.object.Container
import org.groovymud.object.alive.Alive
import static org.groovymud.utils.WordUtils.*

def stream = source.getTerminalOutput()
 
GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, "to")

if(parser.subjectObject != null){
	
	if(parser.prepositionObject != null){
		// is the object in the player?
		obj = parser.subjectObject.findObjectInContainer(source)
		if(obj != null){
		    def mob = parser.prepositionObject.findObjectInContainer(source.getCurrentContainer())
		    if(mob instanceof Alive){
		        if(obj instanceof HashSet){			    
				    def map = source.getMudObjectsMap().clone()
				    def remove = [] as Set
				    def contentsHelper = new ContentsHelper()
				    contentsHelper.removeMudObject(source, map)
				    
				    obj.clone().each{
					       if(mob.checkCanAddItem(it)){
								mob.addMudObject(it)
						    }else{
								stream.writeln("You cannot give ${affixDefiniteArticle(obj)} to ${affixDefiniteArticle(mob)}.")							
								contentsHelper.removeMudObject(it, map);
						    }
					    
					}			 
				    contentsDescription = contentsHelper.getContentsDescription(map, source, false, false)
				}else{
				    contentsDescription = affixDefiniteArticle(obj);
				     
				    if(mob.checkCanAddItem(obj)){
						mob.addMudObject obj
				    }
				    else{
						stream.writeln "You cannot give $contentsDescription to ${affixDefiniteArticle(mob)}"
						return 
				    }
				}
				if(contentsDescription.trim() != ""){
	    		    def scopeMsg = "${source.name} gives ${contentsDescription} to ${affixIndefiniteArticle(mob)}."
	    		    def sourceMsg = "You give $contentsDescription to ${affixDefiniteArticle(mob)}."
	    		    sendMessageToRoom(source, sourceMsg, scopeMsg)
	    		    return
				}
		    }
		}
	}
	   
}
stream.println("You must give something to someone!")

