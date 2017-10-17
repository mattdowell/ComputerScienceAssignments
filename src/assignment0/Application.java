package assignment0;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * ICS 440, program 1
 * 
 * @author Matt
 *
 */
public class Application {

	public static void main(String args[]) {
		// //////////////////////////
		// Create the GUI
		JFrame frame = new JFrame("ICS440 Assignment 1");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUserInterface gui = new SwingUserInterface();
		frame.add(gui);
		frame.pack();
		frame.setVisible(true);
		// //////////////////////////
	}
}
