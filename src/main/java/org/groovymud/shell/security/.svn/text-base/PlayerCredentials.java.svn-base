package org.groovymud.shell.security;

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
/**
 * player credentials
 * 
 * see http://java.sun.com/j2se/1.4.2/docs/guide/security/jaas/JAASRefGuide.html#Credentials
 * 
 */
public class PlayerCredentials {

	private String username;
	private String password;

	public PlayerCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlayerCredentials)) {
			return false;
		}
		PlayerCredentials other = (PlayerCredentials) obj;
		return other.username.equals(this.username) && other.password.equals(this.password);
	}

	@Override
	public int hashCode() {
		return username.hashCode() * 37 + password.hashCode() + 37;
	}
}
