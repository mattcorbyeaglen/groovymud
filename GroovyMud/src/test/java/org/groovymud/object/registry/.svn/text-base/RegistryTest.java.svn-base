package org.groovymud.object.registry;

import java.util.HashSet;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.action.DestroyEvent;
import org.groovymud.engine.event.action.SaveEvent;
import org.groovymud.engine.event.messages.MessageEvent;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.engine.event.system.MovementEvent;
import org.groovymud.object.MudObject;
import org.groovymud.object.ObjectLocation;
import org.groovymud.object.alive.Player;
import org.groovymud.object.room.Room;
import org.groovymud.shell.telnetd.LoginShell;

public class RegistryTest extends TestCase {

	/*
	 * Test method for
	 * 'org.groovymud.object.registry.Registry.addRoom(BaseRoom)'
	 */
	public void testAddRoom() {
		MockControl mockCtrl = MockControl.createNiceControl(Room.class);
		final Room mockRoom = (Room) mockCtrl.getMock();
		MockControl obsCtrl = MockClassControl.createControl(Observable.class);
		final Observable obs = (Observable) obsCtrl.getMock();

		MockControl invCtrl = MockClassControl.createControl(InventoryHandler.class);
		final InventoryHandler inv = (InventoryHandler) invCtrl.getMock();

		Registry reg = new Registry(null) {

			@Override
			public void addMudObject(MudObject object) {
				// TODO Auto-generated method stub
				assertSame(mockRoom, object);
			}

			@Override
			public InventoryHandler getInventoryHandler() {
				// TODO Auto-generated method stub
				return inv;
			}

		};
		mockRoom.getContainerLocation();
		ObjectLocation loc = new ObjectLocation();
		loc.setDefinition("tomb/tomb1.groovy");
		loc.setBeanId("id");
		mockCtrl.setDefaultReturnValue(loc);
		mockRoom.setCurrentContainer(reg);
		mockCtrl.setDefaultVoidCallable();
		mockCtrl.replay();
		inv.addMudObject("id", mockRoom);
		invCtrl.setDefaultVoidCallable();
		invCtrl.replay();

		obs.addObserver(reg);
		obsCtrl.setDefaultVoidCallable();
		obsCtrl.replay();

		reg.addMudObject(loc.getBeanId(), mockRoom);

		mockCtrl.verify();
		invCtrl.verify();
		obsCtrl.verify();
	}

	/*
	 * Test method for
	 * 'org.groovymud.object.registry.Registry.addActivePlayer(Player,
	 * MudShell)'
	 */
	boolean addActivePlayerCalled;
	boolean addItemCalled;

	public void testAddActivePlayer() {
		MockControl mockctrl = MockClassControl.createControl(InventoryHandler.class);
		final Player p = (Player) playerCtrl.getMock();
		final LoginShell mudShell = new LoginShell();
		Registry reg = new Registry(null) {

			@Override
			public void addActivePlayerHandle(LoginShell shell, Player player) {
				// TODO Auto-generated method stub
				assertEquals(p, player);
				assertEquals(mudShell, shell);
				addActivePlayerCalled = true;
			}

		};

		reg.addActivePlayer(mudShell, p);
		assertTrue(addActivePlayerCalled);
	}

	/*
	 * Test method for
	 * 'org.groovymud.object.registry.Registry.removeaActivePlayer(MudShell)'
	 */
	public void testNotifyContents() {
		final HashSet items = new HashSet();
		Player item = (Player) playerCtrl.getMock();

		items.add(item);

		Registry reg = new Registry(null) {

			@Override
			public HashSet<MudObject> getMudObjects() {
				// TODO Auto-generated method stub
				return items;
			}
		};
		handler = (IObservable) item;
		event = new MessageEvent();
		item.update(item, event);
		playerCtrl.setVoidCallable(1);
		playerCtrl.replay();
		reg.notifyContents((IObservable) item, event);
		playerCtrl.verify();
	}

	IObservable handler = null;
	IScopedEvent event = null;
	int updateCnt = 0;
	MockControl playerCtrl = MockControl.createControl(Player.class);;

	boolean methodCalled;

	public void testDoUpdate() {
		final MessageEvent mE = new MessageEvent();
		final MovementEvent moveEvent = new MovementEvent(null, null, null, null);
		final DestroyEvent dE = new DestroyEvent();
		final SaveEvent sE = new SaveEvent();

		Registry reg = new Registry(null) {

			@Override
			public void update(IObservable object, IScopedEvent arg) {
				methodCalled = false;
				super.update(object, arg);
			}

			@Override
			protected void notifyAll(IScopedEvent arg) {
				// TODO Auto-generated method stub
				methodCalled = true;
				assertEquals(mE, arg);
			}

			@Override
			protected synchronized void doMovementEvent(IScopedEvent arg) {
				// TODO Auto-generated method stub
				methodCalled = true;
				assertEquals(moveEvent, arg);
			}

			@Override
			protected void doDestroyEvent(IScopedEvent arg) {
				// TODO Auto-generated method stub
				methodCalled = true;
				assertEquals(dE, arg);
			}

			@Override
			protected void doSaveEvent(IScopedEvent arg) {
				// TODO Auto-generated method stub
				methodCalled = true;
				assertEquals(sE, arg);
			}
		};
		IObservable p = (IObservable) playerCtrl.getMock();

		reg.update(p, mE);
		assertTrue(methodCalled);
		reg.update(p, moveEvent);
		assertTrue(methodCalled);
		reg.update(p, dE);
		assertTrue(methodCalled);
		reg.update(p, sE);
		assertTrue(methodCalled);
	}

}
