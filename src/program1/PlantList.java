package program1;

/**
 * Implementation of the collection that stores all our plants.
 * 
 * @author matt
 *
 */
public class PlantList extends AbstractFILOSyncStorage<Plant> {

	public PlantList(int capacity) {
		super(capacity);
	}

}
