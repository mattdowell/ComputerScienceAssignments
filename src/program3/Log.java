package program3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 
 * This class writes out events to System.out (for testing) or a Log file in the
 * home directory.
 * 
 * @author matt
 *
 */
public final class Log {

	private static Log instance = new Log();
	private static PrintWriter writer = null;

	private Log() {
	}
	
	public void init() {
		// Open the file writer
		try {
			writer = new PrintWriter("bathroom_report.txt", "UTF-8");
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
		System.out.println(inEvent);
	}

}
