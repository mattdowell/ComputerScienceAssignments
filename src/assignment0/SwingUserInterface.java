package assignment0;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the UI Components, and the simple File copy method.
 * 
 * @author Matt
 *
 */
public class SwingUserInterface extends JPanel implements ActionListener {

	JButton openButton;
	JFileChooser chooser;
	JLabel label;

	/**
	 * Constructor
	 */
	public SwingUserInterface() {
		super();

		setOpaque(false);

		// Create a file chooser
		chooser = new JFileChooser();
		chooser.addActionListener(this);
		chooser.setCurrentDirectory(new File("."));

		openButton = new JButton("Choose a File to Copy");
		openButton.addActionListener(this);

		label = new JLabel("Select: ");
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(label);
		buttonPanel.add(openButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
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
			copyFile(file.getAbsolutePath());
		}
	}

	/**
	 * Copy source file to target location. If {@code prompt} is true then
	 * prompt user to overwrite target if it exists. The {@code preserve}
	 * parameter determines if file attributes should be copied/preserved.
	 */
	static void copyFile(String sourceUri) {
		CopyOption[] options = new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING };

		try {
			Path source = Paths.get(sourceUri);
			Path target = Paths.get(getCopiedFileName(sourceUri));
			Files.copy(source, target, options);
		} catch (IOException x) {
			System.err.format("Unable to copy: %s: %s%n", sourceUri, x);
		}
	}
	
	/**
	 * Given a file name, create a proper OUT file name per the requirements.
	 * 
	 * @param inFileName
	 * @return file name with _out before extension
	 */
	static String getCopiedFileName(String inFileName) {
		if (inFileName.contains(".")) {
			String name = inFileName.substring(0,inFileName.lastIndexOf("."));
			String extension = inFileName.substring(inFileName.lastIndexOf("."), inFileName.length());
			return name + "_out" + extension;
		} else {
			return inFileName + "_out";
		}
	}

}
