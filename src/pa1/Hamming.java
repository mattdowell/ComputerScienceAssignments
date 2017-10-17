package pa1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Step1) The first part of the program should read an input.csv file and
 * calculate the parity bits of the 8 bit sent codewords from column 1. The
 * result should be the correct hamming encoded message. Assume this would be
 * the message that would simulate an encoded message transmitted to the
 * receiver. Record this message in column 3 of output.csv
 * 
 * Step 2) The second part of the program will calculate the position of the bit
 * error, if any, of the received transmission. This is NOT the message encoded
 * in step 1. It is the value given in column 2 of the input.csv. This would
 * simulate how a receiver would detect errors in a sent message. The position
 * of the bit error should NOT be determined by comparing the generated hamming
 * code from step one with the given received message in column 2.
 * 
 * Step 3) The output of the program should write a file called output.csv where
 * the first two columns are identical to to the first two columns of input.csv.
 * Output column 3 will contain your calculated hamming code based on the 8 bit
 * sent codeword from step 1. Column 4 will contain the numeric value, 0 through
 * 12, of the detected error bit. If there is no error, column 4 should display
 * a 0. Do not worry about cases where there is more than 1 bit error as none
 * will be given or tested.
 * 
 * @author matt
 *
 */
public class Hamming {

	private static final String FILENAME = "output.csv";

	public static void main(String args[]) {

		try {
			if (args.length < 1) {
				System.err.println("You must specify an input file.");
				System.exit(-1);
			}

			System.out.println("Reading file..");
			List<HammingInput> inputs = readFile(args[0]);
			List<HammingOutput> outputs = new ArrayList<>(inputs.size());

			System.out.println("Processing..");
			for (HammingInput hi : inputs) {
				HammingOutput out = new HammingOutput(hi);

				Packet p = new Packet(out.input.unEncodedDataToSend);
				DecodedPacket dp = Packet.decode(out.input.encodedDataReceived);
				out.encodedValue = p.getEncodedData();
				out.detectedErrorBit = dp.errBitPos;
				outputs.add(out);
			}
			System.out.println("Writing to output file..");
			writeToOutputFile(outputs);
			System.out.println("Complete.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The output of the program should write a file called output.csv where the
	 * first two columns are identical to to the first two columns of input.csv.
	 * Output column 3 will contain your calculated hamming code based on the 8
	 * bit sent codeword from step 1. Column 4 will contain the numeric value, 0
	 * through 12, of the detected error bit.
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private static void writeToOutputFile(List<HammingOutput> outputs)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(FILENAME, "UTF-8");
		for (HammingOutput ho : outputs) {
			writer.println(ho.toString());
		}
		writer.close();
	}

	/**
	 * Reads the input file and places the contents in to a list of domain
	 * objects.
	 * 
	 * @param inPath
	 * @return List of HammingInput populated objects.
	 */
	private static List<HammingInput> readFile(String inPath) {
		File file = new File(inPath);
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<HammingInput> list = new ArrayList<>();
		try {
			while (s.hasNextLine()) {
				StringTokenizer tokens = new StringTokenizer(s.nextLine(), ",");
				HammingInput bin = new HammingInput(tokens.nextToken(), tokens.nextToken());
				list.add(bin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		s.close();
		return list;
	}
}

/**
 * Simple domain class holding the input values.
 * 
 * @author matt
 *
 */
class HammingInput {

	String unEncodedDataToSend;
	String encodedDataReceived;

	public HammingInput(String first, String second) {
		this.unEncodedDataToSend = first;
		this.encodedDataReceived = second;
	}

	public String toString() {
		return unEncodedDataToSend + "," + encodedDataReceived;
	}
}

/**
 * the first two columns are identical to to the first two columns of input.csv.
 * Output column 3 will contain your calculated hamming code based on the 8 bit
 * sent codeword from step 1. Column 4 will contain the numeric value, 0 through
 * 12, of the detected error bit. If there is no error, column 4 should display
 * a 0. Do not worry about cases where there is more than 1 bit error as none
 * will be given or tested.
 * 
 * @author matt
 *
 */
class HammingOutput {

	HammingInput input;
	boolean[] encodedValue;
	int detectedErrorBit;

	public HammingOutput(HammingInput hi) {
		this.input = hi;
	}

	public String toString() {
		return input + "," + Packet.stringify(encodedValue) + "," + detectedErrorBit;
	}
}
