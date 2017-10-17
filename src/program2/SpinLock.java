package program2;

/**
 * This is the parent interface for a spin lock. It is a simple lock/unlock
 * definition.
 * 
 * @author matt
 *
 */
public interface SpinLock {

	public void lock();

	public void unlock();

}
