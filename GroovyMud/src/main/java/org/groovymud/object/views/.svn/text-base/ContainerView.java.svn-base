package org.groovymud.object.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.groovymud.object.Container;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.registry.InventoryHandler;
import static org.groovymud.utils.WordUtils.*;

public class ContainerView extends ViewImpl {

	private transient Logger logger = Logger.getLogger(ContainerView.class);

	public void writeLookHeader(Alive looker, MudObject target) {
		super.writeLookBody(looker, target);
		try {
			looker.getTerminalOutput().writeln(affixIndefiniteArticle(target, true) + " contains:");
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	public void writeLookBody(Alive looker, MudObject target) {
		ContentsHelper contentHelper = createContentsHelper();
		InventoryHandler handler = ((Container) target).getInventoryHandler();
		Map<String, Set<MudObject>> dead = new HashMap<String, Set<MudObject>>(handler.getMudObjectsMap(false));

		String deadContents = contentHelper.getContentsDescription(dead, looker, false);
		if (dead.size() > 0) {
			try {
				looker.getTerminalOutput().write(deadContents);
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
	}

	protected ContentsHelper createContentsHelper() {
		return new ContentsHelper();
	}

	@Override
	public void writeLookFooter(Alive looker, MudObject target) {

	}

}
