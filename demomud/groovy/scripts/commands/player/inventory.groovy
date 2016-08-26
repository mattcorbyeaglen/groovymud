package commands.player

import org.groovymud.object.MudObject
import org.groovymud.object.Container

stream = source.getTerminalOutput()

def look = source.getView();

look.writeLookInventory(source);
