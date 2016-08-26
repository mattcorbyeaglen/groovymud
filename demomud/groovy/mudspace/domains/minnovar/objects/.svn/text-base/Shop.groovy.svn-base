package domains.minnovar.objects;

import org.groovymud.object.alive.Alive;
import org.groovymud.object.registry.MudObjectAttendant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import std.game.objects.containers.RoomImpl;

class Shop extends RoomImpl {
	MudObjectAttendant attendant
	boolean list(Alive mob, String args){
		def keeper = getMudObject("shopkeeper")
		
		keeper?.say("Heheh, here you can get anything. Won't do what you think, mind, but you can buy anything at all!")
		return true
	}
	boolean say(Alive mob, String args){
		def keeper = getMudObject("shopkeeper")
		if(args =~ /\w*pills\w*/){
			Thread.start(){
				Thread.sleep(2000)
				keeper?.say "We don't have any, we're a respectable establishment!!"
			}
		}
		return false
	}
	boolean buy(Alive mob, String args){
		def obj = null
		def keeper = getMudObject("shopkeeper")
		if(args == "red pill"){
			keeper.say "Ahh, very good.. you want the special red pill eh?"
			keeper.emote "turns and reaches up to the top shelf."
			keeper.emote "brings a special looking box down from the shelf and takes out a small red object."
			obj = attendant.cloneObject("redPill")	
		}else {
			//      just load any basic obj from the context
			obj = attendant.cloneObject("blankMudObject")
			obj.setName(args)
			obj.shortNames.addAll(args.split(" ") as List)
			obj.shortNames.remove("of")
			obj.setDescription("This is your average, run of the mill ${args}. It looks like it was made out of cardboard.")
		}
		
		keeper.addMudObject(obj)
		
		keeper.give "${obj.name} to ${mob.name}"
		keeper.say("Thankyou, come again!")
		return true
	}
}