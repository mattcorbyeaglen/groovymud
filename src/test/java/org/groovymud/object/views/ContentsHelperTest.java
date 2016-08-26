package org.groovymud.object.views;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;

public class ContentsHelperTest extends TestCase {

	MockControl ctrl;
	MockControl aliveCtrl;
	
	@Override
	protected void setUp() throws Exception {
		ctrl = MockControl.createControl(MudObject.class);
		aliveCtrl = MockControl.createControl(Alive.class);
	}

	public void testGetContentsDescription() {
		Set<MudObject> objects = new HashSet<MudObject>();
		objects.add(createMudObject());
		Set<MudObject> objects2 = new HashSet<MudObject>();
		MudObject two = createMudObject();
		objects.add(two);

		Map<String, Set<MudObject>> contents = new TreeMap<String, Set<MudObject>>();
		contents.put("something", objects);
		contents.put("somethingelse", objects2);

		ContentsHelper helper = new ContentsHelper();
		String desc = helper.getContentsDescription(contents, (Alive) two, true);
		assertEquals("A something", desc);
	}

	public void testGetContentsDescriptionTwoOthers() {
		Set<MudObject> objects = new HashSet<MudObject>();
		objects.add(createMudObject());
		objects.add(createMudObject());

		Set<MudObject> objects2 = new HashSet<MudObject>();
		MudObject two = createAliveObject();
		objects.add(two);
		Set<MudObject> objects3 = new HashSet<MudObject>();
		objects3.add(createMudObject());

		Map<String, Set<MudObject>> contents = new TreeMap<String, Set<MudObject>>();
		contents.put("something", objects);
		contents.put("somethingelse", objects2);
		contents.put("one more", objects3);

		ContentsHelper helper = new ContentsHelper();
		String desc = helper.getContentsDescription(contents, (Alive) two, true);
		assertEquals("A one more and two somethings", desc);
	}

	protected MudObject createMudObject() {
		return (MudObject) ctrl.getMock();
	}
	protected Alive createAliveObject() {
		return (Alive) aliveCtrl.getMock();
	}
}
