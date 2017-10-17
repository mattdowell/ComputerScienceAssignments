package program3;

import java.util.List;

/**
 * This class is responsible for constructing each thread, from a given list of
 * BathroomInputs, and starting each thread. It will also start the threads in
 * order based upon the initialization time from the input file.
 * 
 * 
 * @author matt
 *
 */
public class LineManager {

	private List<BathroomInput> inputLines;
	private OutputHandler output;
	private Bathroom bath = null;
	private long startTime;

	/**
	 * Constructor takes a output handler and a list of input lines read from a
	 * test file.
	 * 
	 * @param out
	 * @param inputLines
	 */
	public LineManager(OutputHandler out, List<BathroomInput> inputLines) {
		super();
		this.inputLines = inputLines;
		this.output = out;
		this.bath = new BathroomImpl(out);
		startTime = System.currentTimeMillis();
	}

	/**
	 * Starts the processing / creating of the user threads.
	 */
	public void process() {
		try {
			this.output.out("Starting processing...");
			for (BathroomInput bi : inputLines) {
				while (getElapsedTime() < bi.getGoTime()) {
					Thread.sleep(40);
				}
				BathroomUser user = createUser(bath, bi);
				user.go();
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			output.out("Exception in LineHandler: " + e.toString());
		} finally {
			this.output.out("Complete!");
			this.output.close();
		}
	}

	/**
	 * Gets the elapsed time (since creation of this object) in milliseconds.
	 * 
	 * @return
	 */
	private long getElapsedTime() {
		return (System.currentTimeMillis() - startTime);
	}

	/**
	 * Factory method to create the user threads from the given bathroom input.
	 * 
	 * @param bath
	 *            Bathroom
	 * @param bi
	 *            Input creation details
	 * @return Thread
	 */
	private BathroomUser createUser(Bathroom bath, BathroomInput bi) {
		BathroomUser bu = null;
		if (bi.getType().equalsIgnoreCase("M")) {
			bu = new MaleThread(bath, bi);
		} else if (bi.getType().equalsIgnoreCase("F")) {
			bu = new FemaleThread(bath, bi);
		} else if (bi.getType().equalsIgnoreCase("Z")) {
			bu = new ZombieThread(bath, bi);
		} else {
			throw new IllegalStateException("Could not determine type: " + bi.getType());
		}

		return bu;
	}

}
