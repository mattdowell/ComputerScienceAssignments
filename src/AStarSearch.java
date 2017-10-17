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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * AStar Search, Program 14, ICS340
 * 
 * @author Matt Dowell
 * 
 */
@SuppressWarnings("serial")
public class AStarSearch extends JPanel implements ActionListener {

	static private final String newline = "\n";
	JTextArea log = null;
	JButton findHeuristicFileButton, findGraphFileButton, runButton;
	JFileChooser fc;
	File selectedHeuristicFile = null;
	File selectedGraphFile = null;

	Map<String, Integer> heuristics = null;
	String outputContent = "";

	public static void main(String args[]) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();

			}
		});
	}

	/**
	 * Displays the input files chooser to the user
	 * 
	 * @return
	 */
	public AStarSearch() {

		super(new BorderLayout());

		// Create a file chooser
		fc = new JFileChooser();

		// Set to current path
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		fc.setCurrentDirectory(new File(s));

		// Create the log first, because the action listeners
		// need to refer to it.
		log = new JTextArea(15, 25);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		// Create the open button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		findHeuristicFileButton = new JButton("Choose Heuristic");
		findHeuristicFileButton.addActionListener(this);

		findGraphFileButton = new JButton("Choose Graph");
		findGraphFileButton.addActionListener(this);

		// Create the save button. We use the image from the JLF
		// Graphics Repository (but we extracted it from the jar).
		runButton = new JButton("Run!");
		runButton.addActionListener(this);

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(findHeuristicFileButton);
		buttonPanel.add(findGraphFileButton);
		buttonPanel.add(runButton);

		JPanel textPanel = new JPanel(); // use FlowLayout

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(textPanel, BorderLayout.CENTER);
		add(logScrollPane, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == findHeuristicFileButton) {
			int returnVal = fc.showOpenDialog(AStarSearch.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedHeuristicFile = fc.getSelectedFile();
				log("Selected Heuristic: " + selectedHeuristicFile.getName());
			}

		} else if (e.getSource() == findGraphFileButton) {
			int returnVal = fc.showOpenDialog(AStarSearch.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedGraphFile = fc.getSelectedFile();
				log("Selected Graph: " + selectedGraphFile.getName());
			}

		} else if (e.getSource() == runButton) {

			try {
				log("Reading Heuristics: " + selectedHeuristicFile.getName());
				readHeuristicFile();

				log("Reading Graph: " + selectedGraphFile.getName());
				final List<GraphEdge> graph = readGraphFile();

				// log(this.heuristics);
				// log(graph);

				GraphEdge cheapest = null;
				final List<GraphEdge> visitedNodes = new ArrayList<GraphEdge>();
				List<GraphEdge> frontier = findStartEdges(graph);
				boolean isStart = true;

				do {
					output(cheapest, frontier);
					cheapest = findCheapestPath(frontier, visitedNodes, isStart);
					isStart = false;

					System.out.println("Next cheapest edge: " + cheapest);

					frontier = findFrontier(graph, cheapest);

				} while (!cheapest.getSourceNode().equals("G"));

				this.writeFile(this.outputContent);
				log("Wrote to output file.");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Builds a collection of alll possible starting edges
	 * 
	 * @param graph
	 * @return
	 */
	private List<GraphEdge> findStartEdges(List<GraphEdge> graph) {
		List<GraphEdge> frontier = new ArrayList<GraphEdge>();
		for (GraphEdge ge : graph) {
			if (ge.getSourceNode().equalsIgnoreCase("S")) {
				frontier.add(ge);
			}
		}
		return frontier;
	}

	private void log(Object inVal) {
		log.append(String.valueOf(inVal) + newline);
	}

	private void output(GraphEdge chosen, List<GraphEdge> frontier) {

		// If its null, its the start node
		if (chosen == null) {
			chosen = new GraphEdge("S", "S", 0, 0);
		}

		if (!(chosen.getTargetNode().equals("G") || chosen.getSourceNode().equals("G"))) {
			this.outputContent += chosen.getTargetNode() + "\t";

			for (GraphEdge ge : frontier) {
				this.outputContent += ge.toOutputString() + "\t";
			}
		} else {
			this.outputContent += "G";
		}
		this.outputContent += AStarSearch.newline;
	}

	/**
	 * Finds the cheapest path based upon the P cost
	 * 
	 * p =
	 * 
	 * @param inEdges
	 * @param visitedNodes
	 * @return
	 */
	private GraphEdge findCheapestPath(final List<GraphEdge> inEdges, final List<GraphEdge> visitedNodes,
			boolean isStartNode) {
		Collections.sort(inEdges);
		GraphEdge ge = null;
		for (GraphEdge testEdge : inEdges) {
			ge = testEdge;

			// Look for cheapest edge that starts with S
			if (isStartNode) {
				if (ge.getSourceNode().equalsIgnoreCase("S")) {
					visitedNodes.add(ge);
					break;
				}
			}
			if (visitedNodes.contains(testEdge)) {
				System.out.println("Already visited: " + testEdge.toNodeString());
				continue;
			} else {
				log("Next chosen edge: " + testEdge);
				visitedNodes.add(ge);
				break;
			}
		}
		return ge;

	}

	/**
	 * The definition of the frontier is:
	 * 
	 * From the current edge's targetNode, all other edges source nodes.
	 * 
	 * @param inList
	 * @return
	 */
	private List<GraphEdge> findFrontier(final List<GraphEdge> allEdges, final GraphEdge currentNode) {
		List<GraphEdge> frontier = new ArrayList<GraphEdge>();
		for (GraphEdge ge : allEdges) {
			if (currentNode.getTargetNode().equalsIgnoreCase(ge.getSourceNode())) {
				System.out.println("Frontier: " + ge.toNodeString() + " P:" + ge.getPValue());
				frontier.add(ge);
			}
		}
		return frontier;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("AStarSearch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new AStarSearch());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Reads the heuristic file and stores it in a map
	 * 
	 * @throws IOException
	 */
	private void readHeuristicFile() throws IOException {
		heuristics = new HashMap<String, Integer>();
		String sCurrentLine = null;
		BufferedReader br = new BufferedReader(new FileReader(selectedHeuristicFile));
		while ((sCurrentLine = br.readLine()) != null) {

			String[] rowString = sCurrentLine.split("\\s+");
			heuristics.put(rowString[0], new Integer(rowString[1]));
		}
		br.close();
	}

	/**
	 * If I look at the first column and go to the row that says A, then I look
	 * in the first row and go to the column that says B, the value at the
	 * intersection is 2, this means that A-->B is 2.
	 * 
	 * @param inFilePath
	 */
	public List<GraphEdge> readGraphFile() throws IOException {
		List<GraphEdge> rowEdges = new ArrayList<GraphEdge>();
		String sCurrentLine = null;
		BufferedReader br = new BufferedReader(new FileReader(selectedGraphFile));
		int row = 0;
		String[] header = null;
		while ((sCurrentLine = br.readLine()) != null) {

			if (row == 0) {
				// Header row
				header = processHeader(sCurrentLine);
			} else {
				// We should also send in the column row, etc..
				rowEdges.addAll(processRow(sCurrentLine, row, heuristics, header));
			}
			row++;
		}
		br.close();
		return rowEdges;
	}

	/**
	 * Writes to the output file. Output File The output file will be a
	 * tab-separated file. The first column will be the node you visit. The next
	 * columns will alternate between a node on the frontier when you are
	 * visiting that node and the estimated cost to go to the goal node if you
	 * go through that node. In other words, it’s going to be in the format of
	 * the solution to Problem Set 12.
	 * 
	 * A B 10.0
	 * 
	 * @param inputFilePath
	 * @param inEdges
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void writeFile(final String inOutputToWrite) throws FileNotFoundException, UnsupportedEncodingException {
		String inputFilePath = selectedGraphFile.getPath();
		String outPath = inputFilePath.replaceAll(".txt", "_OUT.txt");
		PrintWriter writer = new PrintWriter(outPath, "UTF-8");
		writer.println(inOutputToWrite);
		writer.close();
	}

	/**
	 * Processes a row (all columns) of the input file.
	 * 
	 * A row looks like this:
	 * 
	 * B 1 0 5 3
	 * 
	 * The source node is the first element in the row, and the target node is
	 * the column header
	 * 
	 * @param inRow
	 * @param inRowNum
	 * @return
	 */
	private List<GraphEdge> processRow(final String inRow, final int inRowNum, Map<String, Integer> heuristicArry,
			String[] header) {

		List<GraphEdge> theReturn = new ArrayList<GraphEdge>();
		String[] rowArry = inRow.split("\\s+");

		String sourceNode = null;
		for (int i = 0; i < rowArry.length; i++) {
			// First col is node val.
			if (i == 0) {
				sourceNode = rowArry[i];
			} else {
				int val = Integer.parseInt(rowArry[i]);

				if (val > 0) {
					GraphEdge g = new GraphEdge(sourceNode, header[i], val, heuristicArry.get(sourceNode));
					theReturn.add(g);
				}
			}
		}
		return theReturn;
	}

	private String[] processHeader(final String inRow) {
		return inRow.split("\\s+");
	}

}

/**
 * Domain representation of an Edge
 * 
 * @author Matt Dowell
 * 
 */
class GraphEdge implements Comparable<GraphEdge> {

	private String sourceNode;
	private String targetNode;
	private int distance;
	private double cost;

	public GraphEdge(String source, String target, int distance, double inHeuristic) {
		super();
		this.sourceNode = source;
		this.targetNode = target;
		this.distance = distance;
		this.cost = inHeuristic;
	}

	public String getSourceNode() {
		return sourceNode;
	}

	public String getTargetNode() {
		return targetNode;
	}

	public int getDistance() {
		return distance;
	}

	public double getCost() {
		return cost;
	}

	public double getPValue() {
		return distance + cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + distance;
		result = prime * result + ((sourceNode == null) ? 0 : sourceNode.hashCode());
		result = prime * result + ((targetNode == null) ? 0 : targetNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphEdge other = (GraphEdge) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (distance != other.distance)
			return false;
		if (sourceNode == null) {
			if (other.sourceNode != null)
				return false;
		} else if (!sourceNode.equals(other.sourceNode))
			return false;
		if (targetNode == null) {
			if (other.targetNode != null)
				return false;
		} else if (!targetNode.equals(other.targetNode))
			return false;
		return true;
	}

	public String toString() {
		return "Edge from: " + sourceNode + "-->" + targetNode + " Dist: " + distance + " Cost: " + cost + " PVal: "
				+ getPValue();
	}

	public String toNodeString() {
		return this.sourceNode + "->" + this.targetNode;
	}

	public String toOutputString() {
		return this.targetNode + "[" + this.getPValue() + "]";
	}

	/**
	 * First does a compare on P value then if they are equal, alphabetically on
	 * target node.
	 */
	@Override
	public int compareTo(GraphEdge other) {

		if (other.getPValue() == this.getPValue()) {
			return this.targetNode.compareTo(other.getTargetNode());
		} else {
			return new Double(this.getPValue()).compareTo(new Double(other.getPValue()));
		}
	}
}
