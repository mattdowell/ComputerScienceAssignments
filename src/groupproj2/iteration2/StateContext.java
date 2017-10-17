package groupproj2.iteration2;

import groupproj2.iteration2.states.AbstractState;

/**
 * The temperature differential for the freezer and fridge cooling systems to
 * kick in is...
 * 
 * @author Matt Dowell
 *
 */
public class StateContext implements RefridgeratorStateChangeListener {

	// Set initial state.
	private AbstractState myFridgeState;
	private AbstractState myFreezerState;

	private static final StateContext INSTANCE = new StateContext();

	private StateContext() {
		setFridgeState(new groupproj2.iteration2.states.fridge.DoorClosedIdle());
		setFreezerState(new groupproj2.iteration2.states.freezer.DoorClosedIdle());
	}

	public static StateContext getInstance() {
		return INSTANCE;
	}

	public void setFridgeState(final AbstractState newState) {
		System.out.println("Setting fridge state: " + newState.getClass().getName());
		myFridgeState = newState;
	}

	public void setFreezerState(final AbstractState newState) {
		System.out.println("Setting freezer state: " + newState.getClass().getName());
		myFreezerState = newState;
	}

	@Override
	public void configurationChanged(RefridgeratorSystem inConf) {
		process(inConf);
	}

	public void process(final RefridgeratorSystem inSystem) {
		checkBoundaryValues(inSystem);
		myFridgeState.process(this, inSystem);
		myFreezerState.process(this, inSystem);
	}

	/**
	 * Checks our max/min values and adjusts the user inputted values if needed
	 * 
	 * @param inSystem
	 */
	private void checkBoundaryValues(RefridgeratorSystem inSystem) {
		if (inSystem.getDesiredFreezerTemp() < inSystem.getLowestFreezerTemp()) {
			System.out.println("Desired freezer temp too low. Setting default");
			inSystem.setDesiredFreezerTemp(inSystem.getLowestFreezerTemp());
		}
		if (inSystem.getDesiredFreezerTemp() > inSystem.getHighestFreezerTemp()) {
			System.out.println("Desired freezer temp too high. Setting default");
			inSystem.setDesiredFreezerTemp(inSystem.getHighestFreezerTemp());
		}
		if (inSystem.getDesiredFridgeTemp() < inSystem.getLowestFridgeTemp()) {
			System.out.println("Desired fridge temp too low. Setting default");
			inSystem.setDesiredFridgeTemp(inSystem.getLowestFridgeTemp());
		}
		if (inSystem.getDesiredFridgeTemp() > inSystem.getHighestFridgeTemp()) {
			System.out.println("Desired fridge temp too high. Setting default");
			inSystem.setDesiredFridgeTemp(inSystem.getHighestFridgeTemp());
		}
		if (inSystem.getDesiredRoomTemp() < inSystem.getLowestRoomTemp()) {
			System.out.println("Desired room temp too low. Setting default");
			inSystem.setDesiredRoomTemp(inSystem.getLowestRoomTemp());
		}
		if (inSystem.getDesiredRoomTemp() > inSystem.getHighestRoomTemp()) {
			System.out.println("Desired room temp too low. Setting default");
			inSystem.setDesiredRoomTemp(inSystem.getHighestRoomTemp());
		}
	}
}
