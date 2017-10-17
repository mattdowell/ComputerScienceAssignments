package assignment1;
/**
 * A subclass of Position named Longitude, which represents the longitude of a
 * position. It should have the extra attribute (of type String) to store either
 * “E” or “W”.
 * 
 * @author Matt Dowell
 * 
 */
public class Longitude extends Position {

	private String eastOrWest = null;

	/**
	 * Constructor for longitude
	 * 
	 * @param degree as double
	 * @param minute as double
	 * @param eastOrWest either E or W 
	 */
	public Longitude(int degree, int minute, String eastOrWest) {
		super.setDegree(degree);
		super.setMinute(minute);
		this.eastOrWest = eastOrWest;
	}

	/**
	 * gets the direction as either E or W
	 * @return
	 */
	public String getEastOrWest() {
		return eastOrWest;
	}

	/**
	 * sets the direction as either E or W
	 * @param eastOrWest
	 */
	public void setEastOrWest(String eastOrWest) {
		this.eastOrWest = eastOrWest;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + " Longitude [eastOrWest=" + eastOrWest + "]";
	}

}
