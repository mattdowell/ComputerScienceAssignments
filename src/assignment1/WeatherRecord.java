package assignment1;
/**
 * Interface for any classes that want to implements a recorded weather max/min.
 * 
 * @author Matt Dowell
 * 
 */
public interface WeatherRecord {

	/**
	 * Sets the maximum temperature
	 * 
	 * @param maxTemperature
	 *            the new maximum temperature
	 */
	public void setMaxTemperature(double maxTemperature);

	/**
	 * Sets the minimum temperature
	 * 
	 * @param minTemperature
	 *            the new minimum temperature
	 */
	public void setMinTemperature(double minTemperature);

}
