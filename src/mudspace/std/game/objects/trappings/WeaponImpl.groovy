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
package std.game.objects.trappings

import std.game.objects.trappings.AbstractWeapon
import org.groovymud.engine.event.IScopedEventimport org.groovymud.object.alive.Alive
/**
 * @author corbym
 *
 */
class WeaponImpl extends AbstractWeapon{
	boolean canHold
	def weight
	String bodyPartFor
	def numberOfBodyPartsRequired
	
	def beingWielded
	
	void initialise(){
		
	}
	
	boolean canHold(){
		return canHold
	}
	
	void setWeight(double param0) {
		 
	}
	double getWeight() {
		return weight
	}
	
	String getBodyPartFor() {
		return bodyPartFor
	}
	
	int getNumberOfBodyPartsRequired() {
		return numberOfBodyPartsRequired
	}
	
	void doEvent(IScopedEvent param0) {
		
	}
	
	void doLower(Alive alive) {
		
	}
	
	void doWield(Alive alive) {
		if(beingWielded){
			
		}
	}
	
	boolean canHold(Object param0) {
		
	}
	
	void doHeartBeat() {
		
	}
}
