/**
 * Copyright 2008 Matthew Corby-Eaglen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package utils

import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.Hours
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormat
import org.groovymud.utils.WordUtils




/**
 * 
 * a basic calendar example. 
 * 
 * @author matt
 *
 */
public class MinnovarCalendar{
    
	def static CREATION = new DateTime(2008, 3, 27, 0,0,0,0)
	
	def static MUD_CREATION_DAY = 8 // wollach
	
	def static WEEK_DAYS = ["Peleth", "Kolmar", "Dinwar", "Kilmar", "Dilmar", "Yarathwar", "Kreth", "Wollach"]    
	def static MONTHS = [(0..23): "Boltar", (24 .. 48) : "Kevnith", (49..72) : "Sathqar", (73..99) : "Oonk", (100 .. 126) : "Tilworth", (127 .. 151) : "Yatterway", (152..175):"Diswanth"]
	def static SEASONS = [(0..1) : "Plancher" , (2..3) : "Sommersul", (4..6) : "Ecchel", (7..8) : "Molwinter"]
	def static MUD_DAYS_IN_YEAR = 175
	def static MUD_HOURS_IN_DAY = 5

	def static NIGHT_TIMES = ["midnight", "just after midnight", "the early hours of the morning", "night"]
	def static DAY_TIMES = ["dawn","early morning", "mid morning", "midday", "afternoon", "evening"]
	
	def static TIMESOFDAY = [(0..5) : "midnight", (6..15) : "just after midnight", (16..30) : "the early hours of the morning", (31..35) : "dawn", (36..45) : "early morning", (46..55) : "mid morning", (56..60): "midday", (61..75) : "afternoon", (76..85) : "evening", (86..99) : "night"]
	def month
	def weekDay
	def dayPart
	def wordMonth 
	def dayOfMonth
	def season 
	def time
	def year
	def dayOfYear
	
	def getDate(){
		calculate()
		return """It is $weekDay the ${getOrdinalFor(dayOfMonth)} of $wordMonth, year $year. Currently it is $season time.
You think it is $time."""
	}
	
	void calculate(){
		DateTime now = getNow();
		Hours d = Hours.hoursBetween(CREATION, now);
		int hoursDiff = d.getHours();

		// assume wollach was the creation date
		def mudDaysSinceCreation = (hoursDiff / MUD_HOURS_IN_DAY)
		def wholeDays = mudDaysSinceCreation as Integer
		dayPart = ((mudDaysSinceCreation - wholeDays) * 100) as Integer
		weekDay = WEEK_DAYS.get((MUD_CREATION_DAY + wholeDays) % WEEK_DAYS.size())
		dayOfYear = wholeDays % MONTHS.keySet().size()
		
		
	    MONTHS.eachWithIndex{it, i ->		        
        		        if(it.key.contains(dayOfYear)){
        		            month = i
        		            dayOfMonth = (dayOfYear - it.key.from) + 1       		            
        		            wordMonth = it.value
        		            return
        		        }
        		 }
		          
		SEASONS.each{key, value ->
		                if(key.contains(month)){
		                    season = value
		                    return
		                }
		}
		TIMESOFDAY.each{key, value ->
		                if(key.contains(dayPart)){
		                    time = value
		                    return
		                }
		}
		
		year = mudDaysSinceCreation / MUD_DAYS_IN_YEAR as Integer
	}
	public static String getOrdinalFor(int value) {
	    int hundredRemainder = value % 100;
	    if(hundredRemainder >= 10 && hundredRemainder <= 20) {
	     return "${value}th";
	    }

	    int tenRemainder = value % 10;
	    switch (tenRemainder) {
	     case 1:
	      return "${value}st";
	     case 2:
	      return "${value}nd";
	     case 3:
	      return "${value}rd";
	     default:
	      return "${value}th";
	    }
	   }

	def getRealDate(Locale locale){
	    if(locale == null){
	        locale = Locale.getDefault()
	    }
	    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
	    
	    return "The real date and time is ${fmt.withLocale(locale).print(getNow())}"
	}
	
	def getNow(){
	    return new DateTime();
	}

}
