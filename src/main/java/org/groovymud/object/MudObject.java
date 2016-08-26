package org.groovymud.object;

/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
import java.util.List;

import org.groovymud.engine.event.HeartBeatListener;
import org.groovymud.engine.event.observer.IObservable;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.alive.Player;
import org.groovymud.object.views.View;

/**
 *  basic interface for all mud objects. mudobjects 
 *  are all observable by any container
 * @author matt
 *
 */
public interface MudObject extends IObservable, HeartBeatListener {

	public void initialise();

	public String getDescription();

	public void setDescription(String description);

	public String getName();

	public void setName(String name);

	public String getShortDescription();

	public void setShortDescription(String description);

	public List<String> getShortNames();

	public void addShortName(String shortNames);

	public void dest(boolean wep);

	public boolean isArticleRequired();

	public void setArticleRequired(boolean requiresIndefinateArticle);

	public View getView();

	public void setView(View view);

	public ObjectLocation getObjectLocation();

	public void setObjectLocation(ObjectLocation location);

	public ObjectLocation getContainerLocation();

	public void setContainerLocation(ObjectLocation location);

	public void setCurrentContainer(Container container);

	public Container getCurrentContainer();

	public String getId();

	public boolean doCommand(Alive mob, String command, String args);
}
