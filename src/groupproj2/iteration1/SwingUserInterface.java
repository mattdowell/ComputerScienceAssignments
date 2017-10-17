package groupproj2.iteration1;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class encapsulates all the GUI Componenets. It does not know anything about the calling business
 * logic, only that it is a listener for a state change listener, and it makes the changes to the GUI
 * accordingly.
 * 
 *
 */
@SuppressWarnings("serial")
public class SwingUserInterface extends JPanel implements ActionListener, RefridgeratorStateChangeListener {

	// Constant state label text
	private static final String FRIDGE_LIGHT_OFF = "Fridge Light Off";
	private static final String FREEZER_LIGHT_OFF = "Freezer Light Off";
	private static final String FRIDGE_LIGHT_ON = "Fridge Light On";
	private static final String FREEZER_LIGHT_ON = "Freezer Light On";
	private static final String FRIDGE_TEMP_LBL = "Fridge Temp: ";
	private static final String FREEZER_TEMP_LBL = "Freezer Temp: ";
	private static final String FRIDGE_IDLE = "Fridge Idle";
	private static final String FREEZER_IDLE = "Freezer Idle";
	private static final String FRIDGE_COOLING = "Fridge Cooling";
	private static final String FREEZER_COOLING = "Freezer Cooling";

	private JButton setRoomTempBtn, setFridgeTempBtn, setFreezerTempBtn, openFridgeDoorBtn, closeFridgeDoorBtn,
			openFreezerDoorBtn, closeFreezerDoorBtn;

	private JLabel roomTempLbl, desiredFridgeTempLbl, desiredFreezerTempLbl, statusLbl, fridgeLightStatusLbl,
			freezerLightStatusLbl, fridgeTempStatusLbl, freezerTempStatusLbl, fridgeStatusLbl, freezerStatusLbl;

	private JTextField roomTempTxt, fridgeTempTxt, freezerTempTxt;
	
	private JLabel fridgeBulb, freezerBulb; 

	private RefridgeratorState refridgeratorState = null;

	@Override
	public void actionPerformed(ActionEvent e) {

		// Handle user button actions
		if (e.getSource() == setRoomTempBtn) {
			refridgeratorState.setDesiredRoomTemp(Integer.parseInt(roomTempTxt.getText()));
		} else if (e.getSource() == setFridgeTempBtn) {
			refridgeratorState.setDesiredFridgeTemp(Integer.parseInt(fridgeTempTxt.getText()));
		} else if (e.getSource() == setFreezerTempBtn) {
			refridgeratorState.setDesiredFreezerTemp(Integer.parseInt(freezerTempTxt.getText()));
		} else if (e.getSource() == openFridgeDoorBtn) {
			refridgeratorState.openFridgeDoor();
			openFridgeDoorBtn.setEnabled(false);
			closeFridgeDoorBtn.setEnabled(true);
		} else if (e.getSource() == closeFridgeDoorBtn) {
			refridgeratorState.closeFridgeDoor();
			openFridgeDoorBtn.setEnabled(true);
			closeFridgeDoorBtn.setEnabled(false);
		} else if (e.getSource() == openFreezerDoorBtn) {
			refridgeratorState.openFreezerDoor();
			openFreezerDoorBtn.setEnabled(false);
			closeFreezerDoorBtn.setEnabled(true);
		} else if (e.getSource() == closeFreezerDoorBtn) {
			refridgeratorState.closeFreezerDoor();
			openFreezerDoorBtn.setEnabled(true);
			closeFreezerDoorBtn.setEnabled(false);
		}

		RefridgeratorRulesEngine.process(refridgeratorState);
		adjustUserInputtedValues(refridgeratorState);
		configurationChanged(refridgeratorState);
	}

