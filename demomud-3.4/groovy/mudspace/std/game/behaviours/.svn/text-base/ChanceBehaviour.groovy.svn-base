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
import org.groovymud.utils.Dice

/**
 * @author matt
 *
 */
class ChanceBehaviour extends Behaviour{
    
    def chance
    Behaviour behaviour
   
	int numDice
	int sides
	
	def doBehaviour(arg, ... args){	    
		def rnd = (numDice).d(sides).sum // roll a d x 
	    if(chance.contains(rnd)){
	        return behaviour?.doBehaviour(arg, args)
	    }
	}
}
