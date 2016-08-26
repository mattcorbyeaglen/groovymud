/**
 * Copyright 2008 Matthew Corby-Eaglen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package utils

import java.io.FilenameFilter;
import java.security.AccessController;
import org.groovymud.shell.security.MudPermission;

class FileFilter implements FilenameFilter {
	String extension
	public boolean accept(File f, String filename) {  
		return filename.endsWith(extension)  
	}  
}  
public class DirectoryList{
	String extension = "groovy"
		
	public void list(player, directory, level){

		def stream = player.getTerminalOutput()
		AccessController.checkPermission(new MudPermission("commands.${level}.*"));
		new File("${directory}/").listFiles(new FileFilter(extension: extension)).eachWithIndex{file, idx ->
			if(idx % 5 == 0){
				stream.writeln(file.getName().minus(".groovy"))
			}else{
				stream.write("${file.getName().minus('.groovy')}\t")
			}
		}
		stream.writeln("")
		stream.writeln("Use command [commandname] to find out more about the command.");
	}
	
}
