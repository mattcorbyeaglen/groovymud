package commands.creator;

import org.groovymud.object.Container;
import org.groovymud.object.ObjectLocation;

import utils.GenericSyntaxParser;

/**
 * clones one object
 * Syntax: clone subjectObject [into prepositionObject | me | here ]
 * @author corbym
 *
 */

GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, "into")

def matchedItem = parser.subjectObject
def matchedContainer = parser.prepositionObject
Container container = null
if(matchedContainer?.objectName == "here" || matchedContainer == null){
	container = source.currentContainer
}
if(matchedContainer?.objectName == "me"){
	container = source
}
if(container == null){
	container = matchedContainer?.findObjectInContainer(source)
	if(container == null){
		container = matchedContainer?.findObjectInContainer(source.currentContainer)
	}
}
if(container != null){
	try{
		
		def object = attendant.cloneObject(matchedItem.objectName as String)
		container.addMudObject(object)
	}catch(Exception e ){
		source.terminalOutput.writeln e.getMessage() + ": $argstr"
		e.printStackTrace()
	}
}