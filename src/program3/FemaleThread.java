package program3;

/**
 * Female bathroom user thread. Simple concrete implementation of the parent
 * AbstractBathroomUser.
 * 
 * @author matt
 *
 */
public class FemaleThread extends AbstractBathroomUser implements BathroomUser {

	public FemaleThread(Bathroom bathroom, BathroomInput input) {
		super(bathroom, input);
	}

	public void run() {

		bathroom.enterWoman();
		doMyThing();
		bathroom.leaveWoman();

	}

}
