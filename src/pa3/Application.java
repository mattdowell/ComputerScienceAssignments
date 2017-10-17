package pa3;

/**
 * Matt Dowell PA3
 * 
 * This is the main application class.
 * 
 * @author matt
 *
 */
public class Application {

	public static void main(String args[]) {
		
		Filesystem filesys = new Filesystem();
		//FrameReplacer replacer = new FIFOFrameReplacer();
		FrameReplacer replacer = new LRUFrameReplacer();
		
		MMUImpl mmu = new MMUImpl(replacer, filesys);
		mmu.startSimulation();
		
		for (int i = 0; i < 16384; i++) {
			mmu.writeMemory(i, i);
		}

		for (int i = 0; i < 16384; i++) {
			int mem = mmu.readMemory(i);
			if (mem != i) {
				System.err.println(i + " does not equal mem: " + mem);
			}
		}

		if (mmu.writeMemory(16385, 1))
			System.err.println("Shoot, that should not have worked");
		else
			System.out.println("Good, that write error was caught \n");
			

		mmu.stopSimulation();
	}
}
