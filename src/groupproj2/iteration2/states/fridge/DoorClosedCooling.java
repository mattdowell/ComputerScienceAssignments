package groupproj2.iteration2.states.fridge;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.RefridgeratorSystem.CoolingState;
import groupproj2.iteration2.RefridgeratorSystem.DoorState;
import groupproj2.iteration2.StateContext;
import groupproj2.iteration2.states.AbstractState;
import groupproj2.iteration2.states.Timer;


public class DoorClosedCooling extends AbstractState {
	
	public DoorClosedCooling() {
		myTimer = new Timer(RefridgeratorSystem.getFridgeTimeCoolingNotActiveDoorClosed());
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
		if (inSystem.getFridgeDoorState() == DoorState.Open) {
			
			// Are we still cooling?
			if (inSystem.getFridgeTemp() > inSystem.getDesiredFridgeTemp()) {
				inCtx.setFridgeState(new DoorOpenCooling());
			} else {
				inCtx.setFridgeState(new DoorOpenIdle());
			}
			
		} else {
			
			// Are we now idle?
			if (inSystem.getFridgeTemp() <= inSystem.getDesiredFridgeTemp()) {
				inCtx.setFridgeState(new DoorClosedIdle());
			} 
		}
	}	
}
