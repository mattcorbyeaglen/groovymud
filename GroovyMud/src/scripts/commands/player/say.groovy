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

import org.groovymud.engine.event.messages.MessageEvent
import org.groovymud.engine.event.EventScope
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom

stream = source.getTerminalOutput()
handler = source.getCurrentContainer().getInventoryHandler()
contents = handler.getMudObjects(true)

srcSays = "You say: $argstr"
scpSays = "${source.getName()} says: $argstr"

sendMessageToRoom(source, srcSays, scpSays)
