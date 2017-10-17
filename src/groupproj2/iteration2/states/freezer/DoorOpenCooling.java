package groupproj2.iteration2.states.freezer;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.RefridgeratorSystem.DoorState;
import groupproj2.iteration2.states.AbstractState;
import groupproj2.iteration2.states.Timer;

public class DoorOpenCooling extends AbstractState {

	public DoorOpenCooling() {
		myTimer = new Timer(RefridgeratorSystem.getFreezerCoolingTimeOneDegree());
	}

	@Override
	public void process(StateContext inCtx, RefridgeratorSystem inSystem) {
		
		inSystem.setFreezerCoolingState(CoolingState.On);
		
		// Check the temp
		if (myTimer.ready()) {
			inSystem.setFreezerTemp(inSystem.getFreezerTemp() - 1);
			myTimer.reset();
		} else {
			myTimer.tick();
		}
		
		// Did the user close the door
		if (inSystem.getFreezerDoorState() == DoorState.Closed) {
			
			// Are we still cooling?
			if (inSystem.getFreezerTemp() > inSystem.getDesiredFreezerTemp()) {
				inCtx.setFreezerState(new DoorClosedCooling());
			} else {
				inCtx.setFreezerState(new DoorClosedIdle());
			}
			
		} else {
			
			// Are we now idle?
			if (inSystem.getFreezerTemp() <= inSystem.getDesiredFreezerTemp()) {
				inCtx.setFreezerState(new DoorOpenIdle());
			} 
		}
	}

}
