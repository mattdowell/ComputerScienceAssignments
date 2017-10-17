package groupproj2.iteration2.states.fridge;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.states.AbstractState;

/**
 * Parent Fridge state. Any common methods for Fridge's go here.
 * 
 * @author Matt
 *
 */
public abstract class AbstractFridgeState extends AbstractState {

	/**
	 * This method utilizises the parent business logic to determing cooling, but passes in the Fridge parameters.
	 * @param inSystem
	 * @return
	 */
	boolean reachedDeltaHighTemp(RefridgeratorSystem inSystem) {
		return reachedDeltaHighTemp(inSystem.getFridgeTemp(), inSystem.getDesiredFridgeTemp(),
				RefridgeratorSystem.getFridgeTempDeltaForActive());
	}
}
