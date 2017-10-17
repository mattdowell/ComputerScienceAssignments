package groupproj2.iteration1;

/**
 * This class encapsulates our business logic, and gets called by our Clock.
 * 
 * Fridge: A unit where the temperature is kept between 37 and 41 degrees
 * Freezer: A unit where the temperature is kept between 0 and -9 degrees Room:
 * May vary between 50 degrees and 110 degrees.
 * 
 * PotentialStates  (for refactoring to the State pattern)
 * ----------------
 * Note: Separated in to two state categories that are independent of each other
 * 
 * DoorOpenCooling
 * DoorOpenWarming
 * DoorOpenIdle
 * DoorClosedCooling
 * DoorClosedWarming
 * DoorClosedIdle 
 * 
 *
 */
public class RefridgeratorRulesEngine implements RefridgeratorStateChangeListener {

	// When the temperature within a unit is 1 degree above the desired
	// temperature, the cooling system becomes active.
	// private static final int TEMP_DELTA = 1;


	/**
	 * Called as a RefridgeratorStateChangeListener from the Clock
	 */
	@Override
	public void configurationChanged(RefridgeratorState inConf) {
		process(inConf);		
	}
	
	/**
	 * This method is called for every Clock Tick. This is one tick through the
	 * process.
	 * 
	 * @param currentState
	 */
	public static void process(RefridgeratorState state) {

		// First, make sure the desired temps are not out of bounds.
		checkBoundaryValues(state);

		// When the temperature within a unit is 1 degree above the desired
		// temperature, the cooling system becomes active.
		checkCurrentTempState(state);

		// Although there is insulation, the fact that the room temperature is
		// much higher than the inside temperatures
		// of the two units, the freezer and the fridge temperatures increase
		// and when they become too high,
		// the cooling units (compressor , etc.) start working again.
		checkFridgeWarmingState(state);

		checkFreezerwarmingState(state);
		
		checkFridgeCoolingState(state);
		
		checkFreezerCoolingState(state);
	}

	/**
	 * Checks to see if the fridge cooling state is on, and if so it checks the timer.
	 * @param state
	 */
	private static void checkFridgeCoolingState(RefridgeratorState state) {
		if (state.getFridgeCoolingState() == RefridgeratorState.CoolingState.On) {
			if (state.getFridgeCoolingTimer().ready()) {
				//System.out.println("Lowering freezer temp");
				state.setFridgeTemp(state.getFridgeTemp() - 1);
				state.getFridgeCoolingTimer().reset();
			} else {
				state.getFridgeCoolingTimer().tick();
			}			
		}
	}

	/**
	 * Checks to see if the freezer cooling state is on, and if so it checks the timer.
	 * @param state
	 */
	private static void checkFreezerCoolingState(RefridgeratorState state) {
		if (state.getFreezerCoolingState() == RefridgeratorState.CoolingState.On) {
			if (state.getFreezerCoolingTimer().ready()) {
				//System.out.println("Lowering freezer temp");
				state.setFreezerTemp(state.getFreezerTemp() - 1);
				state.getFreezerTimeTimerDoorOpen().reset();
			} else {
				state.getFreezerCoolingTimer().tick();
			}
		}
	}

	/**
	 * Checks to see if the freezer cooling state is off, and if so it checks the timer
	 * that raises the temp.
	 * @param state
	 */
	private static void checkFreezerwarmingState(RefridgeratorState state) {
		if (state.getFreezerCoolingState() == RefridgeratorState.CoolingState.Off) {

			// OPEN DOOR
			if (state.getFreezerDoorState() == RefridgeratorState.DoorState.Open) {
				if (state.getFreezerTimeTimerDoorOpen().ready()) {
					// The time to raise the temp by one degree is upon us
					System.out.println("Raising freezer temp, door open");
					state.setFreezerTemp(state.getFreezerTemp() + 1);
					state.getFreezerTimeTimerDoorOpen().reset();
				} else {
					state.getFreezerTimeTimerDoorOpen().tick();
				}
			} else {
				// CLOSED DOOR
				if (state.getFreezerTimeTimerDoorClosed().ready()) {
					System.out.println("Raising freezer temp, door closed");
					// The time to raise the temp by one degree is upon us
					state.setFreezerTemp(state.getFreezerTemp() + 1);
					state.getFreezerTimeTimerDoorClosed().reset();
				} else {
					state.getFreezerTimeTimerDoorClosed().tick();
				}
			}
		}
	}

