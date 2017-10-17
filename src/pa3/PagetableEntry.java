package pa3;

import java.util.Date;

/**
 * This class represents an entry in the pagetable. The entries are a mapping
 * between the physical and logical locations in memory. They also contain
 * extra meta-information like dates used and valid bits for easy, quick
 * determination in the MMU.
 * 
 * @author matt
 *
 */
public class PagetableEntry {
	
	private int physFrameNum;
	private int virtFrameNum;
	private boolean valid = false;
	private Date created = new Date();
	private Date lastAccessed = new Date();

	public int getPhysFrameNum() {
		return physFrameNum;
	}

	public void setPhysFrameNum(int physFrameNum) {
		this.physFrameNum = physFrameNum;
	}

	public int getVirtFrameNum() {
		return virtFrameNum;
	}

	public void setVirtFrameNum(int virtFrameNum) {
		this.virtFrameNum = virtFrameNum;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
}
