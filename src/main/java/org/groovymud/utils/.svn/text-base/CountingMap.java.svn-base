package org.groovymud.utils;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class wraps a HashMap and provides methods by which key
 * objects can be associated with "counting" values.
 * 
 * useful for timers or other such counting effects
 * 
 * e.g 
 * countingMap.put(object); // puts (object, 0) into the map
 * countingMap.increment(object) // adds 1 to the value of the object (object, 1)
 * countingMap.decrement(object) // decreases the value at the object's key reference by 1 (object, 0)
 * @author matt
 *
 */
public class CountingMap {

	Map<Object, Integer> backingMap = new HashMap<Object, Integer>();

	public void clear() {
		backingMap.clear();
	}

	public boolean containsKey(Object key) {
		return backingMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return backingMap.containsValue(value);
	}

	public Set<?> entrySet() {
		return backingMap.entrySet();
	}

	public boolean equals(Object o) {
		return backingMap.equals(o);
	}

	public Integer get(Object key) {
		return backingMap.get(key);
	}

	public int hashCode() {
		return backingMap.hashCode();
	}

	public boolean isEmpty() {
		return backingMap.isEmpty();
	}

	public Set<?> keySet() {
		return backingMap.keySet();
	}

	public Integer remove(Object key) {
		return backingMap.remove(key);
	}

	public int size() {
		return backingMap.size();
	}

	public Collection<Integer> values() {
		return backingMap.values();
	}

	public Integer put(Object key) {
		return put(key, new Integer(0));
	}

	public Integer put(Object key, Integer value) {
		return backingMap.put(key, value);
	}

	public void putAll(Map<Object, Integer> m) {
		backingMap.putAll(m);
	}

	public void increment(Object key) {
		if (!backingMap.containsKey(key)) {
			backingMap.put(key, new Integer(0));
		} else {
			Integer i = backingMap.get(key);
			backingMap.put(key, new Integer(i.intValue() + 1));
		}
	}

	public void decrement(Object key) {
		if (!backingMap.containsKey(key)) {
			backingMap.put(key, new Integer(0));
		} else {
			Integer i = backingMap.get(key);
			backingMap.put(key, new Integer(i.intValue() - 1));
		}
	}

	public Set getKeysAt(int limit) {
		Set ret = new HashSet<Object>();
		for (Object obj : backingMap.keySet()) {
			if (backingMap.get(obj).intValue() == limit) {
				ret.add(obj);
			}
		}
		return ret;
	}

	public Set getKeysAbove(int limit) {
		Set ret = new HashSet<Object>();
		for (Object obj : backingMap.keySet()) {
			if (backingMap.get(obj).intValue() > limit) {
				ret.add(obj);
			}
		}
		return ret;
	}

	public Set getKeysBelow(int limit) {
		Set ret = new HashSet<Object>();
		for (Object obj : backingMap.keySet()) {
			if (backingMap.get(obj).intValue() < limit) {
				ret.add(obj);
			}
		}
		return ret;
	}

}
