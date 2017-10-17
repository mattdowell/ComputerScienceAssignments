package program1;

/**
 * 
 * Implementation:
 * 
 * - Each plant is a thread. When it ripens, it places its pumpkin in the stash.
 * - OR Each plant has a maturity date, and this class is responsible for
 * picking the plant when it has reached maturity, and when it does that it
 * creates a new pumpkin object and places it on the Stash collection.
 * 
 * @author matt
 *
 */
public class PlantField {

	private static int INITIAL_PLANT_COUNT = 1000;
	private final RipePumpkins stash;
	private final PlantList plants = new PlantList(INITIAL_PLANT_COUNT * 10);

	/**
	 * Create a field, with a handle on the collection needed to store ripe
	 * pumpkins.
	 * 
	 * @param ripes,
	 *            RipePumpkin collection.
	 */
	public PlantField(final RipePumpkins stash) {
		super();
		this.stash = stash;
		Log.field = this;
		plant(1000);
	}

	/**
	 * Allows a caller to uproot a given number of plants. If they are threads,
	 * they are killed gently, and if they are domain objects they are just
	 * removed.
	 * 
	 * @param numPlants
	 *            number of plants to uproot
	 */
	public void uproot(int numPlants) {

		for (int i = 0; i < numPlants; i++) {
			Plant t = plants.pop();
			if (t != null) {
				t.uproot();
				Log.getInstance().event("plant uprooted");
			}
		}
	}

	/**
	 * Allows a caller to plant more pumpkin plants.
	 * 
	 * @param numPlants
	 *            number of plants to increase in our field
	 */
	public void plant(int numPlants) {
		for (int i = 0; i < numPlants; i++) {
			Plant t = new Plant(stash);
			t.start();
			plants.add(t);
			Log.getInstance().event("new plant sown");
		}
	}

	/**
	 * Checks the maturity of the non-thread Plants. If they are ripe, it tells
	 * the plant to pick the pumpkin.
	 * 
	 * ONLY IF NOT THREAD
	 */
//	public void checkGrowth() {
//		for (Plant p : plants) {
//			if (p.getUnitsSincePlanted() >= TimeUtil.getPlantGrowthTime()) {
//				p.pick(ripes);
//			}
//		}
//	}

	public int getPlantsSize() {
		return plants.size();
	}

}
