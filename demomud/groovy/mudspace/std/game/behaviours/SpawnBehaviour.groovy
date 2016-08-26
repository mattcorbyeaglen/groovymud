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

import org.groovymud.object.Container;

/**
 * Behaviour that spawns items into a container
 * 
 * if "checkExists" is set on the object, if the object already exists in the registry it
 * is not spawned
 * 
 * if "checkInRoom" is set on the object, the object only spawns if not in the room already
 * 
 * @author corbym
 *
 */
class SpawnBehaviour extends Behaviour{
	
	Map spawnItems = [:]
	
	def doBehaviour(container, ... args) {
		
		def reg = container.registry
		spawnItems.each{ k,v ->
			boolean add = true
			switch(k){
				case SpawnChecks.CHECK_EXISTS:
					def items = reg.getMudObjects(v.name) as List
					def vsContainer = null
					if(items?.size() > 0){
						vsContainer = items[0]?.currentContainer
					}
					add = (vsContainer == null)
					break;
				case SpawnChecks.CHECK_IN_ROOM:
					add = (container.getMudObjects(v.name) == null)
					break;
			}
			
			if(add){
				container.addMudObject(v)
			}
		}
	}
}
