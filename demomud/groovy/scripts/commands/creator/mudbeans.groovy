/**
 * 
 */
package commands.creator
import org.groovymud.object.MudObjectimport org.springframework.beans.factory.BeanFactoryUtilsdef stream = source.getTerminalOutput()

try{
	attendant.getObjectHandles().eachWithIndex{ name, idx ->
		stream.writeln("$name")
	}
}catch(Exception e ){
	stream.writeln(e.getMessage())
}