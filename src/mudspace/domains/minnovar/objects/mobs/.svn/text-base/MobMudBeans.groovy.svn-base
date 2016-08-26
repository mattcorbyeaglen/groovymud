package domains.minnovar.objects.mobs

import static  org.groovymud.utils.WordUtils.resolvePossessiveGender
import static  org.groovymud.utils.WordUtils.resolveIndefiniteArticle
import std.game.behaviours.MOBWander
import std.game.objects.alive.MOBImpl
import org.groovymud.utils.Diceimport std.game.behaviours.*
import utils.ClosureDelegate
import std.game.behaviours.*
/**
 * included beans file, not an area
 */

beans{

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
	
		shortNames = [(gender == "male" ? "brogan" : "brogita")]
	
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
		articleRequired = true
		description = "This is a $name."
		heartbeatBehaviours = [ref("tcChanceToWander")]
	}
	
}