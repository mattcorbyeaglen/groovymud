package scripts

import org.corbym.groovymud.world.World
import org.corbym.simplex.persistence.SimplexDaoFactory
import org.corbym.groovymud.world.object.Location
import org.corbym.groovymud.world.World
import org.corbym.groovymud.world.object.Location

println "generating new world ..."
dao = SimplexDaoFactory.instance

//just one room

def startRoom = new Location(name:"Starting Room", contents: [:] as TreeMap)

final world = new World()
world.put startRoom.name, startRoom