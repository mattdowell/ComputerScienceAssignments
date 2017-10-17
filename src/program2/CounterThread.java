package program2;

/**
 * Thread that increments a counter, and uses a SpinLock implementation to
 * acquire the lock, increment the counter, and unlock the lock.
 * 
 * @author matt
 *
 */
public class CounterThread extends Thread {

	private SpinLock lock;
	private boolean alive = true;
	private Counter counter;

	public CounterThread(SpinLock lock, Counter count) {
		super();
		this.lock = lock;
		this.counter = count;
	}

	@Override
	public void run() {
		while (alive) {
			lock.lock();
			try {
				counter.increment();
				lock.unlock();
			} catch (Exception e) {
				System.err.println(e.toString());
				kill();
			}
		}
	}

	public void kill() {
		alive = false;
	}
}
