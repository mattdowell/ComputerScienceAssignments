package program2;

/**
 * This is the shared counter. By encapsulating it in this class, we can ensure
 * it is not bring incremented outside of the thread increment call.
 * 
 * @author matt
 *
 */
public class Counter {

	private int counter = 0;

	public int getCount() {
		return counter;
	}

	public void increment() {
		this.counter++;
	}

}
