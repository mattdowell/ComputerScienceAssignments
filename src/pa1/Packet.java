package pa1;

/**
 * This class represents a packet of data to be:
 * 
 * - initialized - encoded - Decoded
 * 
 * Using Hamming encoding, with EVEN Parity
 * 
 * @author matt dowell
 *
 */
public class Packet {

	private boolean[] data = new boolean[8];
	private boolean[] encodedData = new boolean[12];
	private int bitErrPosition = -1;

	/**
	 * Construct the Packet with your data in String format.
	 * 
	 * @param in
	 */
	public Packet(String in) {
		if (in.length() != 8) {
			throw new RuntimeException("Data must be 8 bits");
		}

		data = convertToBoolArr(in);

		encode();
	}

	/**
	 * Constructor used when you want to decode a sent packet.
	 * 
	 * @param inEncodedData
	 */
	public Packet(boolean[] inEncodedData) {

		// Decode and find any bit errors.
		DecodedPacket dp = decode(inEncodedData);
		this.encodedData = dp.correctedDataWithHamming;
		this.bitErrPosition = dp.errBitPos;
	}

	/**
	 * Convert a string with 1/0 to a boolean array
	 * 
	 * @param in
	 * @return
	 */
	public static boolean[] convertToBoolArr(String in) {
		boolean[] theReturn = new boolean[in.length()];
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			theReturn[i] = (c == '0') ? false : true;
		}
		return theReturn;
	}

	public static String stringify(boolean[] bool) {
		String ret = "";
		for (int i = 0; i < bool.length; i++) {
			ret += (bool[i]) ? "1" : "0";
		}
		return ret;
	}

	/**
	 * @see decode(boolean[] in)
	 * 
	 * @param inEncodedData
	 * @return
	 */
	public static DecodedPacket decode(String inEncodedData) {
		// Convert to a boolean[]
		return decode(convertToBoolArr(inEncodedData));
	}

	/**
	 * This method decodes the encoded data AND does the necessary error
	 * checking.
	 * 
	 * EVEN PARITY
	 * 
	 * @return
	 */
	public static DecodedPacket decode(boolean[] inEncodedData) {

		// Get the hamming values
		boolean p1 = getHammingValueForPosition(0, inEncodedData);
		// System.out.println("H. Val for P1: " + p1);
		boolean p2 = getHammingValueForPosition(1, inEncodedData);
		// System.out.println("H. Val for P2: " + p2);
		boolean p4 = getHammingValueForPosition(3, inEncodedData);
		// System.out.println("H. Val for P4: " + p4);
		boolean p8 = getHammingValueForPosition(7, inEncodedData);
		// System.out.println("H. Val for P8: " + p8);

		// Check to make sure the hamming value is EVEN and if not, flip the bit

		// Are any of them NOT correct? These are the actual values
		boolean p1_act = inEncodedData[0];
		boolean p2_act = inEncodedData[1];
		boolean p4_act = inEncodedData[3];
		boolean p8_act = inEncodedData[7];

		int badBit = 0;
		if (p1 != p1_act) {
			// System.out.println("P1 does not match P1 Actual");
			badBit = 1;
		}
		if (p2 != p2_act) {
			// System.out.println("P2 does not match P2 Actual");
			badBit += 2;
		}
		if (p4 != p4_act) {
			//System.out.println("P4 does not match P4 Actual");
			badBit += 4;
		}
		if (p8 != p8_act) {
			//System.out.println("P8 does not match P8 Actual");
			badBit += 8;
		}

		DecodedPacket dp = new DecodedPacket();
		dp.errBitPos = badBit;

		// Error detection and correction
		if (badBit > 0) {
			// Flip it
			// System.out.println("Flipping bad bit: " + badBit);
			inEncodedData[(badBit - 1)] = !inEncodedData[(badBit - 1)];
		}
		dp.correctedDataWithHamming = inEncodedData;


		return dp;
	}

	/**
	 * Encodes the data using Hamming encoding, and returns the encoded (size
	 * 12) binary array packet.
	 * 
	 * @return
	 */
	boolean[] encode() {

		int e = 0;

		for (int i = 0; i < encodedData.length; i++) {
			if (!isParityPosition(i)) {
				encodedData[i] = data[e];
				e++;
			}
		}

		// EVEN PARITY
		encodedData[0] = getHammingValueForPosition(0, encodedData);
		encodedData[1] = getHammingValueForPosition(1, encodedData);
		encodedData[3] = getHammingValueForPosition(3, encodedData);
		encodedData[7] = getHammingValueForPosition(7, encodedData);

		return encodedData;
	}

	/**
	 * Returns the hamming value for the given position (0,1,3,7) and the given
	 * data array.
	 * 
	 * @param inPos
	 * @param inData
	 *            encoded data
	 * @return false (even) = 0, true (odd) = 1;
	 */
	private static boolean getHammingValueForPosition(int inPos, boolean[] inData) {

		int val = 0;
		int skip = inPos + 1; // How many we should skip
		boolean isSkipTurn = false;

		int count = 0;

		/*
		 * System.out.println("Full byte: " + Packet.stringify(inData));
		 * System.out.print("Skipping every " + skip);
		 * System.out.print(", P" + (inPos + 1) + " ");
		 */

		// Start at the given start position of the data array.
		// PLUS one spot, because the given position is the PARITY BIT (except
		// here all the initial parity bits are ZERO)
		for (int i = inPos; i < inData.length; i++) {

			if (!isParityPosition(i)) {
				// Are we supposed to skip?
				if (!isSkipTurn) {

					// If the value is true
					if (inData[i]) {
						// Increment our value
						val++;
					} 
				}
			}

			// count the number of skip positions...
			if ((count + 1) == skip) {
				// Flip
				isSkipTurn = !isSkipTurn;
				count = 0;
			} else {
				count++;
			}

		}

		// This is because the low bit will always be set on an odd number.
		if ((val & 1) == 0) {
			// Even
			//System.out.println(" = Even 0  (Val=" + val + ")");
			return false;
		} else {
			// Odd
			//System.out.println(" = Odd 1  (Val=" + val + ")");
			return true;
		}
	}

	/**
	 * Is the index given, in a parity position?
	 * 
	 * @param i
	 * @return
	 */
	static boolean isParityPosition(int i) {
		return (i == 0 || i == 1 || i == 3 || i == 7);
	}

	public boolean[] getData() {
		return data;
	}

	public void setData(boolean[] data) {
		this.data = data;
	}

	public boolean[] getEncodedData() {
		return encodedData;
	}

	public void setEncodedData(boolean[] encodedData) {
		this.encodedData = encodedData;
	}

	public int getBitErrPosition() {
		return bitErrPosition;
	}

	public void setBitErrPosition(int bitErrPosition) {
		this.bitErrPosition = bitErrPosition;
	}

}

class DecodedPacket {
	int errBitPos = 0;
	boolean[] correctedDataWithHamming;

	public int getErrBitPos() {
		return errBitPos;
	}

	public void setErrBitPos(int errBitPos) {
		this.errBitPos = errBitPos;
	}

	public boolean[] getDecodedData() {
		return correctedDataWithHamming;
	}

	public void setDecodedData(boolean[] decodedData) {
		this.correctedDataWithHamming = decodedData;
	}

	public String getPayload() {
		String ret = "";
		for (int i = 0; i < correctedDataWithHamming.length; i++) {
			if (!Packet.isParityPosition(i)) {
				ret += (correctedDataWithHamming[i]) ? "1" : "0";
			}
		}
		return ret;
	}

}
