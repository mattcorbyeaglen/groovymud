package std.game.objects;

import org.groovymud.engine.event.ScopedEvent;
import org.groovymud.object.ReactiveMudObject;
import org.groovymud.object.alive.Alive;

import std.game.behaviours.Behaviour 


class GroovyMudObject extends ReactiveMudObject<Behaviour> implements Weighted {
	double weight;
	
	public void setProperty(String param, Object object) {
		if ("name".equals(param)) {
			// unregister and reregister object
			registry.update(this);
		}
	}
	
	void dest(boolean wep){
		registry.dest this, wep
	}
	
	@Override
	boolean doCommand(Alive player, String methodName, String args){
		boolean done = false
		if(this.metaClass.respondsTo(this.getClass(), methodName, player, args)){
			done = this."${methodName}"(player, args)
		}
		return done
	}
	
	void doBehaviours(List behaviours, ScopedEvent event){
		synchronized(behaviours){
			behaviours.each{Behaviour it ->
				it.doBehaviour(this, event);
			}
		}
	}
}
