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
package std.game.objects.containers

import java.util.Map
import java.util.Set
import junit.framework.TestCase
import org.groovymud.object.MudObject
import org.groovymud.object.alive.Playerimport org.groovymud.object.alive.Aliveimport std.game.objects.MudObjectImplimport org.groovymud.object.registry.InventoryHandlerimport org.groovymud.engine.event.IScopedEvent

/**
 * @author corbym
 *
 */
public class ContainerImplTest extends GroovyTestCase{
	
	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#doEvent(org.groovymud.engine.event.IScopedEvent)
	 */
	final void testDoEvent(){
		def event = [:] as IScopedEvent
		def list = [new MudObjectImpl(id:"123")]
		
		def done = false
		ContainerImpl impl = [
			doEventInCollection:{ ev, items ->
				assertTrue event == ev
				assertTrue items.containsAll(list)
				done = true
			},
			getMudObjects : {list as Set}
		] as ContainerImpl
		
		
		impl.doEvent(event)
		assertTrue done == true
	}
	

	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#addItemToInventoryHandler(org.groovymud.object.MudObject, org.groovymud.object.registry.InventoryHandler)
	 */
	final void testAddItemToInventoryHandler(){
		//TODO Implement the Testmethod AddItemToInventoryHandler
	
		fail("Not yet implemented")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#removeItem(org.groovymud.object.MudObject)
	 */
	final void testRemoveMudObject(){
		//TODO Implement the Testmethod RemoveItem
	
		fail("Not yet implemented")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#removeItemFromInventoryHandler(org.groovymud.object.MudObject, org.groovymud.object.registry.InventoryHandler)
	 */
	final void testRemoveItemFromInventoryHandler(){
		//TODO Implement the Testmethod RemoveItemFromInventoryHandler
	
		fail("Not yet implemented")
	}

	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#getMudObjectsMapFromInventoryHandler(org.groovymud.object.registry.InventoryHandler)
	 */
	final void testGetMudObjectsMapFromInventoryHandler(){
		//TODO Implement the Testmethod GetMudObjectsMapFromInventoryHandler
	
		fail("Not yet implemented")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#getItemsFromInventoryHandler(java.lang.String, org.groovymud.object.registry.InventoryHandler)
	 */
	final void testGetItemsFromInventoryHandler(){
		//TODO Implement the Testmethod GetItemsFromInventoryHandler
	
		fail("Not yet implemented")
	}

	
	/* (non-Javadoc)
	 * @see std.game.objects.containers.ContainerImpl#doCommandInContents(java.lang.String, java.lang.String, org.groovymud.object.Container, java.util.Collection)
	 */
	final void testDoCommand(){
		Alive mob = [:] as Alive
		def emc = new ExpandoMetaClass(MudObjectImpl.class, true )

		emc.wield = { m, args ->
			assertTrue mob == m
			assertTrue args == "sword"
			true
		}
		emc.initialize()
		def mudObject = new MudObjectImpl()
		
		def handler = [getMudObjects : {[mudObject] as Set}] as InventoryHandler
		
		def container = new ContainerImpl(inventoryHandler:handler)
		
		assertTrue container.doCommand(mob, "wield", "sword")		
		
	}
	
}
