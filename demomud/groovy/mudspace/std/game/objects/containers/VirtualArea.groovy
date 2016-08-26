package std.game.objects.containers
;

import org.groovymud.object.ObjectLocation;
import org.groovymud.object.registry.InventoryHandler;
import org.groovymud.object.room.Room;
import org.groovymud.object.views.View;

import std.game.objects.containers.RoomImpl;


class VirtualArea extends ContainerImpl{
	def tiles = [:]
	
	def liveArea = [:] // area that has already been explored virtualobject location as the key

	def features = [:] // map of featured rooms, virtualobject location as the key
	View view
	void initialise(){	
		// load tiles
	}
	
}

class VirtualLocation extends ObjectLocation{
	def xPos;
	def yPos;
}
