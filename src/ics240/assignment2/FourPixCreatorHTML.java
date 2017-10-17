//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, James Lindstrom, Homework 2, May 22,2014
package ics240.assignment2;

/*
 * Course:   ICS 240 Programming With Elementary Data Structure
 Semester:    Summer 2014
 Instructor:  Silva Jasthi
 Student:     James Lindstrom
 Assignment:  Homeword 2 (HTML Creator)
 Compiler:    JDK 1.7 with Eclipse
 Due date:    June 3,2014
 */

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The main responsibility of this class is to build the HTML output in response
 * to the user action from the GUI
 * 
 * @author James Lindstrom
 * 
 */
public class FourPixCreatorHTML
{
	private String[] url;
	private String[] word;
	private int noOfRows;
	private int noOfTilesPerRow;
	private String solutionWord;
	private GameHandler.FillerCharType charType = null;
	private GameHandler handler = null;
	private String charTypeString = null;

	/**
	 * 
	 * @param gm
	 */
	public FourPixCreatorHTML(GameHandler a_gm)
	{
		handler = a_gm;
	};

	/**
	 * 
	 * @param x
	 * @throws IOException
	 */
	public void makeHTML() throws IOException
	{
		solutionWord = handler.getSolutionWord();
		word = handler.getWords();
		url = handler.getUrls();
		charType = handler.getFillerCharType();
		charTypeString = charType.toString();
		noOfRows = handler.getNumFillerRows();
		noOfTilesPerRow = handler.getNumTilesPerRow();

		File file = new File(handler.getOutputFileName());
		PrintWriter output = new java.io.PrintWriter(file);

		addHead(output); // add header html to file
		makeBoard(output); // make board to file
		addEndTags(output); // add end html tags to file

		output.close();

		Desktop.getDesktop().browse(file.toURI());

	}

	// This method will add the header HTML to the 'output' file
	private void addHead(PrintWriter output)
	{
		output.write("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN''http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en' lang='en'>");
		output.write("<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><title>Four-Pix-One-Word</title><style type='text/css'>img {height:90px; width:90px;} .words {height: 50px;text-align: center;}h1, h2, h3 {text-align: center;}");
		output.write("table {border:1px solid black;border-collapse: separate;}table td, table th {font-size:20px;padding: 10px; border: solid 1px;} ");
		output.write("#logo { position:absolute; left:38%; top:2%;} #gameBoards {position:absolute; left:50%; margin-left: -100px;  } </style></head><body>");
		output.write("<h1> " + handler.getWorksheetTitle() + " </h1>");
	}

	// This method will write the HTML to output file to create the game board
	private void makeBoard(PrintWriter output) throws IOException
	{

		int solutionWordLength = solutionWord.length();
		output.write("<div id='gameBoards'><table cellspacing='0' cellpadding='2'> <tr> ");
		switch (handler.getDisplayMode())
		{
		case IMAGE: // Displays the url as image
			output.write(" <td> <img src='" + url[0] + "'> </td>");
			output.write(" <td> <img src='" + url[1] + "'> </td></tr>");
			output.write(" <tr><td> <img src='" + url[2] + "'> </td>");
			output.write(" <td> <img src='" + url[3]
					+ "'> </td></tr></table><br>");
			break;
		case TEXT: // displays text
			output.write("<td> " + word[0] + "</td>");
			output.write(" <td> " + word[1] + " </td></tr>");
			output.write(" <tr><td> " + word[2] + " </td>");
			output.write(" <td> " + word[3] + " </td></tr></table><br>");
			break;
		}
		output.write("<div id='answer'><table cellspacing='0' cellpadding='2'> <tr> ");
		for (int i = 0; i < solutionWordLength; i++)
		{
			output.write("<td> </td>");
		}
		output.write("</table> <br> <div id='wordBox'><table cellspacing='0' cellpadding='2'> ");
		output.write(TileCreator.outputToHtml(solutionWord, noOfRows,
				noOfTilesPerRow, charTypeString));
	}

	/**
	 * This method will add the closing HTML tags to output File
	 * 
	 * @param output
	 */
	private static void addEndTags(PrintWriter output)
	{
		output.write("</body></html>");
	}

	/**
	 * This method will return the array of Random letters
	 * 
	 * @return
	 */

}
