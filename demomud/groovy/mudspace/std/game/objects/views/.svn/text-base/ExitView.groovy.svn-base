package std.game.objects.views;
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
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.registry.Registry
import org.groovymud.object.room.Exit;
import org.groovymud.object.views.View

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
