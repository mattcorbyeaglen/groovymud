package std.game.guild.skill;

import java.util.HashSet;
import java.util.Iterator;

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
public class SkillTree {

	Skill skill;
	HashSet children;

	public SkillTree(Skill o) {
		this.skill = o;
		children = new HashSet();
	}

	public int hashCode() {
		return getSkill().getName().hashCode();
	}

	public boolean addSkill(Skill o) {
		SkillTree newSkillTree = new SkillTree(o);
		if (contains(o)) {
			return false;
		}
		return children.add(newSkillTree);
	}

	public boolean contains(Skill skill) {
		if (this.getSkill().equals(skill)) {
			return true;
		}
		Iterator x = children.iterator();
		while (x.hasNext()) {
			SkillTree st = (SkillTree) x.next();
			if (st.contains(skill)) {
				return true;
			}
		}
		return false;
	}

	public SkillTree getChildSkillTree(String skillName) {
		if (getSkill().getName().equals(skillName)) {
			return this;
		}
		Iterator x = children.iterator();
		while (x.hasNext()) {
			SkillTree st = (SkillTree) x.next();
			SkillTree found = st.getChildSkillTree(skillName);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	public Skill getSkill() {
		return skill;
	}

	public void clear() {
		this.skill = null;
		this.children = new HashSet();
	}

	public int size() {
		int size = 1;
		Iterator ch = children.iterator();
		while (ch.hasNext()) {
			size += ((SkillTree) ch.next()).size();
		}
		return size;
	}
}
