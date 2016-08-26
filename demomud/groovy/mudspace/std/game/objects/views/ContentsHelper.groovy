package std.game.objects.views;

import static utils.WordUtils.affixIndefiniteArticle;
import static utils.WordUtils.capitalize;
import static utils.WordUtils.pluralize;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;

public class ContentsHelper {

	/**
	 * helper method to get a description of the contents in a map. the map
	 * would contain name/value pairs from getMudObjectsMap() although this can
	 * be any map with name, hashset<mudobject> values
	 * 
	 * @param contents
	 * @param looker
	 * @param alive
	 * @return
	 * @throws IOException
	 */
	public String getContentsDescription(Map<String, Set<MudObject>> contents, Alive looker, boolean alive) {
		return getContentsDescription(contents, looker, alive, true);
	}

	public String getContentsDescription(Map<String, Set<MudObject>> contents, Alive looker, boolean alive, boolean capitalizeFirst) {
		def description = ""
		Map contentCopy = new HashMap(contents);
		removeMudObject(looker, contentCopy);
		contentCopy.eachWithIndex {key, value, idx ->
			def objects = new LinkedList(value)
			MudObject obj = objects[0];
			def keyText = ""
			if (idx > 0) {
				if (idx > contentCopy.size() - 2) {
					description += " and ";
				} else {
					description += ", "
				}
			}
			if (objects.size() > 1) {
				keyText = pluralize(key, objects.size()); // there are two of them, we
				// just use the name
				// pluralized
			}
			if (objects.size() == 1) {
				keyText = affixIndefiniteArticle(obj); // use the object name
			}
			if (idx == 0 && capitalizeFirst) {
				keyText = capitalize(keyText);
			}
			description += keyText
		}
		return description;
	}

	/**
	 * removes the specified object from a map of contents warning: alters the
	 * contents map
	 * 
	 * @param obj
	 * @param contents
	 * @param keys
	 */
	public void removeMudObject(MudObject obj, Map<Object, HashSet<MudObject>> contents) {
		def keySet = new HashSet(contents.keySet());
		keySet.each{ it ->
			HashSet set = (HashSet) contents.get(it);
			set.remove(obj);
			if (set.isEmpty()) {
				contents.remove(it);
			}
		}
	}
}
