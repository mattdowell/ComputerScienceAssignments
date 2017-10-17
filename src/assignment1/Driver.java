package assignment1;
/**
 * Main driver class does the following: 
 * 
 * It creates 10 different Latitude objects and 
 * 10 different Longitude objects. 
 * 
 * Half of the latitudes must be north of the equator and 
 * the other half must be south of the equator. 
 * 
 * Half of the longitudes must be west of the Prime Meridian and 
 * the other half must be east of the prime meridian. 
 * 
 * It creates 10 weather records for 10 locations on earth. 
 * 
 * All of the latitudes and longitudes created above must be used in
 * one of the records. 
 * 
 * Max temperature must be more than the minimum temperature at every location.
 * 
 * Approximately half of the minimum temperatures must be negative.
 * It prints the string representation of all weather records.
 *  
 * It changes the maximum temperature and minimum temperature of one 
 * of the locations and prints the new information.
 * 
 * @author Matt Dowell
 * 
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Initialize the classes
		Latitude lat = null;
		Longitude lon = null;
		WeatherInformation weather = null;
		
		// Loop through 10 times
		for (int i=0; i < 10; i++) {
			
			// During even iterations
			if (i % 2 == 0) {
				
				lat = new Latitude(15+i, 10 + i, "N");
				lon = new Longitude(150+i, 100 + i, "E");
				weather = new WeatherInformation(lat, lon, ((double)-30+i), ((double)80+i));
			} else {
				
				// ODD iterations
				lat = new Latitude(20+i, 25 + i, "S");
				lon = new Longitude(250+i, 200 + i, "W");
				weather = new WeatherInformation(lat, lon, ((double)20+i), ((double)110+i));
			}

			// Print it out
			System.out.println(weather);
		}
		
		// change the max and min of one of the records (the last one, in this case)
		System.out.println("Changing Values!");
		weather.setMaxTemperature(150);
		weather.setMinTemperature(2);
		
		// Print it out
		System.out.println(weather);
	}
}
