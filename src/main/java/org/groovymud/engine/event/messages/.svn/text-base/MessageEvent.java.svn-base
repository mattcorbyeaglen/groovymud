package org.groovymud.engine.event.messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.groovymud.engine.event.EventScope;
import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.engine.event.observer.Observable;
import org.groovymud.object.MudObject;

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

public class MessageEvent implements IScopedEvent {

	private Object source;
	private List<MudObject> targets;

	private EventScope scope;

	private String sourceMessage;
	private String targetMessage;
	private String scopeMessage;

	public MessageEvent(EventScope scope) {
		targets = Collections.synchronizedList(new ArrayList<MudObject>());
		this.scope = scope;
	}

	public MessageEvent() {
		targets = Collections.synchronizedList(new ArrayList<MudObject>());
		this.scope = EventScope.PLAYER_SCOPE;
	}

	public EventScope getScope() {
		return scope;
	}

	public void setScope(EventScope scope) {
		this.scope = scope;
	}

	public String getTargetMessage() {
		return targetMessage;
	}

	public void setTargetMessage(String targetMessage) {
		this.targetMessage = targetMessage;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public List<MudObject> getTargets() {
		return targets;
	}

	public boolean addTarget(MudObject o) {
		return targets.add(o);
	}

	public boolean removeTarget(MudObject o) {
		return targets.remove(o);
	}

	public String getMessage(MudObject messageFor) {
		if (messageFor.equals(source) && getSourceMessage() != null) {
			return getSourceMessage();
		}
		if (targets != null && targets.contains(messageFor)) {
			return getTargetMessage();
		}
		return messageFor.equals(source) ? "" : getScopeMessage();
	}

	public String getScopeMessage() {
		return scopeMessage;
	}

	public void setScopeMessage(String scopeMessage) {
		this.scopeMessage = scopeMessage;
	}

	public String getSourceMessage() {
		return sourceMessage;
	}

	public void setSourceMessage(String sourceMessage) {
		this.sourceMessage = sourceMessage;
	}

	public void setTargets(List<MudObject> targets) {
		this.targets = targets;
	}

	public IScopedEvent copy() {
		try {
			return (IScopedEvent) this.clone();
		} catch (CloneNotSupportedException e) {
			logger.error(e, e);
		}
		return null;
	}
}
