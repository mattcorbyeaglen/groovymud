/**
 * 
 */
package std.game.behaviours

import net.sf.groovydice.GroovyDiceimport org.groovymud.object.MudObjectimport groovy.util.GroovyTestCase

/**
 * @author corbym
 *
 */
class ChanceBehaviourTest extends GroovyTestCase{
	
	/* (non-Javadoc)
	 * @see std.game.behaviours.ChanceBehaviour#doBehaviour(org.groovymud.object.MudObject)
	 */
	final void testDoBehaviour(){
		def mockObject = [:] as MudObject
		def config = new GroovyDice()
		config.numberGenerator = { sides -> return 1 }
		config.initialize()
		def done = false
		def mockBehaviour = [doBehaviour : {obj, objs -> 
			assertTrue obj == mockObject 
			done = true
		}] as Behaviour
		
		def chanceBeh = new ChanceBehaviour(chance: 0..10, numDice:1, sides:100, behaviour: mockBehaviour)
		chanceBeh.doBehaviour(mockObject)
		assertTrue done
		
		
	}
	final void testDontDoBehaviour(){
		def mockObject = [:] as MudObject
		def config = new GroovyDice()
		config.numberGenerator = { sides -> return 11 }
		config.initialize()
		def done = false
		def mockBehaviour = [doBehaviour : {obj -> 
			assertTrue obj == mockObject 
			done = true
		}] as Behaviour
		
		def chanceBeh = new ChanceBehaviour(chance: 0..10, numDice:1, sides:100, behaviour: mockBehaviour)
		chanceBeh.doBehaviour(mockObject)
		assertFalse done
	}
}
