package program1;

/**
 * Requirements:
 * 
 * Jack has a stash that can hold 10,000 pumpkins. If the stash gets full, he
 * will not collect any more pumpkins until his stash drops to at most 9000
 * pumpkins. A full stash means that the pumpkins are being produced faster than
 * they can be consumed, so they might rot. To avoid this, when the stash gets
 * full, in addition to not collecting pumpkins for a while, he will uproot 5
 * pumpkin plants and use them for compost. The plants do not have to be chosen
 * at random.
 * 
 * Implementation:
 * 
 * Pumpkin implementation of our synchronized Stash collection.
 * 
 * @author matt
 *
 */
public class Stash extends AbstractFILOSyncStorage<Pumpkin> {

	public Stash(int capacity) {
		super(capacity);
	}

}
