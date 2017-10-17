package groupproj2.iteration2.states.fridge;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.states.Timer;

public class DoorOpenIdle extends AbstractFridgeState {

	public DoorOpenIdle() {
		myTimer = new Timer(RefridgeratorSystem.getFridgeTimeCoolingNotActiveDoorOpen());
	}

	@Override
	public void process(StateContext inCtx, RefridgeratorSystem inSystem) {

		inSystem.setFridgeCoolingState(CoolingState.Off);

		if (myTimer.ready()) {
			// The time to raise the temp by one degree is upon us
			System.out.println("Raising fridge temp, door open");
			if (inSystem.getFridgeTemp() < inSystem.getRoomTemp()) {
				inSystem.setFridgeTemp(inSystem.getFridgeTemp() + 1);
			}
			myTimer.reset();
		} else {
			myTimer.tick();
		}

		// Check the delta temperature
		boolean needToBeCooling = reachedDeltaHighTemp(inSystem);

		// The user closed the door
		if (inSystem.getFridgeDoorState() == RefridgeratorSystem.DoorState.Closed) {

			// Are we still cooling?
			if (needToBeCooling) {
				inCtx.setFridgeState(new DoorClosedCooling());
			} else {
				inCtx.setFridgeState(new DoorClosedIdle());
			}

		} else {

			// Are we now cooling?
			if (needToBeCooling) {
				inCtx.setFridgeState(new DoorOpenCooling());
			}
		}
	}

}
