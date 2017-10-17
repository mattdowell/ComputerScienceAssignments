package groupproj1.iteration2;
/**
 * Code taken directly from library example
 * 
 * @author Andre
 *
 * @param <K>
 *            the key type
 */
public interface Matchable<K> {
	/**
	 * Checks whether an item's key matches the given key.
	 * 
	 * @param key
	 *            the key value
	 * @return true iff the item's key matches the given key
	 */
	public boolean matches(K key);
}
