package program3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concrete implementation of the Bathroom interface. This class is responsible
 * for controlling the flow of the users in to and out of the bathroom. It owns
 * the locks and controll's the intrathread communication.
 * 
 * @author matt
 *
 */
public class BathroomImpl implements Bathroom {

	private Lock lock = new ReentrantLock();

	private OutputHandler output = null;

	private Condition womenWaiting = lock.newCondition();
	private Condition menWaiting = lock.newCondition();

	private AtomicInteger menWithin = new AtomicInteger();
	private AtomicInteger womenWithin = new AtomicInteger();
	private AtomicInteger zombiesWithin = new AtomicInteger();

	private int menWaitingCount = 0;
	private int womenWaitingCount = 0;

	private long startTime = 0;

	public BathroomImpl() {
		startTime = System.currentTimeMillis();
	}

	public BathroomImpl(OutputHandler out) {
		this();
		output = out;
	}

	/**
	 * A women tries to enter the bathroom. This call triggers the appropriate
	 * counters and acquires a lock initially. The woman has to wait until the
	 * men have left the bathroom.
	 */
	@Override
	public void enterWoman() {
		womenWaitingCount++;
		lock.lock();
		event("Arrive W");
		try {
			while (menWithin.get() > 0) {
				womenWaiting.await();
			}
			womenWaitingCount--;
			event("Enter W");
			womenWaiting.signalAll();
			womenWithin.incrementAndGet();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * A man tries to enter the bathroom. This call triggers the appropriate
	 * counters and acquires a lock initially. The man has to wait until the
	 * women have left the bathroom.
	 */
	@Override
	public void enterMan() {
		menWaitingCount++;
		lock.lock();
		event("Arrive M");
		try {
			while (womenWithin.get() > 0) {
				menWaiting.await();
			}
			menWaitingCount--;
			event("Enter M");
			menWaiting.signalAll();
			menWithin.incrementAndGet();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * A Zombie eneters the bathroom. The zombies can enter regardless of
	 * whether a man or woman is in the bathoom because no one cares to see
	 * zombies using the bathroom and zombies dont care about nudity either.
	 * THey only care about BRAINS!
	 */
	@Override
	public void enterZombie() {
		lock.lock();
		try {
			event("Enter Z");
			zombiesWithin.incrementAndGet();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Woman leaves the bathroom and reduces the occupied counter.
	 */
	@Override
	public void leaveWoman() {
		event("Leave W");
		womenWithin.decrementAndGet();
	}

	/**
	 * Write an event to the report.
	 * @param in Event
	 */
	private void event(String in) {
		output.event(getElapsedTime(), in, getBathroomStatus(), getQueueStatus());
	}

	/**
	 * Builds the string representation of the men and women waiting.
	 * @return
	 */
	private String getQueueStatus() {
		String ret = "";
		for (int i = 0; i < menWaitingCount; i++) {
			ret += "M";
		}
		for (int i = 0; i < womenWaitingCount; i++) {
			ret += "W";
		}
		return ret;
	}

	/**
	 * Builds the string representation of the men/women/zombies using the bathroom.
	 * @return
	 */
	private String getBathroomStatus() {
		String ret = "";
		for (int i = 0; i < menWithin.get(); i++) {
			ret += "M";
		}
		for (int i = 0; i < womenWithin.get(); i++) {
			ret += "W";
		}
		for (int i = 0; i < zombiesWithin.get(); i++) {
			ret += "Z";
		}
		return ret;
	}

	/**
	 * Man leaves the bathroom.
	 */
	@Override
	public void leaveMan() {
		menWithin.decrementAndGet();
	}

	/**
	 * Zombie leaves the bathroom.
	 */
	@Override
	public void leaveZombie() {
		zombiesWithin.decrementAndGet();
	}

	/**
	 * Returns the elapsed time since the creation of this object.
	 * @return
	 */
	private long getElapsedTime() {
		return System.currentTimeMillis() - startTime;
	}

}
