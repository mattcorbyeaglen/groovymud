package std.game.objects.views;

import static net.wimpi.telnetd.io.terminal.ColorHelper.CYAN;
import static net.wimpi.telnetd.io.terminal.ColorHelper.GREEN;
import static net.wimpi.telnetd.io.terminal.ColorHelper.WHITE;
import static net.wimpi.telnetd.io.terminal.ColorHelper.colorizeText;
import static utils.WordUtils.pluralize;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.groovymud.object.MudObject;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.room.Room;
import org.groovymud.shell.security.MudPrincipal;
import org.groovymud.shell.telnetd.ExtendedTerminalIO;

public class RoomView extends ContainerView {

	private transient static final Logger logger = Logger.getLogger(RoomView.class);

	public void writeLookHeader(Alive looker, MudObject target) {
		ExtendedTerminalIO stream = looker.getTerminalOutput();
		
		try {
			
			boolean isCreator = looker.metaClass.respondsTo("getSubject")? new MudPrincipal("trialcreator").implies(looker.subject) : false;

			stream.write(colorizeText(target.name, GREEN));
			if(isCreator){
				stream.write "(${target.getClass().protectionDomain.codeSource.location.path})"
			}
			stream.writeln ""
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	public void writeLookBody(Alive looker, MudObject target) {
		Room container = (Room) target;
		ExtendedTerminalIO stream = looker.getTerminalOutput();
		
		try {
			writeDynamicText(looker, target, target.getDescription());
			
			if (container.getInventoryHandler().getMudObjectsMap(false).size() > 0) {
				stream.write "You see: "
				super.writeLookBody(looker, target)
				stream.writeln(".");
			}
			Map<String, Set<MudObject>> alive = container.getInventoryHandler().getMudObjectsMap(true);
			ContentsHelper contentsHelper = createContentsHelper();
			String aliveContents = contentsHelper.getContentsDescription(alive, looker, true);
			
			int aliveSize = container.getInventoryHandler().size(true) - 1;
			if (aliveSize > 0) {
				stream.write(colorizeText(aliveContents, GREEN));
			}
			if (aliveSize > 0) {
				stream.writeln(aliveSize > 1 ? " are here." : " is here.");
			}
		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	public void writeLookFooter(Alive looker, MudObject target) {
		Room room = (Room) target;
		List exits = room.getExitInventory().getMudObjects() as List;
		ExtendedTerminalIO stream = looker.getTerminalOutput();
		stream.write(colorizeText("There ${(exits.size() == 1 ? 'is' : 'are')} ${pluralize('exit', exits.size())}: ", CYAN))
		def coloredExits = []
		exits.each{ exit ->
			coloredExits << colorizeText(exit.getDirection(), WHITE)
		}
		stream.writeln("${coloredExits.join(", ")}.")
	}
}
