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
/**
 * converts numbers to fuzzy amounts
 * 
 */
public class NumberToWordConverter {

	final static String[] numbers = { "no", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty" };

	final static String[] fuzzyAmounts = { "many", "a lot of", "hundreds of", "thousands of", "far too many" };

	public static String convert(int number, boolean capitalCase) {
		String ret = "";
		if (number < 0) {
			ret += "minus";
			number = Math.abs(number);
		}
		if (number <= 20) {
			ret += numbers[number];
		}
		if (number > 20 && number < 50) {
			ret += fuzzyAmounts[0];
		}
		if (number > 50 && number < 300) {
			ret += fuzzyAmounts[1];
		}
		if (number >= 300 && number < 999) {
			ret += fuzzyAmounts[2];
		}
		if (number >= 999 && number < 9999) {
			ret += fuzzyAmounts[3];
		}
		if (number >= 9999) {
			ret += fuzzyAmounts[3];
		}
		if (capitalCase) {
			ret = ret.replaceFirst(ret.substring(0, 1), ret.substring(0, 1).toUpperCase());
		}
		return ret;
	}
}
