package std.game.objects.alive

import java.util.List;

import javax.security.auth.Subject;

import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.action.SaveEvent;
import org.groovymud.object.alive.Player;
import org.groovymud.shell.io.filters.TerminalIOFilter;
import org.groovymud.shell.security.PlayerCredentials;

/* Copyright 2008 Matthew Corby-Eaglen
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



class PlayerImpl extends MOBImpl implements Player {
	Subject subject;
	PlayerCredentials playerCredentials
	List<TerminalIOFilter> filters = [];
	/**
	 * override initialiase, we dont want the player registered until we are ready
	 */
	void initialise() {
		if(filters != null){
			terminalOutput.filterChain.setFilters(filters)
		}
	}
	
	void dest(boolean wep) {
		if (!wep) {
			SaveEvent save = new SaveEvent();
			save.setSource(this);
			save.setScope(EventScope.GLOBAL_SCOPE);
			fireEvent(save);
		}
		super.dest wep;
	}
	
	
	
	
}