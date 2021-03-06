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
import org.groovymud.object.MudObject
import org.groovymud.utils.Diceimport std.game.objects.alive.MOBImplimport org.groovymud.shell.command.CommandInterpreterimport groovy.lang.Range

/**
 * @author corbym
 *
 */
class MOBWander extends Behaviour{


	def doBehaviour(mob, ... args){		
		def exits = mob.getCurrentContainer()?.getExits() as List
		def exit = exits?.get(1.d(exits?.size()).sum - 1)
		if(exit != null){
			mob.go exit.direction
		}
		
	}
}
