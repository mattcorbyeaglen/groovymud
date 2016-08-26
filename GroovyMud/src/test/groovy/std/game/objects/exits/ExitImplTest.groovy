/**
 * Copyright 2008 Matthew Corby-Eaglen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package std.game.objects.exits

import org.easymock.classextension.MockClassControlimport org.groovymud.object.room.Roomimport org.easymock.MockControlimport org.groovymud.engine.event.messages.MessageEventimport org.groovymud.object.room.Exitimport org.groovymud.shell.command.CommandInterpreterimport org.groovymud.object.alive.Aliveimport org.groovymud.engine.event.EventScopeimport org.groovymud.engine.event.observer.Observableimport std.game.objects.alive.MOBImplimport std.game.objects.containers.RoomImplimport org.groovymud.object.ObjectLocationimport org.groovymud.engine.event.system.MovementEvent

/**
 * @author corbym
 *
 */
public class ExitImplTest extends GroovyTestCase{
	
	
	int call = 0;
	
	/* (non-Javadoc)
	 * @see std.game.objects.exits.ExitImpl#doMove(java.lang.Object, java.lang.Object, java.lang.Object)
	 */	
	public void testDoMove() throws ResourceException, ScriptException {
		final String[] mockMovingStringArray = new String[1]
		mockMovingStringArray[0] = new String("moved");
		
		Class[] evClazzArr =  new Class[1]
		evClazzArr[0] = EventScope
		
		Object[] objArr = new Object[1]
		objArr[0] = EventScope.ROOM_SCOPE
		
		MockClassControl leaveEventCtrl = (MockClassControl) MockClassControl.createControl(MessageEvent.class, evClazzArr, objArr);
		MockClassControl arriveEventCtrl = (MockClassControl) MockClassControl.createControl(MessageEvent.class, evClazzArr, objArr);
		
		MockControl srControl = MockControl.createControl(Room.class);
		MockControl fndControl = MockControl.createControl(Room.class);
		
		Room sourceRoom = (Room) srControl.getMock();
		Room foundRoom = (Room) fndControl.getMock();
		
		final ObjectLocation dest = new ObjectLocation(definition: "loc");
		
		final MessageEvent leaveEvent = (MessageEvent) leaveEventCtrl.getMock();
		final MessageEvent arriveEvent = (MessageEvent) arriveEventCtrl.getMock();
		MockControl obsCtrl = MockClassControl.createControl(Observable.class);
		final Observable movingObject = (Observable) obsCtrl.getMock();
		movingObject.fireEvent(leaveEvent);
		obsCtrl.setDefaultVoidCallable();
		movingObject.fireEvent(arriveEvent);
		obsCtrl.setDefaultVoidCallable();
		
		final MOBImpl aliveMovingObject = [
		getDepartureMessage : {dir ->  "boing" },		
		getArrivalMessage: {dir -> 	"freak" },
		fireEvent: {event -> 
			switch(call){
				case 1: 
				assertSame event, leaveEvent
				break;
				case 2: 
				assertSame event, arriveEvent
				break;
			}
			
		},
		setContainerLocation: {location -> assertEquals dest, location}] as MOBImpl 
		
		
		sourceRoom.removeMudObject(aliveMovingObject);
		srControl.setDefaultReturnValue(aliveMovingObject);
		
		foundRoom.addMudObject(aliveMovingObject);
		fndControl.setDefaultVoidCallable();
		srControl.replay();
		fndControl.replay();
		leaveEvent.setScope(EventScope.ROOM_SCOPE);
		leaveEvent.setSource(movingObject);
		leaveEventCtrl.setDefaultVoidCallable();
		leaveEvent.setSourceMessage("You go north.");
		leaveEventCtrl.setDefaultVoidCallable();
		leaveEvent.setScopeMessage("mover goes north.");
		leaveEventCtrl.setDefaultVoidCallable();
		
		arriveEvent.setScope(EventScope.ROOM_SCOPE);
		arriveEvent.setSource(movingObject);
		arriveEventCtrl.setDefaultVoidCallable();
		arriveEvent.setSourceMessage(null);
		arriveEventCtrl.setDefaultVoidCallable();
		arriveEvent.setScopeMessage("move arrives from the south.");
		arriveEventCtrl.setDefaultVoidCallable();
		
		leaveEventCtrl.replay();
		arriveEventCtrl.replay();
		
		MockControl intControl = MockClassControl.createControl(CommandInterpreter.class);
		
		final CommandInterpreter mockInterpreter = (CommandInterpreter) intControl.getMock();
		mockInterpreter.doCommand("look", null, aliveMovingObject);
		intControl.setDefaultReturnValue(null);
		intControl.replay();
		
		
		Exit testExit = [ 
				getDestination : {return dest},
				createMessageEvent : {
					if (call++ == 0) {
						return leaveEvent;
					}
					return arriveEvent;
				}, 
				castToObservable :  {alive ->
					assertEquals(aliveMovingObject, alive);
					return movingObject;
				}, 
				createMovingStringArray : { mo, direction ->
					assertEquals("mover", mo.getName());
					if (!direction.equals("north") && !direction.equals("south")) {
						fail(direction);
					}
					// TODO Auto-generated method stub
					return mockMovingStringArray;
				}, 
				getCommandInterpreter : { return mockInterpreter; }
				] as ExitImpl
		
		testExit.doMove(sourceRoom, foundRoom, aliveMovingObject);
		leaveEventCtrl.verify();
		arriveEventCtrl.verify();
		srControl.verify();
		fndControl.verify();
		intControl.verify();
	}
	

	
	/* (non-Javadoc)
	 * @see std.game.objects.exits.ExitImpl#go(org.groovymud.object.alive.Alive)
	 */
	final void testGo(){
		//TODO Implement the Testmethod Go
		Alive mob
		
		mob = [fireEvent: {event -> 
			event.class == MovementEvent
			println owner
			assertTrue event.movingObject == mob
		},
		getCurrentContainer : {  null }
		] as Alive
		
		
		Exit exit = new ExitImpl(direction: "west")
		
		exit.go(mob)
	}
	
	
}
