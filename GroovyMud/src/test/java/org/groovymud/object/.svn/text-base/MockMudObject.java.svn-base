package org.groovymud.object;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.groovymud.engine.event.IScopedEvent;
import org.groovymud.engine.event.observer.Observer;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.views.View;
import org.joda.time.DateTime;

public class MockMudObject implements MudObject {

	String name;
	String[] shortNames = {};
	public volatile boolean logging = false;
	DateTime start = null;
	double heartBeats = 0;

	public void addShortName(String shortNames) {
		// TODO Auto-generated method stub

	}

	public void dest(boolean wep) {
		// TODO Auto-generated method stub

	}

	public boolean doCommand(Alive mob, String command, String args) {
		// TODO Auto-generated method stub
		return false;
	}

	public ObjectLocation getContainerLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public Container getCurrentContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return toString();
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public ObjectLocation getObjectLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getShortDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getShortNames() {
		// TODO Auto-generated method stub
		return Arrays.asList(shortNames);
	}

	public View getView() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initialise() {
		// TODO Auto-generated method stub

	}

	public boolean isArticleRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setArticleRequired(boolean requiresIndefinateArticle) {
		// TODO Auto-generated method stub

	}

	public void setContainerLocation(ObjectLocation location) {
		// TODO Auto-generated method stub

	}

	public void setCurrentContainer(Container container) {
		// TODO Auto-generated method stub

	}

	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObjectLocation(ObjectLocation location) {
		// TODO Auto-generated method stub

	}

	public void setShortDescription(String description) {
		// TODO Auto-generated method stub

	}

	public void setView(View view) {
		// TODO Auto-generated method stub

	}

	public void addObserver(Observer obj) {
		// TODO Auto-generated method stub

	}

	public void deleteObserver(Observer o) {
		// TODO Auto-generated method stub

	}

	public void doEvent(IScopedEvent event) {
		// TODO Auto-generated method stub

	}

	public void fireEvent(IScopedEvent event) {
		// TODO Auto-generated method stub

	}

	public Set getObservers() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	public void notifyObservers(IScopedEvent arg) {
		// TODO Auto-generated method stub

	}

	public void setChanged() {
		// TODO Auto-generated method stub

	}

	public void setObservers(HashSet<Observer> observers) {
		// TODO Auto-generated method stub

	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void heartBeat() {
		if (start == null) {
			start = new DateTime();
		}
		this.heartBeats++;
		DateTime now = new DateTime();
		long secondsPast = now.minus(start.getMillis()).getMillis() / 1000;
		double heartBeatsPerSecond = heartBeats / (secondsPast == 0 ? 1 : secondsPast);
		if (logging) {
			System.out.println("heartBeats: " + heartBeats);
			System.out.println("duration:" + secondsPast);
			System.out.println("heartBeatsPerSecond:" + heartBeatsPerSecond);

		}

	}

}
