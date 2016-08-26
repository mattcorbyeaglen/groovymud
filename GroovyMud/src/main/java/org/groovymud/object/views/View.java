package org.groovymud.object.views;

import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;

public interface View {

	public void writeLookHeader(Alive looker, MudObject object);

	public void writeLookBody(Alive looker, MudObject object);

	public void writeLookFooter(Alive looker, MudObject object);
}
