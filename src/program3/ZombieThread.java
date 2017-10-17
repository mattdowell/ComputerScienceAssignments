package program3;

/**
 * Zombie bathroom user thread. Simple concrete implementation of the parent
 * AbstractBathroomUser.
 * 
 * @author matt
 *
 */
public class ZombieThread extends AbstractBathroomUser implements BathroomUser {

	public ZombieThread(Bathroom bathroom, BathroomInput input) {
		super(bathroom, input);
	}

	public void run() {

		bathroom.enterZombie();
		doMyThing();
		bathroom.leaveZombie();

	}
}
