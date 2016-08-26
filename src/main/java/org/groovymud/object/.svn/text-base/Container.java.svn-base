package org.groovymud.object;

/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
import java.util.Map;
import java.util.Set;

import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.observer.Observer;
import org.groovymud.object.registry.InventoryHandler;

/**
 * 
 * basic interface for all containers.
 * 
 * @author matt
 * 
 */
public interface Container extends Observer {

	public MudObject removeMudObject(MudObject object);

	public void addMudObject(MudObject obj);

	public Set<MudObject> getMudObjects();

	public Set<MudObject> getMudObjects(String name);

	public Map getMudObjectsMap();

	public InventoryHandler getInventoryHandler();

	public void setInventoryHandler(InventoryHandler handler);

	public void setScope(EventScope scope);

	public EventScope getScope();
}
