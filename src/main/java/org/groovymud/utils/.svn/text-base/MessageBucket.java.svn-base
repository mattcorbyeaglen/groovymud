package org.groovymud.utils;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author corbym
 * @deprecated
 */
public class MessageBucket {

	private Map<String, String> messageBucket;

	public Map<String, String> getMessageBucket() {
		if (messageBucket == null) {
			messageBucket = Collections.synchronizedMap(new HashMap<String, String>());
		}
		return messageBucket;
	}

	public Object putMessage(String key, String value) {
		return getMessageBucket().put(key, value);
	}

	public String getMessage(String key) {
		// TODO Auto-generated method stub
		return getMessageBucket().get(key);
	}

}
