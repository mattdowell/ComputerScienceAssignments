package project.step4;

import java.awt.Color;

import javax.swing.JFrame;

public class Application {

	public static void main(String args[]) {
		// //////////////////////////
		// Create the GUI
		JFrame frame = new JFrame("ICS440 Program 2");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,70);
		SwingUserInterface gui = new SwingUserInterface();
		frame.add(gui);
		frame.setVisible(true);
		// //////////////////////////
	}
	
}
