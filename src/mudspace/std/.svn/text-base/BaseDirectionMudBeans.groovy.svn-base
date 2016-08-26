package std/** * Copyright 2008 Matthew Corby-Eaglen *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not * use this file except in compliance with the License. You may obtain a copy of * the License at *  * http://www.apache.org/licenses/LICENSE-2.0 *  * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the * License for the specific language governing permissions and limitations under * the License. */import std.game.objects.exits.ExitImpl
/** * standard abstract directions * @author corbym * */

beans{
	
	
	'std:north'(ExitImpl, name:"north", shortNames:["n"]){ bean ->		bean.parent = ref("baseExit", true)
		bean.'abstract' = true				bean.scope = 'prototype'
		direction = "north"
		arrivalDirection = "south"		
	}
	'std:south'(ExitImpl, name:"south", shortNames:["s"]){ bean ->		bean.parent = ref("baseExit", true)
		bean.'abstract' = true		bean.scope = 'prototype'
		direction = "south"
		arrivalDirection = "north"		
	}
	'std:east'(ExitImpl,  name:"east", shortNames:["e"]){ bean ->		bean.parent = ref("baseExit", true)
		bean.'abstract' = true		bean.scope = 'prototype'
		direction = "east"
		arrivalDirection = "west"		
	}
	'std:west'(ExitImpl,  name:"west", shortNames:["w"]){ bean ->		bean.parent = ref("baseExit", true)
		bean.'abstract' = true		bean.scope = 'prototype'
		direction = "west"
		arrivalDirection = "east"
	}
	'std:northeast'(ExitImpl, name:"northeast", shortNames:["ne"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true		bean.scope = 'prototype'
		direction = "northeast"
		arrivalDirection = "southwest"
	}
	'std:southeast'(ExitImpl, name:"southeast", shortNames:["se"]){ bean ->		bean.parent = ref("baseExit", true)
		bean.'abstract' = true		bean.scope = 'prototype'		direction = "southeast"
		arrivalDirection = "northwest"
		shortNames = ["se"]
	}
	'std:northwest'(ExitImpl, name:"northwest", shortNames:["nw"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true		bean.scope = 'prototype'
		direction = "northwest"		
	}
	'std:southwest'(ExitImpl, name:"southwest", shortNames:["sw"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true
		direction = "southwest"
		arrivalDirection = "northeast"
		shortNames = ["sw"]
	}
	'std:up'(ExitImpl, name:"up", shortNames:["u"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true
		
		direction = "up"
		arrivalDirection = "below"
	}
	'std:down'(ExitImpl,  name:"down", shortNames:["d"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true		bean.scope = 'prototype'
		direction = "down"
		arrivalDirection = "above"		
	}
	
	'std:in'(ExitImpl, name:"in", shortNames:["in", "inside"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true		bean.scope = 'prototype'
		articleRequired = false
		direction = "in"
		arrivalDirection = "outside"
	}
	'std:out'(ExitImpl, name:"out", shortNames:["out", "outside"]){ bean ->		bean.parent = ref("baseExit", true)		bean.'abstract' = true		bean.scope = 'prototype'
		articleRequired = false 
		direction = "out"
		arrivalDirection = "inside"	
	}
}