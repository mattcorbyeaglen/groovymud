package domains.minnovar.objects.containers

import std.game.objects.containers.ContainerImpl
/**
 * included beans file, not an area
 */


beans {
	// needs to restrict the objects to only torches
	"townCentre:torchBundle"(ContainerImpl){bean ->		
	    bean.lazyInit = true
	    bean.parent = ref("baseContainer", true)
		name = "bundle of torches"
		shortNames = ['bundle', 'torch bundle']
		weight = 1
		//items = [ref("townCentre:Torch") * 5]
	}
	
	"townCentre:sack"(ContainerImpl){bean ->
        bean.lazyInit = true
	    bean.parent = ref("baseContainer", true)
	    name = "sack"
		shortNames = ['sack']
		weight = 0.1	
	}
}