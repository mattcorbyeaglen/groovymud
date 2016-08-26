package org.groovymud.utils;

import java.util.Iterator;
import java.util.List;

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
/**
 * not used any more
 * @deprecated
 */
public class MessageMunger {

	public final static String mungeMessage(String message, String[] replacements) {
		for (int x = 0; x < replacements.length; x++) {
			message = message.replaceAll("\\%" + x, replacements[x]);
		}
		return message;
	}

	/**
	 * first and second objects in list are source and target must use ${0} ${1}
	 * notation on multiple targetted message eg. ${0} casts spell on ${1} ${0}
	 * moves ${1}
	 * 
	 */
	public final static String mungeMessage(String message, MudObject messageFor, List objects) {
		Iterator x = objects.iterator();
		int count = 0;
		while (x.hasNext()) {
			Object object = x.next();
			if (object instanceof MudObject) {
				MudObject mudObject = (MudObject) object;
				if (messageFor.equals(mudObject)) {
					message = message.replaceFirst("\\%" + count++, "You");
				} else {
					String name = (String) mudObject.getShortNames().toArray()[0];
					message = message.replaceAll("\\%" + count++, (mudObject.isArticleRequired() ? WordUtils.affixIndefiniteArticle(name) : name));
				}
			}
		}
		return message;
	}

}
