package program1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This is an abstract implementation of a synchronized, First-in First-out storage 
 * mechanism. The underlying storage mechanism is a FIFO queue, with a LinkedList
 * implementation.
 * 
 * @author matt
 *
 * @param <E>
 */
public abstract class AbstractFIFOSyncStorage<E> implements Iterable<E> {

	private final Queue<E> objects = new LinkedList<E>();

	/**
	 * Adds an object to the collection. It synchronizes on the object array and
	 * increments the index.
	 * 
	 * @param inE
	 *            object added to end of collection. NPE thrown if null
	 */
	public void add(E inE) {
		if (inE == null) {
			throw new IllegalStateException("Cannot add null object");
		}

		synchronized (objects) {
			objects.add(inE);
		}
	}

	/**
	 * Pops the last (end) object off the end of the internal storage. Should be
	 * thread safe, as it synchronizes on the collection.
	 * 
	 * @return The last object in the stack, or null if empty.
	 */
	public E pop() {
		synchronized (objects) {
			return objects.poll();
		}
	}

	/**
	 * Gives the size of the internal storage, not the capacity.
	 * 
	 * @return number of objects stored
	 */
	public int size() {
		return objects.size();
	}

	public boolean isEmpty() {
		synchronized (objects) {
			return objects.isEmpty();
		}
	}

	/**
	 * Returns an iterator of all objects stored.
	 */
	@Override
	public Iterator<E> iterator() {
		synchronized (objects) {
			int index = objects.size();
			List<E> objs = new ArrayList<E>(index);
			for (int i = 0; i < index + 1; i++) {
				E o = (E) objects.poll();
				if (o != null) {
					objs.add(o);
				}
			}
			return objs.iterator();
		}
	}

}
