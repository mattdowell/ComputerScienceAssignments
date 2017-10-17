package pa3;

/**
 * This interface is the contract for any uses of the MMU implementation.
 * 
 * @author matt
 *
 */
public interface MMU {

	boolean writeMemory(int address, int value);

	int readMemory(int address);

	void startSimulation();

	void stopSimulation();

	String getReferenceString();

	int getTotalPageFaults();
}
