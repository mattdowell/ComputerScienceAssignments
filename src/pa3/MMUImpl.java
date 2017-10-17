package pa3;

/**
 * This class manages the memory replacement. It acts as a central interface
 * implementation for any client. It implements the contract defined by the assignment.
 * 
 * It manages the:
 *  - Replacer
 *  - Filesystem
 *  - Accessing the Patetable
 *  - Accessing the Physical memory.
 *  
 *  It is responsible for orchestrating the ordered access of all those classes.
 *  
 * @author matt
 *
 */
public class MMUImpl implements MMU {

	private static final int FRAME_SIZE = 1024;
	private FrameReplacer replacer;
	private Filesystem filesystem;
	private PhysicalMemory memory;
	private Pagetable pagetable;

	private int pageFaults = 0;
	private String refString = "";

	/**
	 * Constructor. Takes a replacer algorithm and filesystem.
	 * 
	 * @param replacer
	 * @param filesystem
	 */
	public MMUImpl(FrameReplacer replacer, Filesystem filesystem) {
		super();
		this.replacer = replacer;
		this.filesystem = filesystem;
		this.memory = new PhysicalMemory();
		this.pagetable = new Pagetable();

	}

	/**
	 * Write to memory, with the given address and value.
	 * 
	 */
	@Override
	public boolean writeMemory(int address, int value) {
		int logicalFrameNum = this.getLogicalFrameNum(address);
		// Error check
		if (logicalFrameNum >= pagetable.getEntries().size()) {
			return false;
		}
		handleSwap(address);
		int offsetNum = this.getOffset(address);
		log("Writing to logical frame: " + logicalFrameNum + " off:" + offsetNum + " Val:" + value);
		// Convert logical to phyiscal
		int physFrameNum = pagetable.getPhysicalFrameNum(logicalFrameNum);
		return memory.write(physFrameNum, offsetNum, value);
	}

	/**
	 * Read the memory value from the given address.
	 */
	@Override
	public int readMemory(int address) {
		handleSwap(address);
		int logicalFramNum = this.getLogicalFrameNum(address);
		int offsetNum = this.getOffset(address);
		int physicalNum = pagetable.getPhysicalFrameNum(logicalFramNum);
		return memory.read(physicalNum, offsetNum);
	}

	/**
	 * Handles the swam in and out of virtual address if needed.
	 * 
	 * @param address
	 */
	private void handleSwap(int address) {
		
		// Get the logical frame num and just return if it is out of bounds.
		int logFrameNum = this.getLogicalFrameNum(address);
		if (logFrameNum >= pagetable.getEntries().size()) {
			return;
		}
		refString +="["+logFrameNum+"]";
		
		// Check to see if the frame has been loaded.
		if (!pagetable.frameIsLoadedInPhysical(logFrameNum)) {
			log("Frame: " + logFrameNum + " is not loaded...");
			pageFaults++;
			// If there is free space, use it
			if (memory.isFull()) {
				log("Memory is full");
				// IF there is not, consult the replacer.
				int victimNum = replacer.getVictim(pagetable);
				log("Physical victim: " + victimNum);
				Frame victim = memory.getFrame(victimNum);
				filesystem.writeToDisk(victim);
				memory.clearFrame(victimNum);
				pagetable.markPhyscalInvalid(victimNum);
			}
			// Load the frame.
			log("Loading virtual frame " + logFrameNum + " from disk..");
			Frame activeFrame = filesystem.readFromDisk(logFrameNum);
			if (activeFrame == null) {
				// Must be new
				activeFrame = new Frame(logFrameNum);
			} else {
				if (logFrameNum != activeFrame.getLogicalNumber()) {
					throw new IllegalStateException("There is a problem with the serialized frame. The logical addresses do not match!");
				}
			}
			
			// Update the table to mark the new frame loaded
			pagetable.markFrameLoaded(activeFrame.getLogicalNumber(), memory.loadToFirstEmpty(activeFrame));
		}
	}

	@Override
	public void startSimulation() {
		refString = "";
		this.pageFaults = 0;
	}

	@Override
	public void stopSimulation() {
		System.out.println("Simulation Stopped. Reference String: \n" + refString);
		System.out.println("Page Faults: " + pageFaults);
	}

	@Override
	public String getReferenceString() {
		return refString;
	}

	@Override
	public int getTotalPageFaults() {
		return pageFaults;
	}

	private int getLogicalFrameNum(int address) {
		return address / FRAME_SIZE;
	}

	private int getOffset(int address) {
		return address % FRAME_SIZE;
	}

	private void log(String in) {
		System.out.println(in);
	}

}
