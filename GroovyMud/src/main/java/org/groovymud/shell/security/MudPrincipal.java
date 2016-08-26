package org.groovymud.shell.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.Subject;

import org.apache.log4j.Logger;

import com.sun.security.auth.PrincipalComparator;

/**
 * a mud principal
 * 
 * see
 * http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/JAASRefGuide.html#
 * Principals
 * 
 * TODO: should be extensible, and allow additional levels.
 * 
 * @author matt
 * 
 */
@SuppressWarnings("restriction")
public class MudPrincipal implements Principal, PrincipalComparator, Serializable {
	
	private String[] levels = { "god", "lord", "creator", "trialcreator", "player", "guest" };
	private static final Logger logger = Logger.getLogger(MudPrincipal.class);
	/**
	 * @serial
	 */
	private static final long serialVersionUID = 6368149668689598146L;

	String name;

	public MudPrincipal(String string) {
		name = string;
	}

	private int getMudPrincipalLevel(String string) {
		return Arrays.asList(levels).indexOf(string);
	}

	public String getName() {
		return name;
	}

	public boolean implies(Subject subject) {
		Set<MudPrincipal> set = subject.getPrincipals(MudPrincipal.class);
		Iterator<MudPrincipal> i = set.iterator();
		while (i.hasNext()) {
			MudPrincipal p = i.next();
			if (getMudPrincipalLevel(p.name)== getMudPrincipalLevel(this.name)) {
				// logger.debug(p.name + " == " + this.name);
				return true;
			}
			if (getMudPrincipalLevel(p.name) < getMudPrincipalLevel(this.name)) {
				// logger.debug(this.name + " -> " + p.name);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MudPrincipal)) {
			return false;
		}
		return ((MudPrincipal) obj).name.equals(name);
	}

	public String toString() {
		return super.toString() + ": " + this.name;
	}

	@Override
	public int hashCode() {
		return getName().hashCode() * 37 + getMudPrincipalLevel(name);
	}
}
