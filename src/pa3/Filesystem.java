package pa3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Shamelessly borrowed from this example:
 * http://www.mkyong.com/java/how-to-read-an-object-from-file-in-java/
 * 
 * This class is responsible for writing and reading objects (in this
 * case, Frames) from the operating system.
 * 
 * @author matt
 *
 */
public class Filesystem {

	/**
	 * Write the given frame to a serialized, long-term storage.
	 * 
	 * @param Frame to write
	 */
	public void writeToDisk(Frame victim) {
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream(buildName(victim.getLogicalNumber()));
			oos = new ObjectOutputStream(fout);
			oos.writeObject(victim);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			try {
				if (fout != null)
					fout.close();

				if (oos != null)
					oos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Read the Frame from long-term storage for the given
	 * logical frame address.
	 * 
	 * @param logicalAddress
	 * @return Frame
	 */
	public Frame readFromDisk(int logicalAddress) {
		Frame frame = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			fin = new FileInputStream(buildName(logicalAddress));
			ois = new ObjectInputStream(fin);
			frame = (Frame) ois.readObject();
			ois.close();
			fin.close();
		} catch (FileNotFoundException fne) {
			// Can be normal
			System.err.println("File: " + buildName(logicalAddress) + " not found...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			try {
				if (ois != null)
					ois.close();

				if (fin != null)
					fin.close();
			} catch (Exception e) {
			}
		}
		return frame;
	}

	private String buildName(int id) {
		return "frame_" + id + ".ser";
	}

}
