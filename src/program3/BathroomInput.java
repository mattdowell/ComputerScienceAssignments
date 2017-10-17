package program3;

/**
 * A class that represents a single line from the input file.
 * 
 * @author matt
 *
 */
public class BathroomInput {

	private String type;
	private int goTime;
	private int bathroomTime;
	
	public String getType() {
		return type;
	}
	public void setType(String string) {
		this.type = string;
	}
	public int getGoTime() {
		return goTime;
	}
	public void setGoTime(int goTime) {
		this.goTime = goTime;
	}
	public int getBathroomTime() {
		return bathroomTime;
	}
	public void setBathroomTime(int bathroomTime) {
		this.bathroomTime = bathroomTime;
	}
	
	
}
