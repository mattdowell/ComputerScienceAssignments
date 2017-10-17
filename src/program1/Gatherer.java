package program1;

/**
 * Jack has a stash that can hold 10,000 pumpkins.
 * 
 * 
 * Implementation:
 * 
 * This class is implemented as a Runnable, waking every X time units. Its
 * responsibilities are to check the ripeness of pumpkins, if they are ripe,
 * pick them. (there is an alternate implementation where the PLants are threads
 * and they ripen themselves). The Gatherer also determines if a stash is at
 * capacity, and if so it will uproot X number of plants, to slow production.
 * 
 * When a pumpkin is ripe, it is moved from the RipePumpkins storage to the
 * Stash storage.
 * 
 * 
 * @author matt
 *
 */
public class Gatherer implements Runnable {

	private boolean alive = true;

	private final PlantField sower;
	private final RipePumpkins ripes;
	private final Stash stash;
	private boolean stashAtCapacity = false;

	/**
	 * 
	 * @param sower
	 *            The field where pumpkins are stored
	 * @param ripes
	 *            Ripening pumpkins
	 * @param stash
	 *            Picked pumpkins, ready for delivery.
	 */
	public Gatherer(PlantField sower, RipePumpkins ripes, Stash stash) {
		super();
		this.sower = sower;
		this.ripes = ripes;
		this.stash = stash;
	}

	/**
	 * In this method, we check the growth of the pumpkins and pick them if they
	 * are ripe. If they are ripe, they are placed on the stash.
	 * 
	 * @throws InterruptedException
	 */
	private void visitPatch() {
		// sower.checkGrowth(); Use in non-thread mode
		pickPumpkin();
	}

	/**
	 * A pumpkin that has ripened is placed on the Stash.
	 * 
	 * @return true if stored.
	 * @throws InterruptedException
	 */
	private void pickPumpkin() {
		Pumpkin p = this.ripes.pop();
		Log.getInstance().debug("Popped: " + p + " off of ripes. Size: " + ripes.size());
		if (p != null) {
			this.stash.add(p);
		}
	}

	/**
	 * The gathererer's work: - If the stash gets full, uproot 5 plants - If the
	 * stash is not full, call the visitPatch() method every X time units.
	 */
	@Override
	public void run() {
		while (alive) {
			try {
				// If the stash gets full, he will not collect any more pumpkins
				// until his stash drops to at most 9000 pumpkins.
				if (stashAtCapacity) {
					// check to see if it is down to 9000
					if (stash.size() < 9001) {
						stashAtCapacity = false;
					} else {
						// We are at capacity, so uproot 5 plants.
						sower.uproot(5);
						
						// Give it some time after uprooting, otherwise we uproot all quickly
						Thread.sleep(100);
					}
				}

				if (stash.isFull()) {
					stashAtCapacity = true;
				}

				// After the checking, we know we have room, so...
				if (!stashAtCapacity && !this.ripes.isEmpty()) {
					visitPatch();
				}

				Thread.sleep(TimeUtil.getGatherTime());

			} catch (InterruptedException e) {
				e.printStackTrace();
				Log.getInstance().debug(e.toString());
				kill();
			}
		}
	}

	/**
	 * Safe method for killing this thread.
	 */
	public void kill() {
		alive = false;
	}
}
