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
package std.game.objects.exits

import groovy.mock.interceptor.MockForimport org.groovymud.object.alive.Playerimport org.groovymud.engine.event.messages.MessageEventimport groovy.util.GroovyTestCaseimport groovy.mock.interceptor.StubForimport org.groovymud.object.registry.MudObjectAttendantimport org.groovymud.object.ObjectLocationimport org.easymock.classextension.MockClassControlimport std.game.objects.containers.RoomImpl
import org.groovymud.object.room.Roomimport org.groovymud.object.MudObject
/**
 * @author corbym
 *
 */
public class DoorImplTest extends GroovyTestCase{
	def mockDoor
	
	def otherMockDoor = [] as DoorImpl
	
	def mockPlayer

	
	void setUp(){		
		mockDoor = [
			getName : {return "mock"},
			getDirection: {return "mock"},
			getShortNames : {return ['mock door', 'door']},
			findOtherDoor: {direction, location ->   return otherMockDoor  }, 
			fireDoorEvent: { player ->  assertTrue mockPlayer == player }, 
			
		] as DoorImpl
	}
	/* (non-Javadoc)
	 * @see std.game.objects.exits.DoorImpl#doOpen(java.lang.Object)
	 */
	void testDoOpen(){
		mockDoor.setOpen(false)
		mockPlayer = [
				getName: { return "me" }, 
				fireEvent: { event -> 
					assertTrue event.sourceMessage == "You open the mock door."
					assertTrue event.scopeMessage == "me opens the mock door."
				}
				] as Player
		
		mockDoor.open(mockPlayer, "mock door")
		
		assertTrue otherMockDoor == mockDoor.otherDoor
		assertTrue otherMockDoor.getOpen()
		
		
	}
	
	void testDoClose(){
		mockDoor.setOpen(true)
		mockPlayer = [
		getName: { return "me" }, 
		fireEvent: { event -> 
			assertTrue event.sourceMessage == "You close the mock door."
			assertTrue event.scopeMessage == "me closes the mock door."
		}
		] as Player
		
		mockDoor.close(mockPlayer, "mock door")
	
		assertTrue otherMockDoor == mockDoor.otherDoor
		assertTrue !otherMockDoor.getOpen()
	}
	/* (non-Javadoc)
	 * @see std.game.objects.exits.DoorImpl#doOpen(java.lang.Object)
	 */
	void testDoAlreadyOpen(){
		mockDoor.setOpen(true)
		mockPlayer = [
				getName: { return "me" }, 
				fireEvent: { event-> 
					assertTrue event instanceof MessageEvent
					assertTrue event.sourceMessage == "You try to open the mock door but it is already open."
				}
				] as Player
		mockDoor.open(mockPlayer, "mock door")
	}
	/* (non-Javadoc)
	 * @see std.game.objects.exits.DoorImpl#doClose(java.lang.Object)
	 */
	void testDoAlreadyClosed(){
		mockDoor.setOpen(false)
		mockPlayer = [
		getName: { return "me" }, 
		fireEvent: { event-> 
			assertTrue event instanceof MessageEvent
			assertTrue event.sourceMessage == "You try to close the mock door but it is already closed."
		}
		] as Player
		mockDoor.setOpen(false)
		mockDoor.close(mockPlayer, "mock door")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.exits.DoorImpl#findOtherDoor(java.lang.String, org.groovymud.object.ObjectLocation)
	 */
	void testFindOtherDoor(){
		//def mockAttendant = [load: {id, l -> assertEquals(loc , l); assert id == "mock"; return otherRoom}] as MudObjectAttendant
		def ctrl = MockClassControl.createControl(MudObjectAttendant)
		MudObjectAttendant mockAttendant = ctrl.getMock()
		mockDoor = [
			getName : {return "mock door"},
			getDirection: {return "mock"},
			fireDoorEvent: { player ->  assertTrue mockPlayer == player }, 
			sendMessageToRoom: {			
			},
			sendMessageToPlayer: {
			
			}
		] as DoorImpl
		mockDoor.setOpen(false)
		def otherRoom = [getExit: {direction -> return otherMockDoor}] as Room
		
		def loc = [beanId: "mock", definition: "my/areaDefinition"] as ObjectLocation
		
		mockDoor.setAttendant(mockAttendant)
		mockAttendant.load(loc)
		ctrl.setDefaultReturnValue(otherRoom as MudObject)
		ctrl.replay()

		assertEquals mockDoor.findOtherDoor("mock", loc), otherMockDoor
		
		ctrl.verify()
	}
	
	void testInitialise(){
		
		def mockDoor = [sendMessageToRoom: {obj, str, message ->}]
	}
}
