package groupproj2.iteration2;

/**
 * This class represents all the configuration values for our system.
 * 
 * Notes:
 * 
 * 1. assume that the minimum room temperature is always more than the desired
 * temperatures of the fridge and the freezer.
 * 
 *
 */
public class RefridgeratorSystem {

	public static enum DoorState {
		Open, Closed
	}

	public static enum LightState {
		On, Off
	}

	public static enum CoolingState {
		On, Off
	}

	// The current states of this Refridgerator
	private DoorState fridgeDoorState = DoorState.Closed;
	private DoorState freezerDoorState = DoorState.Closed;
	private LightState fridgeLightState = LightState.Off;
	private LightState freezerLightState = LightState.Off;
	private CoolingState fridgeCoolingState = CoolingState.Off;
	private CoolingState freezerCoolingState = CoolingState.Off;

	// These next three fields are dynamic
	private int fridgeTemp;
	private int freezerTemp;
	private int roomTemp;

	// These fields are user inputted
	private int desiredFridgeTemp;
	private int desiredFreezerTemp;
	private int desiredRoomTemp;

	// 1) The lowest setable temperature for the fridge
	private static int lowestFridgeTemp;

	// 2) The highest settable temperature for the fridge
	private static int highestFridgeTemp;

	// 3) The lowest settable temperature for the freezer
	private static int lowestFreezerTemp;

	// 4) The highest settable temperature for the freezer
	private static int highestFreezerTemp;

	// 5) The lowest possible room temperature
	private static int lowestRoomTemp;

	// 6) The highest possible room temperature
	private static int highestRoomTemp;

	// 7) The time in minutes needed for the fridge to rise by 1 degree when the
	// cooling system is not
	// active and the door is closed
	private static int fridgeTimeCoolingNotActiveDoorClosed;


	// 8) The time in minutes needed for the fridge to rise by 1 degree when the
	// cooling system is not
	// active and the door is open
	private static int fridgeTimeCoolingNotActiveDoorOpen;


	// 9) The time in minutes needed for the freezer to rise by 1 degree when
	// the cooling system is not
	// active and the door is closed
	private static int freezerTimeCoolingNotActiveDoorClosed;


	// 10) The time in minutes needed for the freezer to rise by 1 degree when
	// the cooling system is not
	// active and the door is open
	private static int freezerTimeCoolingNotActiveDoorOpen;


	// 11) The difference between the fridge temperature and its desired
	// temperature for the cooling
	// system in the fridge to become active
	private static int fridgeTempDeltaForActive;

	// 12) The difference between the freezer temperature and its desired
	// temperature for the cooling
	// system in the freezer to become active
	private static int freezerTempDeltaForActive;

	// 13) The number of minutes needed for the fridge to cool by 1 degree.
	private static int fridgeCoolingTimeOneDegree;


	// 14) The number of minutes needed for the freezer to cool by 1 degree.
	private static int freezerCoolingTimeOneDegree;


	/**
	 * this sets the Refridgerator in to its initial state
	 */
	public void setInitialState() {
		// Initially, set the freezer and fridge temperatures the same as the outside temperature.
		this.roomTemp = 70;
		this.fridgeTemp = this.roomTemp;
		this.freezerTemp = this.roomTemp;
		this.desiredFreezerTemp = 0;
		this.desiredFridgeTemp = 37;
		this.desiredRoomTemp = this.roomTemp;
	}

	public int getFridgeTemp() {
		return fridgeTemp;
	}

	public void setFridgeTemp(int fridgeTemp) {
		this.fridgeTemp = fridgeTemp;
	}

	public int getFreezerTemp() {
		return freezerTemp;
	}

	public void setFreezerTemp(int freezerTemp) {
		this.freezerTemp = freezerTemp;
	}

	public int getRoomTemp() {
		return roomTemp;
	}

	public void setRoomTemp(int roomTemp) {
		this.roomTemp = roomTemp;
	}

	public int getLowestFridgeTemp() {
		return lowestFridgeTemp;
	}

	public void setLowestFridgeTemp(int lowestFridgeTemp) {
		this.lowestFridgeTemp = lowestFridgeTemp;
	}

	public int getHighestFridgeTemp() {
		return highestFridgeTemp;
	}

	public void setHighestFridgeTemp(int highestFridgeTemp) {
		this.highestFridgeTemp = highestFridgeTemp;
	}

	public int getLowestFreezerTemp() {
		return lowestFreezerTemp;
	}

	public void setLowestFreezerTemp(int lowestFreezerTemp) {
		this.lowestFreezerTemp = lowestFreezerTemp;
	}

	public int getHighestFreezerTemp() {
		return highestFreezerTemp;
	}

	public void setHighestFreezerTemp(int highestFreezerTemp) {
		this.highestFreezerTemp = highestFreezerTemp;
	}

	public int getLowestRoomTemp() {
		return lowestRoomTemp;
	}

	public void setLowestRoomTemp(int lowestRoomTemp) {
		this.lowestRoomTemp = lowestRoomTemp;
	}

	public int getHighestRoomTemp() {
		return highestRoomTemp;
	}

	public void setHighestRoomTemp(int highestRoomTemp) {
		this.highestRoomTemp = highestRoomTemp;
	}

