//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, Blake Sartor, Homework 2, May 22,2014
package ics240.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This classes reponsibilities are to open and read the config file.
 * 
 * @author Blake Sartor
 *
 */
public class A2OpenFile
{

	private GameHandler handler = null;

	public A2OpenFile(GameHandler inHandler)
	{
		handler = inHandler;
	}

	/**
	 * Opens and displays text file in JTextArea called contentArea. Requires
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public String openFile() throws FileNotFoundException
	{
		Scanner lineScanner = null;

		lineScanner = new Scanner(new File(handler.getInputFileName()));

		String theReturn = new String();

		while (lineScanner.hasNextLine())
		{
			String line = lineScanner.nextLine();
			theReturn += line + "\n\r";
		}
		lineScanner.close();
		return theReturn;
	}

}
