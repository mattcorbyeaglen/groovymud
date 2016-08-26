package domains.minnovar.objects


import org.groovymud.object.alive.Alive;
import org.groovymud.shell.security.MudPrincipal;
import std.game.objects.*;
import static org.groovymud.utils.MessengerUtils.sendMessageToRoom;

class RedPill extends GroovyMudObject {
	boolean eat( Alive player, String str){
		if(str == name && this.currentContainer == player){
			Thread.start(){
				sendMessageToRoom(player, "You almost choke as you swallow the little red pill...", "${player.name} looks a bit sick as he eats a red pill.")
				Thread.sleep(2000)
				sendMessageToRoom(player, "Wow, that feels MUCH better.", "A flash of light bursts forth from ${player.name}'s mouth as the power of a God issues forth. ")
				// how to stop creators doing this when they write objects??
				player.subject.principals.add(new MudPrincipal("god"))
				this.dest(true)
			}
			return true
		}
		return false
	}
	void heartBeat(){
		super.heartBeat()
		println "ping"
	}
}