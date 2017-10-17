package pa4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Hex utils from Michael Dorin.
 * 
 * @author matt
 *
 */
public class HexUtils {

	public static byte[] read(String inputFileName, int start, int end)

			throws FileNotFoundException, IOException {

		File theFile = new File(inputFileName);
		FileInputStream input = new FileInputStream(theFile);
		int skipped = 0;

		while (skipped < start) {
			skipped += input.skip(start - skipped);
		}

		int length = (int) (Math.min(end, theFile.length()) - start);
		byte[] bytes = new byte[length];
		int bytesRead = 0;

		while (bytesRead < bytes.length) {
			bytesRead = input.read(bytes, bytesRead, bytes.length - bytesRead);
			if (bytesRead == -1) {
				break;
			}
		}
		input.close();
		return bytes;

	}
}
