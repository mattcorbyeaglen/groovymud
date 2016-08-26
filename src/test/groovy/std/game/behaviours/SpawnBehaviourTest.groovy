/**
 * 
 */
package std.game.behaviours

import org.groovymud.object.MudObjectimport org.groovymud.object.Containerimport org.groovymud.object.registry.InventoryHandlerimport org.groovymud.object.registry.Registryimport org.easymock.classextension.MockClassControlimport org.easymock.MockControlimport std.game.objects.containers.ContainerImplimport std.game.objects.MudObjectImpl

/**
 * @author corbym
 *
 */
class SpawnBehaviourTest extends GroovyTestCase{
	
	/* (non-Javadoc)
	 * @see std.game.behaviours.SpawnBehaviour#doBehaviour(org.groovymud.object.MudObject)
	 */
	void testDoBehaviour(){
		def obj = { getName : {"test"
			} } as MudObjectImpl
		Behaviour spawnBehaviour = new SpawnBehaviour(spawnItems : [(SpawnChecks.CHECK_IN_ROOM) : obj])
		
		MockControl mockRegControl = MockClassControl.createNiceControl(Registry)
		
		def mockRegistry = mockRegControl.getMock() as Registry
		
		mockRegistry.getMudObjects("test")
		mockRegControl.setDefaultReturnValue([] as Set)
		mockRegControl.replay()
		def mockHandlerCtrl = MockClassControl.createNiceControl(InventoryHandler)
		
		def mockHandler = mockHandlerCtrl.getMock() as InventoryHandler
		
		mockHandler.getRegistry()
		mockHandlerCtrl.setDefaultReturnValue(mockRegistry)
		mockHandlerCtrl.replay()
		
		MockControl containerCtrl = MockControl.createControl(Container)
		
		def mockContainer = containerCtrl.getMock()
		mockContainer.getInventoryHandler();
		containerCtrl.setDefaultReturnValue(mockHandler)
		mockContainer.getMudObjects("test")
		containerCtrl.setDefaultReturnValue(null)
		mockContainer.addMudObject(obj)
		containerCtrl.setDefaultVoidCallable()
		
		containerCtrl.replay()
		
		spawnBehaviour.doBehaviour(mockContainer)
		
		mockHandlerCtrl.verify()
		mockRegControl.verify()
		containerCtrl.verify()
	}
	
	void testDoBehaviour2(){
		def obj = { getName : {"test"
			} } as MudObjectImpl
		Behaviour spawnBehaviour = new SpawnBehaviour(spawnItems : ["checkInRoom" : obj])
		
		MockControl mockRegControl = MockClassControl.createNiceControl(Registry)
		
		def mockRegistry = mockRegControl.getMock() as Registry
		
		mockRegistry.getMudObjects("test")
		mockRegControl.setDefaultReturnValue([] as Set)
		mockRegControl.replay()
		def mockHandlerCtrl = MockClassControl.createNiceControl(InventoryHandler)
		
		def mockHandler = mockHandlerCtrl.getMock() as InventoryHandler
		
		mockHandler.getRegistry()
		mockHandlerCtrl.setDefaultReturnValue(mockRegistry)
		mockHandlerCtrl.replay()
		
		MockControl containerCtrl = MockControl.createControl(Container)
		
		def mockContainer = containerCtrl.getMock()
		mockContainer.getInventoryHandler();
		containerCtrl.setDefaultReturnValue(mockHandler)
		mockContainer.getMudObjects("test")
		containerCtrl.setDefaultReturnValue(null)
		mockContainer.addMudObject(obj)
		containerCtrl.setDefaultVoidCallable()
		
		containerCtrl.replay()
		
		spawnBehaviour.doBehaviour(mockContainer)
		
		mockHandlerCtrl.verify()
		mockRegControl.verify()
		containerCtrl.verify()
	}
	
	void testNoSpawnExists(){
		def mockContainer = [:] as Container
		def obj = [getName : {"test"}
		, getCurrentContainer: {mockContainer}] as MudObjectImpl
		
		MockControl regCtrl = MockClassControl.createControl(Registry)
		
		def mockRegistry = regCtrl.getMock()
		
		mockRegistry.getMudObjects("test")
		regCtrl.setDefaultReturnValue([obj] as Set)
		regCtrl.replay()
		
		def mockHandler = [getRegistry: {mockRegistry}] as InventoryHandler
		def container = [getInventoryHandler: {mockHandler},
		addMudObject : { fail("must not add item")}
		] as ContainerImpl
		SpawnBehaviour b = new SpawnBehaviour(spawnItems : [(SpawnChecks.CHECK_EXISTS) : obj])
		
		b.doBehaviour(container)
		regCtrl.verify()
	}
	
	void testNoSpawnPresent(){
		def obj = [getName : {"test"}] as MudObject
		def mockHandler = [:] as InventoryHandler
		def container = [getInventoryHandler: {mockHandler},
		addMudObject : { fail("must not add item")},
		getMudObjects: {name ->
			assertEquals(obj.getName(), name) 
			return [obj] as Set
		}
		] as ContainerImpl
		SpawnBehaviour b = new SpawnBehaviour(spawnItems : [(SpawnChecks.CHECK_IN_ROOM) : obj])
		
		b.doBehaviour(container)
		
	}
}
