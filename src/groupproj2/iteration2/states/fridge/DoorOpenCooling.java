package groupproj2.iteration2.states.fridge;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.RefridgeratorSystem.DoorState;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.states.AbstractState;
import groupproj2.iteration2.states.Timer;

public class DoorOpenCooling extends AbstractState {
	
	public DoorOpenCooling() {
		myTimer = new Timer(RefridgeratorSystem.getFridgeCoolingTimeOneDegree());
	}

	@Override
	public void process(StateContext inCtx, RefridgeratorSystem inSystem) {
		
		inSystem.setFridgeCoolingState(CoolingState.On);
		
		// Check the temp
		if (myTimer.ready()) {
			inSystem.setFridgeTemp(inSystem.getFridgeTemp() - 1);
			myTimer.reset();
		} else {
			myTimer.tick();
		}
		
		// Did the user close the door
		if (inSystem.getFridgeDoorState() == DoorState.Closed) {
			
			// Are we still cooling?
			if (inSystem.getFridgeTemp() > inSystem.getDesiredFridgeTemp()) {
				inCtx.setFridgeState(new DoorClosedCooling());
			} else {
				inCtx.setFridgeState(new DoorClosedIdle());
			}
			
		} else {
			
			// Are we now idle?
			if (inSystem.getFridgeTemp() <= inSystem.getDesiredFridgeTemp()) {
				inCtx.setFridgeState(new DoorOpenIdle());
			} 
		}
	}

}
