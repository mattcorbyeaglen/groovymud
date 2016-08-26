package commands.creator;

import utils.GenericSyntaxParser;

GenericSyntaxParser parser = new GenericSyntaxParser()
parser.parse(argstr, " ")
def obj = null
if(parser.subjectObject.objectName == "here"){
	obj = source.currentContainer
}else if(parser.subjectObject.objectName == "me"){
	obj = source
}else {
	obj = parser.subjectObject.findObjectInContainer(source.currentContainer)
	if(obj == null){
		obj = parser.subjectObject.findObjectInContainer(source)
	}	
}

if(obj != null){
	def observers = obj.observers
	source.terminalOutput.writeln "Observers for $argstr:"
	observers.each{ it ->
		source.terminalOutput.writeln it.toString()
	}
}else{
	source.terminalOutput.writeln "cannot find $argstr"
}