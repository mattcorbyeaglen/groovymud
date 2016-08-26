package org.groovymud.object.registry;

import java.util.LinkedList;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.groovymud.object.MudObject;

public class InventoryHandlerTest extends TestCase {

	
	
	public void testAddMudObjectStringMudObject() {
		InventoryHandler hand = new InventoryHandler();
		MockControl ctrl = MockControl.createNiceControl(MudObject.class);
		MudObject obj = (MudObject) ctrl.getMock();
		obj.getName();
		ctrl.setDefaultReturnValue("name");
		obj.getShortNames();
		ctrl.setDefaultReturnValue(new LinkedList());
		ctrl.replay();
		
		hand.addMudObject(obj);
		assertTrue(hand.size() == 1);
		hand.addMudObject(obj);
		assertTrue(hand.size() == 1);
		ctrl.verify();
		
		MockControl ctrl2 = MockControl.createNiceControl(MudObject.class);
		MudObject obj2 = (MudObject) ctrl2.getMock();
		obj2.getName();
		ctrl2.setDefaultReturnValue("name");
		obj2.getShortNames();
		ctrl2.setDefaultReturnValue(new LinkedList());
		ctrl2.replay();
		hand.addMudObject(obj2);
		assertTrue(hand.size() == 2);

		ctrl2.verify();
	}

	public void testRemoveMudObjectMudObject() {
		InventoryHandler hand = new InventoryHandler();
		
		MockControl ctrl = MockControl.createNiceControl(MudObject.class);
		MudObject obj = (MudObject) ctrl.getMock();
		obj.getName();
		ctrl.setDefaultReturnValue("name");
		obj.getShortNames();
		ctrl.setDefaultReturnValue(new LinkedList());
		ctrl.replay();
		
		hand.addMudObject(obj);
		
		assertTrue(hand.size() == 1);
		hand.removeMudObject(obj);
		assertTrue(hand.size() == 0);
		assertTrue(hand.getMudObject("obj") == null);
		hand.addMudObject(obj);
		assertTrue(hand.size() == 1);
		ctrl.verify();
	}

	public void testRemoveMudObjectStringInt() {
		InventoryHandler hand = new InventoryHandler();
		MockControl ctrl = MockControl.createNiceControl(MudObject.class);
		MudObject obj = (MudObject) ctrl.getMock();
		obj.getName();
		ctrl.setDefaultReturnValue("obj");
		obj.getShortNames();
		ctrl.setDefaultReturnValue(new LinkedList());
		ctrl.replay();
		hand.addMudObject(obj);

		MockControl ctrl2 = MockControl.createNiceControl(MudObject.class);
		MudObject obj2 = (MudObject) ctrl2.getMock();
		obj2.getName();
		ctrl2.setDefaultReturnValue("obj");
		obj2.getShortNames();
		ctrl2.setDefaultReturnValue(new LinkedList());		
		ctrl2.replay();
		hand.addMudObject(obj2);
		
		assertTrue(hand.size() == 2);
		hand.removeMudObject("obj", 1);
		assertTrue(hand.size() == 1);
		ctrl.verify();
		ctrl2.verify();
	}

}
