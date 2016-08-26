package org.groovymud.utils;

import org.jvnet.inflector.Noun;

import junit.framework.TestCase;

public class WordUtilsTest extends TestCase {

	public void testPlurals() {
		assertEquals(WordUtils.pluralize("knife"), "knives");
		assertEquals(WordUtils.pluralize("day"), "days");
		assertEquals(WordUtils.pluralize("calf"), "calves");
		assertEquals(WordUtils.pluralize("branch"), "branches");
		assertEquals(WordUtils.pluralize("fox"), "foxes");
		assertEquals(WordUtils.pluralize("loaf"), "loaves");
		assertEquals(WordUtils.pluralize("thief"), "thieves");
		assertEquals(WordUtils.pluralize("man"), "men");
		assertEquals(WordUtils.pluralize("woman"), "women");
		assertEquals(WordUtils.pluralize("bottle of water"), "bottles of water");
	}

	public void testNumberedPlurals() {
		assertEquals(WordUtils.pluralize("knife", 10), "ten knives");
		assertEquals(WordUtils.pluralize("day", 5), "five days");
		assertEquals(WordUtils.pluralize("calf", 32), "many calves");
		assertEquals(WordUtils.pluralize("branch", 1020), "thousands of branches");
		assertEquals(WordUtils.pluralize("fox", 2), "two foxes");
		assertEquals(WordUtils.pluralize("thief", 40), "many thieves");
		assertEquals(WordUtils.pluralize("bottle of water", 1000000), "thousands of bottles of water");
	}

	public void testNumberedPluralsCapitalize() {
		assertEquals(WordUtils.pluralize("knife", 10, true), "Ten knives");
		assertEquals(WordUtils.pluralize("day", 5, false), "five days");
		assertEquals(WordUtils.pluralize("calf", 32, true), "Many calves");
		assertEquals(WordUtils.pluralize("branch", 1020, true), "Thousands of branches");
		assertEquals(WordUtils.pluralize("fox", 2, false), "two foxes");
		assertEquals(WordUtils.pluralize("thief", 40, true), "Many thieves");
		assertEquals(WordUtils.pluralize("bottle of water", 1000000, true), "Thousands of bottles of water");
	}
}
