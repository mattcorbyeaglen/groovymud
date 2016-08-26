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
package domains.minnovar.objects.mobs

import static  org.groovymud.utils.WordUtils.resolvePossessiveGender
import static  org.groovymud.utils.WordUtils.resolveIndefiniteArticle
import std.game.behaviours.MOBWander
import std.game.objects.alive.MOBImpl
import org.groovymud.utils.Dice
import std.game.behaviours.*
import utils.ClosureDelegate
import std.game.behaviours.*
import std.game.behaviours.TriggeredBehaviourimport org.groovymud.engine.event.messages.MessageEventimport std.game.objects.exits.events.ArrivalEvent/**
 * included beans file, not an area
 */

beans{
    
    scriptedBehaviour(ScriptedBehaviour, scriptName:"domains/minnovar/objects/mobs/scripts/Greet.groovy"){
        engine = ref("groovyScriptEngine", true)
    }
    
    shopKeeperGreet(TriggeredBehaviour, eventClazz: ArrivalEvent){
        behaviour = ref("scriptedBehaviour");
    }
    
	"townCentre:shopKeeper"(MOBImpl){	bean->
		bean.parent = ref("baseMob", true)
		bean.lazyInit = true
		articleRequired = false
		def rnd = 1.d100 // using groovydice here :D
		
		if(rnd > 50){
			gender = "male"
		}else{
			gender = "female"
		}
		name = (gender == "male" ? "Brogan" : "Brogita")
		description = "This is $name the shopkeeper."		
	
		shortNames = [(gender == "male" ? "brogan" : "brogita"), "shopkeeper", "shopkeep"]
		triggeredBehaviours = [ref("shopKeeperGreet")]
	}

	tcWander(MOBWander)
	
	tcChanceToWander(ChanceBehaviour){
		numDice = 1
		sides = 1000
		chance = (0..5)
		behaviour = ref('tcWander')
	}
	
	"townCentre:townCrier"(MOBImpl){bean->
		bean.parent = ref("baseMob", true)
		bean.lazyInit = true
		name = "Town Crier"
		shortNames = ["crier", "town crier"]
		articleRequired = true
		description = "This is a $name."
		heartbeatBehaviours = [ref("tcChanceToWander")]
	}
	
}