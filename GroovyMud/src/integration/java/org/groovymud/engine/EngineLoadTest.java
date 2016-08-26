package org.groovymud.engine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.groovymud.object.MockMudObject;
import org.groovymud.object.MudObject;
import org.groovymud.object.registry.InventoryHandler;
import org.groovymud.object.registry.Registry;

public class EngineLoadTest extends TestCase {

	public void testLoad() throws InterruptedException {
		InventoryHandler handler = new InventoryHandler();
		final Registry reg = new Registry(handler) {

		};

		final JMudEngine engine = new JMudEngine() {

			@Override
			public ShutdownBehaviour createShutdownBehaviour() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Registry getObjectRegistry() {
				// TODO Auto-generated method stub
				return reg;
			}

			@Override
			protected void handleTheNetDead() {

			}

			@Override
			protected void savePlayers() {
				// TODO Auto-generated method stub

			}

			@Override
			protected void resetRooms() {

			}

			@Override
			protected void checkPlayerHandles() {
			}

			@Override
			protected void doHeartBeat() {

				super.doHeartBeat();
			}
		};
		engine.setExecutor(Executors.newCachedThreadPool());
		engine.setRunning(true);
		engine.start();
		Thread.sleep(1000);
		for (int x = 0; x < 10000000; x++) {
			MockMudObject obj = new MockMudObject() {

				@Override
				public void heartBeat() {
					if ("wombat0".equals(getName())) {
						logging = true;
					}
					super.heartBeat();
				}
			};
			obj.setName("wombat" + x);

			if (reg.getMudObjects().size() % 1000 == 0) {
				System.out.println("objects.size: " + reg.getMudObjects().size());
			}
			reg.addMudObject(obj);
			Thread.yield();
		}

		Thread.sleep(10000);
		for (int x = 0; x < 10000000; x++) {
			reg.removeMudObject((MudObject) reg.getMudObjects().toArray()[reg.getMudObjects().size() - 1]);
			Thread.yield();
		}
		engine.requestShutDown(null);
	}
}
