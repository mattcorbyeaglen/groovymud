package org.groovymud.engine.event.observer;

import java.util.HashSet;
import java.util.Set;

import org.groovymud.engine.event.IScopedEvent;

public interface IObservable {

	public Set getObservers();

	public void setObservers(HashSet<Observer> observers);

	public void addObserver(Observer obj);

	public void deleteObserver(Observer o);

	public void notifyObservers(IScopedEvent arg);

	public void fireEvent(IScopedEvent event);

	public abstract void doEvent(IScopedEvent event);

	public boolean hasChanged();

	public void setChanged();

	public int size();
}
