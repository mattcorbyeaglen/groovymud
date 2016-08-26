package commands.creator;
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
*/import org.groovymud.shell.telnetd.ExtendedTerminalIO
import net.wimpi.telnetd.io.BasicTerminalIO
import static net.wimpi.telnetd.io.BasicTerminalIO.*

ExtendedTerminalIO stream = source.terminalOutput

if(argstr == null){
	stream.writeln("You need to specify a filename to edit.")
	return
}
stream.setLinewrapping(false)
enum Mode {
	INSERT, APPEND, DELETE, MOVE
}
Mode currentMode = Mode.MOVE

int xPos = 0
int yPos = 0
def file = new File(argstr)
def fileContents = "" 

if(file.exists()){
	fileContents = file.getText()
}else{
	file.createNewFile()
}

List<StringBuffer> buffers = fileContents.tokenize('\n').collect{ new StringBuffer(it) }

stream.eraseToBeginOfScreen()
stream.write(fileContents)
stream.moveCursor(xPos, yPos)

boolean exit = false
def redrawLine = {
	stream.eraseToBeginOfLine()
	stream.write buffers[yPos]
    stream.moveCursor(xPos, yPos)
}
Map commandMap = [
	"i": {currentMode = INSERT}, 
	"a" : {
		currentMode = APPEND
		stream.moveCursor(buffer[yPos].length, yPos)
	}, 
	"x" : {
		  if(currentMode != INSERT || currentMode != APPEND){
			  buffers[yPos].deleteCharAt(xPos)
			  redrawLine
		  }
	},
	UP :{ 
	    	 if(yPos != 0){
	    		 yPos = yPos - 1
	    		 if(xPos > buffers[yPos].length){
	    			 xPos = buffers[yPos].length
	    		 }
	    		 stream.moveCursor(xPos, yPos)
	    	 }
	},
	DOWN : {
	    	 if(yPos != buffers.size()){
	    		 yPos = yPos + 1
	    		 if(xPos > buffers[yPos].length){
	    			 xPos = buffers[yPos].length
	    		 }
	    		 stream.moveCursor(xPos, yPos)
	    	 }
	     },
	LEFT : {	     
	    	 if(xPos != 0){
	    		 xPos = xPos - 1		            		 
	    		 stream.moveCursor(xPos, yPos)
	    	 }
	},
	RIGHT: {
		 if(xPos != buffers[yPos].length){
			 xPos = xPos + 1		            		 
			 stream.moveCursor(xPos, yPos)
		 }
	},
	ESCAPE : {
		if(currentMode == MOVEMENT){
			buffers.each{
				file.write("${it}\n") // save the file
				stream.eraseToBeginOfScreen()
				stream.writeln(".. saved ${argstr}")
			}
			
			exit = true
		}
	}
]

Map insertModeMap = [
    ESCAPE : {currentMode = MOVEMENT},
    ENTER : {
    	buffers.add(yPos, new StringBuffer(""))
    },
    DELETE : {
    	// do some deleting here
    }
]

while(!exit){
	char ch = stream.read()
	if(currentMode == INSERT || currentMode == APPEND){		                             
		StringBuffer strbuf= buffers[yPos]
        if(insertModeMap.respondsTo("${ch}")){
        	insertModeMap."${ch}"
        }else{
	        strbuf.insert(xPos, ch)
	        redrawLine
        }
	}else{
		if(commandMap.respondsTo("${ch}")){
			commandMap."${ch}"
		}
		
	}
}
stream.setLinewrapping(true)


