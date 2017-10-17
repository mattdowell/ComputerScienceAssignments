//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, Matt Dowell, mdowell@gmail.com, Homework 2, May 19,2014
package ics240.midterm;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 * This is the main JApplet class
 * 
 * @author Matt Dowell
 * 
 */
public class FourPixMain extends JApplet
{
	private static final long serialVersionUID = 3838163543520014L;

	public void init()
	{
		try
		{
			GameHandler handler = new GameHandler(new Configuration());
			setContentPane(handler.getFourPixCreatorGUI());
			resize(new Dimension(720, 900));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		try
		{
			GameHandler handler = new GameHandler(new Configuration());
			JFrame frame = new JFrame(handler.getWorksheetTitle());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(handler.getFourPixCreatorGUI());
			frame.pack();
			// Centers the frame
			frame.setLocationRelativeTo(null);
			frame.setSize(new Dimension(720, 900));

			// Makes it visible
			frame.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
}
