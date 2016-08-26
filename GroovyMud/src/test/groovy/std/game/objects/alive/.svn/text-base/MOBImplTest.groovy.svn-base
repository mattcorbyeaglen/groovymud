/**
 * 
 */
package std.game.objects.alive

import std.game.behaviours.Behaviourimport org.easymock.classextension.MockClassControlimport org.easymock.MockControlimport org.groovymud.object.registry.MudObjectAttendantimport org.groovymud.shell.command.CommandInterpreterimport org.groovymud.object.registry.Registryimport org.groovymud.object.MudObjectimport groovy.util.ResourceException

/**
 * @author corbym
 *
 */
public class MOBImplTest extends GroovyTestCase{
	
	/* (non-Javadoc)
	 * 
	 * @see std.game.objects.alive.MOBImpl#initialise()
	 */
	final void testInitialise(){
	    MOBImpl mob = new MOBImpl(id:"1", name:"mob", shortNames:["mob"])
	    
	    Class[] x = new Class[3]	    
	    x[0] = MudObjectAttendant
	    x[1] = CommandInterpreter
		x[2] = Registry
		
	    Object[] objs = new Object[3]
	    objs[0] = null
	    objs[1] = null
		objs[2] = null
	    MockControl ctrl = MockClassControl.createControl(Registry)
	    
	    Registry customizer = ctrl.getMock()
	    customizer.register(mob)
	    ctrl.setDefaultVoidCallable()
	    ctrl.replay()
		mob.customizer = customizer
		Behaviour behaviour = [doBehaviour: {arg, arg2 -> assertEquals mob, arg}] as Behaviour
		mob.initBehaviours = [behaviour]
	    mob.setRegistry(customizer)
		mob.initialise()
		ctrl.verify()
		
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.alive.MOBImpl#doEvent(org.groovymud.engine.event.IScopedEvent)
	 */
	final void testDoEvent(){
		//TODO Implement the Testmethod DoEvent
	
		fail("Not yet implemented")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.alive.MOBImpl#heartBeat()
	 */
	final void testHeartBeat(){
		//TODO Implement the Testmethod HeartBeat
	
		fail("Not yet implemented")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.alive.MOBImpl#getDepartureMessage(org.groovymud.object.room.Exit)
	 */
	final void testGetDepartureMessage(){
		//TODO Implement the Testmethod GetDepartureMessage
	
		fail("Not yet implemented")
	}
	
	/* (non-Javadoc)
	 * @see std.game.objects.alive.MOBImpl#getArrivalMessage(org.groovymud.object.room.Exit)
	 */
	final void testGetArrivalMessage(){
		//TODO Implement the Testmethod GetArrivalMessage
	
		fail("Not yet implemented")
	}
	
	final void testInvokeMissingMethod() {
		
		MockControl attCtrl = MockClassControl.createControl(Registry.class);
		Registry reg = (Registry) attCtrl.getMock();
		
		reg.register(null);
		attCtrl.setDefaultVoidCallable();
		attCtrl.replay();
		
		MockControl interpCtrl = MockClassControl.createControl(CommandInterpreter.class);
		CommandInterpreter interp = (CommandInterpreter) interpCtrl.getMock();
		
		MOBImpl testSubject = new MOBImpl(interpreter:interp, registry: reg, name:"brian rat", id:"1234")
		
		interp.doCommand("north", null, testSubject)
		interpCtrl.setDefaultReturnValue(testSubject)
		interpCtrl.replay();
		
		testSubject."north"()
	
		attCtrl.verify()
		interpCtrl.verify()
		
	}
	final void testInvokeMissingMethodException() {
		MockControl attCtrl = MockClassControl.createControl(Registry);
		Registry reg = (Registry) attCtrl.getMock();
		
		reg.register(null);
		attCtrl.setDefaultVoidCallable();
		attCtrl.replay();
		
		MockControl interpCtrl = MockClassControl.createControl(CommandInterpreter);
		CommandInterpreter interp = (CommandInterpreter) interpCtrl.getMock();
		
		MOBImpl testSubject = new MOBImpl(interpreter:interp, registry: reg, name:"brian rat", id:"1234")
		
		interp.doCommand("north", null, testSubject)
		interpCtrl.setDefaultThrowable(new ResourceException())
		interpCtrl.replay();
		try{
			testSubject."north"()
			fail("should throw missing method exception")
		}catch(MissingMethodException e){
			
		}
		attCtrl.verify()
		interpCtrl.verify()
		
	}
	
}
