package program1;

import java.util.Date;

/**
 * Requirements:
 * 
 * produce their first pumpkin in an average of 100,000 time units (std. dev. =
 * 3,000, Gaussian distribution again).
 * 
 * Implementation:
 * 
 * This plant has two modes it can work in, 1) as a Thread where it keeps track
 * of itself, using a timer/sleep mechanism, or as a domain object with a plant
 * time. As a domain object, then the gatherer is responsible for "picking" the
 * ripened pumpkin.
 * 
 * @author matt
 *
 */
public class Plant extends Thread {

	private RipePumpkins ripes;

	private boolean uprooted = false;

	private Date planted;

	/**
	 * Each plant has a handle to the ripe pumpking storage.
	 * 
	 * @param inP
	 *            RipePumpkins storage.
	 */
	public Plant(final RipePumpkins inP) {
		ripes = inP;
		planted = new Date();
	}

	/**
	 * Default constructor to be used in non-thread mode.
	 */
	// public Plant() {
	// planted = new Date();
	// }

	/**
	 * This method is used when this class is a Thread. It picks itself, etc..
	 */
	@Override
	public void run() {
		try {

			while (!uprooted) {
				Thread.sleep(TimeUtil.getPlantGrowthTime());
				pick();
				Log.getInstance().event("Pumpkin ripened");
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	/**
	 * This class / plant can be uprooted and it just dies as a thread
	 * naturally.
	 */
	public void uproot() {
		this.uprooted = true;
	}

	/**
	 * @return time units since it was planted.
	 */
	public long getUnitsSincePlanted() {
		return (new Date().getTime() - planted.getTime());
	}

	/**
	 * When the gatherer picks this plant, it places a pumpkin on the
	 * RipePumpkins and starts growing another one.
	 * 
	 * @param pumps
	 */
	public void pick() {
		if (!ripes.isFull()) {
			// planted = new Date();
			Pumpkin p = new Pumpkin();
			ripes.add(p);
			Log.getInstance().debug("added: " + p + " to ripes.");
		} else {
			Log.getInstance().debug("The stash is full");
		}
	}
}
