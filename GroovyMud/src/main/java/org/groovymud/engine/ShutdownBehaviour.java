package org.groovymud.engine;

import org.apache.log4j.Logger;
import org.groovymud.object.MudObject;
import org.groovymud.object.ObjectLocation;
import org.groovymud.object.registry.MudObjectAttendant;
import org.groovymud.utils.MessengerUtils;
import org.groovymud.utils.WordUtils;

/**
 * This object is responsible for managing the shutdown
 * it informs the mud via the registered shutdown informer mud object
 * @author matt
 *
 */
public class ShutdownBehaviour {

	private MudObjectAttendant attendant;
	protected MudObject informer;
	private int maxShutdownHeartBeats;
	private int shutdownHeartBeats;
	private JMudEngine engine;
	private ObjectLocation shutDownInformer;

	private static final Logger logger = Logger.getLogger(ShutdownBehaviour.class);

	protected void initialise() {
		MudObject informer = null;
		if (getInformer() == null) {
			try {
				informer = getAttendant().load(getShutDownInformer());

				setInformer(informer);
			} catch (Exception e) {
				logger.error(e, e);
			}

		}
	}

	public void handleShutdown() {
		if (shutdownHeartBeats++ == getMaxShutdownHeartBeats()) {
			if (informer != null) {
				String message = WordUtils.affixDefiniteArticle(informer) + " shouts: Mud shutting down NOW!!";
				MessengerUtils.sendMessageToMud(informer, "", message);
			}
			logger.info("Shutdown NOW!");
			engine.shutdownNow();
		}
		if (shutdownHeartBeats % 20 == 0 || shutdownHeartBeats == 1 || (maxShutdownHeartBeats - shutdownHeartBeats) < 10) {
			informMudOfShutdown();
		}
	}

	public void informMudOfShutdown() {
		String timeLeft = calculateTimeLeft();
		if (informer != null) {
			String message = WordUtils.affixDefiniteArticle(informer) + " shouts: Mud shutting down in " + timeLeft;
			MessengerUtils.sendMessageToMud(informer, "", message);
		}

		logger.info("shutting down in " + timeLeft);
	}

	protected String calculateTimeLeft() {
		int secondsLeft = ((maxShutdownHeartBeats - shutdownHeartBeats) * JMudEngine.DEFAULT_MUD_HEARTBEAT_LENGTH_MS) / 1000;
		int timeLeft = 0;
		String value = "";

		if (secondsLeft > 60) {
			timeLeft = secondsLeft / 60;
			secondsLeft -= timeLeft * 60;
		}
		if (timeLeft > 0) {
			value = timeLeft + " minutes and ";
		}
		value += secondsLeft + " seconds";
		return value;
	}

	public int getMaxShutdownHeartBeats() {
		return maxShutdownHeartBeats;
	}

	public void setMaxShutdownHeartBeats(int maxShutdownHeartBeats) {
		this.maxShutdownHeartBeats = maxShutdownHeartBeats;
	}

	public int getShutdownHeartBeats() {
		return shutdownHeartBeats;
	}

	public void setShutdownHeartBeats(int shutdownHeartBeats) {
		this.shutdownHeartBeats = shutdownHeartBeats;
	}

	public JMudEngine getEngine() {
		return engine;
	}

	public void setEngine(JMudEngine engine) {
		this.engine = engine;
	}

	public MudObject getInformer() {
		return informer;
	}

	public void setInformer(MudObject informer) {
		this.informer = informer;
	}

	public ObjectLocation getShutDownInformer() {
		return shutDownInformer;
	}

	public void setShutDownInformer(ObjectLocation shutDownInformer) {
		this.shutDownInformer = shutDownInformer;
	}

	public void setAttendant(MudObjectAttendant attendant) {
		this.attendant = attendant;
	}

	public MudObjectAttendant getAttendant() {
		return attendant;
	}

}
