/**
 * 
 */
package std.game.behaviours

import org.groovymud.engine.event.messages.MessageEventimport org.groovymud.object.MudObjectimport org.easymock.MockControlimport org.easymock.classextension.MockClassControlimport std.game.objects.MudObjectImplimport org.groovymud.engine.event.IScopedEvent

/**
 * @author matt
 *
 */
public class TriggeredBehaviourTest extends GroovyTestCase{
	
	/* (non-Javadoc)
	 * @see std.game.behaviours.TriggeredBehaviour#doBehaviour(java.lang.Object[])
	 */
	final void testDoBehaviour(){
		boolean done = false
		boolean called = false
		def mockEvent = [:] as MessageEvent
		
		def mob = [:] as MudObjectImpl
		
		def testBehaviour = [doBehaviour: {object, event -> 
			assertTrue mob == object; 
			done = true
		}] as Behaviour
		def triggered = new TriggeredBehaviour(eventClazz: mockEvent.getClass(), behaviour: testBehaviour)
		
		triggered.triggerLogic = { event -> 
			called = true
		}
		
		triggered.doBehaviour(mob, mockEvent)
		assertTrue(done)
		assertTrue(called)

	}
	
}
