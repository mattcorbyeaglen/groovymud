package org.groovymud.object.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;

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
/**
 * The inventory handler is a TreeMap backed mud object management system The
 * handler registers the objects using names and shortnames determined from the
 * mud object its self. It stores each object in a Set.
 * 
 * When the Set is empty, the set and its key is removed.
 */
public class InventoryHandler {

	private transient Registry registry;

	protected Map<String, Set<MudObject>> mudObjectHashSets;

	public InventoryHandler() {
		mudObjectHashSets = Collections.synchronizedMap(new TreeMap<String, Set<MudObject>>());
	}

	public void addMudObject(MudObject object) {
		addMudObject(object.getName(), object);
		for (int x = 0; x < object.getShortNames().size(); x++) {
			addMudObject((object.getShortNames().toArray()[x]).toString(), object);
		}
	}

	public void addAllMudObjects(Set<MudObject> objects) {
		Iterator<MudObject> hashIterator = objects.iterator();
		while (hashIterator.hasNext()) {
			MudObject next = (MudObject) hashIterator.next();
			addMudObject(next);
		}
	}

	protected void addMudObject(String key, MudObject object) {
		addMudObjectToMap(key, object, getMudObjectHashSets());
	}

	private Map<String, Set<MudObject>> addMudObjectToMap(String key, MudObject object, Map<String, Set<MudObject>> mudObjectHashSets) {
		Set<MudObject> objectHashSet = null;
		if (mudObjectHashSets.get(key) != null) {
			objectHashSet = mudObjectHashSets.get(key);
		} else {
			objectHashSet = new HashSet<MudObject>();
		}
		objectHashSet.add(object);
		mudObjectHashSets.put(key, objectHashSet);

		return mudObjectHashSets;
	}

