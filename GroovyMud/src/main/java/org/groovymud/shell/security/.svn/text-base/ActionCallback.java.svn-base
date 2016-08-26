package org.groovymud.shell.security;

import javax.security.auth.callback.Callback;

import org.groovymud.object.MudObject;

public class ActionCallback implements Callback {

	public final static String CREATE_ACTION = "create";
	public final static String LOAD_ACTION = "load";
	public static final String EXISTS_ACTION = "exists";

	public String action;
	public String objectName;
	public MudObject object;

	public ActionCallback(String objectName, String action) {
		this.objectName = objectName;
		this.action = action;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setUsername(String username) {
		this.objectName = username;
	}

	public void setMudObject(MudObject object) {
		this.object = object;
	}

	public MudObject getMudObject() {
		return object;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
