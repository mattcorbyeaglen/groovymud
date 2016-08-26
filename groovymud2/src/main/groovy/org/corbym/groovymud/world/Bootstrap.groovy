package org.corbym.groovymud.world

import scripts.generateNewWorldOrder
import org.corbym.simplex.persistence.SimplexDaoFactory
import org.corbym.groovymud.engine.GameEngine
import static org.corbym.groovymud.engine.event.SystemEventType.START_UP_EVENT


class Bootstrap {
    def dao = SimplexDaoFactory.instance
    def update(def event) {
            def world
            if (dao.singletonExists("world")) {
                world = dao.loadSingleton("world")
            } else {
                world = new generateNewWorldOrder().run();
            }
            GameEngine engine = GameEngine.instance
            engine.addActivity world
    }
    def accepts(def eventType){
        eventType == START_UP_EVENT
    }
}