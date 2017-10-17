package assignment1;
/**
 * This is the implementing class of WeatherRecord. Stores the exact location
 * and temp.
 * 
 * @author Matt Dowell
 * 
 */
public class WeatherInformation implements WeatherRecord {

	private Latitude latitude = null;
	private Longitude longitude = null;
	private double maxTemp;
	private double minTemp;

	/**
	 * Constructor
	 * 
	 * @param latitude
	 * @param longitude
	 * @param minTemperature
	 * @param maxTemperature
	 */
	public WeatherInformation(Latitude latitude, Longitude longitude, double minTemperature, double maxTemperature) {
		this.latitude = latitude;
		this.longitude = longitude;
		setMaxTemperature(maxTemperature);
		setMinTemperature(minTemperature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see WeatherRecord#setMaxTemperature(double)
	 */
	@Override
	public void setMaxTemperature(double maxTemperature) {
		maxTemp = maxTemperature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see WeatherRecord#setMinTemperature(double)
	 */
	@Override
	public void setMinTemperature(double minTemperature) {
		minTemp = minTemperature;
	}

	/**
	 * gets MaxTemp
	 * 
	 * @return
	 */
	public double getMaxTemperature() {
		return maxTemp;
	}

	/**
	 * Gets min temp
	 * 
	 * @return
	 */
	public double getMinTemperature() {
		return minTemp;
	}

	/**
	 * Getter for latitude
	 * 
	 * @return
	 */
	public Latitude getLatitude() {
		return latitude;
	}

	/**
	 * Setter for latitude
	 * 
	 * @param latitude
	 */
	public void setLatitude(Latitude latitude) {
		this.latitude = latitude;
	}

	/**
	 * Getter for longitude
	 * 
	 * @return
	 */
	public Longitude getLongitude() {
		return longitude;
	}

	/**
	 * Setter for longitude
	 * 
	 * @param longitude
	 */
	public void setLongitude(Longitude longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "WeatherInformation [latitude=" + latitude + ", longitude=" + longitude + ", maxTemp=" + maxTemp
				+ ", minTemp=" + minTemp + "]";
	}

}
