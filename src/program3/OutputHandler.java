package program3;

/**
 * Interface for the output handler. We abstract it this way for easy testing
 * and outputting to the console instead of a file.
 * 
 * @author matt
 *
 */
public interface OutputHandler {

	/**
	 * Write out and display to user in a non-persistent way.
	 * 
	 * @param in
	 */
	void out(String in);

	/**
	 * Record an event.
	 * 
	 * @param time
	 * @param event
	 * @param bathroom
	 * @param queue
	 */
	void event(long time, String event, String bathroom, String queue);
	
	/**
	 * Close any resources
	 */
	void close();
}
