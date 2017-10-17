package groupproj2.iteration2.states.freezer;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.states.AbstractState;
import groupproj2.iteration2.states.Timer;

public class DoorClosedIdle extends AbstractState {

	public DoorClosedIdle() {
		myTimer = new Timer(RefridgeratorSystem.getFreezerTimeCoolingNotActiveDoorClosed());
	}

	@Override
	public void process(StateContext inCtx, RefridgeratorSystem inSystem) {

		inSystem.setFreezerCoolingState(CoolingState.Off);

		if (myTimer.ready()) {
			System.out.println("Raising freezer temp, door closed");
			if (inSystem.getFreezerTemp() < inSystem.getRoomTemp()) {
				inSystem.setFreezerTemp(inSystem.getFreezerTemp() + 1);
			}
			myTimer.reset();
		} else {
			myTimer.tick();
		}

		// The user closed the door
		if (inSystem.getFreezerDoorState() == RefridgeratorSystem.DoorState.Open) {

			// Are we still cooling?
			if (inSystem.getFreezerTemp() > inSystem.getDesiredFreezerTemp()) {
				inCtx.setFreezerState(new DoorOpenCooling());
			} else {
				inCtx.setFreezerState(new DoorOpenIdle());
			}

		} else {

			// Are we now cooling?
			if (inSystem.getFreezerTemp() > inSystem.getDesiredFreezerTemp()) {
				inCtx.setFreezerState(new DoorClosedCooling());
			}
		}
	}

}
