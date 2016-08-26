 package commands.player
import org.groovymud.shell.telnetd.ExtendedTerminalIOimport org.groovymud.object.views.View/* Copyright 2008 Matthew Corby-Eaglen
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
import org.groovymud.object.alive.Alive;
import org.groovymud.object.registry.Registry
import org.groovymud.object.MudObject
import org.groovymud.object.room.Room
import org.groovymud.object.Container
import java.util.List
import org.groovymud.object.views.*;

def target = null
def stream = ((Alive)source).getTerminalOutput()

if(args == null || args.size() == 0){
	target = source.getCurrentContainer();
}else{
	args.remove("at")
	if(args[0] == ("me")){
		target = source
	}else{
		def item = args[0]
		if(item == "in"){
			item = args[1]
		}
		if(target == null){
			target = ((Container)source).getMudObjects(item);		
		}
		if(target == null && item.charAt(item.size() - 1) == "s"){
			def buff = new StringBuffer(item)
			buff.deleteCharAt(item.size() - 1)
			item = buff.toString()
			target = source.getItems(item);
		}
		def container = source.getCurrentContainer()
		if(target == null || target.size() == 0){
			target = container.getMudObjects(item);
		}
		if(target == null){
		    if(item in container.getShortNames() || item == container.getName())
		    {
		        target = container
		    }
		}
	}
}
if(target != null){

	def item = args != null ? args[0] : null

    	if(target instanceof Set){
    		target.each
			{ 
    		    obj -> 
    		    View look = obj.getView();				
    		    look.writeLookHeader(source, obj);
        	    look.writeLookBody(source, obj);
        	    look.writeLookFooter(source,obj); 
			}
    	}else{
    	    View look = target.getView()
			
	    	look.writeLookHeader(source, target);
    	    look.writeLookBody(source, target);
    	    look.writeLookFooter(source,target);
	    }
  

}else{
	stream.writeln("You cannot see any ${args[0]} here");
}

