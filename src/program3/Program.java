package program3;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * Main class to start the UI.
 * 
 * @author matt
 *
 */
public class Program {

	public static void main(String args[]) {
		// //////////////////////////
		// Create the GUI
		JFrame frame = new JFrame("ICS440 Program 2");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUserInterface gui = new SwingUserInterface();
		frame.add(gui);
		frame.pack();
		frame.setVisible(true);
		// //////////////////////////
	}

}
