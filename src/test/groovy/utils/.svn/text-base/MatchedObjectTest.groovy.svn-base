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
package utils

import org.easymock.MockControlimport java.util.LinkedListimport java.util.SortedSetimport groovy.util.ProxyGeneratorimport groovy.mock.interceptor.StubForimport static groovy.util.Proxy.proxyimport groovy.util.Proxy
import org.groovymud.object.Container


/**
 * @author corbym
 *
 */
public class MatchedObjectTest extends GroovyTestCase{
	
	void testAll(){
		MatchedObject matched = new MatchedObject();
		matched.objectName = "all cookies"        
		assertTrue(matched.all())
		
		matched.objectName = "ship"          
		assertFalse(matched.all())
		
		matched.objectName = "all echoes"
		assertTrue(matched.all())
		
		matched.objectName = "echoes"
		assertFalse(matched.all())
	}
	

	void testPlural(){
		MatchedObject matched = new MatchedObject();
		matched.objectName = "cookies"        
		assertTrue(matched.plural())
		
		matched.objectName = "ship"          
		assertFalse(matched.plural())
		
		matched.objectName = "echoes"
		assertTrue(matched.plural())
	}
	

	void testFindObjectInContainer(){
	    def closureMap = [ doHeartbeat: {}, 
	                       getName : { return "monkey" }] as Map
	    def interfaces = [Container] as List
	   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	
		def items = [mudObj1, mudObj2] as HashSet
		MockControl control = MockControl.createNiceControl(Container.class)
		Container testContainer = control.getMock()        
		testContainer.getMudObjects("monkey")
		control.setDefaultReturnValue(items)
		control.replay()
		
		MatchedObject matched = new MatchedObject()
	    
	    matched.objectName = "monkey"
	    def returnVal = matched.findObjectInContainer(testContainer) 
	    assertTrue(returnVal == mudObj1 || returnVal == mudObj2)
	    control.verify()
	    
	}
	
	void testFindAllObjectInContainer(){
	    def closureMap = [ doHeartbeat: {}, 
	                       getName : { return "monkey" }] as Map
	    def interfaces = [Container] as List
	   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	
		def items = [mudObj1, mudObj2] as HashSet
		MockControl control = MockControl.createNiceControl(Container.class)
		Container testContainer = control.getMock()        
		testContainer.getMudObjects("monkey")
		control.setDefaultReturnValue(items)
		control.replay()
		
		MatchedObject matched = new MatchedObject()
	    
	    matched.objectName = "all monkeys"
	    def returnVal = matched.findObjectInContainer(testContainer) 
	    assertTrue(returnVal == items)
	    control.verify()
	    
	}	

	void testFindSecondObjectInContainer(){
	    def closureMap = [ doHeartbeat: {}, 
	                       getName : { return "monkey" }] as Map
	    def interfaces = [Container] as List
	   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	
		def items = [mudObj1, mudObj2] as LinkedList
		MockControl control = MockControl.createNiceControl(Container.class)
		Container testContainer = control.getMock()        
		testContainer.getMudObjects("monkey")
		control.setDefaultReturnValue(items as Set)
		control.replay()
		
		MatchedObject matched = new MatchedObject()
	    
	    matched.objectName = "monkey 2"
	    def returnVal = matched.findObjectInContainer(testContainer)
	    items.eachWithIndex{it, i ->
	        if(it == returnVal){
	            assertTrue i == 1
	        }
	    }
	    control.verify()
	    
	}	
	/**
	 * this is a silly test as the order of the set will change
	 * depending on java's mood
	 * 
	 */
	void testFindFirstObjectInContainer(){
	    def closureMap = [ doHeartbeat: {}, 
	                       getName : { return "monkey" }] as Map
	    def interfaces = [Container] as List
	   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
	   	
		def items = [mudObj1, mudObj2] as Set
		MockControl control = MockControl.createNiceControl(Container.class)
		Container testContainer = control.getMock()        
		testContainer.getMudObjects("monkey")
		control.setDefaultReturnValue(items)
		control.replay()
		
		MatchedObject matched = new MatchedObject()
	    
	    matched.objectName = "monkey 1"
	    def returnVal = matched.findObjectInContainer(testContainer) 
	    items.eachWithIndex{it, i ->
	        if(it == returnVal){
	            assertTrue i == 0
	        }
	    }
	    control.verify()
	    
	}

	void testFindObjectInHashSet(){
	    
	    def proxy = new MockMatchedObject()
	    proxy.objectName = "monkey"
	    def findObjs = [proxy.mudObj1, proxy.mudObj2] as HashSet
		    
		def obj = proxy.findObjectIn(findObjs)
		assertTrue(obj == proxy.mudObj1 || obj == proxy.mudObj2)
	}
	
	void testFindObjectInHashSetAll(){
	    
	    def proxy = new MockMatchedObject()
	    proxy.objectName = "all monkeys"
	    def findObjs = [proxy.mudObj1, proxy.mudObj2] as HashSet
		    
		def obj = proxy.findObjectIn(findObjs)
		assertTrue(obj == findObjs)
	}
}

class MockMatchedObject extends MatchedObject{

    def closureMap = [ doHeartbeat: {}, 
                       getName : { return "monkey" }] as Map
    def interfaces = [Container] as List
   	def mudObj1 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
   	def mudObj2 = ProxyGenerator.instantiateAggregate(closureMap, interfaces)
   	
    protected Object findObjectInContainer(Container container){
		return  [mudObj1, mudObj2] as HashSet
    }
    
    
}