	/**
	 * Displays the input files chooser to the user
	 * 
	 * @param inConf
	 * 
	 * @return
	 */
	public SwingUserInterface(RefridgeratorState inConfiguration) {

		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// THe user can make changes at runtime so we have our own handle.
		refridgeratorState = inConfiguration;

		// Instantiate buttons
		setRoomTempBtn = new JButton("Set Room Temp");
		setFridgeTempBtn = new JButton("Set Fridge Temp");
		setFreezerTempBtn = new JButton("Set Freezer Temp");
		openFridgeDoorBtn = new JButton("Open Fridge Door");
		closeFridgeDoorBtn = new JButton("Close Fridge Door");
		openFreezerDoorBtn = new JButton("Open Freezer Door");
		closeFreezerDoorBtn = new JButton("Close Freezer Door");

		// Text fields for user submitted values
		roomTempTxt = new JTextField(10);
		fridgeTempTxt = new JTextField(10);
		freezerTempTxt = new JTextField(10);

		// Set the action listeners
		setRoomTempBtn.addActionListener(this);
		setFridgeTempBtn.addActionListener(this);
		setFreezerTempBtn.addActionListener(this);
		openFridgeDoorBtn.addActionListener(this);
		closeFridgeDoorBtn.addActionListener(this);
		openFreezerDoorBtn.addActionListener(this);
		closeFreezerDoorBtn.addActionListener(this);

		// Instantiate static labels
		roomTempLbl = new JLabel("Room Temp");
		desiredFridgeTempLbl = new JLabel("Desired Fridge Temp");
		desiredFreezerTempLbl = new JLabel("Desired Freezer Temp");
		statusLbl = new JLabel("Status");

		// Instantiate status labels.
		fridgeLightStatusLbl = new JLabel(FRIDGE_LIGHT_OFF);
		freezerLightStatusLbl = new JLabel(FREEZER_LIGHT_OFF);
		fridgeTempStatusLbl = new JLabel(FRIDGE_TEMP_LBL + "0");
		freezerTempStatusLbl = new JLabel(FREEZER_TEMP_LBL + "0");
		fridgeStatusLbl = new JLabel(FRIDGE_IDLE);
		freezerStatusLbl = new JLabel(FREEZER_IDLE);

		// First row
		JPanel roomTempPanel = new JPanel();
		roomTempPanel.add(roomTempLbl);
		roomTempPanel.add(roomTempTxt);
		roomTempPanel.add(setRoomTempBtn);

		// second row
		JPanel fridgeTempPanel = new JPanel();
		fridgeTempPanel.add(desiredFridgeTempLbl);
		fridgeTempPanel.add(fridgeTempTxt);
		fridgeTempPanel.add(setFridgeTempBtn);

		// third row
		JPanel freezerTempPanel = new JPanel();
		freezerTempPanel.add(desiredFreezerTempLbl);
		freezerTempPanel.add(freezerTempTxt);
		freezerTempPanel.add(setFreezerTempBtn);

		// fourth row
		JPanel fridgeDoorButtonsPanel = new JPanel();
		fridgeDoorButtonsPanel.add(openFridgeDoorBtn);
		fridgeDoorButtonsPanel.add(closeFridgeDoorBtn);

		// Fifth row
		JPanel freezerDoorButtonsPanel = new JPanel();
		freezerDoorButtonsPanel.add(openFreezerDoorBtn);
		freezerDoorButtonsPanel.add(closeFreezerDoorBtn);

		// Status label row
		JPanel statusPanel = new JPanel();
		statusPanel.add(statusLbl);

		// light status row
		JPanel lightStatusPanel = new JPanel();

		try {
			BufferedImage myPicture = ImageIO.read(ClassLoader.getSystemResource( "groupproj2/iteration1/resources/light_bulb.jpg" )); 
			Image fridgeBulbImg = myPicture.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			fridgeBulb = new JLabel(new ImageIcon(fridgeBulbImg));
			lightStatusPanel.add(fridgeBulb);
		} catch (IOException e) {
			System.err.println("The lightbulb image is not found, make sure its in the BIN dir.");
		}

		lightStatusPanel.add(fridgeLightStatusLbl);
		lightStatusPanel.add(freezerLightStatusLbl);
		
		try {
			BufferedImage myPicture = ImageIO.read(ClassLoader.getSystemResource( "groupproj2/iteration1/resources/light_bulb.jpg" )); 
			Image freezerBulbImg = myPicture.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			freezerBulb = new JLabel(new ImageIcon(freezerBulbImg));
			lightStatusPanel.add(freezerBulb);
		} catch (IOException e) {
			System.err.println("The lightbulb image is not found, make sure its in the BIN dir.");
		}		
		lightStatusPanel.setPreferredSize(new Dimension(400, 38));

		// Temp status row
		JPanel tempStatusPanel = new JPanel();
		tempStatusPanel.add(fridgeTempStatusLbl);
		tempStatusPanel.add(freezerTempStatusLbl);

		// Cooling status row
		JPanel coolingStatusPanel = new JPanel();
		coolingStatusPanel.add(fridgeStatusLbl);
		coolingStatusPanel.add(freezerStatusLbl);

		JPanel titlePanel = new JPanel();
		JLabel refridgeName = new JLabel("Acme Refrigerators");
		refridgeName.setFont(new Font(refridgeName.getName(), Font.BOLD, 28));
		titlePanel.add(refridgeName);

		// Add to the parent frame from top down
		add(titlePanel);
		add(roomTempPanel);
		add(fridgeTempPanel);
		add(freezerTempPanel);
		add(fridgeDoorButtonsPanel);
		add(freezerDoorButtonsPanel);
		add(statusPanel);
		add(lightStatusPanel);
		add(tempStatusPanel);
		add(coolingStatusPanel);

		this.closeFreezerDoorBtn.setEnabled(false);
		this.closeFridgeDoorBtn.setEnabled(false);
		this.fridgeBulb.setVisible(false);
		this.freezerBulb.setVisible(false);

		configurationChanged(refridgeratorState);
		adjustUserInputtedValues(refridgeratorState);
		
	}

