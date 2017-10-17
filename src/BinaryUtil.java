import java.math.BigInteger;

public class BinaryUtil {

	public static void main(String args[]) {
		System.out.println("Starting");
		System.out.println("----------------------------------");
		System.out.println(floatToBinaryString(.625));
		System.out.println(floatToBinaryString(.6252));
		System.out.println(floatToBinaryString(.62525));
		System.out.println(floatToBinaryString(.625258));
		System.out.println(floatToBinaryString(.6252589));
		System.out.println(floatToBinaryString(.62525894));
		System.out.println(floatToBinaryString(.625258946));
		System.out.println(floatToBinaryString(.6252589469));
		System.out.println(floatToBinaryString(.62525894693));
		System.out.println(floatToBinaryString(.62525894693156756));
		System.out.println(floatToBinaryString(.6252589469317567568568));

		System.out.println("\nStarting int conversion");
		System.out.println("----------------------------------");
		System.out.println(intToBinaryString(625));
		System.out.println(intToBinaryString(6252));
		System.out.println(intToBinaryString(62525));
		System.out.println(intToBinaryString(625258));
		System.out.println(intToBinaryString(6252589));
		System.out.println(intToBinaryString(62525894));
		System.out.println(intToBinaryString(625258946));
		System.out.println(intToBinaryString(625258946));
		System.out.println(intToBinaryString(725258946));
		System.out.println(intToBinaryString(825258946));
		System.out.println(intToBinaryString(925258946));

		System.out.println("\nStarting BigInt conversion");
		System.out.println("----------------------------------");
		System.out.println(bigIntToBinaryString(new BigInteger("625258")));
		System.out.println(bigIntToBinaryString(new BigInteger("6252589")));
		System.out.println(bigIntToBinaryString(new BigInteger("62525894")));
		System.out.println(bigIntToBinaryString(new BigInteger("625258946")));
		System.out.println(bigIntToBinaryString(new BigInteger("6252589467")));
		System.out.println(bigIntToBinaryString(new BigInteger("72525894689")));
		System.out
				.println(bigIntToBinaryString(new BigInteger("825258946512")));
		System.out
				.println(bigIntToBinaryString(new BigInteger("92525894641098")));
		System.out.println(bigIntToBinaryString(new BigInteger(
				"72525894689232523523")));
		System.out.println(bigIntToBinaryString(new BigInteger(
				"82525894651224624637537")));
		System.out.println(bigIntToBinaryString(new BigInteger(
				"9252589464109845246745634687")));
	}

	private static String floatToBinaryString(double inNum) {
		String theBinaryVal = "0.";
		while (inNum > 0) {
			double remainder = inNum * 2; // Multiply current fraction (n) by 2
			if (remainder >= 1) { // If the ones-place digit >= 1
				theBinaryVal += "1"; // Concat a "1" to the end of the result
				inNum = remainder - 1; // Remove the 1 from the current fraction
			} else { // If the ones-place digit == 0
				theBinaryVal += "0"; // Concat a "0" to the end of the result
				inNum = remainder; // Set the current fraction to the new val
			}
		}
		return theBinaryVal + " " + theBinaryVal.length() + " bits in binary";
	}

	public static String intToBinaryString(int input) {
		int copyOfInput = input;
		StringBuilder sb = new StringBuilder();

		while (copyOfInput > 0) {
			byte bit = (byte) (copyOfInput % 2);
			sb.append(bit);
			copyOfInput = copyOfInput / 2;
		}

		return sb.toString() + " " + sb.length() + " bits in binary";
	}

	public static String bigIntToBinaryString(BigInteger input) {
		System.out.print(input.toString().length() + " digits, ");
		BigInteger copyOfInput = input;
		StringBuilder sb = new StringBuilder();

		while (copyOfInput.floatValue() > 0.0) {
			BigInteger bit = copyOfInput.mod(new BigInteger("2"));
			sb.append(bit.toString());
			copyOfInput = copyOfInput.divide(new BigInteger("2"));
		}

		return sb.toString() + " " + sb.length() + " bits in binary";
	}
}