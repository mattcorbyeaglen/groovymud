/**
 * 
 */
package std.game.objects.exits

import org.groovymud.engine.event.EventScope
import org.groovymud.engine.event.IScopedEvent
import org.groovymud.object.ObjectLocation

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
class DoorEvent implements IScopedEvent{

    EventScope scope = EventScope.ROOM_SCOPE
    def source
    ObjectLocation targetRoomLocation
    String targetDirection
    boolean open;

 
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#getScope()
     */
     EventScope getScope(){
        // TODO Auto-generated method stub
        return scope
    }
    
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#setScope(org.groovymud.engine.event.EventScope)
     */
    void setScope(EventScope scope){
        this.scope = scope
    }
    
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#setSource(java.lang.Object)
     */
    void setSource(Object object){
        source = object
    }
    
    /* (non-Javadoc)
     * @see org.groovymud.engine.event.IScopedEvent#getSource()
     */
    Object getSource(){
        return source
    }
}
