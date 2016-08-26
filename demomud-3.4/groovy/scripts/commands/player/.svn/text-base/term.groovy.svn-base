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
package commands.player
import net.wimpi.telnetd.io.terminal.TerminalManager;

def manager = TerminalManager.getReference()
def terms = manager.availableTerminals as List
def io = source.getTerminalOutput()
def termType = args[0].trim()
if(terms.contains(termType)){
	io.setTerminal(termType)
	io.writeln("Term set to " + termType)
}else if(termType == "types"){
	io.writeln("Valid term types:")
	terms.each{it -> io.writeln(it)}
}else{
	io.writeln("Invalid term type: " + termType)
}

