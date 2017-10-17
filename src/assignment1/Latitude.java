package assignment1;
/**
 * A subclass of Position named Latitude, which represents the latitude of a
 * position. It should have the extra attribute (of type String) to store either
 * “N” or “S”.
 * 
 * @author Matt Dowell
 * 
 */
public class Latitude extends Position {

	private String northOrSouth = null;

	/**
	 * 
	 * @param degree as double
	 * @param minute as double
	 * @param northOrSouth as either N or S
	 */
	public Latitude(int degree, int minute, String northOrSouth) {
		super.setDegree(degree);
		super.setMinute(minute);
		this.northOrSouth = northOrSouth;
	}

	/**
	 * gets the direction as either N or S
	 * @return
	 */
	public String getNorthOrSouth() {
		return northOrSouth;
	}

	/**
	 * sets the direction as either N or S
	 * @param northOrSouth
	 */
	public void setNorthOrSouth(String northOrSouth) {
		this.northOrSouth = northOrSouth;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + " Latitude [northOrSouth=" + northOrSouth + "]";
	}

}
