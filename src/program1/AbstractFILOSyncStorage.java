package program1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is the parent class for all of our FILO storage mechanisms. It synchronizes
 * on access to the internal collection. You have to specify the capacity at
 * creation, and you cannot exceed the capacity or a IllegalStateException is
 * thrown.
 * 
 * This class is a little more efficient than our FIFO mechanism because it
 * uses a Object[] as its underlying storage. You cannot change the size
 * after the initial capacity call.
 * 
 * 
 * @author matt
 *
 * @param <E>
 *            The object type of the storage.
 */
public abstract class AbstractFILOSyncStorage<E> implements Iterable<E> {

	private final Object[] objects;
	private int index = 0;

	public AbstractFILOSyncStorage(int capacity) {
		objects = new Object[capacity];
	}

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
		if (isFull()) {
			throw new IllegalStateException("Capacity exceeded.");
		}
		synchronized (objects) {
			objects[index] = inE;
			index++;
		}
	}

	/**
	 * Pops the last (end) object off the end of the internal storage. Should be
	 * thread safe, as it synchronizes on the collection.
	 * 
	 * @return The last object in the stack, or null if empty.
	 */
	@SuppressWarnings("unchecked")
	public E pop() {
		synchronized (objects) {
			if (index > 0 && index <= objects.length) {
				//Log.debug("Popping index: " + index + " from object length: " + objects.length);
				return (E) objects[--index];
			} 
			return null;
		}

	}

	/**
	 * Gives the size of the internal storage, not the capacity.
	 * 
	 * @return number of objects stored
	 */
	public int size() {
		return index;
	}

	public boolean isFull() {
		return (index == objects.length);
	}

	public boolean isEmpty() {
		if (index <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns an iterator of all objects stored.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
		synchronized (objects) {
			List<E> objs = new ArrayList<E>(index);
			for (int i = 0; i < index + 1; i++) {
				E o = (E) objects[i];
				if (o != null) {
					objs.add(o);
				}
			}
			return objs.iterator();
		}
	}
}
