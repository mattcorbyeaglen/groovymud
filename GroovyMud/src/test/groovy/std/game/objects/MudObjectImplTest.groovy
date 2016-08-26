/**
 * 
 */
package std.game.objects

import std.game.objects.alive.MOBImplimport org.easymock.MockControlimport org.groovymud.object.alive.Aliveimport org.easymock.internal.Rangeimport org.groovymud.object.registry.Registryimport org.groovymud.shell.command.CommandInterpreterimport org.easymock.classextension.MockClassControlimport org.groovymud.object.MudObject

/**
 * @author corbym
 *
 */
public class MudObjectImplTest extends GroovyTestCase{
	
	final void testSetProperty(){
		
		MockControl attCtrl = MockClassControl.createControl(Registry);
		Registry reg = (Registry) attCtrl.getMock();
		
		MudObject impl = new MudObjectImpl(registry:reg, name:"mr monkey", id: "sausage")
		reg.update(impl)
		attCtrl.setDefaultVoidCallable();
		attCtrl.replay();
		impl.id = "twinkle"
		impl.name = "wombat"
		impl.setName("trump")
		
		attCtrl.verify()
		
	}
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
		
		assertTrue mudObject.doCommand(mob, "wield", "sword")	
	}
}
