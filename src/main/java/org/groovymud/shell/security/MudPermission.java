package org.groovymud.shell.security;

import java.security.BasicPermission;

/**
 * ooh a permission object. who'd of thunk it
 */
public class MudPermission extends BasicPermission {

	private static final long serialVersionUID = 2188702893697358487L;

	public MudPermission(String name) {
		super(name);
	}

}
