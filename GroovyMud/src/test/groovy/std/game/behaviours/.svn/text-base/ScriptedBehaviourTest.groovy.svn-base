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
package std.game.behaviours

import junit.framework.TestCase
import groovy.util.GroovyScriptEngineimport std.game.objects.alive.MOBImplimport groovy.mock.interceptor.MockForimport groovy.lang.Bindingimport groovy.mock.interceptor.StubForimport org.easymock.classextension.MockClassControl

/**
 * @author corbym
 *
 */
public class ScriptedBehaviourTest extends TestCase{
	
	/* (non-Javadoc)
	 * @see std.game.behaviours.ScriptedBehaviour#doBehaviour(java.lang.Object, java.lang.Object[])
	 */
	void testDoBehaviour(){
		
		
		def mob = [say : {arg -> println arg} ] as MOBImpl
		Class[] clazzAr = new Class[1]
		clazzAr[0] = String
		Object[] objAr = new Object[1]
		objAr[0] = null
		Class[] clz = new Class[1] 
		clz[0] = String
		String[] str = new String[1]
		str[0] = new String("file://") 
		def scriptCtrl = MockClassControl.createControl(GroovyScriptEngine, clz, str)
		def mockBinding = [setProperty: {name, arg ->}, getProperty : { "property"}] as Binding
		
		final GroovyScriptEngine eng = (GroovyScriptEngine) scriptCtrl.getMock();
		eng.run("test", mockBinding)
		scriptCtrl.setDefaultReturnValue(null)
		scriptCtrl.replay()
		
		def behaviour = [getEngine : {
			eng
			}, getScriptName: {"test"}, createBinding : {mockBinding}] as ScriptedBehaviour
		
		def th = behaviour.doBehaviour(mob)
		th.join()
		
		
		
	}
	
}
