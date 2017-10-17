//*******1*********2*********3*********4*********5*********6*********7*********8
// TicTacTwice.java, Matt Dowell, mdowell@gmail.com, Homework 1, May 18,2014
package ics240.assignment1;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * This is the main program. This program handles the command line switches the
 * user sends in and either builds a gui interface for the user, or can take
 * command line arguments to change parameters.
 * 
 * @author Matt
 * 
 */
public class TicTacTwiceMain
{

	private static TicTacTwice builder = null;

	/**
	 * The user can, via command line switches, determine whether they wish to
	 * use the gui or the command line application.
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			builder = new TicTacTwice();
			if (args.length > 0 && args[0].equalsIgnoreCase("gui"))
			{
				// Run in interactive mode
				showGui();
			} else
			{
				// run in automatic mode, using default values
				builder.process();
			}
		} catch (Exception e)
		{
			System.err.println("There was an error: " + e.toString());
		}
	}

	/**
	 * Call this method to show the GUI for the application
	 */
	private static void showGui()
	{
		TicTacTwiceLayoutBuilder layoutBuilder = new TicTacTwiceLayoutBuilder();
		JFrame mainFrame = layoutBuilder.build();

		// Centers the frame
		mainFrame.setLocationRelativeTo(null);

		// Makes it visible
		mainFrame.setVisible(true);

		layoutBuilder.getButton().addActionListener(
				new BuildActionListener(builder, layoutBuilder));
	}
}

/**
 * This is the action listener wrapper for the GUI portion of the program. It
 * takes the gui builder as an argument and passes on the action to build when
 * the user clicks the GO button.
 * 
 * @author Matt
 * 
 */
class BuildActionListener implements ActionListener
{

	private TicTacTwice builder = null;
	private TicTacTwiceLayoutBuilder layoutBuilder = null;

	public BuildActionListener(TicTacTwice a_TicTacTwice,
			TicTacTwiceLayoutBuilder a_layoutBuilder)
	{
		builder = a_TicTacTwice;
		layoutBuilder = a_layoutBuilder;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			// First, get the values from the gui
			builder.setInputFilePath(layoutBuilder.getInputField().getText());
			builder.setLogoPath(layoutBuilder.getLogoField().getText());
			builder.setOutputFilePath(layoutBuilder.getOutputField().getText());
			builder.setTitle(layoutBuilder.getTitleField().getText());

			// Process the file
			builder.process();
			
			File file = new File(builder.getOutputFilePath());
			Desktop.getDesktop().browse(file.toURI());

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
