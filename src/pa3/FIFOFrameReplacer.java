package pa3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * First in First Out victim algorithm.
 * 
 * @author matt
 *
 */
public class FIFOFrameReplacer implements FrameReplacer {

	/**
	 * Should return the PHYSICAL slot number.
	 * 
	 * Sorting example taken from here:
	 * http://stackoverflow.com/questions/5207029/how-to-sort-a-list-of-objects-
	 * by-their-date-java-collections-listobject
	 */
	@Override
	public int getVictim(Pagetable inPageTable) {

		List<PagetableEntry> entries = inPageTable.getValidEntries();
		// Find the first loaded frame.
		Collections.sort(entries, new Comparator<PagetableEntry>() {
			public int compare(PagetableEntry m1, PagetableEntry m2) {
				if (m1 != null && m2 != null) {
					return m1.getCreated().compareTo(m2.getCreated());
				} else {
					return 0;
				}
			}
		});
		
		// Get the top entry
		PagetableEntry pe = entries.get(0);
		return pe.getPhysFrameNum();
	}

}
