package commands.creator;

stream = source.terminalOutput

def allObjects = registry.inventoryHandler.mudObjectHashSets

allObjects.each{ key, value ->
	stream.write "\n$key"
	value.eachWithIndex {it, idx ->  
		stream.writeln "\t\t\t====>${it.name} (${idx})"
	}
	
}