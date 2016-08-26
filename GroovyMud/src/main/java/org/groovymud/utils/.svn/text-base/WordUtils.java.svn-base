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
import java.util.ArrayList;
import java.util.List;

import org.groovymud.object.MudObject;
import org.jvnet.inflector.Rule;
import org.jvnet.inflector.lang.en.NounPluralizer;
import org.jvnet.inflector.rule.SuffixInflectionRule;

/**
 * helps dealing with words and articles
 * 
 * @author matt
 * 
 */
public class WordUtils {

	private static NounPluralizer pluralizer;

	static {
		pluralizer = new NounPluralizer();
		List<Rule> rules = new ArrayList<Rule>(pluralizer.getRules());
		// all things ending in thief should be ves
		rules.add(0, new SuffixInflectionRule("-thief", "-f", "-ves"));
		pluralizer.setRules(rules);// thief
	}

	/**
	 * pluralizes a word
	 * 
	 * @param word
	 * @return
	 */
	public static String pluralize(String word) {
		return pluralizer.pluralize(word);
	}

	/**
	 * 
	 * pluralizes words, and capilatizes the first letter of the result such
	 * that:
	 * 
	 * assert pluralize("dog", 13, true) == "Thirteen dogs" assert
	 * pluralize("bottles of water", 13, true) == "Thirteen bottles of water"
	 * 
	 * @param word
	 * @param number
	 * @param capitalize
	 * @return
	 */
	public static String pluralize(String word, int number, boolean capitalize) {
		return (number > 1 ? NumberToWordConverter.convert(number, capitalize) : affixIndefiniteArticle(word, capitalize)) + " " + pluralizer.pluralize(word, number);
	}

	public static String pluralize(String word, int number) {
		return pluralize(word, number, false);
	}

	public static String affixIndefiniteArticle(MudObject obj) {
		return affixIndefiniteArticle(obj, false);
	}

	public static String affixIndefiniteArticle(MudObject obj, boolean capitalize) {
		if (obj.isArticleRequired()) {
			return affixIndefiniteArticle(obj.getName().toString(), false);
		}
		return obj.getName().toString();
	}

	public static String affixDefiniteArticle(MudObject obj) {
		return affixDefiniteArticle(obj, false);
	}

	public static String affixDefiniteArticle(MudObject obj, boolean capitalize) {
		if (obj.isArticleRequired()) {
			return affixDefiniteArticle(obj.getName().toString(), false);
		}
		return obj.getName().toString();
	}

	public static String affixIndefiniteArticle(String word) {
		return WordUtils.affixIndefiniteArticle(word, false);
	}

	public static String affixIndefiniteArticle(String word, boolean capitalise) {
		if (word.matches("$(a|e|i|o|u)")) {
			return (capitalise ? "An" : "an") + " " + word;
		}
		return (capitalise ? "A" : "a") + " " + word;
	}

	public static String affixDefiniteArticle(String word, boolean capitalise) {
		return (capitalise ? "The" : "the") + " " + word;
	}

	public static String resolvePossessiveGender(String gender) {
		if (gender != null && gender.equalsIgnoreCase("male")) {
			return "his";
		}
		if (gender != null && gender.equalsIgnoreCase("female")) {
			return "her";
		}
		return "it's";
	}

	public static String resolveThirdPersonSingular(String gender) {
		if (gender != null && gender.equalsIgnoreCase("male")) {
			return "he";
		}
		if (gender != null && gender.equalsIgnoreCase("female")) {
			return "she";
		}
		return "it";
	}

	/**
	 * 
	 * @param word
	 * @return
	 */
	public static String depluralize(String word) {
		if (word.matches("^(a-zA-Z)ies$")) {
			return word.substring(0, word.length() - 1);
		}
		if (word.matches("ies$")) {
			return word.replaceAll("ies$", "y");
		}
		if (word.matches("(s|z|ch|sh|x)es$")) {
			return word.replaceAll("es$", "");
		}
		return word.replaceAll("s$", "");

	}

}
