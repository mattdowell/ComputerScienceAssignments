package program3;

/**
 * Male bathroom user thread. Simple concrete implementation of the parent
 * AbstractBathroomUser.
 * 
 * @author matt
 *
 */
public class MaleThread extends AbstractBathroomUser implements BathroomUser {

	public MaleThread(Bathroom bathroom, BathroomInput input) {
		super(bathroom, input);
	}

	public void run() {
		bathroom.enterMan();
		doMyThing();
		bathroom.leaveMan();
	}

}
