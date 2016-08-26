package org.groovymud.object.views;

import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.registry.Registry;
import org.groovymud.object.room.Exit;

public class ExitView implements View {

	private transient Registry registry;

	public void writeLookBody(Alive looker, MudObject target) {
		Exit exit = (Exit) target;

		getRegistry().getMudObject(exit.getDestination().getBeanId());

		target.getView().writeLookBody(looker, target);
	}

	public void writeLookFooter(Alive looker, MudObject object) {
		looker.getView().writeLookFooter(looker, object);
	}

	public void writeLookHeader(Alive looker, MudObject object) {
		looker.getView().writeLookFooter(looker, object);
	}

	public void setRegistry(Registry registry) {
		this.registry = registry;
	}

	public Registry getRegistry() {
		return registry;
	}

}
