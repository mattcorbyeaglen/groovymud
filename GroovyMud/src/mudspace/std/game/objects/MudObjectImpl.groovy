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
package std.game.objects
import org.groovymud.engine.event.EventScopeimport groovy.util.Evalimport groovy.lang.GStringimport groovy.lang.GroovyShellimport std.game.behaviours.Behaviourimport groovy.lang.MissingMethodExceptionimport groovy.util.ResourceExceptionimport groovy.util.ScriptExceptionimport org.groovymud.object.alive.Aliveimport org.groovymud.engine.event.IScopedEventimport org.groovymud.shell.command.CommandInterpreterimport org.groovymud.object.registry.MudObjectAttendantimport com.sun.servicetag.Registryimport org.groovymud.object.registry.Registryimport org.groovymud.object.views.Viewimport org.groovymud.object.ObjectLocationimport org.groovymud.object.Containerimport java.util.Collectionimport org.groovymud.object.MudObjectimport org.groovymud.engine.event.action.DestroyEventimport org.groovymud.engine.event.observer.Observable
/**
 * a bog standard, unamed mud object
 * @author corbym
 *
 */
class MudObjectImpl extends Observable implements MudObject, Weighted {
	String id
	double weight
	String name
	String description
	List shortNames = []
	
	String shortDescription
	
	boolean articleRequired = true
	
	ObjectLocation objectLocation // where this bean can be loaded from
	transient Container currentContainer
	ObjectLocation containerLocation // metadata on the container //
	View view
	
	transient Registry registry = null
	transient CommandInterpreter interpreter = null
	
	List<Behaviour> initialBehaviours = []
	List<Behaviour> heartbeatBehaviours = []
	List<Behaviour> triggeredBehaviours = []
	
	def properties = [:]
	
	void initialise(){
		if(id == null){
			id = hashCode()
			println("initialiseing object id " + id)
		}
		registry.register(this)
		initialBehaviours?.each{ it ->
			it.doBehaviour(this)
		}
		
	}
	
	
	void dest(boolean wep) {
		DestroyEvent event = new DestroyEvent();
		event.setSource(this);
		event.setScope(EventScope.GLOBAL_SCOPE);
		fireEvent(event);
	}
	
	
	void heartBeat() {
		heartbeatBehaviours?.each{ it ->
			it.doBehaviour(this)
		}
	}
	
	void doEvent(IScopedEvent arg) {
		triggeredBehaviours?.each{it->
			it.doBehaviour(this, arg)
		}
	}
	
	void setProperty(String param, Object object) {
		if ("name".equals(param)) {
			// unregister and reregister object
			registry.update(this);
		}
	}
	
	void addShortName(String shortName) {
		shortNames << shortName;
	}
	
	boolean doCommand(Alive player, String methodName, String args){
		boolean done = false
		if(this.metaClass.respondsTo(this.class, methodName, player, args)){
			done = this."${methodName}"(player, args)
		}
		return done
	}

}
