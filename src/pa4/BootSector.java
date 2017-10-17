package pa4;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.win.tue.nl/~aeb/linux/fs/fat/fat-1.html
 * 
 * 0-2 Jump to bootstrap (E.g. eb 3c 90; on i86: JMP 003E NOP.
 * One finds either eb xx 90, or e9 xx xx.
 * The position of the bootstrap varies.)
 * 
 * 3-10 OEM name/version (E.g. "IBM  3.3", "IBM 20.0", "MSDOS5.0", "MSWIN4.0".
 * Various format utilities leave their own name, like "CH-FOR18".
 * Sometimes just garbage. Microsoft recommends "MSWIN4.1".)
 * 
 * BIOS Parameter Block starts here
 * 11-12 Number of bytes per sector (512)
 * Must be one of 512, 1024, 2048, 4096.
 * 
 * 13 Number of sectors per cluster (1)
 * Must be one of 1, 2, 4, 8, 16, 32, 64, 128
 * A cluster should have at most 32768 bytes. In rare cases 65536 is OK.
 * 
 * 14-15 Number of reserved sectors (1)
 * FAT12 and FAT16 use 1. FAT32 uses 32.
 * 
 * 16 Number of FAT copies (2)
 * 
 * 17-18 Number of root directory entries (224)
 * 0 for FAT32. 512 is recommended for FAT16.
 * 
 * 19-20 Total number of sectors in the filesystem (2880)
 * (in case the partition is not FAT32 and smaller than 32 MB)
 * 
 * 21 Media descriptor type (f0: 1.4 MB floppy, f8: hard disk; see below)
 * 
 * 22-23 Number of sectors per FAT (9)
 * 0 for FAT32.
 * 24-25 Number of sectors per track (12)
 * 26-27 Number of heads (2, for a double-sided diskette)
 * 
 * BIOS Parameter Block ends here
 * 28-31 Number of hidden sectors (0)
 * Hidden sectors are sectors preceding the partition.
 * 
 * 32-35 Total number of sectors in the filesystem
 * (in case the total was not given in bytes 19-20)
 * 
 * 36 Logical Drive Number (for use with INT 13, e.g. 0 or 0x80)
 * 
 * 37 Reserved (Earlier: Current Head, the track containing the Boot Record)
 * Used by Windows NT: bit 0: need disk check; bit 1: need surface scan
 * 
 * 38 Extended signature (0x29)
 * Indicates that the three following fields are present.
 * Windows NT recognizes either 0x28 or 0x29.
 * 
 * 39-42 Serial number of partition
 * 
 * 43-53 Volume label or "NO NAME    "
 * 
 * 54-61 Filesystem type (E.g. "FAT12   ", "FAT16   ", "FAT     ", or all zero.)
 * 
 * 62-509 Bootstrap
 * 
 * 510-511 Signature 55 aa
 * 
 * @author matt
 *
 */
public class BootSector {

	private byte[] contents;
	private static final List<FatEntry> FAT_ENTRIES = new ArrayList<>();

	// BOOT SECTOR
	static {
		FAT_ENTRIES.add(new FatEntry(0, 2, "BOOT_LOC"));
		FAT_ENTRIES.add(new FatEntry(3, 10, "OEM_NAME"));
		FAT_ENTRIES.add(new FatEntry(11, 12, "BYTES_PER_SECT"));
		FAT_ENTRIES.add(new FatEntry(13, 13, "SECT_PER_CLUSTER"));
		FAT_ENTRIES.add(new FatEntry(14, 15, "NUM_RESERVED_SECT"));
		FAT_ENTRIES.add(new FatEntry(16, 16, "NUM_FAT_COPIES"));
		FAT_ENTRIES.add(new FatEntry(17, 18, "NUM_ROOT_DIR_ENT"));
		FAT_ENTRIES.add(new FatEntry(19, 20, "TOTAL_NUM_SECT"));
		FAT_ENTRIES.add(new FatEntry(21, 21, "MED_DESC_TYPE"));
		FAT_ENTRIES.add(new FatEntry(22, 23, "NUM_SECT_PER_FAT"));
		FAT_ENTRIES.add(new FatEntry(24, 25, "NUM_SECT_PER_TRACK"));
		FAT_ENTRIES.add(new FatEntry(26, 27, "NUM_HEADS"));
		FAT_ENTRIES.add(new FatEntry(28, 31, "NUM_HIDDEN_SECT"));
		FAT_ENTRIES.add(new FatEntry(32, 35, "NUM_SECT_TOT"));
		FAT_ENTRIES.add(new FatEntry(36, 36, "LOG_DRIVE_NUM"));
		FAT_ENTRIES.add(new FatEntry(37, 37, "RESERVED"));
		FAT_ENTRIES.add(new FatEntry(38, 38, "EXT_SIG"));
		FAT_ENTRIES.add(new FatEntry(39, 42, "PART_SERIAL_NUM"));
		FAT_ENTRIES.add(new FatEntry(43, 53, "VOL_NAME"));
		FAT_ENTRIES.add(new FatEntry(54, 61, "FILESYSTEM_TYPE"));
		FAT_ENTRIES.add(new FatEntry(62, 509, "BOOTSTRAP"));
		FAT_ENTRIES.add(new FatEntry(510, 511, "SIGNATURE"));
	}

