package std.game.objects.alive

import org.apache.log4j.Logger
import org.groovymud.object.views.View
import org.groovymud.engine.event.EventScope
import org.groovymud.object.alive.Alive
import org.groovymud.engine.event.EventScope
import org.groovymud.engine.event.ScopedEvent
import org.groovymud.engine.event.messages.MessageEvent

import std.game.objects.containers.ContainerImpl
import org.groovymud.shell.telnetd.ExtendedTerminalIO
import org.groovymud.object.MudObject
import org.groovymud.object.room.Exit
import static utils.WordUtils.affixDefiniteArticle

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

class MOBImpl extends ContainerImpl implements Alive {
	private static final Logger logger = Logger.getLogger(MOBImpl)
	
	Collection principals
	
	transient ExtendedTerminalIO terminalOutput = null;	
	
	boolean dead
	boolean inCombat = false
	
	long hitPoints;
	
	String gender;
	String oldDescription
	String oldName
	
	String leaves = "leaves"
	String arrives = "arrives from"
	
	def departureMessage = {direction -> "${name} ${leaves} ${affixDefiniteArticle(direction)}"
	}
	def arrivalMessage = {direction -> "${name} ${arrives} ${affixDefiniteArticle(direction)}"
	}
	
	@Override
	void addStatus(String status) {
		if(status == "net dead"){
			oldDescription = description
			setDescription ("$oldDescription ${getName()} looks all white and chalky...")
			oldName = name
			setName("The net dead statue of $oldName")
		}
	}
	
	@Override
	void removeStatus(String status) {
		if(status == "net dead"){
			setDescription ("$oldDescription")
			setName(oldName)
		}		
	}
	
	def methodMissing(String name, args){
		def retObject = null
		boolean done = super.doCommand(this as Alive, name as String, args as String) 
		if(!done){			
			def argsAsString = (args != null && args.size() > 0) ? args[0] : null		
			
			try{
				retObject = interpreter.doCommand(name, argsAsString, this)
				done = true
			}catch(ResourceException e){
				throw new MissingMethodException(name, this.class, args)
			}
			
		}
		return retObject
	}
	
	void onMudEvent(ScopedEvent arg) {
		super.onMudEvent(arg)
		def out = terminalOutput
		if (arg instanceof MessageEvent) {
			MessageEvent event = (MessageEvent) arg;
			
			try {
				def msg = event.getMessage(this) 
				if(msg != null){
					out?.writeln(msg)
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
		}
		
	}
	
	public String getDepartureMessage(Exit direction){
		return departureMessage(direction)
	}
	
	public String getArrivalMessage(Exit direction){
		return arrivalMessage(direction)
	}
	
}
