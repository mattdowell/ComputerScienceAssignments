package pa4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 1. On this file system is a deleted cat picture. Hint: It is found in the
 * root directory. You may assume all the clusters are consecutive. (even if it
 * turns out they are not)
 * You are to write a program in java which restores the the deleted cat
 * picture. This will require rebuilding the file allocation table.
 * 
 * 2. You are to write a program which creates a new file which holds the text
 * from pages 3 and 4 of this document. When completed, the file allocation
 * table you constructed should support the new file and the recovered file.
 * 
 * @author matt
 *
 */
public class Program {

	/**
	 * Read the disk image and send the first 512 bytes in to the BootSector
	 * class for parsing.
	 * 
	 * @return
	 * @throws Exception
	 */
	public BootSector readBootSector() throws Exception {
		RandomAccessFile raf = new RandomAccessFile("ICS462.img", "r");
		byte[] sectStorage = new byte[512];
		raf.read(sectStorage);
		BootSector fat = new BootSector(sectStorage);
		raf.close();
		return fat;
	}

	/**
	 * Reads a text file in to a byte[].
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] readTextFile(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		return Files.readAllBytes(path);
	}

	/**
	 * Steps to recover the cat picture.
	 * 1. Load the Fat IMG
	 * 2. Change the filename bytes
	 * 3. Rebuild the Cluster links
	 * 
	 * @returns last cluter num for pic
	 * @throws Exception
	 */
	public int recoverFatData(BootSector fat) throws Exception {
		
		System.out.println("Loading the bad image...");

		// load the image
		RandomAccessFile raf = new RandomAccessFile("ICS462.img", "rw");

		// Determine data start (81)
		int rootDirSectNum = fat.getRootDirSectorNum();
		int bytesPerSect = fat.getBytesPerSector();
		int rootDirOffset = (rootDirSectNum * bytesPerSect);
		raf.seek(rootDirOffset);
		
		System.out.println("Undeleting cat picture...");

		raf.writeByte(73);
		// raf.read(sectStorage);
		// System.out.println(HexDump.dumpHexString(sectStorage));

		// Rebuild cluster links in FAT
		// get the start cluster number
		byte[] startClusterNum = new byte[2];
		byte[] fileSize = new byte[4];

		// Read the starting cluster number
		raf.seek(rootDirOffset + 26);
		raf.read(startClusterNum);
		// System.out.println(HexDump.dumpHexString(startClusterNum));
		String hexClusterNum = convertLittleEndianHex(startClusterNum);
		int intClusterNum = BootSector.getIntFromHex(hexClusterNum);
		// System.out.println(intClusterNum);

		// Read the file size
		raf.seek(rootDirOffset + 28);
		raf.read(fileSize);
		// System.out.println(HexDump.dumpHexString(fileSize));
		String hexFileSize = convertLittleEndianHex(fileSize);
		int intFileSize = BootSector.getIntFromHex(hexFileSize);
		// System.out.println(intFileSize);

		// Determine number of clusters
		int clustersForFile = intFileSize / fat.getClusterSize() + 1;
		// System.out.println(clustersForFile);

		System.out.println("Recreating " + clustersForFile + " FAT clusters...");
		
		// Write the fat!
		int offset = (intClusterNum * 2) + 512;   // 516
		int lastCluster = 0;
		for (int i = (intClusterNum + 1); i < clustersForFile; i++) {
			writeInt(raf, offset, i); // Each one of these is a cluster ID in
										// the FAT
			offset += 2;
			lastCluster = i;
		}
		writeInt(raf, offset, 0xFFFF);
		/*
		 * System.out.println("Last cluster: " + lastCluster);
		 * System.out.println("Last FAT offset: " + offset);
		 */
		int emptySlotIndex = rootDirOffset + (32 * 11);
		int startOfSlot = emptySlotIndex;
		raf.seek(emptySlotIndex);

		// Now write the STUFF TXT and the other attributes
		String fileName = "STUFF   TXT";
		int chars[] = fileName.chars().toArray();
		for (int c : chars) {
			raf.seek(emptySlotIndex);
			raf.write(c);
			emptySlotIndex++;
		}

		/*
		 * 26-27 Starting cluster (0 for an empty file)
		 * 28-31 Filesize in bytes
		 * Last cluster: 1322
		 * Last FAT offset: 3156
		 */
		emptySlotIndex = startOfSlot + 26;
		intFileSize = Program.readTextFile("FiletextPA4.txt").length;

		// Determine number of clusters
		clustersForFile = intFileSize / fat.getClusterSize() + 1;
		
		System.out.println("Writing " + clustersForFile + " NEW clusters of data...");

		// Now write starting cluster (one cluster after LAST cluster)
		raf.seek(emptySlotIndex);

		// choose next cluster STARTING CLUSTER
		lastCluster = lastCluster + 2;
		Program.writeInt(raf, emptySlotIndex, lastCluster);

		// Now write file size   FILE SIZE
		emptySlotIndex = emptySlotIndex + 2;
		Program.writeInt(raf, emptySlotIndex, intFileSize);

		// Now write the data,
		// Find the data start
		int fileSector = fat.getDataStart() + ((lastCluster - 2)) * fat.getSectorsPerCluster();
		int fileStart = fileSector * fat.getBytesPerSector();
		byte[] fileBytes = readTextFile("FileTextPA4.txt");
		for (int i = 0; i < fileBytes.length; i++) {
			raf.seek(fileStart + i);
			raf.write(fileBytes[i]);
		}
		/// END DATA WRITE


		// Now write clusters in fat.
		offset += 2; // Move to next FAT entry
		raf.seek(offset);
		
		System.out.println("Now Writing new FAT clusters at offest: " + offset);
		
		for (int i = lastCluster +1; i <= (lastCluster + clustersForFile); i++) {
			// Each one of these is a cluster ID in the FAT
			writeInt(raf, offset, i);
			offset += 2;
		}
		writeInt(raf, offset, 0xFFFF);
		/// END CLUSTER WRITE

		// Close up shop
		raf.close();
		
		System.out.println("Complete.");
		return 0;
	}

	/**
	 * Given a (little endian) hex byte[] convert it to a String, in correct
	 * order
	 * 
	 * @param inContents
	 * @return
	 */
	public static String convertLittleEndianHex(byte[] inContents) {
		StringBuilder sb = new StringBuilder();

		// Get the values in Hex. Start at the END and move backwards
		for (int i = inContents.length - 1; i >= 0; i--) {
			sb.append(String.format("%02X ", inContents[i]).trim());
		}
		return sb.toString();
	}

	/**
	 * Write an INT value to the give offset in the given file.
	 * 
	 * @param file
	 * @param offset
	 * @param val
	 * @throws Exception
	 */
	public static void writeInt(RandomAccessFile file, int offset, int val) throws Exception {
		file.seek(offset);
		file.write(val);
		val = val >> 8;
		file.write(val);
	}

	public static void main(String args[]) {
		try {
			Program p4 = new Program();
			BootSector boot = p4.readBootSector();
			p4.recoverFatData(boot);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
