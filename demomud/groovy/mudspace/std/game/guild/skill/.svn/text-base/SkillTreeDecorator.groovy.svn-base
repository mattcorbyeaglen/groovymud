package std.game.guild.skill;

import java.util.StringTokenizer;

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
 * 
 * decorates the skilltree by allowing skills to be added in a string format eg
 * 
 * fighting.combat.dodging magic dodging.wombats etc
 * 
 * uses a dummy root node for the tree to bind them all together
 */
public class SkillTreeDecorator {

	private static final String ROOT_NODE_NAME = "dummy.";
	SkillTree tree;

	public SkillTreeDecorator() {
		super();
		tree = new SkillTree(new Skill("dummy", 0));
	}

	/**
	 * 
	 * finds a skill by its skillname, and returns parent skill unless its the
	 * dummy node in which case it returns null.
	 * 
	 * eg getSkillByName("fighting.combat") will return the parent skill
	 */
	public Skill getSkillByName(String skillName) {
		SkillTree found = findSkill(skillName);
		if (found.getSkill().getName().equals("dummy")) {
			return null;
		}
		return found.getSkill();
	}

	/**
	 * finds a skill with the skillname like fighting.combat or magic.spells or
	 * something if it can't find the skill it returns the parent
	 */
	public SkillTree findSkill(String skillName) {
		SkillTree found = findSkill(getTree(), ROOT_NODE_NAME + skillName);
		return found;
	}

	/**
	 * finds skills with using . deliminated string of skills
	 **/
	protected SkillTree findSkill(SkillTree currentTree, String skillName) {
		StringTokenizer toks = new StringTokenizer(skillName, ".");
		while (toks.hasMoreTokens()) {
			String skilltok = toks.nextToken();
			SkillTree sk = currentTree.getChildSkillTree(skilltok);
			if (sk != null && toks.hasMoreTokens()) {
				StringBuffer buffer = new StringBuffer(skillName);
				buffer.delete(skillName.indexOf(skilltok), skilltok.length() + 1);
				sk = findSkill(sk, buffer.toString());
			}
			return sk != null ? sk : currentTree;
		}
		return getTree();
	}

	/**
	 * add a skill to teh skilltree
	 */
	public boolean addSkill(String skillName, long value) {
		SkillTree found = findSkill(getTree(), ROOT_NODE_NAME + skillName);
		String trueName = findSkillTrueName(skillName);
		return found.addSkill(new Skill(trueName, value));
	}

	private String findSkillTrueName(String skillName) {
		return skillName.substring(skillName.lastIndexOf(".") + 1, skillName.length());
	}

	public int size() {
		return getTree().size() - 1;
	}

	protected SkillTree getTree() {
		return tree;
	}

}
