package program2;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Outline taken from: 
 * http://furkankamaci.com/implementing-filter-and-bakery-locks-in-java/
 * 
 * @author Furkan KAMACI
 * @author matt
 * 
 */
public class BakeryLock implements SpinLock {
	
	private AtomicBoolean[] flag;
	private AtomicInteger[] label;
	private int n;

	/**
	 * Constructor for Bakery lock
	 *
	 * @param n
	 *            thread count
	 */
	public BakeryLock(int n) {
		init(n);
	}
	
	public BakeryLock() {
		int threadCount = ManagementFactory.getThreadMXBean().getThreadCount() ;
		init(threadCount);
	}
	
	private void init(int n) {
		this.n = n;
		flag = new AtomicBoolean[n];
		label = new AtomicInteger[n];
		for (int i = 0; i < n; i++) {
			flag[i] = new AtomicBoolean();
			label[i] = new AtomicInteger();
		}
	}

	/**
	 * Acquires the lock.
	 */
	@Override
	public void lock() {
		int i = (int) Thread.currentThread().getId();
		flag[i].set(true);
		label[i].set(max(label) + 1);
		for (int k = 0; k < n; k++) {
			while ((k != i) && flag[k].get()
					&& ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {
				// spin wait
			}
		}
	}

	/**
	 * Releases the lock.
	 */
	@Override
	public void unlock() {
		flag[(int) Thread.currentThread().getId()].set(false);
	}

	/**
	 * Finds maximum element within and
	 * {@link java.util.concurrent.atomic.AtomicInteger} array
	 *
	 * @param elementArray
	 *            element array
	 * @return maximum element
	 */
	private int max(AtomicInteger[] elementArray) {
		int maxVal = Integer.MIN_VALUE;
		for (AtomicInteger element : elementArray) {
			if (element.get() > maxVal) {
				maxVal = element.get();
			}
		}
		return maxVal;
	}
}