	/**
	 * returns the first object found with the given name
	 * 
	 * @param name
	 *            - name of the object
	 */
	public MudObject getMudObject(String name) {
		Set<MudObject> obj = getMudObjectHashSets().get(name);

		if (obj != null && !obj.isEmpty()) {
			synchronized (obj) {
				return obj.iterator().next();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 *            - mud object name
	 * @return Set of the HashSet of mudobjects that have that name
	 */
	public Set<MudObject> getMudObjects(String name) {
		return getMudObjectHashSets().get(name);
	}

	/**
	 * gets all mud objects
	 * not backed by the registry
	 * 
	 * @return a new Set containing all MudObjects.
	 */
	public Set<MudObject> getMudObjects() {
		Set<MudObject> allObjects = Collections.synchronizedSet(new HashSet<MudObject>());

		synchronized (getMudObjectHashSets()) {
			Collection<Set<MudObject>> values = getMudObjectHashSets().values();
			Iterator<Set<MudObject>> x = values.iterator();
			while (x.hasNext()) {
				allObjects.addAll(x.next());
			}
		}
		return allObjects;
	}

	public Set<MudObject> getMudObjects(boolean alive) {
		Set<MudObject> allObjects = Collections.synchronizedSet(new HashSet<MudObject>());
		synchronized (getMudObjectHashSets()) {
			Collection<Set<MudObject>> values = getMudObjectHashSets().values();
			Iterator<Set<MudObject>> x = values.iterator();
			while (x.hasNext()) {
				Set<MudObject> next = x.next();
				Iterator<MudObject> hashIter = next.iterator();
				while (hashIter.hasNext()) {
					MudObject mo = hashIter.next();
					if (alive == (mo instanceof Alive)) {
						allObjects.add(mo);
					}
				}
			}
		}
		return new HashSet<MudObject>(allObjects);
	}

	public Map<String, Set<MudObject>> getMudObjectsMap() {
		Map<String, Set<MudObject>> map = new HashMap<String, Set<MudObject>>(getMudObjectsMap(true));
		map.putAll(getMudObjectsMap(false));
		return map;
	}

	public Map<String, Set<MudObject>> getMudObjectsMap(boolean alive) {
		Map<String, Set<MudObject>> objs = createMap();

		synchronized (getMudObjectHashSets()) {
			Collection<String> keySet = getMudObjectHashSets().keySet();
			Iterator<String> moi = keySet.iterator();
			while (moi.hasNext()) {
				Set<MudObject> set = getMudObjectHashSets().get(moi.next());
				if (set != null) {
					Iterator<MudObject> setI = set.iterator();
					while (setI.hasNext()) {
						MudObject x = setI.next();
						if (alive == (x instanceof Alive)) {
							addMudObjectToMap(x.getName(), x, objs);
						}
					}
				}
			}
		}
		return objs;
	}

	protected Map<String, Set<MudObject>> createMap() {
		return Collections.synchronizedMap(new TreeMap<String, Set<MudObject>>());
	}

	/**
	 * Removes a given mud object from its Set and deletes the key if needed.
	 * @param object the object to remove
	 * @return object the removed object
	 */
	public MudObject removeMudObject(MudObject object) {

		synchronized (getMudObjectHashSets()) {
			Collection<Set<MudObject>> objects = getMudObjectHashSets().values();
			Iterator<Set<MudObject>> i = objects.iterator();
			synchronized (objects) {
				while (i.hasNext()) {
					Set<MudObject> set = i.next();
					if (set.contains(object)) {
						set.remove(object);
						if (set.size() == 0) {
							i.remove();
						}
					}
				}
			}
		}
		return object;
	}

	public MudObject removeMudObject(String objectName) {
		return removeMudObject(objectName, 0);
	}

	public Set<MudObject> getItemsOfClass(Class<?> clazz) {
		Set<MudObject> allObjects = Collections.synchronizedSet(new HashSet<MudObject>());

		synchronized (getMudObjectHashSets()) {
			Collection<Set<MudObject>> values = getMudObjectHashSets().values();
			Iterator<Set<MudObject>> x = values.iterator();
			while (x.hasNext()) {
				if (x.getClass().equals(clazz)) {
					allObjects.addAll(x.next());
				}
			}
		}
		return new HashSet<MudObject>(allObjects);
	}

	/**
	 * removes an object from the inventory at a given index
	 * 
	 * @param objectName
	 *            object to remove
	 * @param index
	 *            index of object to remove
	 * @return the removed object
	 */
	public MudObject removeMudObject(String objectName, int index) {
		Set<MudObject> mudObjectHashSet = getMudObjectHashSets().get(objectName);
		Object obj = null;
		if (mudObjectHashSet != null) {
			ArrayList<MudObject> list = new ArrayList<MudObject>();
			list.addAll(mudObjectHashSet);
			obj = list.get(index);
			synchronized (mudObjectHashSet) {
				mudObjectHashSet.remove(obj);
			}
		}
		return (MudObject) obj;
	}

	public void addAll(InventoryHandler handler) {
		this.mudObjectHashSets.putAll(handler.mudObjectHashSets);
	}

	public int size() {
		return getMudObjects().size();
	}

	public int size(boolean alive) {
		return getMudObjects(alive).size();
	}

	public void setMudObjectHashSets(Map<String, Set<MudObject>> mudObjectHashSets) {
		this.mudObjectHashSets = mudObjectHashSets;
	}

	public Map<String, Set<MudObject>> getMudObjectHashSets() {
		return mudObjectHashSets;
	}

	public void clear() {
		Map<String, Set<MudObject>> map = getMudObjectHashSets();
		synchronized (map) {
			for (Set<MudObject> set : map.values()) {
				for (MudObject mudObject : set) {
					mudObject.dest(true);
				}
				set.clear();
			}

			map.clear();
		}
	}

	/**
	 * container objects, when reloaded after persistance, loose their observers
	 * and link to the registry. this method relinks them with the game.
	 * 
	 * warning - this method is recursive, and calls its self.
	 * 
	 * @param obj
	 */
	public void relinkContent(Container obj) {
		Set<MudObject> mos = getMudObjects();
		synchronized (mos) {
			for (MudObject mudObject : mos) {

				if (mudObject instanceof Container) {
					Container container = castToContainer(mudObject);
					container.getInventoryHandler().relinkContent(container);
				}

				mudObject.setCurrentContainer(obj); // relink container data
				mudObject.addObserver(obj);
				getRegistry().addMudObject(mudObject); // re-register contents
			}
		}
	}

	public Registry getRegistry() {
		return registry;
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	protected MudObject castToMudObject(Container obj) {
		return ((MudObject) obj);
	}

	protected Container castToContainer(MudObject mudObject) {
		return ((Container) mudObject);
	}
}
