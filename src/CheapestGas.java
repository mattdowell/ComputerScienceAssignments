import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 
 * @author Matt Dowell
 * 
 *         http://anandtechblog.blogspot.com/2011/01/dynamic-programming-qvh-
 *         quaint-valley.html http://people.cs.clemson.edu/~bcdean/dp_practice/
 *         http
 *         ://www.geeksforgeeks.org/dynamic-programming-set-36-cut-a-rope-to-
 *         maximize-product/
 *         http://cs.geneseo.edu/~baldwin/csci242/spring2013/0221dp.html
 * 
 */
@SuppressWarnings("serial")
public class CheapestGas extends JPanel implements ActionListener {

	static private final String newline = "\n";
	// private static final JFrame parent = new JFrame();
	// private static final JFileChooser fileChooser = new JFileChooser();
	JTextArea log = null;
	JButton openButton, runButton;
	JFileChooser fc;
	JTextField mpgTxt, tankSizeTxt;
	File selectedFile = null;

	public static void main(String args[]) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(CheapestGas.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = fc.getSelectedFile();
				log.append("Selected File: " + selectedFile.getName() + "." + newline);
			}

		} else if (e.getSource() == runButton) {

			int tankSize = Integer.parseInt(tankSizeTxt.getText());
			int carMpg = Integer.parseInt(mpgTxt.getText());
			int totalDistance = (tankSize * carMpg);

			// Testing
//			selectedFile = new File("c:/temp/testing/java/gasStations.txt");
//			log.setText("");
			//

			try {
				log.append("Reading File: " + selectedFile.getName() + "." + newline);
				List<GasStation> stations = readFile(tankSize);

				log.append("Read " + stations.size() + " stations." + newline);
				GasStation[] stationsAry = new GasStation[stations.size()];
				stationsAry = stations.toArray(stationsAry);

				log.append("Determining stops... " + newline);
				List<GasStation> selectedStations = bestCostGas(stationsAry, totalDistance);

				log.append("Number of stops: " + selectedStations.size() + newline);
				log.append("Stations: " + newline);
				for (GasStation gs : selectedStations) {
					log.append(gs + newline);
				}
				log.append("Total cost: " + getTotalTripCost(selectedStations, carMpg) + newline);
				writeFile(selectedStations);
				log.append("Wrote to output file." + newline);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Displays the input files chooser to the user
	 * 
	 * @return
	 */
	public CheapestGas() {

		super(new BorderLayout());

		// Create a file chooser
		fc = new JFileChooser();

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(15, 25);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		openButton = new JButton("Choose Gas Station File");
		openButton.addActionListener(this);

		// Create the save button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		runButton = new JButton("Run Process");
		runButton.addActionListener(this);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openButton);
		buttonPanel.add(runButton);

		JPanel textPanel = new JPanel(); // use FlowLayout
		JLabel mpgLabel = new JLabel("Enter car MPG");
		mpgTxt = new JTextField(7);
		JLabel tankSizeLabel = new JLabel("Enter tank size");
		tankSizeTxt = new JTextField(7);

		textPanel.add(mpgLabel);
		textPanel.add(mpgTxt);
		textPanel.add(tankSizeLabel);
		textPanel.add(tankSizeTxt);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(textPanel, BorderLayout.CENTER);
		add(logScrollPane, BorderLayout.SOUTH);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("FileChooserDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new CheapestGas());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Miles Traveled / MPG = Gallons used
	 * 
	 * @param stations
	 * @param inMpg
	 * @return
	 */
	private static double getTotalTripCost(final List<GasStation> stations, final double inMpg) {
		double theReturn = 0.0;
		double gallonsUsed = 0.0;
		int milesTraveled = 0;
		for (GasStation gs : stations) {
			if (milesTraveled > 0) {
				gallonsUsed = (milesTraveled / inMpg);
			}
			theReturn += gs.getFillupCost(gallonsUsed);
			milesTraveled += gs.getDistance();
		}
		return Math.round(theReturn);
	}

	/**
	 * http://stackoverflow.com/questions/21374496/gas-station-like-algorithm-
	 * with-minimum-cost-greedy-or-dp
	 * 
	 * http://cs.geneseo.edu/~baldwin/csci242/spring2013/0221dp.html
	 * 
	 * <pre>
	 * bestcost_serviced_at[j] 
	 *  = min(0<i<j: bestcost_serviced_at[i] + C[j] s.t. D[j]-D[i] <= 100)
	 * </pre>
	 * 
	 * @param m
	 * @param p
	 * @param min_dis
	 * @return will be the minimum cost of a trip to that gas station
	 */
	private List<GasStation> bestCostGas(final GasStation[] stations, final int milesOnFullTank) {

		List<GasStation> theReturn = new ArrayList<GasStation>();

		for (int i = 0; i < stations.length; i++) {

			if (i > 0) {

				List<GasStation> possibleStations = getPossibleStops(milesOnFullTank, i, stations);
				GasStation nextStop = determineStop(possibleStations);
				theReturn.add(nextStop);
				i += nextStop.getIndex();

			} else {
				// Add the first station
				theReturn.add(stations[i]);
			}
		}
		return theReturn;
	}

	/**
	 * Gets the nex subset of stations I can travel to without running out of
	 * gas.
	 * 
	 * @param inMilesICanGo
	 * @param inIndexToStart
	 * @param stations
	 * @return
	 */
	private List<GasStation> getPossibleStops(final int inMilesICanGo, final int inIndexToStart,
			final GasStation[] stations) {
		List<GasStation> theReturn = new ArrayList<GasStation>();
		int milesTraveled = 0;
		for (int i = inIndexToStart; i < stations.length; i++) {
			milesTraveled += stations[i].getDistance();
			if (milesTraveled <= inMilesICanGo) {
				theReturn.add(stations[i]);
			}
		}
		return theReturn;
	}

	/**
	 * This method determines the best stop from the SUBSET of gas stations
	 * 
	 * q = ∞ for i = 1 to j q = min( q, p[i] + r[j-i] ) r[j] = q
	 * 
	 * @param stations
	 * @return most efficient gas station
	 */
	private GasStation determineStop(final List<GasStation> stations) {
		GasStation theReturn = null;
		for (int i = 0; i < stations.size(); i++) {
			theReturn = getMinCost(theReturn, stations.get((stations.size() - 1) - i));
		}
		return theReturn;
	}

	/**
	 * Returns the best price of the two stations
	 * 
	 * @param inOne
	 * @param inTwo
	 * @return
	 */
	private GasStation getMinCost(final GasStation inOne, final GasStation inTwo) {
		if (inOne == null) {
			return inTwo;
		} else if (inTwo == null) {
			return inOne;
		} else {
			return (inOne.getFillupCost() < inTwo.getFillupCost()) ? inOne : inTwo;
		}
	}

	/**
	 * 
	 * @param inFilePath
	 */
	public List<GasStation> readFile(final double inTankSize) throws IOException {
		List<GasStation> rowEdges = new ArrayList<GasStation>();
		String sCurrentLine = null;
		BufferedReader br = new BufferedReader(new FileReader(selectedFile));
		int row = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			rowEdges.add(processRow(sCurrentLine, row, inTankSize));
			row++;
		}
		br.close();
		return rowEdges;
	}

	/**
	 * Writes to the output file.
	 * 
	 * @param inputFilePath
	 * @param inEdges
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void writeFile(final List<GasStation> inEdges) throws FileNotFoundException, UnsupportedEncodingException {
		String inputFilePath = selectedFile.getPath();
		String outPath = inputFilePath.replaceAll(".txt", "GAS.txt");
		PrintWriter writer = new PrintWriter(outPath, "UTF-8");
		BigDecimal totalGas = new BigDecimal("0");
		for (GasStation e : inEdges) {
			writer.println("Stop at station " + e.getDistance() + " Price per gallon: " + e.getGallonCost());
			totalGas.add(new BigDecimal(String.valueOf(e.getGallonCost())));
		}
		writer.println("Total gas cost for trip: " + totalGas.doubleValue());
		writer.close();
	}

	/**
	 * Processes a row (all columns) of the input file.
	 * 
	 * A row looks like this:
	 * 
	 * INT COST_PER_GALLON
	 * 
	 * eg: 23 2.95
	 * 
	 * @param inRow
	 * @param inRowNum
	 * @return
	 */
	private GasStation processRow(final String inRow, final int inRowNum, final double inTankSize) {

		GasStation theReturn = null;
		String[] rowArry = inRow.split("\\s+");
		theReturn = new GasStation(inRowNum, Integer.parseInt(rowArry[0]), Double.parseDouble(rowArry[1]),
				(Double.parseDouble(rowArry[1]) * inTankSize));
		return theReturn;
	}

}

/**
 * Domain representation of an Edge, complete with compareTo method.
 * 
 * @author Matt Dowell
 * 
 */
class GasStation {

	private int index;
	private int distance;
	private double gallonCost;
	private double fillupCost;

	public GasStation(int inIndex, int distance, double gallonCost, double fillupCost) {
		super();
		this.index = inIndex;
		this.distance = distance;
		this.gallonCost = gallonCost;
		this.fillupCost = fillupCost;
	}

	public int getIndex() {
		return index;
	}

	public int getDistance() {
		return distance;
	}

	public double getGallonCost() {
		return gallonCost;
	}

	public double getFillupCost() {
		return Math.round(fillupCost);
	}

	public double getFillupCost(double inNumGallons) {
		return Math.round(gallonCost * inNumGallons);
	}

	public String toString() {
		return "Index: " + index + " Dist: " + distance + " Cost per gallon: " + gallonCost;
	}
}
