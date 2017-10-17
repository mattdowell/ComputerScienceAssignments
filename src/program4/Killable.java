package program4;

/**
 * This is a simple interface that all the threads have to implement so we can
 * safely kill the threads when the program is done running. Nice and neat.
 * 
 * @author matt
 *
 */
public interface Killable {

	// Tells the thread to die
	public void kill();

	// Gets the thread id
	public long getId();
}
