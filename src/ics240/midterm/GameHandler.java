//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, Matt Dowell, mdowell@gmail.com, Homework 2, May 19,2014
package ics240.midterm;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the main handler class. It's responsibility is to act as a
 * "controller" and be the main dependency between the other classes/builders
 * and the GUI.
 * 
 * 
 * @author Matt Dowell
 * 
 */
public class GameHandler
{

	public enum DisplayMode
	{
		IMAGE, TEXT
	}

	public enum FillerCharType
	{
		ANY, VOWELS, CONSTANANTS
	}

	private GameReader gameReader = null;
	private FourPixOneWordGUIOut fourPixOneWordGUIOut = null;

	public GameHandler(Configuration inInitValues)
	{
		super();
		gameReader = new GameReader(this);
		fourPixOneWordGUIOut = new FourPixOneWordGUIOut(this);
		processLoadEvent();
	}

	/**
	 * This method is called when the user clicks on the LOAD button on the GUI
	 * This method delegates the call to the "File Reader" classes.
	 */
	public void processLoadEvent()
	{
		try
		{
			gameReader.readGames();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Game getNextGame()
	{
		return gameReader.getNextGame();
	}

	/**
	 * This method is called when the user clicks on the GUI_OUT button. This
	 * method delegates to the "GUI builder classes" for the screen-shot
	 * 
	 * @throws IOException
	 */
	public FourPixOneWordGUIOut getFourPixCreatorGUI() throws IOException
	{
		processLoadEvent();
		fourPixOneWordGUIOut.activateQuiz();
		return fourPixOneWordGUIOut;

	}

	/**
	 * s This method will call the proper JTextBox to get the configuration
	 * values.
	 * 
	 * @return String with Worksheet title
	 */
	public String getWorksheetTitle()
	{
		return Configuration.WORKSHEET_TITLE;
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return String with input file name
	 */
	public String getInputFileName()
	{
		return Configuration.INPUT_FILE_NAME;
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return String with output filename
	 */
	public String getOutputFileName()
	{
		return Configuration.OUTPUT_FILE_NAME;
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return int representing number of filler rows
	 */
	public int getNumFillerRows()
	{
		return Configuration.NUMBER_OF_FILLER_ROWS;
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return int representing number of tiles per row
	 */
	public int getNumTilesPerRow()
	{
		return Configuration.NUMBER_OF_TITLES_PER_ROW;
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return DisplayMode
	 */
	public DisplayMode getDisplayMode()
	{
		return DisplayMode.IMAGE;
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return FillerCharType enum
	 */
	public FillerCharType getFillerCharType()
	{
		return FillerCharType.ANY;
	}
}
