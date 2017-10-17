package program3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This is the UI Components, and a simple file chooser button. This class also
 * reads in the file and constructs the LineManager which starts the process and
 * contains all the business rules.
 * 
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class SwingUserInterface extends JPanel implements ActionListener, OutputHandler {

	JButton openButton;
	JFileChooser chooser;
	JLabel label;
	JTextArea textOutput;

	/**
	 * Constructor
	 */
	public SwingUserInterface() {
		super();

		setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Create a file chooser
		chooser = new JFileChooser();
		chooser.addActionListener(this);
		chooser.setCurrentDirectory(new File("."));

		openButton = new JButton("Choose a bathroom file to process");
		openButton.addActionListener(this);

		label = new JLabel("Select: ");
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(label);
		buttonPanel.add(openButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel);

		// Create the text area
		textOutput = new JTextArea(2, 40);
		JScrollPane scrollPane = new JScrollPane(textOutput);
		JPanel textPanel = new JPanel();
		textPanel.add(scrollPane);
		add(textPanel);
		textOutput.setText("Choose a file...");
	}

	public void out(String in) {
		/*
		 * String oldText = textOutput.getText(); textOutput.setText(oldText+
		 * "\n" + in); textOutput.repaint();
		 */
		System.out.println(in);
	}

	/**
	 * Mouse clicked
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Handle open button action.
		int returnVal = chooser.showOpenDialog(SwingUserInterface.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			Scanner s = null;
			try {
				s = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				out("There was an error opening the file. Check console output.");
			}
			textOutput.setText(null);
			out("File opened.");
			List<BathroomInput> list = new ArrayList<BathroomInput>();
			try {
				while (s.hasNextLine()) {
					StringTokenizer tokens = new StringTokenizer(s.nextLine());
					BathroomInput bin = new BathroomInput();
					bin.setType(tokens.nextToken());
					bin.setGoTime(Integer.parseInt(tokens.nextToken()));
					bin.setBathroomTime(Integer.parseInt(tokens.nextToken()));
					list.add(bin);
				}
			} catch (Exception e) {
				e.printStackTrace();
				out("There was an error reading the file. Check console output.");
			}
			s.close();
			out("File read.");
			LineManager mgr = new LineManager(new LogOutputHandler(), list);
			mgr.process();

		}
	}

	@Override
	public void event(long time, String event, String bathroom, String queue) {
		String full = "Time " + time + "\t" + event + "\t Bathroom = " + bathroom + "\t Queue = " + queue;
		System.out.println(full);
	}
	
	public void close() {
	}

}
