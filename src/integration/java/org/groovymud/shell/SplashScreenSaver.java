package org.groovymud.shell;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import net.wimpi.telnetd.io.terminal.ColorHelper;

public class SplashScreenSaver {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		SplashLoader loader = new SplashLoader();
		loader.setMudSplashPageLocation("C:\\dev\\workspace-groovymud\\GroovyMud-trunk\\src\\mudspace\\resources\\splashpageold.txt");
		String splash = loader.loadMudSplash();
		File writeTo = new File("C:\\dev\\workspace-groovymud\\GroovyMud-trunk\\src\\mudspace\\resources\\splash-"+ System.currentTimeMillis() +".txt");

		Writer output = new BufferedWriter(new FileWriter(writeTo));
		try {
			boolean inside = false;
			// FileWriter always assumes default encoding is OK!
			for (int x = 0; x < splash.length(); x++) {
				String ch = splash.substring(x, x + 1);

				if (!inside && ch.equals("@")) {
					inside = !inside;
				}
				if (inside && ch.equals(" ")) {
					output.write(ColorHelper.colorizeBackground(ch, ColorHelper.WHITE));
				}else if (ch.equals(":")) {
					output.write(ColorHelper.colorizeText(ch, ColorHelper.CYAN));
				}else{
					output.write(ch);
				}
			}
		} finally {
			output.close();
		}

	}

}
