package commands.player

import groovy.util.slurpersupport.NoChildren

def root = new XmlSlurper().parse(new File("$scriptSpace/commands/player/man-text.xml"))

def manRecord = root.entry.find{ it.@name == args[0] }
def io = source.getTerminalOutput()
if(manRecord.size() > 0){
	if(manRecord.gpath != null){
		io.writeln(manRecord.gpath)
	}else{
		def desc = manRecord.description.text().replaceAll('\t', '').replaceAll('  ', '')
		io.writeln(desc)
		if(manRecord.syntax.size() > 0){
			io.writeln("Syntax:");
			io.writeln(manRecord.syntax.text().replaceAll('\t', ''))
		}
		if(manRecord.example.size() > 0){
			io.writeln("Example:");
			io.writeln(manRecord.syntax.text().replaceAll('\t', ''))
		}
		if(manRecord.seealso.size() > 0){
			io.writeln("See also:")
			io.writeln(manRecord.seealso.text().replaceAll('\t', ''))
		}
	}
}else{
	io.writeln("No man entry for ${args[0]}")
}