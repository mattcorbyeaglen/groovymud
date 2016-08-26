package domains.minnovar.objects.containers
import std.game.objects.containers.ContainerImpl/**
 * included beans file, not an area
 */


beans {
	// needs to restrict the objects to only torches
	"townCentre:torchBundle"(ContainerImpl){bean ->
		bean.parent = ref('baseContainer', true)
		bean.lazyInit = true
		name = "bundle of torches"
		shortNames = ['bundle', 'torch bundle']
		weight = 1
		maxFullness = 2
		//items = [ref("townCentre:Torch") * 5]
	}
	
	"townCentre:sack"(ContainerImpl){bean ->
		bean.parent = ref('baseContainer', true)	
		bean.lazyInit = true
		name = "sack"
		shortNames = ['sack']
		weight = 0.1
		maxFullness = 1
		
	}
}