	public static int getFridgeTimeCoolingNotActiveDoorClosed() {
		return fridgeTimeCoolingNotActiveDoorClosed;
	}

	public void setFridgeTimeCoolingNotActiveDoorClosed(int fridgeTimeCoolingNotActiveDoorClosed) {
		this.fridgeTimeCoolingNotActiveDoorClosed = fridgeTimeCoolingNotActiveDoorClosed;
	}

	public static  int getFridgeTimeCoolingNotActiveDoorOpen() {
		return fridgeTimeCoolingNotActiveDoorOpen;
	}

	public void setFridgeTimeCoolingNotActiveDoorOpen(int fridgeTimeCoolingNotActiveDoorOpen) {
		this.fridgeTimeCoolingNotActiveDoorOpen = fridgeTimeCoolingNotActiveDoorOpen;
	}

	public static int getFreezerTimeCoolingNotActiveDoorClosed() {
		return freezerTimeCoolingNotActiveDoorClosed;
	}

	public void setFreezerTimeCoolingNotActiveDoorClosed(int freezerTimeCoolingNotActiveDoorClosed) {
		this.freezerTimeCoolingNotActiveDoorClosed = freezerTimeCoolingNotActiveDoorClosed;
	}

	public static int getFreezerTimeCoolingNotActiveDoorOpen() {
		return freezerTimeCoolingNotActiveDoorOpen;
	}

	public void setFreezerTimeCoolingNotActiveDoorOpen(int freezerTimeCoolingNotActiveDoorOpen) {
		this.freezerTimeCoolingNotActiveDoorOpen = freezerTimeCoolingNotActiveDoorOpen;
	}

	public static int getFridgeTempDeltaForActive() {
		return fridgeTempDeltaForActive;
	}

	public void setFridgeTempDeltaForActive(int fridgeTempDeltaForActive) {
		this.fridgeTempDeltaForActive = fridgeTempDeltaForActive;
	}

	public static int getFreezerTempDeltaForActive() {
		return freezerTempDeltaForActive;
	}

	public  void setFreezerTempDeltaForActive(int freezerTempDeltaForActive) {
		this.freezerTempDeltaForActive = freezerTempDeltaForActive;
	}

	public static int getFridgeCoolingTimeOneDegree() {
		return fridgeCoolingTimeOneDegree;
	}

	public void setFridgeCoolingTimeOneDegree(int fridgeCoolingTimeOneDegree) {
		this.fridgeCoolingTimeOneDegree = fridgeCoolingTimeOneDegree;
	}

	public static int getFreezerCoolingTimeOneDegree() {
		return freezerCoolingTimeOneDegree;
	}

	public void setFreezerCoolingTimeOneDegree(int freezerCoolingTimeOneDegree) {
		this.freezerCoolingTimeOneDegree = freezerCoolingTimeOneDegree;
	}

	public DoorState getFridgeDoorState() {
		return fridgeDoorState;
	}

	public void setFridgeDoorState(DoorState fridgeDoorState) {
		this.fridgeDoorState = fridgeDoorState;
	}

	public DoorState getFreezerDoorState() {
		return freezerDoorState;
	}

	public void setFreezerDoorState(DoorState freezerDoorState) {
		this.freezerDoorState = freezerDoorState;
	}

	public LightState getFridgeLightState() {
		return fridgeLightState;
	}

	public void setFridgeLightState(LightState fridgeLightState) {
		this.fridgeLightState = fridgeLightState;
	}

	public LightState getFreezerLightState() {
		return freezerLightState;
	}

	public void setFreezerLightState(LightState freezerLightState) {
		this.freezerLightState = freezerLightState;
	}

	public CoolingState getFridgeCoolingState() {
		return fridgeCoolingState;
	}

	public void setFridgeCoolingState(CoolingState fridgeCoolingState) {
		this.fridgeCoolingState = fridgeCoolingState;
	}

	public CoolingState getFreezerCoolingState() {
		return freezerCoolingState;
	}

	public void setFreezerCoolingState(CoolingState freezerCoolingState) {
		this.freezerCoolingState = freezerCoolingState;
	}

	public int getDesiredFridgeTemp() {
		return desiredFridgeTemp;
	}

	public void setDesiredFridgeTemp(int desiredFridgeTemp) {
		this.desiredFridgeTemp = desiredFridgeTemp;
	}

	public int getDesiredFreezerTemp() {
		return desiredFreezerTemp;
	}

	public void setDesiredFreezerTemp(int desiredFreezerTemp) {
		this.desiredFreezerTemp = desiredFreezerTemp;
	}

	public int getDesiredRoomTemp() {
		return desiredRoomTemp;
	}

	public void setDesiredRoomTemp(int desiredRoomTemp) {
		this.desiredRoomTemp = desiredRoomTemp;
	}

	public void openFridgeDoor() {
		setFridgeDoorState(DoorState.Open);
		setFridgeLightState(LightState.On);
	}

	public void closeFridgeDoor() {
		setFridgeDoorState(DoorState.Closed);
		setFridgeLightState(LightState.Off);
	}

	public void openFreezerDoor() {
		setFreezerDoorState(DoorState.Open);
		setFreezerLightState(LightState.On);
	}

	public void closeFreezerDoor() {
		setFreezerDoorState(DoorState.Closed);
		setFreezerLightState(LightState.Off);
	}
}


