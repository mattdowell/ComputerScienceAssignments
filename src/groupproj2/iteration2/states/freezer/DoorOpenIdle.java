package groupproj2.iteration2.states.freezer;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.states.AbstractState;
import groupproj2.iteration2.states.Timer;

public class DoorOpenIdle extends AbstractState {

	public DoorOpenIdle() {
		myTimer = new Timer(RefridgeratorSystem.getFreezerTimeCoolingNotActiveDoorOpen());
	}

	@Override
	public void process(StateContext inCtx, RefridgeratorSystem inSystem) {

		inSystem.setFreezerCoolingState(CoolingState.Off);

		if (myTimer.ready()) {
			// The time to raise the temp by one degree is upon us
			System.out.println("Raising freezer temp, door open");
			if (inSystem.getFreezerTemp() < inSystem.getRoomTemp()) {
				inSystem.setFreezerTemp(inSystem.getFreezerTemp() + 1);
			}
			myTimer.reset();
		} else {
			myTimer.tick();
		}

		// The user closed the door
		if (inSystem.getFreezerDoorState() == RefridgeratorSystem.DoorState.Closed) {

			// Are we still cooling?
			if (inSystem.getFreezerTemp() > inSystem.getDesiredFreezerTemp()) {
				inCtx.setFreezerState(new DoorClosedCooling());
			} else {
				inCtx.setFreezerState(new DoorClosedIdle());
			}

		} else {

			// Are we now cooling?
			if (inSystem.getFreezerTemp() > inSystem.getDesiredFreezerTemp()) {
				inCtx.setFreezerState(new DoorOpenCooling());
			}
		}
	}

}
