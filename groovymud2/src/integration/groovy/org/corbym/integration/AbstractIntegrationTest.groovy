package org.corbym.integration

import com.jayway.awaitility.groovy.AwaitilitySupport
import groovy.time.Duration
import org.corbym.groovymud.engine.GameEngine
import org.corbym.groovymud.world.object.Exit
import org.corbym.groovymud.world.object.Location
import org.corbym.groovymud.world.object.MudItem
import org.corbym.simplex.persistence.SimplexDao
import org.corbym.simplex.persistence.SimplexDaoFactory

class AbstractIntegrationTest extends AwaitilitySupport{
    protected static SimplexDao dao = SimplexDaoFactory.instance
    protected MudItem mudItem
    protected Location location
    protected GameEngine gameEngine

    def theMudItem() {
        mudItem
    }

    def aDao(){
        dao = SimplexDaoFactory.instance
    }

    static Duration seconds(int seconds) {
        new Duration(0, 0, seconds, 0)
    }
    def theGameEngine() {
        this.gameEngine
    }

    def aGameEngine() {
        this.gameEngine = GameEngine.instance
        this
    }
    def aRunningGameEngine() {
        this.gameEngine = GameEngine.instance
        gameEngine.start()
        this
    }

    def theLocation() {
        location
    }

    def anotherLocationCalled(String name) {
        new Location(name: name)
    }

    def andTheLocationHasAnExit(def direction) {
        final exit = new Exit(name: "door").inTheDirectionOf(direction)
        location = new Location(contents: [:] as TreeMap).hasAn(exit)
        return exit
    }

    def theLocationIsSaved() {
        assertSaveItem(this.location);
    }

    Location containing(MudItem mudItem) {
        location.putAll(mudItem)
        return location
    }

    void theLocationCanBeReloaded() {
        assertLoadObject(Location, location.itemId)
    }

    def aMudItemCalled(String name) {
        return new MudItem(name: name)
    }

    def aLocationCalled(String name) {
        this.location = new Location(name: name, contents: [:] as TreeMap)
        return this
    }

    def theItemCanBeLoadedFromThePersistentStore() {
        assertLoadObject(MudItem.class, mudItem.itemId)
    }

    def assertLoadObject(Class clazz, def id) {
        assert dao.load(clazz, id)
    }

    def assertSaveItem(def mudItem) {
        dao.save(mudItem)
        assert mudItem.itemId
    }

    def aNewMudItemCalled(def name) {
        mudItem = new MudItem(name: name, wombat: "floop")
        return this
    }


}
