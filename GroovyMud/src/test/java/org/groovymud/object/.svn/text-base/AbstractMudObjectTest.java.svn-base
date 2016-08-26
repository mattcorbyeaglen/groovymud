package org.groovymud.object;

import java.io.FileNotFoundException;

import junit.framework.TestCase;

import org.codehaus.groovy.control.CompilationFailedException;
import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.object.registry.MudObjectAttendant;

public class AbstractMudObjectTest extends TestCase {

	public void testSetCurrentContainer() {
		//        BaseMudObject obj = new BaseMudObject() {
		//
		//            public void doHeartBeat() {
		//                // TODO Auto-generated method stub
		//
		//            }
		//
		//            public void initialise() {
		//                // TODO Auto-generated method stub
		//
		//            }
		//
		//        };
		//        BaseContainer container = new BaseContainer() {
		//
		//            public String getScriptFileName() {
		//                // TODO Auto-generated method stub
		//                return "container";
		//            }
		//
		//            public void doHeartBeat() {
		//                // TODO Auto-generated method stub
		//
		//            }
		//
		//            public void initialise() {
		//                // TODO Auto-generated method stub
		//
		//            }
		//
		//        };
		//        obj.setCurrentContainer(container);
		//
		//        assertEquals(obj.getCurrentContainer(), container);
		//        assertEquals("container", obj.getCurrentContainerFileName());

	}

	/*    public void testFireEvent() {
	        MockControl eventCtrl = MockControl.createControl(IScopedEvent.class);
	        final IScopedEvent event = (IScopedEvent) eventCtrl.getMock();
	        event.getSource();
	        eventCtrl.setDefaultReturnValue(null);

	        BaseMudObject obj = new BaseMudObject() {

	            public void doHeartBeat() {
	                // TODO Auto-generated method stub

	            }

	            public void initialise() {
	                // TODO Auto-generated method stub

	            }

	            public synchronized void notifyObservers(IScopedEvent arg) {
	                // TODO Auto-generated method stub
	                assertEquals(event, arg);

	            }

	            public void setWeight(long weight) {
	                // TODO Auto-generated method stub

	            }
	        };
	        event.setSource(obj);
	        eventCtrl.setDefaultVoidCallable();
	        eventCtrl.replay();

	        ((Observable) obj).fireEvent(event);

	    }

	    public void testLoad() throws CompilationFailedException, FileNotFoundException, InstantiationException {
	        String path = "/path/";
	        BaseMudObject object = new BaseMudObject() {

	            public void doHeartBeat() {
	                // TODO Auto-generated method stub

	            }

	            public void initialise() {
	                // TODO Auto-generated method stub

	            }

	            public void setWeight(long weight) {
	                // TODO Auto-generated method stub

	            }

	        };
	        MockControl attendantCtrl = MockClassControl.createControl(MudObjectAttendant.class);

	        final MudObjectAttendant mockAttendant = (MudObjectAttendant) attendantCtrl.getMock();

	        mockAttendant.load(path);
	        attendantCtrl.setDefaultReturnValue(object);
	        attendantCtrl.replay();
	        BaseMudObject loader = new BaseMudObject() {

	            public void doHeartBeat() {
	                // TODO Auto-generated method stub

	            }

	            public void initialise() {
	                // TODO Auto-generated method stub

	            }

	            public MudObjectAttendant getMudObjectAttendant() {
	                // TODO Auto-generated method stub
	                return mockAttendant;
	            }

	            public void setWeight(long weight) {
	                // TODO Auto-generated method stub

	            }
	        };
	        assertEquals(object, loader.getAttendant().load(path));
	    }
	*/
}
