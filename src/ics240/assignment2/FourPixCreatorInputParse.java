//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.assignment2;

/*
 Course:   ICS 240 Programming With Elementary Data Structure
 Semester:    Summer 2014
 Instructor:  Silva Jasthi
 Assignment:  Homeword 2 (Input Parser)
 Compiler:    JDK 1.7 with Eclipse
 Due date:    June 3,2014
 * 
 */

/**
 * The responsibility of this class is to take in the main input box as 
 * a String and parse it into a manageable form
 */
public class FourPixCreatorInputParse
{

	GameHandler handler = null;
	String mainText = null;
	String[] line = new String[5];
	private String solutionWord = null;
	private String[] urls = new String[4];
	private String[] words = new String[4];

	FourPixCreatorInputParse(GameHandler handle)
	{
		handler = handle;
	}

	public void parseMainText()
	{
		mainText = handler.getMainText();

		line = mainText.split("\\r?\\n");
		String[] hold = line[0].split(":\\s?");
		solutionWord = hold[1];
		String[] line1 = line[1].split(",\\s?");
		String[] line2 = line[2].split(",\\s?");
		String[] line3 = line[3].split(",\\s?");
		String[] line4 = line[4].split(",\\s?");
		words[0] = line1[0];
		words[1] = line2[0];
		words[2] = line3[0];
		words[3] = line4[0];
		urls[0] = line1[1];
		urls[1] = line2[1];
		urls[2] = line3[1];
		urls[3] = line4[1];
	}

	/*
	 * This method will return the solution word as a String
	 */
	public String getSolutionWord()
	{
		return solutionWord;
	}

	/*
	 * This method will return the URLs as a String array
	 */
	public String[] getUrls()
	{
		return urls;
	}

	/*
	 * This method will return the words as a String array
	 */
	public String[] getWords()
	{
		return words;
	}
}
