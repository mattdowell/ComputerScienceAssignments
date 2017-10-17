package groupproj2.iteration2.states;

/**
 * This inner convenience class keeps track of some of our settings that are not
 * changed every tick of the clock. Some of our system settings have to wait X
 * number of ticks before they change their state. This class keeps track of
 * whether they have waited long enough.
 * 
 *
 */
public class Timer {
	int timeToWait;
	int numTicks;

	public Timer(int inTimeToWait) {
		timeToWait = inTimeToWait;
		numTicks = 0;
	}

	public void tick() {
		numTicks++;
	}

	public void reset() {
		numTicks = 0;
	}

	public boolean ready() {
		return (numTicks >= timeToWait);
	}
}
