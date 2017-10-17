package groupproj2.iteration2.states;

import groupproj2.iteration2.RefridgeratorSystem;
import groupproj2.iteration2.StateContext;

/**
 * Parent, Abstract state. All concrete states must subclass this.
 * @author Matt Dowell
 *
 */
public abstract class AbstractState {
	
	protected Timer myTimer = null;
	
	public abstract void process(StateContext inCtx, RefridgeratorSystem inSystem);
	
	/**
	 * This method retails the business logic for any "warming" states on when to start cooling.
	 * @param inCurrentTemp
	 * @param inDesiredTemp
	 * @param inDelta
	 * @return
	 */
	protected boolean reachedDeltaHighTemp(int inCurrentTemp, int inDesiredTemp, int inDelta) {
		if (inCurrentTemp > (inDesiredTemp + inDelta)) {
			return true;
		} else {
			return false;
		}
	}

}
