//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, Matt Dowell, mdowell@gmail.com, Homework 2, May 19,2014
package ics240.assignment2;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the main handler class. It's responsibility is to act as a
 * "controller" and be the main dependency between the other classes/builders
 * and the GUI.
 * 
 * 
 * 1) When Vitaly creates the GUI, it will be passed in as a dependency for this
 * Handler: DONE
 * 
 * 2) When Seretseab creates the GUI_OUT, this class will delegate calls
 * 
 * 3) When James / Landon create the HTML Builder classes, they will be
 * delegated from here
 * 
 * 4) When Blake creates the File reader class, it will be called from here.
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

	private FourPixCreatorGUI fourPixCreatorGUI = null;
	private FourPixCreatorHTML fourPixCreatorHTML = null;
	private FourPixCreatorInputParse inputParse = null;
	private A2OpenFile a2OpenFile = null;
	private FourPixOneWordGUIOut fourPixOneWordGUIOut = null;

	public GameHandler(Configuration inInitValues)
	{
		super();
		fourPixCreatorGUI = new FourPixCreatorGUI(this);
		fourPixCreatorHTML = new FourPixCreatorHTML(this);
		a2OpenFile = new A2OpenFile(this);
		inputParse = new FourPixCreatorInputParse(this);
		fourPixOneWordGUIOut = new FourPixOneWordGUIOut(this);

		// Set the initial values of the text fields
		fourPixCreatorGUI.setWorkseetTitle(Configuration.WORKSHEET_TITLE);
		fourPixCreatorGUI.setOutputFileName(Configuration.OUTPUT_FILE_NAME);
		fourPixCreatorGUI.setInputFileName(Configuration.INPUT_FILE_NAME);
		fourPixCreatorGUI.setNumFillerRows(Configuration.NUMBER_OF_FILLER_ROWS);
		fourPixCreatorGUI
				.setNumTilesPerRow(Configuration.NUMBER_OF_TITLES_PER_ROW);

	}

	public FourPixCreatorGUI getFourPixCreatorGUI()
	{
		return fourPixCreatorGUI;
	}

	public FourPixCreatorHTML getFourPixCreatorHTML()
	{
		return fourPixCreatorHTML;
	}

	/**
	 * This method is called when the user clicks on the LOAD button on the GUI
	 * This method delegates the call to the "File Reader" classes.
	 */
	public void processLoadEvent()
	{
		try
		{
			String file = a2OpenFile.openFile();
			fourPixCreatorGUI.setMainText(file);

		} catch (FileNotFoundException e)
		{
			fourPixCreatorGUI.showErrorMessage("File not found: "
					+ fourPixCreatorGUI.getInputFileName(), "File not found");
		}
	}

	/**
	 * This method is called when the CREATE button is clicked on in the GUI.
	 * This method delegates the call to the "HTML Builder" classes.
	 */
	public void processCreateEvent()
	{
		try
		{
			inputParse.parseMainText();
			fourPixCreatorHTML.makeHTML();
		} catch (IOException e)
		{
			// Show the user there was an error
			fourPixCreatorGUI.showErrorMessage(e.toString(), "Error!");
			e.printStackTrace();
		}
	}

	/**
	 * This method is called when the user clicks on the GUI_OUT button. This
	 * method delegates to the "GUI builder classes" for the screen-shot
	 */
	public void processGuiOutEvent()
	{
		try
		{
			fourPixOneWordGUIOut.createGUIOut();
		} catch (IOException e)
		{
			fourPixCreatorGUI.showErrorMessage(e.toString(), "Error!");
		}
	}

	/**
	 * This method will call the proper JTextBox to get the configuration
	 * values.
	 * 
	 * @return String with Worksheet title
	 */
	public String getWorksheetTitle()
	{
		return fourPixCreatorGUI.getWorksheetTitle();
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return String with input file name
	 */
	public String getInputFileName()
	{
		return fourPixCreatorGUI.getInputFileName();
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return String with output filename
	 */
	public String getOutputFileName()
	{
		return fourPixCreatorGUI.getOutputFileName();
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return int representing number of filler rows
	 */
	public int getNumFillerRows()
	{
		return fourPixCreatorGUI.getNumFillerRows();
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return int representing number of tiles per row
	 */
	public int getNumTilesPerRow()
	{
		return fourPixCreatorGUI.getNumTilesPerRow();
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return DisplayMode
	 */
	public DisplayMode getDisplayMode()
	{
		return fourPixCreatorGUI.getDisplayMode();
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return FillerCharType enum
	 */
	public FillerCharType getFillerCharType()
	{
		return fourPixCreatorGUI.getFillerCharType();
	}

	public String getMainText()
	{
		return fourPixCreatorGUI.getMainText();
	}

	public String getSolutionWord()
	{
		inputParse.parseMainText();
		return inputParse.getSolutionWord();
	}

	public String[] getWords()
	{
		inputParse.parseMainText();
		return inputParse.getWords();
	}

	public String[] getUrls()
	{
		inputParse.parseMainText();
		return inputParse.getUrls();
	}
}
