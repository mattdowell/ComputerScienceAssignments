package groupproj2.iteration1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is responsible for reading a configuration file and setting the
 * values properly on to a newly created Configuration object.
 * 
 *
 */
public class FileReaderUtil {

	/**
	 * Reads in a given file (in precise fixed-row format) and returns a
	 * RefridgeratorState object.
	 * 
	 * @param inFileName
	 * @return
	 * @throws Exception
	 */
	public static RefridgeratorState readFromFile(String inFileName) throws Exception {
		RefridgeratorState conf = new RefridgeratorState();

		FileReader fr = new FileReader(inFileName);
		BufferedReader br = new BufferedReader(fr);

		// 1) The lowest settable temperature for the fridge
		conf.setLowestFridgeTemp(next(br));

		// 2) The highest settable temperature for the fridge
		conf.setHighestFridgeTemp(next(br));

		// 3) The lowest settable temperature for the freezer
		conf.setLowestFreezerTemp(next(br));

		// 4) The highest settable temperature for the freezer
		conf.setHighestFreezerTemp(next(br));

		// The outside temperature (the temperature of the room where the
		// refrigerator is kept) may vary between 50 degrees and 110 degrees.
		// 5) The lowest possible room temperature
		conf.setLowestRoomTemp(next(br));
		if (conf.getLowestRoomTemp() < 50) {
			conf.setLowestRoomTemp(50);
		}

		// 6) The highest possible room temperature
		conf.setHighestRoomTemp(next(br));
		if (conf.getHighestRoomTemp() > 110) {
			conf.setHighestRoomTemp(110);
		}

		// 7) The time in minutes needed for the fridge to rise by 1 degree when
		// the
		// cooling system is not
		// active and the door is closed
		conf.setFridgeTimeCoolingNotActiveDoorClosed(next(br));

		// 8) The time in minutes needed for the fridge to rise by 1 degree when
		// the
		// cooling system is not
		// active and the door is open
		conf.setFridgeTimeCoolingNotActiveDoorOpen(next(br));

		// 9) The time in minutes needed for the freezer to rise by 1 degree
		// when
		// the cooling system is not
		// active and the door is closed
		conf.setFreezerTimeCoolingNotActiveDoorClosed(next(br));

		// 10) The time in minutes needed for the freezer to rise by 1 degree
		// when
		// the cooling system is not
		// active and the door is open
		conf.setFreezerTimeCoolingNotActiveDoorOpen(next(br));

		// 11) The difference between the fridge temperature and its desired
		// temperature for the cooling
		// system in the fridge to become active
		conf.setFridgeTempDeltaForActive(next(br));

		// 12) The difference between the freezer temperature and its desired
		// temperature for the cooling
		// system in the freezer to become active
		conf.setFreezerTempDeltaForActive(next(br));

		// 13) The number of minutes needed for the fridge to cool by 1 degree.
		conf.setFridgeCoolingTimeOneDegree(next(br));

		// 14) The number of minutes needed for the freezer to cool by 1 degree.
		conf.setFreezerCoolingTimeOneDegree(next(br));

		fr.close();
		return conf;
	}

	private static int next(BufferedReader br) throws NumberFormatException, IOException {
		return Integer.parseInt(br.readLine());
	}

}
