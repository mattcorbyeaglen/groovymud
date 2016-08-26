package domains.minnovar.town

import utils.MinnovarCalendar
import grails.spring.BeanBuilder
import std.game.objects.exits.ExitImpl
import std.game.objects.exits.DoorImpl
import org.groovymud.object.ObjectLocation
import groovy.lang.Binding
import std.game.objects.containers.RoomImpl
import org.groovymud.engine.event.EventScope
import std.game.behaviours.SpawnBehaviour
import std.game.behaviours.SpawnChecks


import org.groovymud.object.alive.Alive
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext
import org.groovymud.object.alive.Player
import static org.groovymud.utils.MessengerUtils.*
import org.groovymud.shell.security.MudPrincipal
import domains.minnovar.objects.RedPill;;
import domains.minnovar.objects.Shop;
/**
 * this will give us the beans we require to build the town area.
 * 
 * this is a rubbish implementation and probably should be done with
 * a database or something
 * 
 * @author corbym
 *
 */

def area = "domains:minnovar:TownArea"
def templateInclude ='<@ domains/minnovar/town/mainDescription.template@>'
def scriptName = "domains/minnovar/town/TownArea.groovy"

beans {
 	redPill(RedPill) { bean ->
		bean.parent = ref("baseMudObject", true)
		bean.scope = 'prototype'
		name="red pill"
		shortNames=["red pill", "pill"]
		description = "A little red pill.. I wonder what it does?"	
	}	
	"${area}:townCentreRoom"(RoomImpl){ bean->	
		bean.parent = ref("baseRoom", true)
		bean.'abstract' = true
		bean.scope = 'prototype'	
		calendar = ref("domains:minnovar:calendar", true);
		name = "Town Square"
	}
	
	// shop. exits: e	
	"${area}:localShop:e" (DoorImpl){bean->
		bean.parent = "std:east"
		objectAttendant = attendant
		destination = {ObjectLocation l ->
			definition = scriptName
			handle = "${area}:townCentre"
		}
	}
	
	"${area}:localShop" (Shop, name:"Shoppe", shortNames:["shop", "local shop"]){ bean->		
		bean.parent = ref("${area}:townCentreRoom")
		attendant = ref("objectAttendant", true)
		description = 'You are in the local shoppe. However you feel a bit grotty.'
		shortDescription = "Town Square, Local Shop"
		items = [ref("townCentre:shopKeeper")]
		exits = [ref("${area}:localShop:e")]
		
	}
	
	// towncentre: exits n, s, w
	"${area}:townCentre:n"(ExitImpl) { bean->
		bean.parent = ref("std:north", true)
		
		destination = {ObjectLocation l ->
			definition = scriptName
			handle = "$area:townCentreNorth"			
		}
	}
	"${area}:townCentre:s"(ExitImpl){bean->
		bean.parent = ref("std:south", true)
		
		destination = {ObjectLocation l ->
			definition = scriptName
			handle = "${area}:townCentreSouth"
		}				
	}
	"${area}:townCentre:w"(DoorImpl){bean->
		bean.parent = ref("std:west", true)
		objectAttendant = attendant
		destination = {ObjectLocation l ->
			definition = scriptName
			handle = "${area}:localShop"
		}
	}
	
	tcSpawn(SpawnBehaviour, spawnItems:[(SpawnChecks.CHECK_IN_ROOM) : ref("townCentre:torchBundle", true), (SpawnChecks.CHECK_EXISTS) : ref("townCentre:townCrier", true)])
	
	"${area}:townCentre" (RoomImpl, shortNames:["square", "town square"]){ bean ->
		bean.parent = ref("${area}:townCentreRoom")
		
		shortDescription = "Town Square, Centre"
		description = "${templateInclude} You are standing in the town centre."
		
		initialBehaviours = [ref(tcSpawn)] // - remove the pillspawn
		
		exits = [ref("${area}:townCentre:n"),ref("${area}:townCentre:s"), ref("${area}:townCentre:w")]
		
	}
	
	// north: exits s
	"${area}:townCentreNorth:s"(ExitImpl) {bean->
		bean.parent = ref("std:south", true)
		destination = {ObjectLocation l ->
			definition = scriptName
			handle = "${area}:townCentre"
		}				
	}
	"${area}:townCentreNorth"(RoomImpl, shortNames:["square", "town square"]){ bean->
		bean.parent = ref("${area}:townCentreRoom")
		
		description = "${templateInclude} You are standing in the town centre."
		shortDescription = "Town Square, North"
		exits = [ref("${area}:townCentreNorth:s")]
	}
	
	//south: exits n
	"${area}:townCentreSouth:n"(ExitImpl){bean-> 
		bean.parent = ref("std:north", true)
		destination = {ObjectLocation l ->
			definition = scriptName
			handle= "${area}:townCentre"			
		}
	}
	
	"${area}:townCentreSouth" (RoomImpl, name:"Town Square", shortNames:["square", "town square"]){ bean ->
		bean.parent = ref("${area}:townCentreRoom")
		description = "${templateInclude} You seem to be south of the centre. A gate to the south leads into a ominous looking wood."
		shortDescription = "Town Square, South"
		
		exits = [ref("${area}:townCentreSouth:n")]
	}
	
}




