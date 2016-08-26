package org.groovymud.engine.event.observer;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.groovymud.engine.event.IScopedEvent;

public class ObservableTest extends TestCase {

	public void testNotifyObservers() {
		MockControl eventCtrl = MockControl.createControl(IScopedEvent.class);
		IScopedEvent event = (IScopedEvent) eventCtrl.getMock();
		eventCtrl.replay();
		final HashSet observers = new HashSet();

		Observable obj = new Observable() {

			@Override
			public Set getObservers() {
				// TODO Auto-generated method stub
				return observers;
			}

			@Override
			public void doEvent(IScopedEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void fireEvent(IScopedEvent event) {
				// TODO Auto-generated method stub

			}
		};
		MockControl control = MockControl.createControl(Observer.class);
		Observer observer = (Observer) control.getMock();
		observer.update(obj, event);
		control.setDefaultVoidCallable();
		control.replay();

		observers.add(observer);
		obj.setChanged();
		obj.notifyObservers(event);
		assertFalse(obj.hasChanged());
		eventCtrl.verify();
		control.verify();
	}

}
