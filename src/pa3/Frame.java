package pa3;

import java.io.Serializable;

/**
 * This class represents a serializable Frame. It has dynamic
 * storage that needs to be intiialized on construction.
 * It also contains a logical/virtual number.
 * 
 * @author matt
 *
 */
public class Frame implements Serializable {

	private static final long serialVersionUID = -4413432893908573824L;
	private int logicalNumber = -1;
	private final int[] storage;

	/**
	 * Construct the frame with a unique logical number.
	 * 
	 * @param inLogNum
	 */
	public Frame(int inLogNum) {
		this.logicalNumber = inLogNum;
		storage = new int[1024];
	}

	/**
	 * Read from the offset-memory location
	 * 
	 * @param inMemLocation
	 * @return int value
	 */
	public int read(int inMemLocation) {
		return storage[inMemLocation];
	}

	/**
	 * Write to the offset, the value.
	 * 
	 * @param offset
	 * @param value
	 */
	public void write(int offset, int value) {
		storage[offset] = value;
	}

	@Override
	public String toString() {
		String ret = "Logical: " + logicalNumber + " -- ";
		for (int i = 0; i < storage.length; i++) {
			ret += storage[i] + "  ";

		}
		return ret;
	}

	public int getLogicalNumber() {
		return logicalNumber;
	}

	public void setLogicalNumber(int logicalNumber) {
		this.logicalNumber = logicalNumber;
	}

	public int[] getStorage() {
		return storage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + logicalNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Frame other = (Frame) obj;
		if (logicalNumber != other.logicalNumber)
			return false;
		return true;
	}

}
