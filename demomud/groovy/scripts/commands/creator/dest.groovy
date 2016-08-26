package commands.creator
import utils.GenericSyntaxParser;
import utils.MatchedObject;


/** Copyright 2008 Matthew Corby-Eaglen
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
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Room

def stream = source.getTerminalOutput()
source = (Player) source
if(args == null){
	stream.writeln "Usage: dest objectname [index]"
}
GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, "in")
def container = registry
if(parser.prepositionObject?.objectName == "me"){
	container = source
}
if(parser.prepositionObject?.objectName == "here"){
	container = source.currentContainer
}
def objToDest = parser.subjectObject.findObjectInContainer(container)

if(objToDest != null){
	if(objToDest instanceof Set){
		objToDest.each{
			registry.dest(it, true)
		}
	}else{
		registry.dest(objToDest, true)
	}
}else{
	stream.writeln "Cannot find a ${argstr} to dest"
}