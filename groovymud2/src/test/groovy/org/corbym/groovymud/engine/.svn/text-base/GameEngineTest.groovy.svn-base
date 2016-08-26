package org.corbym.groovymud.engine

import org.corbym.groovymud.engine.event.SystemEventType
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.fail
import java.util.concurrent.TimeUnit
import com.jayway.awaitility.groovy.AwaitilitySupport
import org.hamcrest.Matchers
import static org.junit.Assert.assertThat

class GameEngineTest extends AwaitilitySupport{
    GameEngine gameEngine

    @Before
    void setUp() {
        gameEngine = GameEngine.instance
    }
    @Test
    void "Game engine can start and sends a startup event "() {
        boolean listenerMethodWasCalled = false

        def startUpdate = [update: { event ->
            if (!listenerMethodWasCalled)
                assert event.type == SystemEventType.START_UP_EVENT
            listenerMethodWasCalled = true
        }, accepts:{evenType->true}]
        gameEngine.addListener(startUpdate)
        gameEngine.start()

        await().atMost(5, TimeUnit.SECONDS).until{ listenerMethodWasCalled && gameEngine.started }
    }

    @Test
    void "Game Engine can send a shutdown event to a shutdown listener"() {
        boolean listenerMethodWasCalled = false

        def gameEngineListener = [update: { event ->
            if(!listenerMethodWasCalled)
                assert event.type == SystemEventType.SHUT_DOWN_EVENT
                listenerMethodWasCalled = true
        }, accepts:{eventType-> eventType == SystemEventType.SHUT_DOWN_EVENT}];
        gameEngine.addListener(gameEngineListener)

        gameEngine.shutdown()
        assertThat listenerMethodWasCalled, Matchers.is(true)
    }

    @Test
    void "Game engine should run a game loop that calls a game activity and stop when requested"() {
        int called = 0;
        def gameActivity = [execute: {->
            called++
        }]

        gameEngine.addActivity gameActivity
        gameEngine.start()

        await().atMost(5, TimeUnit.SECONDS).until{ called > 1 && gameEngine.gameActivitiesThread != null}
    }

    void "game engine should call shutdown listeners in the order they were added"() {
        LinkedList listenerMethodWasCalled = []

        def gameEngineListener1 = [update: { event ->
            listenerMethodWasCalled << 1
            assert event.type == SystemEventType.SHUT_DOWN_EVENT
        }, accepts:{eventType->eventType == SystemEventType.SHUT_DOWN_EVENT}];
        def gameEngineListener2 = [update: { event ->
            listenerMethodWasCalled << 2
            assert event.type == SystemEventType.SHUT_DOWN_EVENT
        }, accepts:{eventType->eventType == SystemEventType.SHUT_DOWN_EVENT}];
        gameEngine.addListener(gameEngineListener1)
        gameEngine.addListener(gameEngineListener2)

        gameEngine.shutdown()
        await().atMost(5, TimeUnit.SECONDS).until{ !gameEngine.started }

        def lastIt = 0
        listenerMethodWasCalled.eachWithIndex {it, idx ->
            assert it > lastIt
            lastIt = it
        }
    }

    void "game engine should call startup listeners in the order they were added"() {
        LinkedList listenerMethodWasCalled = []

        def gameEngineListener1 = [update: { event ->
            listenerMethodWasCalled << 1
            assert event.type == SystemEventType.START_UP_EVENT
        }, accepts:{evenType->true}]
        def gameEngineListener2 = [update: { event ->
            assert event.type == SystemEventType.START_UP_EVENT
        }, accepts:{evenType->true}]
        gameEngine.addListener(gameEngineListener1)
        gameEngine.addListener(gameEngineListener2)

        gameEngine.start()
        listenerMethodWasCalled.eachWithIndex {it, idx ->
            assert idx == it
        }
    }
    @Test
    void "game engine does not call update if listener cannot accept shutdownevents"(){
        def gameEngineListener = [accepts:{evenType->false}, update: {event-> fail("Should not call update on shutdown")}];
        gameEngine.addListener(gameEngineListener)
        gameEngine.shutdown()
    }
    @Test
    void "game engine does not call update if listener cannot accept startupevents"(){
        def gameEngineListener = [accepts:{evenType->false}, update: {event-> fail("Should not call update on shutdown")}];
        gameEngine.addListener(gameEngineListener)
        gameEngine.start()
    }

}
