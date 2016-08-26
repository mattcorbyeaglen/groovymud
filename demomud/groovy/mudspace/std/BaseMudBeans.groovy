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
import org.groovymud.engine.event.EventScope
import javax.security.auth.Subject

import std.game.objects.containers.RoomImpl
import std.game.objects.alive.MOBImpl
import std.game.objects.alive.PlayerImpl
import org.groovymud.object.ObjectLocation
import std.game.objects.GroovyMudObject
import std.game.objects.containers.ContainerImpl

import std.game.objects.views.ContainerView;
import std.game.objects.views.ExitView
import std.game.objects.views.RoomView
import std.game.objects.views.MOBView
import std.game.objects.views.ViewImpl;

beans{
	view(ViewImpl);
	
	mobView(MOBView)
	
	exitView(ExitView);
	
	viewImpl(ViewImpl)
	
	roomView(RoomView)
	
	containerView(ContainerView)
	
	baseMudObject(GroovyMudObject){ bean->
		bean.'abstract' = true
		bean.lazyInit = true
		bean.scope = 'prototype'
		bean.parent = 'mudObjectImpl'
		view = ref("view")
	}
	
	baseContainer(ContainerImpl){bean->
		bean.'abstract' = true
		bean.parent = ref("baseMudObject")
		inventoryHandler = ref("inventory", true)
		view = ref("containerView")
		scope = EventScope.CONTAINER_SCOPE
	}
	baseExit(ExitImpl){bean->
		bean.parent = baseMudObject
		bean.scope = 'prototype'
		bean.'abstract' = true
		view = exitView
		articleRequired = false
	}
	baseRoom(RoomImpl){ bean ->
		bean.parent = baseContainer
		bean.'abstract' = true
		exitInventory = ref("inventory", true)
		scope = EventScope.ROOM_SCOPE
		view = roomView
	}
	
	baseMob(MOBImpl){ bean ->
		bean.parent = baseContainer		
		bean.'abstract' = true
		articleRequired = true
		view = mobView
		scope = EventScope.PLAYER_SCOPE
	}
	
	playerSubject(Subject);
	
	"playerImpl"(PlayerImpl){ bean ->
		bean.parent = baseMob	
		bean.scope = 'prototype'
		subject = playerSubject
		containerLocation = {ObjectLocation l -> 
			definition = "domains/minnovar/town/TownArea.groovy"
			handle = "domains:minnovar:TownArea:townCentre"
		}		
		description = '${it.getName()} looks pretty cool.'
		articleRequired = false
	}
	
	"theVoid"(RoomImpl, name:"void", shortNames:["void"]){ bean ->
		bean.parent = baseRoom		
		description = 'You are standing in the void. This is where everything goes that doesn\'t have a room to speak of.'
	}
	
	"armageddon"(MOBImpl, name:"Armageddon", shortNames:["armageddon"]){bean->
		bean.parent = baseMob
		articleRequired = false
		description = 'Armegeddon, bringer of shutdowns'
	}
	
}
