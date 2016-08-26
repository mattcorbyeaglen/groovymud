package utils
import org.groovymud.utils.WordUtils/**
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
import org.groovymud.object.MudObject
import org.groovymud.object.Container

import static org.groovymud.utils.WordUtils.*


/**
 * helps find the objects based on regexp matches. 
 * set the objectName and index and it will look for the object in a hashset or a container
 * @author matt
 *
 */
 class MatchedObject{
  
    	String foundNames = "" // used for storing a list of found object names		
    	String objectName = "" // the parsed object name
		int index = 0
	
		static final def allMatcher = /^(all$|all .*$)/
		boolean all(){
			def val = (this.objectName ==~ allMatcher)
			return val
		}
		static final def pluralMatcher = /^.+s$/
		boolean plural(){
			return (this.objectName ==~ pluralMatcher)
		}
		
		// finds the object in a container, returns a Map if all is set or was plural
		// otherwise returns the object
		protected def findObjectInContainer(Container container){
			def wasAll = false
			if(all()){
				objectName = objectName.replaceFirst("all", "")
				wasAll = true
			}
			if(plural()){
			    objectName = depluralize(objectName)
			    wasAll = true
			}
			def objs
			if(objectName == ""){
			    objs = container.getItems();			    
			}else{
			    objs = container.getMudObjects(objectName)
			}
			
			if(wasAll || objs == null){
				return objs
			}
			return objs.asList().toArray()[index - 1]	
		}
		/** 
		 * finds the object in a given hashset, returns a HashSet if all is set
		 * collates a list of object names otherwise returns the object
		 */
		public def findObjectIn(HashSet set){
		    def ret = [] as HashSet
			set.each{
			    prospectiveContainer -> 
				if(prospectiveContainer instanceof Container){
					def found = findObjectInContainer(prospectiveContainer);
					if(found instanceof HashSet){
					    found.eachWithIndex{
							obj, idx -> 
								foundNames += 
								    affixIndefiniteArticle(obj.getName()) + 
									(idx < found.size() - 1 ? ", " : "") +
									(idx == found.size() - 1 ? " and " : "")
					    }
						ret.addAll(found)
					}else{
					    foundNames = affixIndefinateArticle(found.getName())
						ret.add(found)
					}
				}
			}
			if(all()){
				return ret
			}
			return ret.asList().toArray()[index]
		}
	}
