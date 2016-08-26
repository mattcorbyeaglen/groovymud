package domains.minnovar.town

import utils.MinnovarCalendar
import grails.spring.BeanBuilderimport std.game.objects.exits.ExitImpl
import std.game.objects.exits.DoorImplimport org.groovymud.object.ObjectLocationimport groovy.lang.Bindingimport std.game.objects.containers.RoomImpl
import org.groovymud.engine.event.EventScopeimport std.game.behaviours.SpawnBehaviourimport std.game.behaviours.SpawnChecks/**
 * this will give us the beans we require to build the town area.
 * 
 * this is a rubbish implementation and probably should be done with
 * a database or something
 * 
 * @author corbym
 *
 */
def area = "domains:minnovar:TownArea"

def beanbuilder = new BeanBuilder(parentContext)

beanbuilder.beans {
    def templateInclude ='<@ domains/minnovar/town/mainDescription.template@>'
	
	def scriptName = "domains/minnovar/town/TownArea.groovy"
	// this should definitely be done in a db or in another script file
	
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
		destination = {ObjectLocation l ->
			areaName = area
			definition = scriptName
			beanId = "${area}:townCentre"
		}
	}
	
	"${area}:localShop" (RoomImpl, id:"${area}:localShop", name:"Shoppe", shortNames:["shop", "local shop"]){ bean->		
		bean.parent = ref("${area}:townCentreRoom")
		bean.initMethod = "initialise"
		description = 'You are in the local shoppe. However you feel a bit grotty.'
		shortDescription = "Town Square, Local Shop"
		items = [ref("townCentre:shopKeeper")]
		exits = [ref("${area}:localShop:e")]
	}
	
	// towncentre: exits n, s, w
	"${area}:townCentre:n"(ExitImpl) { bean->
		bean.parent = ref("std:north", true)
		bean.initMethod = "initialise"
		name = "north"
		direction = name
		arrivalDirection = 'south'
		shortNames = ["n", "nor"]
		destination = {ObjectLocation l ->
			areaName = "$area"
			definition = scriptName
			beanId = "$area:townCentreNorth"			
		}
	}
	"${area}:townCentre:s"(ExitImpl){bean->
		bean.parent = ref("std:south", true)
		bean.initMethod = "initialise"
		destination = {ObjectLocation l ->
			areaName = "$area"
				definition = scriptName
			beanId = "${area}:townCentreSouth"
		}				
	}
	"${area}:townCentre:w"(DoorImpl){bean->
		bean.parent = ref("std:west", true)
		bean.initMethod = "initialise"
		objectAttendant = ref("objectAttendant", true)
		destination = {ObjectLocation l ->
			areaName = "$area"
			definition = scriptName
			beanId = "${area}:localShop"
		}
	}
	
	tcSpawn(SpawnBehaviour, spawnItems:[(SpawnChecks.CHECK_EXISTS) : ref("townCentre:townCrier", true)])
	
	"${area}:townCentre" (RoomImpl, id:"${area}:townCentre", shortNames:["square", "town square"]){ bean ->
		bean.parent = ref("${area}:townCentreRoom")
		bean.initMethod = "initialise"
		shortDescription = "Town Square, Centre"
		description = "${templateInclude} You are standing in the town centre."
		
		initialBehaviours = [ref(tcSpawn)]
		
		exits = [ref("${area}:townCentre:n"),ref("${area}:townCentre:s"), ref("${area}:townCentre:w")]
		
	}
	
	// north: exits s
	"${area}:townCentreNorth:s"(ExitImpl) {bean->
			bean.parent = ref("std:south", true)
			bean.initMethod = "initialise"
			destination = {ObjectLocation l ->
				areaName = "$area"
				definition = scriptName
				beanId = "${area}:townCentre"
			}				
		}
	"${area}:townCentreNorth"(RoomImpl, id:"${area}:townCentreNorth", shortNames:["square", "town square"]){ bean->
		bean.parent = ref("${area}:townCentreRoom")
		bean.initMethod = "initialise"
		description = "${templateInclude} You are standing in the town centre."
		shortDescription = "Town Square, North"
		exits = [ref("${area}:townCentreNorth:s")]
	}
	
	//south: exits n
	"${area}:townCentreSouth:n"(ExitImpl){bean-> 
		bean.parent = ref("std:north", true)
		bean.initMethod = "initialise"
		destination = {ObjectLocation l ->
			areaName = '$AREA'
			definition = scriptName
			beanId = "${area}:townCentre"			
		}
	}

	"${area}:townCentreSouth" (RoomImpl, id:"${area}:townCentreSouth", name:"Town Square", shortNames:["square", "town square"]){ bean ->
		bean.parent = ref("${area}:townCentreRoom")
		bean.initMethod = "initialise"
		description = "${templateInclude} You seem to be south of the centre. A gate to the south leads into a ominous looking wood."
		shortDescription = "Town Square, South"
		
		exits = [ref("${area}:townCentreSouth:n")]
	}
	
}
context = beanbuilder.createApplicationContext()

attendant.setApplicationContext(context)
