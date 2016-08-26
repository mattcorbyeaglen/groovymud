package commands.creator
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
import org.groovymud.object.room.Room

def stream = source.getTerminalOutput()

if(args == null){
	stream.writeln "Usage: dest objectname [index]"
}

def item =  source.getCurrentContainer().getMudObject(argstr)

if(item == null){
    item = source.getMudObject(argstr);
}

if(item == null){
	item = registry.getMudObject(argstr)
}
if(item == null){
	if(args[0] == "here"){
		item = source.getCurrentContainer()
	}
}
if(item != null){
	def index = 0;
	def items = []
	if(item instanceof Set){
	    items.addAll(item)
	}else{
	    items = [item]
	}
	try{
    	if(args[2] != null){
    		index = Integer.parseInt(args[2]).intValue();
    		index -= 1;    		
    	}
    	item = items[index]
    	item.dest(true)
    	
    	if(source.getCurrentContainer().equals(item)){
    		source.getMudObjectAttendant().movePlayerToVoid(source)
    	}
	}catch(java.lang.NumberFormatException e){
		stream.writeln "Usage: dest objectname [index]";
	}
	
}else{
	stream.writeln "Cannot find a ${args[0]} to dest"
}