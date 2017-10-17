package groupproj2.iteration2.states.fridge;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.states.Timer;

public class DoorClosedIdle extends AbstractFridgeState {

	public DoorClosedIdle() {
		myTimer = new Timer(RefridgeratorSystem.getFridgeTimeCoolingNotActiveDoorClosed());
	}

	@Override
	public void process(StateContext inCtx, RefridgeratorSystem inSystem) {

		inSystem.setFridgeCoolingState(CoolingState.Off);

		if (myTimer.ready()) {
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
		if (inSystem.getFridgeDoorState() == RefridgeratorSystem.DoorState.Open) {

			// Should we be cooling?
			if (needToBeCooling) {
				inCtx.setFridgeState(new DoorOpenCooling());
			} else {
				inCtx.setFridgeState(new DoorOpenIdle());
			}

		} else {

			// Are we now cooling?
			if (needToBeCooling) {
				inCtx.setFridgeState(new DoorClosedCooling());
			}
		}
	}
}
