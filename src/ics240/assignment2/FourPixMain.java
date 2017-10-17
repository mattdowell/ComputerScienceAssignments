//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, Matt Dowell, mdowell@gmail.com, Homework 2, May 19,2014
package ics240.assignment2;

import javax.swing.JFrame;

/**
 * This is the main application runner.
 *  
 * @author Matt Dowell
 *
 */
public class FourPixMain
{

	public static void main(String args[])
	{
		GameHandler handler = new GameHandler(new Configuration());

		JFrame frame = new JFrame("FourPix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(handler.getFourPixCreatorGUI().getContentPane());
		frame.setResizable(false);
		frame.pack();

		// Centers the frame
		frame.setLocationRelativeTo(null);

		// Makes it visible
		frame.setVisible(true);

		// Do an initial load of the file
		handler.processLoadEvent();
	}
}
