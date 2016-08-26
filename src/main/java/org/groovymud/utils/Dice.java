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
import java.util.List;
import java.util.Random;

/**
 * Emulates a dice.
 * 
 * rolling will return a random number between (numberOfDice) and (dieMax)
 * 
 * eg
 * 
 * Dice myDice = new Dice(1, 6) // 1d6
 * myDice.roll() // random number between 1 and 6
 * 
 * myDice = new Dice(2, 10) // 2d10
 * myDice.roll() // random number between 2 and 10
 * 
 * @author matt
 * @deprecated use groovyDice
 *
 */
public class Dice {

	int numberOfDice;
	int dieMax;

	public Dice(int numberOfDice, int dieMax) {
		this.numberOfDice = numberOfDice;
		this.dieMax = dieMax;
	}

	public int roll() {
		Random rnd = new Random(System.currentTimeMillis());
		return rnd.nextInt(dieMax * numberOfDice) + numberOfDice;
	}
}
