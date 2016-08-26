package org.groovymud.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * loads the splash screen when the user connects as a String
 * @author matt
 *
 */
public class SplashLoader {

	private String mudSplashPageLocation;
	private final static Logger logger = Logger.getLogger(SplashLoader.class);

	public String loadMudSplash() {
		InputStream in;
		BufferedReader bIn;
		try {
			in = new FileInputStream(new File(getMudSplashPageLocation()));

			bIn = new BufferedReader(new InputStreamReader(in));

			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bIn.readLine()) != null) {
				buffer.append(str + "\n");
			}
			return buffer.toString();

		} catch (IOException e) {
			logger.error(e, e);
		}
		return null;
	}

	public void setMudSplashPageLocation(String mudSplashPageLocation) {
		this.mudSplashPageLocation = mudSplashPageLocation;
	}

	public String getMudSplashPageLocation() {
		return mudSplashPageLocation;
	}
}
