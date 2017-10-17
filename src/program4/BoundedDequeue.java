package program4;

/**
 * This class has a method that allows you to push an item at the front of the
 * data structure as well as enqueue and dequeue it.
 * 
 * @author matt
 *
 * @param <T>
 */
public class BoundedDequeue<T> extends BoundedQueue<T> {

	public BoundedDequeue(int _capacity) {
		super(_capacity);
	}

	/**
	 * This method is similar to the Enq method in the parent class but instead
	 * of appending the item to the end of the linked-list chain, it prepends it
	 * to the head node and pushes the rest of the items down.
	 * 
	 * @param x
	 */
	public void pushToFront(T x) {
		boolean mustWakeDequeuers = false;
		enqLock.lock();
		try {
			while (size.get() == capacity) {
				notFullCondition.await();
			}

			// Since the DEQUEUE method in the parent class can manipulate the
			// head, in a different lock, we have to synchornize on this.
			synchronized (head) {
				Node newHead = new Node(x);
				newHead.next = head;
				head = newHead;
			}
			if (size.getAndIncrement() == 0) { // in case people are waiting to
												// dequeue
				mustWakeDequeuers = true;
			}
		} catch (InterruptedException ie) {
			System.out.println("enq(): Interrupted Exception");
		} finally {
			enqLock.unlock();
		}

		if (mustWakeDequeuers) {
			deqLock.lock();
			try {
				notEmptyCondition.signalAll();
			} finally {
				deqLock.unlock();
			}
		}
	}
}