	public BootSector(byte[] inContents) {
		contents = inContents;
	}

	public int getClusterSize() {
		return (getBytesPerSector() * getSectorsPerCluster());
	}
	
	public int getFatSectors() {
		return (getNumSectorsPerFat() * getNumFats());
	}
	
	public int getRootDirSectorNum() {
		return (getNumFats() * getNumSectorsPerFat()) + 1;
	}
	
	public int getNumRootDirSectors() {
		return (getNumberOfRootDirs() * 32) / getBytesPerSector();
	}
	
	public int getDataStart() {
		return getReservedSectors() + getFatSectors() + getNumRootDirSectors();
	}

	public int getReservedSectors() {
		return getIntFromHex(getHexValue("NUM_RESERVED_SECT"));
	}
	public int getNumFats() {
		return getIntFromHex(getHexValue("NUM_FAT_COPIES"));
	}
	
	public int getNumSectorsPerFat() {
		return getIntFromHex(getHexValue("NUM_SECT_PER_FAT"));
	}
	
	public int getTotalNumSectors() {
		return getIntFromHex(getHexValue("TOTAL_NUM_SECT"));
	}

	public int getNumberOfRootDirs() {
		return getIntFromHex(getHexValue("NUM_ROOT_DIR_ENT"));
	}

	public int getSectorsPerCluster() {
		return getIntFromHex(getHexValue("SECT_PER_CLUSTER"));
	}

	public String getOEMName() {
		return getHexValue("OEM_NAME");
	}

	public int getBootLocation() {
		return getIntFromHex(getHexValue("BOOT_LOC"));
	}

	public int getBytesPerSector() {
		return getIntFromHex(getHexValue("BYTES_PER_SECT"));
	}

	public String getHexValue(String inLabel) {
		FatEntry fe = getEntryByLabel(inLabel);
		// Now, read the values from the FAT..
		StringBuilder sb = new StringBuilder();

		// Get the values in Hex. Start at the END and move backwards
		for (int i = fe.getEndIndex(); i >= fe.startIndex; i--) {
			sb.append(String.format("%02X ", this.contents[i]).trim());
		}
		//System.out.println("Entry: " + fe + " Hex Val: " + sb.toString());
		return sb.toString();
	}
	

	private FatEntry getEntryByLabel(String inLabel) {
		for (FatEntry fe : FAT_ENTRIES) {
			if (fe.getName().equalsIgnoreCase(inLabel)) {
				return fe;
			}
		}
		return null;
	}

	/**
	 * http://stackoverflow.com/questions/20110533/converting-hexadecimal-string
	 * -to-decimal-integer
	 * 
	 * @param inHex
	 * @return
	 */
	public static int getIntFromHex(String inHex) {
		int val = 0;
		if (inHex != null) {
			inHex = inHex.trim().toUpperCase();
			if (inHex.contains("A") || inHex.contains("B") || inHex.contains("C") || inHex.contains("D") || inHex.contains("E")
					|| inHex.contains("F")) {
				val = Integer.parseInt(inHex, 16);
			} else {
				// val = Double.parseDouble(inHex);
				val = Integer.parseInt(inHex, 16);
			}
		}
		if (val < 0) {
			val = val * -1;
		}
		return val;
	}
}

/**
 * This class represents a FAT entry. It has start/end indicies and a name.
 * @author matt
 *
 */
class FatEntry {
	int startIndex;
	int endIndex;
	String name;

	public FatEntry(int startIndex, int endIndex, String name) {
		super();
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.name = name;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FatEntry [startIndex=" + startIndex + ", endIndex=" + endIndex + ", name=" + name + "]";
	}

}
