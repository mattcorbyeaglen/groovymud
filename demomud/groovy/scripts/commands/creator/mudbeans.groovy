/**
 * 
 */
package commands.creator


try{
	attendant.getObjectHandles().eachWithIndex{ name, idx ->
		stream.writeln("$name")
	}
}catch(Exception e ){
	stream.writeln(e.getMessage())
}