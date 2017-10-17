package pa3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LRUFrameReplacer implements FrameReplacer {

	/**
	 * Should return the PHYSICAL slot number.
	 */
	@Override
	public int getVictim(Pagetable inPageTable) {

		List<PagetableEntry> entries = inPageTable.getValidEntries();
		// Find the first loaded frame.
		Collections.sort(entries, new Comparator<PagetableEntry>() {
			public int compare(PagetableEntry m1, PagetableEntry m2) {
				if (m1 != null && m2 != null) {
					return m1.getLastAccessed().compareTo(m2.getLastAccessed());
				} else {
					return 0;
				}
			}
		});
		
		// Get the top entry, now sorted
		PagetableEntry pe = entries.get(0);
		return pe.getPhysFrameNum();
	}

}
