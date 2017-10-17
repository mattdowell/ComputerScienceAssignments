package program3;

/**
 * Abstract parent class if the bathroom user threads. This class abstracts the
 * start method and defines the dependencies for each bathroom user.
 * 
 * @author matt
 *
 */
public class AbstractBathroomUser extends Thread {

	Bathroom bathroom;
	BathroomInput input;

	public AbstractBathroomUser(Bathroom bathroom, BathroomInput input) {
		super();
		this.bathroom = bathroom;
		this.input = input;
	}

	/**
	 * Sleeps for the amount of time defined in the file
	 */
	void doMyThing() {
		try {
			Thread.sleep(input.getBathroomTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wraps the start method
	 */
	public void go() {
		try {
			start();
		} catch (Throwable t) {
			t.printStackTrace();
			super.destroy();
		}
	}
}
