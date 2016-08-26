package org.corbym.groovymud.engine

import org.corbym.groovymud.engine.event.Event
import static org.corbym.groovymud.engine.event.SystemEventType.SHUT_DOWN_EVENT
import static org.corbym.groovymud.engine.event.SystemEventType.START_UP_EVENT

/**
 * Main game engine, start this to start the mud up
 *
 */
@Singleton
class GameEngine {
    def listeners = []
    def activities = []

    boolean started = false
    Thread gameActivitiesThread
    static final HEART_BEAT_SLEEP_TIME_MS = 1000

    def start() {
        listeners.each {it ->
            if(it.accepts(START_UP_EVENT)){
                it.update(new Event(type: START_UP_EVENT))
            }
        }
        started = true
        beginGameActivities()
    }

    private def beginGameActivities() {
        gameActivitiesThread = Thread.start() {
            while (started) {
                activities.each {
                    it.execute()
                }
                Thread.sleep(HEART_BEAT_SLEEP_TIME_MS)
            }
        }

    }

    def shutdown() {
        listeners.each {it ->
            if(it.accepts(SHUT_DOWN_EVENT)){
                it.update new Event(type: SHUT_DOWN_EVENT)
            }
        }
        started = false;
    }

    def addActivity(def activity) {
        activities.add activity
    }

    def addListener(def gameEngineListener) {
        listeners.add(gameEngineListener)
    }
}