	/**
	 * This method encapsulates all the change to THIS ui, when the
	 * configuration changes. This UI neither knows, nor cares about the
	 * business process or state of the Refridgerator, it only cares about what
	 * it should display to the user based upon certain values.
	 */
	@Override
	public void configurationChanged(RefridgeratorState inConf) {

		if (inConf.getFridgeLightState() == RefridgeratorState.LightState.On) {
			fridgeLightStatusLbl.setText(FRIDGE_LIGHT_ON);
			this.fridgeBulb.setVisible(true);
		} else {
			fridgeLightStatusLbl.setText(FRIDGE_LIGHT_OFF);
			this.fridgeBulb.setVisible(false);
		}

		if (inConf.getFreezerLightState() == RefridgeratorState.LightState.On) {
			freezerLightStatusLbl.setText(FREEZER_LIGHT_ON);
			this.freezerBulb.setVisible(true);
		} else {
			freezerLightStatusLbl.setText(FREEZER_LIGHT_OFF);
			this.freezerBulb.setVisible(false);
		}

		fridgeTempStatusLbl.setText(FRIDGE_TEMP_LBL + inConf.getFridgeTemp());
		freezerTempStatusLbl.setText(FREEZER_TEMP_LBL + inConf.getFreezerTemp());

		if (inConf.getFridgeCoolingState() == RefridgeratorState.CoolingState.On) {
			fridgeStatusLbl.setText(FRIDGE_COOLING);
		} else {
			fridgeStatusLbl.setText(FRIDGE_IDLE);
		}

		if (inConf.getFridgeCoolingState() == RefridgeratorState.CoolingState.On) {
			freezerStatusLbl.setText(FREEZER_COOLING);
		} else {
			freezerStatusLbl.setText(FREEZER_IDLE);
		}
	}

	/**
	 * We have to move this functionality out of the configurationChanged method
	 * because it is being called every second from another thread.
	 * 
	 * @param inConf
	 */
	private void adjustUserInputtedValues(RefridgeratorState inConf) {
		fridgeTempTxt.setText(String.valueOf(inConf.getDesiredFridgeTemp()));
		freezerTempTxt.setText(String.valueOf(inConf.getDesiredFreezerTemp()));
		roomTempTxt.setText(String.valueOf(inConf.getDesiredRoomTemp()));
	}
}
