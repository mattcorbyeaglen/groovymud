/**
 * 
 */
package org.groovymud.object.views;

import static net.wimpi.telnetd.io.terminal.ColorHelper.BOLD;
import static net.wimpi.telnetd.io.terminal.ColorHelper.colorizeText;
import static org.groovymud.utils.WordUtils.affixIndefiniteArticle;
import groovy.text.Template;
import groovy.text.TemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.groovy.control.CompilationFailedException;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;
import org.groovymud.utils.text.IncludeTemplateEngine;

/**
 * @author matt
 * 
 */
public class ViewImpl implements View {

	private transient final static Logger logger = Logger.getLogger(ViewImpl.class);

	public void writeLookHeader(Alive looker, MudObject object) {
		try {
			looker.getTerminalOutput().writeln("You see " + affixIndefiniteArticle(object.getName().toString()) + ":");
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	public void writeLookBody(Alive looker, MudObject object) {
		if (object.getDescription() != null) {
			writeDynamicText(looker, object, object.getDescription());
		}
	}

	public void writeLookFooter(Alive looker, MudObject object) {
	}

	public void writeDynamicText(Alive looker, MudObject target, String text) {

		ExtendedTerminalIO stream = looker.getTerminalOutput();
		Map binding = new HashMap();
		binding.put("it", target);
		binding.put("looker", looker);
		TemplateEngine engine = new IncludeTemplateEngine();
		try {
			//auto import some utils
			String header = "<%import static org.groovymud.utils.WordUtils.*;\nimport static org.groovymud.utils.NumberToWordConverter.*%>";
			Template template = engine.createTemplate(header + text);

			stream.writeln(colorizeText(template.make(binding).toString(), BOLD));
		} catch (CompilationFailedException e) {
			logger.error(e, e);
		} catch (ClassNotFoundException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
	}
}