	/**
	 * Checks to see if the fridge cooling state is off and if so it checks the timer
	 * that raises the temperature.
	 * 
	 * @param state
	 */
	private static void checkFridgeWarmingState(RefridgeratorState state) {
		// the fridge door is open and the compressor is off. Start the heating
		// timer
		if (state.getFridgeCoolingState() == RefridgeratorState.CoolingState.Off) {

			// OPEN DOOR
			if (state.getFridgeDoorState() == RefridgeratorState.DoorState.Open) {
				if (state.getFridgeTimeTimerDoorOpen().ready()) {
					// The time to raise the temp by one degree is upon us
					System.out.println("Raising fridge temp, door open");
					state.setFridgeTemp(state.getFridgeTemp() + 1);
					state.getFridgeTimeTimerDoorOpen().reset();
				} else {
					state.getFridgeTimeTimerDoorOpen().tick();
				}
			} else {
				// CLOSED DOOR
				if (state.getFridgeTimeTimerDoorClosed().ready()) {
					System.out.println("Raising fridge temp, door closed");
					// The time to raise the temp by one degree is upon us
					state.setFridgeTemp(state.getFridgeTemp() + 1);
					state.getFridgeTimeTimerDoorClosed().reset();
				} else {
					state.getFridgeTimeTimerDoorClosed().tick();
				}
			}
		}
	}

	/**
	 * Checks the current temp and adjusts the current state accordingly.
	 * @param state
	 */
	private static void checkCurrentTempState(RefridgeratorState state) {
		if (state.getFridgeTemp() > state.getDesiredFridgeTemp()) {
			System.out.println("Setting fridge cooling on");
			state.setFridgeCoolingState(RefridgeratorState.CoolingState.On);
		} else {
			System.out.println("Setting fridge cooling off");
			state.setFridgeCoolingState(RefridgeratorState.CoolingState.Off);
		}
		if (state.getFreezerTemp() > state.getDesiredFreezerTemp()) {
			System.out.println("Setting freezer cooling on");
			state.setFreezerCoolingState(RefridgeratorState.CoolingState.On);
		} else {
			System.out.println("Setting freezer cooling off");
			state.setFreezerCoolingState(RefridgeratorState.CoolingState.Off);
		}
	}

	/**
	 * Checks our max/min values and adjusts the user inputted values if needed
	 * 
	 * @param state
	 */
	private static void checkBoundaryValues(RefridgeratorState state) {
		if (state.getDesiredFreezerTemp() < state.getLowestFreezerTemp()) {
			System.out.println("Desired freezer temp too low. Setting default");
			state.setDesiredFreezerTemp(state.getLowestFreezerTemp());
		}
		if (state.getDesiredFreezerTemp() > state.getHighestFreezerTemp()) {
			System.out.println("Desired freezer temp too high. Setting default");
			state.setDesiredFreezerTemp(state.getHighestFreezerTemp());
		}
		if (state.getDesiredFridgeTemp() < state.getLowestFridgeTemp()) {
			System.out.println("Desired fridge temp too low. Setting default");
			state.setDesiredFridgeTemp(state.getLowestFridgeTemp());
		}
		if (state.getDesiredFridgeTemp() > state.getHighestFridgeTemp()) {
			System.out.println("Desired fridge temp too high. Setting default");
			state.setDesiredFridgeTemp(state.getHighestFridgeTemp());
		}
		if (state.getDesiredRoomTemp() < state.getLowestRoomTemp()) {
			System.out.println("Desired room temp too low. Setting default");
			state.setDesiredRoomTemp(state.getLowestRoomTemp());
		}
		if (state.getDesiredRoomTemp() > state.getHighestRoomTemp()) {
			System.out.println("Desired room temp too low. Setting default");
			state.setDesiredRoomTemp(state.getHighestRoomTemp());
		}
	}

}
