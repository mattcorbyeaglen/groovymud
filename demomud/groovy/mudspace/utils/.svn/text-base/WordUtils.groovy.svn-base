package utils;

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
import org.groovymud.utils.NumberToWordConverter;
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
	static Class  loader = getClass()
	def static verbExceptions = "^(" + new BufferedInputStream(loader.getResourceAsStream("/utils/vowel-exceptions.txt")).text.split("\n").join("|") + ")"
	def static nounExceptions = "^(" +new BufferedInputStream(loader.getResourceAsStream("/utils/consonant-exceptions.txt")).text.split("\n").join("|") + ")"

	static {
		pluralizer = new NounPluralizer();
		List<Rule> rules = new ArrayList<Rule>(pluralizer.getRules());
		// all things ending in thief should be ves
		rules.add(0, new SuffixInflectionRule("-thief", "-f", "-ves"));
		rules.add(1, new SuffixInflectionRule("-s", "-s", "-s"));
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
	 * @return
	 */
	public static String pluralize(String word, int number) {
		return NumberToWordConverter.convert(number) + " " + pluralizer.pluralize(word, number);
	}

	public static String affixIndefiniteArticle(MudObject obj) {
		assert(obj.getName() != null);
		if (obj.isArticleRequired()) {
			return affixIndefiniteArticle(obj.getName().toString());
		}
		return obj.getName().toString();
	}

	public static String affixDefiniteArticle(MudObject obj) {
		assert(obj.getName() != null);
		if (obj.isArticleRequired()) {
			return affixDefiniteArticle(obj.getName().toString(), false);
		}
		return obj.getName().toString();
	}

	public static String affixIndefiniteArticle(String word) {
		def iArticle = "a"
		if (word.endsWith("s") && !word.contains(" of ")) {
			iArticle = "some"
		}
		
		if ((word.toLowerCase() =~ /^(a|e|i|o|u).*$/ && !(word.toUpperCase() =~ verbExceptions)) || word.toUpperCase() =~ nounExceptions) {
			iArticle = "an"
		}
		return "$iArticle $word";
	}

	public static String capitalize(String word) {
		return word.substring(0, 1).toUpperCase() + word.substring(1, word.length());
	}

	public static String affixDefiniteArticle(String word, boolean capitalise) {
		return "the " + word;
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
		if (word.contains(" of ")) {
			return word;
		}
		if (word =~ /^(a-zA-Z)ies$/) {
			return word.substring(0, word.length() - 1);
		}
		if (word =~ /"ies$/) {
			return word.replaceAll('ies$', "y");
		}
		if (word =~ /(s|z|ch|sh|x)es$/) {
			return word.replaceAll('es$', "");
		}
		return word.replaceAll('s$', "");

	}

}
