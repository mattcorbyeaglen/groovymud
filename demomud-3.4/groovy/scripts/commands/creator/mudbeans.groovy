/**
 * 
 */
package commands.creator


try{
	BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context, MudObject).eachWithIndex{ name, idx ->
		stream.writeln("$name")
	}
}catch(Exception e ){
	stream.writeln(e.getMessage())
}