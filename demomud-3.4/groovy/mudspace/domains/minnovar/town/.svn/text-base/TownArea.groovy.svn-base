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
import std.game.objects.MudObjectImpl


import org.groovymud.object.alive.Alive
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ApplicationContext
import org.groovymud.object.alive.Player
import static org.groovymud.utils.MessengerUtils.*
import org.groovymud.shell.security.MudPrincipal
import domains.minnovar.objects.RedPill;
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
	 
class Shop extends RoomImpl implements ApplicationContextAware{
	ApplicationContext applicationContext
	boolean list(Alive mob, String args){
		def keeper = getMudObject("shopkeeper")
		
		keeper.say("Heheh, here you can get anything. Won't do what you think, mind, but you can buy anything at all!")
		return true
	}
	boolean say(Alive mob, String args){
	    if(args =~ /\w*pills\w*/){
	        keeper.say "We don't have any, we're a respectable establishment!!"
			
		}
		return false
	}
	boolean buy(Alive mob, String args){
		def obj = null
		def keeper = getMudObject("shopkeeper")
		if(args == "red pill"){
			keeper.say "Ahh, very good.. you want the special red pill eh?"
			keeper.emote "turns and reaches up to the top shelf."
			keeper.emote "brings a special looking box down from the shelf and takes out a small red object."
			obj = applicationContext.getBean("redPill")	
		}else{
			//      just load any basic obj from the context
			obj = applicationContext.getBean("blankMudObject")
			obj.setName(args)
			obj.shortNames.addAll(args.split(" ") as List)
			obj.shortNames.remove("of")
			obj.setDescription("This is your average, run of the mill ${args}. It looks like it was made out of cardboard.")
		}
		
		keeper.addMudObject(obj)
		
		keeper.give "${obj.name} to ${mob.name}"
		keeper.say("Thankyou, come again!")
		return true
	}
}

def beanbuilder = new BeanBuilder(context)

def templateInclude ='<@ domains/minnovar/town/mainDescription.template@>'
def scriptName = "domains/minnovar/town/TownArea.groovy"

beanbuilder.beans {
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
		objectAttendant = ref("objectAttendant", true)
		destination = {ObjectLocation l ->
			areaName = area
			definition = scriptName
			beanId = "${area}:townCentre"
		}
	}
	
	"${area}:localShop" (Shop, id:"${area}:localShop", name:"Shoppe", shortNames:["shop", "local shop"]){ bean->		
		bean.parent = ref("${area}:townCentreRoom")
		description = 'You are in the local shoppe. However you feel a bit grotty.'
		shortDescription = "Town Square, Local Shop"
		items = [ref("townCentre:shopKeeper")]
		exits = [ref("${area}:localShop:e")]
		
	}
	"${area}:clock"()
	// towncentre: exits n, s, w
	"${area}:townCentre:n"(ExitImpl) { bean->
		bean.parent = ref("std:north", true)
		
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
		
		destination = {ObjectLocation l ->
			areaName = "$area"
			definition = scriptName
			beanId = "${area}:townCentreSouth"
		}				
	}
	"${area}:townCentre:w"(DoorImpl){bean->
		bean.parent = ref("std:west", true)
		objectAttendant = ref("objectAttendant", true)
		destination = {ObjectLocation l ->
			areaName = "$area"
			definition = scriptName
			beanId = "${area}:localShop"
		}
	}
	
	tcSpawn(SpawnBehaviour, spawnItems:[(SpawnChecks.CHECK_IN_ROOM) : ref("townCentre:torchBundle", true), (SpawnChecks.CHECK_EXISTS) : ref("townCentre:townCrier", true)])
	
	"${area}:townCentre" (RoomImpl, id:"${area}:townCentre", shortNames:["square", "town square"]){ bean ->
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
			areaName = "$area"
			definition = scriptName
			beanId = "${area}:townCentre"
		}				
	}
	"${area}:townCentreNorth"(RoomImpl, id:"${area}:townCentreNorth", shortNames:["square", "town square"]){ bean->
		bean.parent = ref("${area}:townCentreRoom")
		
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
		description = "${templateInclude} You seem to be south of the centre. A gate to the south leads into a ominous looking wood."
		shortDescription = "Town Square, South"
		
		exits = [ref("${area}:townCentreSouth:n")]
	}
	
}

beanbuilder.createApplicationContext()


