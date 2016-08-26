package commands.god
/** Copyright 2008 Matthew Corby-Eaglen
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0 
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License. 
*/
import groovy.util.slurpersupport.NoChildren

def root = new XmlSlurper().parse(new File("$scriptSpace/commands/god/man-text.xml"))

def manRecord = root.entry.find{ it.@name == args[0] }
def io = source.getTerminalOutput()
if(manRecord.size() > 0){
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
}else{
	io.writeln("No man entry for ${args[0]}")
}