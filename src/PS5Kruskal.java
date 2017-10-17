import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Got help from the following pages:
 * 
 * http://en.wikipedia.org/wiki/Kruskal%27s_algorithm
 * 
 * http://stackoverflow.com/questions
 * /14658951/implementing-kruskals-algorithm-in-java
 * 
 * 
 * @author Matt Dowell
 * 
 */
public class PS5Kruskal {

	private static final JFrame parent = new JFrame();
	private static final JFileChooser fileChooser = new JFileChooser();
	private static String[] ALPHABET = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private List<String> vertices = null;
	private Map<String, Set<String>> forest = null;

	public static void main(String args[]) {
		try {
			PS5Kruskal prog = new PS5Kruskal();
			String filePath = prog.execFileChooser();
			List<Edge> edges = prog.readFile(filePath);
			prog.buildForest();
			List<Edge> mst = prog.buildMst(edges);
			prog.writeFile(filePath, mst);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	/**
	 * This is the main working method. It takes a List of Edges and returns a
	 * List<Edge> that is comprised of the MST.
	 * 
	 * @param edges
	 * @return
	 */
	public List<Edge> buildMst(List<Edge> edges) {

		// Sorts the smallest weights to the top, effectively making it a
		// priority Queue.
		Collections.sort(edges);

		List<Edge> minSpanTree = new ArrayList<Edge>();

		while (true) {
			Edge edge = edges.remove(0);

			Set<String> destVertices = forest.get(edge.destinationvertex);
			Set<String> sourceVertices = forest.get(edge.sourcevertex);

			if (!createsEdgeCycle(edge)) {
				System.out.println("No cycle created for: " + edge);
				// Add this edge to the MST
				minSpanTree.add(edge);

				// As far as I am concerned, the next two operations are the
				// magic part of Kruskal's whole algorithm. Cyclic detection
				destVertices.addAll(sourceVertices);

				for (String nodesConnected : destVertices) {
					// For each node we are now connected to, union it with all
					// the other connected vertices
					forest.put(nodesConnected, destVertices);
				}

			} else {
				System.out.println("Yes, cycle created for: " + edge);
			}

			// If we have visited all destination vertices, quit
			//System.out.println("Checking destVertices.size() == vertices.size(): " + destVertices.size() + "==" + vertices.size() );
			if (destVertices.size() == vertices.size()) {
				break;
			}
		}
		return minSpanTree;
	}

	/**
	 * For each edge, merge the sets of reachable vertices for the 2 vertices
	 * joined by the edge.
	 * 
	 * If the sets of reachable vertices is the same for the 2 vertices: return
	 * TRUE If they don't: return FALSE
	 * 
	 * @param inEdge
	 * @return true if causes cycle, false otherwise
	 */
	private boolean createsEdgeCycle(Edge inEdge) {
		Set<String> visited1 = forest.get(inEdge.destinationvertex);
		Set<String> visited2 = forest.get(inEdge.sourcevertex);
		return (visited1.equals(visited2)) ? true : false;
	}

	/**
	 * The algorithm calls for us to create a forest that contains all the
	 * verices along with any members (currently only themselves)
	 * 
	 * @return
	 */
	public void buildForest() {
		forest = new HashMap<String, Set<String>>();
		for (String vertex : vertices) {
			// Each set stores the known vertices reachable from this vertex
			// initialize with it self.
			Set<String> vs = new HashSet<String>();
			vs.add(vertex);
			forest.put(vertex, vs);
			System.out.println("Adding vertex: " + vertex);
		}
	}

	/**
	 * Processes a row (all columns) of the input file.
	 * 
	 * @param inRow
	 * @param inRowNum
	 * @return
	 */
	private List<Edge> processRow(String inRow, int inRowNum) {
		StringTokenizer tokenizer = new StringTokenizer(inRow);
		int column = 0;
		List<Edge> rowEdges = new ArrayList<Edge>();
		while (tokenizer.hasMoreTokens()) {
			String weightStr = tokenizer.nextToken();
			Edge edge = new Edge();
			edge.weight = Integer.parseInt(weightStr);
			edge.destinationvertex = ALPHABET[inRowNum];
			edge.sourcevertex = ALPHABET[column];

			if (edge.weight > -1) {
				rowEdges.add(edge);
				System.out.println("Processed edge: " + edge);
			}
			column++;
		}
		return rowEdges;

	}

	/**
	 * 
	 * @param inFilePath
	 */
	public List<Edge> readFile(String inFilePath) throws IOException {
		List<Edge> rowEdges = new ArrayList<Edge>();
		String sCurrentLine = null;
		BufferedReader br = new BufferedReader(new FileReader(inFilePath));
		vertices = new ArrayList<String>();
		int row = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			rowEdges.addAll(processRow(sCurrentLine, row));
			vertices.add(ALPHABET[row]);
			System.out.println("Reading row: " + ALPHABET[row]);
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
	public void writeFile(String inputFilePath, List<Edge> inEdges) throws FileNotFoundException, UnsupportedEncodingException {
		String outPath = inputFilePath.replaceAll(".txt", "MST.txt");
		PrintWriter writer = new PrintWriter(outPath, "UTF-8");
		for (Edge e : inEdges) {
			writer.println(e.sourcevertex + e.destinationvertex);
		}
		writer.close();
	}

	/**
	 * Displays the input files chooser to the user
	 * 
	 * @return
	 */
	public String execFileChooser() {
		String theReturn = null;
		parent.setVisible(true);
		// fileChooser.setCurrentDirectory(new
		// File(System.getProperty("user.home")));
		fileChooser.setCurrentDirectory(new File("C:/Users/Matt/Google Drive/College/ICS 340"));
		int result = fileChooser.showOpenDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			theReturn = selectedFile.getAbsolutePath();
		}
		parent.setVisible(false);
		parent.setEnabled(false);
		return theReturn;
	}
}

/**
 * Domain representation of an Edge, complete with compareTo method.
 * 
 * @author Matt Dowell
 * 
 */
class Edge implements Comparable<Edge> {
	String sourcevertex;
	String destinationvertex;
	int weight;

	@Override
	public int compareTo(Edge other) {
		if (other.weight == this.weight) {
			return 0;
		}
		return other.weight < this.weight ? 1 : -1;
	}

	public String toString() {
		return "Source: " + sourcevertex + " Dest: " + destinationvertex + " Weight: " + weight;
	}
}
