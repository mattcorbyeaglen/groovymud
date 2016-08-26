package org.groovymud.engine.event.observer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.groovymud.engine.event.IScopedEvent;

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
public abstract class Observable implements IObservable {

	private transient Set<Observer> observers;
	private transient boolean changed;

	public Set<Observer> getObservers() {
		if (observers == null) {
			observers = createObservers();
		}
		return new HashSet(observers);
	}

	public void setObservers(HashSet<Observer> observers) {
		this.observers = createObservers();
		this.observers.addAll(observers);
	}

	private Set<Observer> createObservers() {
		return Collections.synchronizedSet(new HashSet<Observer>());
	}

	public void addObserver(Observer obj) {
		if (observers == null) {
			observers = createObservers();
		}
		observers.add(obj);
	}

	public void deleteObserver(Observer o) {
		observers.remove(o);
	}

	public synchronized void notifyObservers(IScopedEvent arg) {
		Set<Observer> observers = getObservers();

		Iterator<Observer> i = observers.iterator();
		if (hasChanged()) {
			changed = false;
			while (i.hasNext()) {
				Observer ob = i.next();
				ob.update((IObservable) this, arg);
			}
		}
	}

	public void fireEvent(IScopedEvent event) {
		setChanged();
		if (event.getSource() == null) {
			event.setSource(this);
		}
		notifyObservers(event);
	}

	// actually performs the event
	public abstract void doEvent(IScopedEvent event);

	public boolean hasChanged() {
		return changed;
	}

	public void setChanged() {
		this.changed = true;
	}

	public int size() {
		return getObservers().size();
	}

}
