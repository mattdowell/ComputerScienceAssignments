package assignment2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to help with the reading/writing of objects.
 * 
 * @author Matt
 *
 */
public class SerialUtil {

	public static String SERIAL_NAME = "figures";

	/**
	 * Read all the figures stored on disk, and return a List
	 * 
	 * @return List<Figure>
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Figure> readFigures() throws IOException, ClassNotFoundException {
		List<Figure> theReturn = new ArrayList<>();

		InputStream file;
		try {
			file = new FileInputStream(SERIAL_NAME);
		} catch (FileNotFoundException e) {
			// Must be first time.
			return theReturn;
		}
		InputStream buffer = new BufferedInputStream(file);
		ObjectInput input = new ObjectInputStream(buffer);

		// deserialize the List
		List<Figure> figsOnDisk = (List<Figure>) input.readObject();
		for (Figure fig : figsOnDisk) {
			theReturn.add(fig);
		}
		input.close();

		return theReturn;
	}

	/**
	 * Writes the figure list to disk
	 * @param inFigures
	 * @throws IOException
	 */
	public static void writeFigures(List<Figure> inFigures) throws IOException {
		OutputStream file = new FileOutputStream(SERIAL_NAME);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(inFigures);
		output.close();
	}

}
