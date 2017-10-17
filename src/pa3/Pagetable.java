package pa3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class is primarily responsible for maintaining the pagetable.
 * The pagetable handles the mapping between physical and virtual memory.
 * 
 * @author matt
 *
 */
public class Pagetable {

	private static final int SIZE = 16;

	private static final PagetableEntry[] ENTRIES = new PagetableEntry[SIZE];

	public Pagetable() {
		super();
	}

	/**
	 * Mark the given logical and physical slots as loaded and ready to use.
	 * This methor updates the created and last-accessed timestamps, which
	 * are used by the replacer algorithms.
	 * 
	 * @param logicalFrameNum
	 * @param physicalFrameNum
	 */
	public void markFrameLoaded(int logicalFrameNum, int physicalFrameNum) {
		PagetableEntry ent = ENTRIES[logicalFrameNum];
		if (ent == null) {
			log("Frame is null, so creating");
			ent = new PagetableEntry();
		}
		ent.setValid(true);
		ent.setLastAccessed(new Date());
		ent.setCreated(new Date());
		ent.setPhysFrameNum(physicalFrameNum);
		ent.setVirtFrameNum(logicalFrameNum);
		log("Updating pagetable for logical frame: " + logicalFrameNum + " in memory: " + physicalFrameNum);
		ENTRIES[logicalFrameNum] = ent;
	}

	public boolean frameIsLoadedInPhysical(int frameNum) {
		try {
			PagetableEntry ent = ENTRIES[frameNum];
			if (ent != null && ent.isValid()) {
				ent.setLastAccessed(new Date()); // Mark as used
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Normal to try to access out of bounds memory
		}
		return false;
	}

	public List<PagetableEntry> getEntries() {
		return Arrays.asList(ENTRIES);
	}

	private void log(String string) {
		System.out.println(string);
	}

	public void markPhyscalInvalid(int victimNum) {
		for (PagetableEntry pe : ENTRIES) {
			if (pe != null) {
				if (pe.getPhysFrameNum() == victimNum) {
					pe.setValid(false);
					pe.setPhysFrameNum(-1);
					break;
				}
			}
		}
	}

	public int getPhysicalFrameNum(int logicalFrameNum) {
		PagetableEntry pe = ENTRIES[logicalFrameNum];
		if (pe == null) {
			throw new IllegalStateException("Could not find physical frame in pagetable for logical: " + logicalFrameNum);
		} else {
			return pe.getPhysFrameNum();
		}
	}

	public List<PagetableEntry> getValidEntries() {
		List<PagetableEntry> theReturn = new ArrayList<>(4);
		for (PagetableEntry pe : ENTRIES) {
			if (pe != null && pe.isValid()) {
				theReturn.add(pe);
			}
		}
		return theReturn;
	}
}