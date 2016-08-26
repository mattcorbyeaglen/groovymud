/**
 * 
 */
package commands.creator
import org.groovymud.object.MudObjectimport org.springframework.beans.factory.BeanFactoryUtilsdef stream = source.getTerminalOutput()

try{
	BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context, MudObject).eachWithIndex{ name, idx ->
		stream.writeln("$name")
	}
}catch(Exception e ){
	stream.writeln(e.getMessage())
}