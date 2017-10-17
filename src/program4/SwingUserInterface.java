package program4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
public class SwingUserInterface extends JPanel implements ActionListener {

	private JButton runPart1Btn = new JButton("Run Part 1");
	private JButton runPart2Btn = new JButton("Run Part 2");

	private JTextField queueSize = new JTextField(5);
	private JTextField enqThreads = new JTextField(5);
	private JTextField deqThreads = new JTextField(5);
	private JTextField pushThreads = new JTextField(5);
	private JTextField timeToRunTxt = new JTextField(5);

	private JLabel queueSizeLabel = new JLabel("Size of bounded queue:");
	private JLabel enqThreadsLabel = new JLabel("Enqueue Threads:");
	private JLabel deqThreadsLabel = new JLabel("Dequeue Threads:");
	private JLabel pushThreadsLabel = new JLabel("Push Threads:");
	private JLabel timeToRunLabel = new JLabel("Total Time to Run (sec.):");

	JTextArea textOutput;

	/**
	 * Constructor
	 */
	public SwingUserInterface() {
		super();
		Log.getInstance().init();
		
		super.

		setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel qSizePanel = new JPanel(); 
		qSizePanel.add(queueSizeLabel);
		qSizePanel.add(queueSize);
		add(qSizePanel);
		
		JPanel enqThreadPanel = new JPanel(); 
		enqThreadPanel.add(enqThreadsLabel);
		enqThreadPanel.add(enqThreads);
		add(enqThreadPanel);

		JPanel deqThreadPanel = new JPanel(); 
		deqThreadPanel.add(deqThreadsLabel);
		deqThreadPanel.add(deqThreads);
		add(deqThreadPanel);

		JPanel pushThreadPanel = new JPanel(); 
		pushThreadPanel.add(pushThreadsLabel);
		pushThreadPanel.add(pushThreads);
		add(pushThreadPanel);
		
		JPanel timeRunThreadPanel = new JPanel(); 
		timeRunThreadPanel.add(timeToRunLabel);
		timeRunThreadPanel.add(timeToRunTxt);
		add(timeRunThreadPanel);

		// Set up the buttons
		runPart1Btn.addActionListener(this);
		runPart2Btn.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(runPart1Btn);
		buttonPanel.add(runPart2Btn);
		// Add the buttons and the log to this panel.
		add(buttonPanel);

		// Create the text area
		textOutput = new JTextArea(2, 40);
		JScrollPane scrollPane = new JScrollPane(textOutput);
		JPanel textPanel = new JPanel();
		textPanel.add(scrollPane);
		add(textPanel);
		textOutput.setText(null);
	}

	/**
	 * Output to window and system out.
	 * 
	 * @param in
	 */
	public void out(String in) {
		String oldText = textOutput.getText();

		if (oldText == null || oldText.equals("")) {
			textOutput.setText(in);
		} else {
			textOutput.setText(oldText + "\n" + in);
		}
		textOutput.repaint();

		System.out.println(in);
	}

	/**
	 * Override this methd in case it is called we can use it to clean up the log.
	 */
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		Log.getInstance().close();
	}
	
	/**
	 * Mouse clicked
	 */
	@Override
	public void actionPerformed(ActionEvent inEvent) {

		textOutput.setText("");

		if (inEvent.getSource().equals(this.runPart1Btn)) {
			
			out("Part 1. Push Thread values ignored.");
			
			ThreadDriver driver = new ThreadDriver(Integer.parseInt(enqThreads.getText()), 
					Integer.parseInt(deqThreads.getText()), 
					0, 
					new BoundedQueue<Integer>(Integer.parseInt(this.queueSize.getText())),
					Integer.parseInt(this.timeToRunTxt.getText()));
			
			driver.start();
			
		} else if (inEvent.getSource().equals(this.runPart2Btn)) {
			
			out("Part 2.");
			
			ThreadDriver driver = new ThreadDriver(Integer.parseInt(enqThreads.getText()), 
					Integer.parseInt(deqThreads.getText()), 
					Integer.parseInt(pushThreads.getText()), 
					new BoundedDequeue<Integer>(Integer.parseInt(this.queueSize.getText())),
					Integer.parseInt(timeToRunTxt.getText()));
			
			driver.start();
		}
	}
}
