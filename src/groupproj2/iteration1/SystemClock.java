package groupproj2.iteration1;

import java.util.List;

/**
 * This class encapsulates the logic AND the clock cycles (in this case, one second) and executes
 * the logic based upon the given configuration and publishes changes to the given listener.
 * 
 * We could easily take a List of listeners if we need to in the future.
 * 
 *
 */
public class SystemClock extends Thread {
	
	// 1 minute normally, down to 1 second for convenience.
	private static final int SLEEP_TIME_SECONDS = 1000 * 1;

	private List<RefridgeratorStateChangeListener> listeners;
	private RefridgeratorState configuration;

	/**
	 * Constructor takes a listener and a config.
	 * @param listener
	 * @param configuration
	 */
	public SystemClock(List<RefridgeratorStateChangeListener> inListeners, RefridgeratorState configuration) {
		super();
		this.listeners = inListeners;
		this.configuration = configuration;
	}
	
	/**
	 * Thread run method, to be called by the client.
	 */
	public void run() {
		while (true) {
			
			try {
				sleep(SLEEP_TIME_SECONDS);
				
				notifyListeners();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loop through the given listeners and notify them.
	 */
	private void notifyListeners() {
		for (RefridgeratorStateChangeListener listener : listeners) {
			listener.configurationChanged(configuration);
		}
	}

}
