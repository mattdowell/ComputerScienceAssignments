package pa3;

/**
 * Physical memory has 4 frames
 * Frame size: 1024
 * 
 * @author matt
 *
 */
public class PhysicalMemory {

	private Frame[] memory;

	public PhysicalMemory() {
		memory = new Frame[4];
	}

	public Frame getFrame(int inFrame) {
		return memory[inFrame];
	}

	/**
	 * Read from the given physical frame slot, and the offset.
	 * 
	 * @param inFrame
	 * @param inOffset
	 * @return
	 */
	public int read(int inFrame, int inOffset) {
		Frame f = getFrame(inFrame);
		log("Read phys:" + inFrame + " log:" + f.getLogicalNumber() + " off:" + inOffset);
		return f.read(inOffset);
	}

	public void clearFrame(int victim) {
		memory[victim] = null;
	}

	/**
	 * Checks to see if our memory is full by looking for frames in our storage.
	 * 
	 * @return true if full
	 */
	public boolean isFull() {
		boolean full = true;
		for (Frame f : memory) {
			if (f == null) {
				full = false;
				break;
			}
		}
		return full;
	}

	/**
	 * 
	 * @param inFrame
	 * @return
	 */
	public int loadToFirstEmpty(Frame inFrame) {
		int i = 0;
		boolean foundSlot = false;
		for (; i < memory.length; i++) {
			// log("Checking memory slot: " + i);
			if (!foundSlot && memory[i] == null) {
				log("Slot: " + i + " is empty. Loading frame.");
				memory[i] = inFrame;
				foundSlot = true;
				break;
			}
		}
		if (!foundSlot) {
			throw new IllegalStateException("No free slot found in physical memory");
		}
		return i;
	}

	/**
	 * Write to the given physical frame number and the offset. If the address
	 * is out of bounds, log an error but do not throw an exception.
	 * 
	 * @param frameNum
	 * @param offset
	 * @param value
	 * @return
	 */
	public boolean write(int frameNum, int offset, int value) {
		try {
			getFrame(frameNum).write(offset, value);
			return true;
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("Could not write to frame: " + frameNum + " Offset: " + offset + " Value:" + value);
		}
		return false;
	}

	private void log(String in) {
		System.out.println(in);
	}
}
