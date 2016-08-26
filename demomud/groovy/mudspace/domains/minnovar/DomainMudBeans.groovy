/**
 * common mudbeans that belong to the domain, such as weather, or calendars
 */
package domains.minnovar

import utils.MinnovarCalendar
import std.game.objects.GroovyMudObjectbeans{	
	"domains:minnovar:calendar"(MinnovarCalendar)
	
	"blankMudObject"(GroovyMudObject){bean->
        bean.parent = ref("baseMudObject", true)
        bean.scope = 'prototype'
        name="flirble"
        description = "This is a flirble. Nothing to see here, please move along."
    }
}
