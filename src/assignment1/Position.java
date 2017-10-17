package assignment1;
/**
 * An abstract class named Position, which stores the degree and minute of
 * either a longitude or a latitude.
 * 
 * @author Matt Dowell
 * 
 */
public abstract class Position {

	private double degree;
	private double minute;

	/**
	 * @return the degrees in double
	 */
	public double getDegree() {
		return degree;
	}

	/**
	 * Sets the degrees in double format
	 * @param degree
	 */
	public void setDegree(double degree) {
		this.degree = degree;
	}

	/**
	 * 
	 * @return minute as double
	 */
	public double getMinute() {
		return minute;
	}

	/**
	 * Sets minute as double
	 * 
	 * @param minute
	 */
	public void setMinute(double minute) {
		this.minute = minute;
	}

	@Override
	public String toString() {
		return "Position [degree=" + degree + ", minute=" + minute + "]";
	}

}
