package program1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Requirements:
 * 
 * The following information:
 * 
 * Time stamp of event Type of event (cryptic – keep it short) If customer
 * delivery, how long it took to deliver the pumpkin Number of pumpkins in stash
 * after event Number of plants in the field after event
 * 
 * The events are: New plant sown. Pumpkin ripens on plant. Jack gathers
 * pumpkins. Customer places order. Order drives to UPuS to deliver pumpkins.
 * 
 * IMPLEMENTATION:
 * 
 * This class writes out events to System.out (for testing) or a Log file in the
 * home directory.
 * 
 * @author matt
 *
 */
public final class Log {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// These must be initted in app startup.
	public static Stash stash;
	public static PlantField field;
	public static OrderList orders;
	public static RipePumpkins ripes;

	private static final int EVENT_LENGTH = 50;

	private static Log instance = new Log();
	private static PrintWriter writer = null;

	private Log() {
	}
	
	public void init() {
		// Open the file writer
		try {
			writer = new PrintWriter("pumpkin_log.txt", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	public void close() {
		writer.close();
	}

	public static Log getInstance() {
		return instance;
	}

	/**
	 * Write an event to Console or log file.
	 * 
	 * @param inEvent
	 */
	public void event(String inEvent) {
		int pad = (EVENT_LENGTH - inEvent.trim().length());
		String event = inEvent;
		if (pad > 0) {
			event = padRight(inEvent, pad);
		}

		String fullEvent = formatter.format(new Date()) + " " + event + getInventory();
		write(fullEvent);
	}

	public void write(String inEvent) {
		// System.out.println(inEvent);
		writer.println(inEvent);
	}

	/**
	 * Debug logging to console.
	 * 
	 * @param inEvent
	 */
	public void debug(String inEvent) {
		// write(inEvent);
	}

	/**
	 * Compiles the common inventory settings for each write.
	 * 
	 * @return
	 */
	private String getInventory() {
		return " Stash: " + stash.size() + " Plants: " + field.getPlantsSize()
				+ " Orders: " + orders.size();
	}

	/**
	 * Pads our events so they are easier to read
	 * 
	 * @param s
	 * @param n
	 * @return
	 */
	public String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

}
