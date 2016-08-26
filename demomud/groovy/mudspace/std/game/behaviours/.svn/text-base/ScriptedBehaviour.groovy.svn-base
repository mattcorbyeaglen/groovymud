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
package std.game.behaviours

import groovy.lang.Binding
import groovy.util.GroovyScriptEngine

/**
 * 
 * this is a scripted behaviour. the script identified by "scriptName" determines the behavior of the object
 *  
 * @author corbym
 *
 */
class ScriptedBehaviour extends Behaviour {
	GroovyScriptEngine engine
	String scriptName
	def doBehaviour(mob, ... args){
		Binding binding = createBinding();
		binding.setProperty("mob", mob);
		binding.setProperty("args", args);
		// start off a new thread so we don't waste heartbeat time
		Thread.start{
			delegate.engine.run(scriptName, binding);
		}		 
	}
	def createBinding(){
		new Binding()
	}
	
}
