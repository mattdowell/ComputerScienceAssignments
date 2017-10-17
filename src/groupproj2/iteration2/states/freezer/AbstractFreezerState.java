package groupproj2.iteration2.states.freezer;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.states.AbstractState;

/**
 * Parent Freezer state. Any common freezer methods go here.
 * @author Matt
 *
 */
public abstract class AbstractFreezerState extends AbstractState {

	/**
	 * Use the parent business logic but pass in the Freezer parameters to determing cooling.
	 * @param inSystem
	 * @return
	 */
	boolean reachedDeltaHighTemp(RefridgeratorSystem inSystem) {
		return reachedDeltaHighTemp(inSystem.getFreezerTemp(), inSystem.getDesiredFreezerTemp(),
				RefridgeratorSystem.getFreezerTempDeltaForActive());
	}

}
