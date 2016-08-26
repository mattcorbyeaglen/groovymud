package std
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
import std.game.objects.exits.ExitImpl
import org.groovymud.object.MudObject
import org.groovymud.engine.event.EventScope
import org.groovymud.shell.security.PlayerCredentials
import javax.security.auth.Subject
import org.groovymud.object.views.ViewImpl
import grails.spring.BeanBuilder

import std.game.objects.containers.RoomImpl
import std.game.objects.alive.MOBImpl
import std.game.objects.alive.PlayerImpl
import org.groovymud.object.ObjectLocation
import std.game.objects.MudObjectImpl
import std.game.objects.containers.ContainerImpl
import org.groovymud.object.registry.InventoryHandler
import org.groovymud.object.views.ContainerView
import org.groovymud.object.registry.MudObjectAttendant
import org.springframework.scripting.support.ScriptFactoryPostProcessor

import std.game.objects.views.ExitView
import std.game.objects.views.RoomView
import std.game.objects.views.MOBView

import utils.ClosureDelegate
import groovy.util.ResourceException
beans{
	
	mobView(MOBView)
	
	inventory(InventoryHandler){bean ->
		bean.scope = "prototype"
		registry = ref("registry", true)
	}
	
	exitView(ExitView);
	
	viewImpl(ViewImpl)
	
	roomView(RoomView)
	
	containerView(ContainerView)
	
	baseMudObject(MudObjectImpl){ bean->
		bean.'abstract' = true
		bean.lazyInit = true
		bean.initMethod = 'initialise'
		registry = ref("registry", true)
		interpreter = ref("interpreter", true)
		
		bean.scope = 'prototype'
		view = viewImpl
	}
	
	baseContainer(ContainerImpl){bean->
		bean.'abstract' = true
		bean.parent = baseMudObject
		bean.scope = 'prototype'
		bean.lazyInit = true
		inventoryHandler = inventory
		view = containerView
		scope = EventScope.CONTAINER_SCOPE
	}
	baseExit(ExitImpl){bean->
		bean.parent = baseMudObject
		bean.scope = 'prototype'
		bean.lazyInit = true
		bean.'abstract' = true	
		view = exitView
		commandInterpreter = ref("interpreter", true)
		articleRequired = false
	}
	baseRoom(RoomImpl){ bean ->
		bean.parent = baseContainer
		bean.'abstract' = true
		bean.lazyInit = true
		bean.scope = 'prototype'
		exitInventory = inventory
		scope = EventScope.ROOM_SCOPE
		view = roomView
	}
	
	baseMob(MOBImpl){ bean ->
		bean.parent = baseContainer		
		bean.'abstract' = true
		bean.scope = 'prototype'
		bean.lazyInit = true
		articleRequired = true
		view = mobView
		scope = EventScope.PLAYER_SCOPE
	}
		
	playerSubject(Subject);
	
	"playerImpl"(PlayerImpl){ bean ->
		bean.parent = baseMob	
		bean.scope = 'prototype'
		bean.lazyInit = true
		subject = playerSubject
		containerLocation = {ObjectLocation l -> 
			definition = "domains/minnovar/town/TownArea.groovy"
			beanId = "domains:minnovar:TownArea:townCentre"
		}		
		description = '${it.getName()} looks pretty cool.'
		articleRequired = false
	}
	
	"theVoid"(RoomImpl, id:"stdVoid", name:"void", shortNames:["void"]){ bean ->
		bean.parent = baseRoom		
		bean.lazyInit = true
		description = 'You are standing in the void. This is where everything goes that doesn\'t have a room to speak of.'
	}
	
	"armageddon"(MOBImpl, id:"stdArmageddon", name:"Armageddon", shortNames:["armageddon"]){bean->
		bean.parent = baseMob
		bean.lazyInit = true
		articleRequired = false
		description = 'Armegeddon, bringer of shutdowns'
	}
	
}
