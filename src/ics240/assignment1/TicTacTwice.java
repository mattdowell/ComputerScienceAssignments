//*******1*********2*********3*********4*********5*********6*********7*********8
// TicTacTwice.java, Matt Dowell, mdowell@gmail.com, Homework 1, May 18,2014
package ics240.assignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * [2] INPUT: There is a text file at “C:\ics240\tic_tac_twice.txt” containing
 * 32 lines in the following format. It will exactly contain 32 lines (not more
 * or not less).
 * 
 * Each line contains two fields separated by comma.
 * 
 * The <some text N> can be a simple text string (cat, dog) or a reference to an
 * online image (eg: http://www.wikipedia.com/cat.jpg,
 * https://www.images.com/dog.jpb )
 * 
 * A, <some text 1> B, <some text 2> C, <some text 3> P, <some text 16>
 * 
 * A,<some text 17> B, <some text 18> C, <some text 19> P, <some text 32>
 * 
 * [3] OUTPUT: will be a HTML file called “C:\ics240\tic_tac_twice.html”
 * containing the two game boards one below another.
 * 
 * The HTML page will also contain a “Logo Image” and “Title” and as well.
 * 
 * While creating the game boards,
 * 
 * first 16 lines are meant for the 1st board while second 16 lines are meant
 * for 2nd board.
 * 
 * 
 * @author Matt Dowell
 * 
 */
public class TicTacTwice
{

	// private String inputFilePath = "C:/ics240/tic_tac_twice.txt";
	private String inputFilePath = "C:/temp/tic_tac_twice.txt";

	// private String outputFilePath = "C:/ics240/tic_tac_twice.html";
	private String outputFilePath = "C:/temp/tic_tac_twice.html";

	private String logoPath = "http://www.logicd.com/wp-content/uploads/2013/12/Final-Logo-2.jpg";
	private String title = "TicTac Twice!";

	public TicTacTwice()
	{

	}

	/**
	 * Constructor that takes (and can override) the default values
	 * 
	 * @param inputFilePath
	 * @param outputFilePath
	 * @param logoPath
	 * @param title
	 */
	public TicTacTwice(String inputFilePath, String outputFilePath,
			String logoPath, String title)
	{
		super();
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.logoPath = logoPath;
		this.title = title;
	}

	/**
	 * The main working method for this class. This method generates the file
	 * contents and then writes them to the file, with the path specified by
	 * outputFilePath.
	 * 
	 * @throws IOException
	 */
	public void process() throws IOException
	{
		String htmlContents = "<html><head><title>" + title
				+ "</title></head><body><div><img src=\"" + logoPath
				+ "\" /><h1>" + title + "</h1></div>";
		List<String> firstTable = readLinesToList(0);
		List<String> secondTable = readLinesToList(16);
		htmlContents += processTable(firstTable);
		
		htmlContents += "<br /><br />";
		
		// Shuffle the contents of the second table
		Collections.shuffle(secondTable);
		
		htmlContents += processTable(secondTable);
		htmlContents += "</body></html>";

		PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
		writer.println(htmlContents);
		writer.close();
	}


	/**
	 * Process the file and returns the content.
	 * 
	 * @return The two tables content.
	 * @throws IOException
	 *             if the path cannot be found, for either the input or output
	 *             file
	 */
	private List<String> readLinesToList(int a_startLine) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
		List<String> theReturn = new ArrayList<String>();
		int i = 0;
		int rowCount = 0;
		for (String line; (line = br.readLine()) != null;)
		{
			if (i >= a_startLine && rowCount <= 15)
			{
				theReturn.add(line);
				rowCount++;
			}
			i++;
		}
		br.close();
		return theReturn;
	}

	/**
	 * Turns a given list of file rows in to a 4x4 table.
	 * 
	 * @param a_rows
	 * @return HTML table
	 */
	private String processTable(List<String> a_rows)
	{
		String theReturn = new String();
		int lineNumber = 0;
		theReturn += "<table border=\"1\"><tr>";
		for (String row : a_rows)
		{
			Scanner line = new Scanner(row);
			line.useDelimiter(",");
			try
			{
				// We don't care about the letter, so just move past
				line.next();

				// Get the contents of the next token.
				String cellContents = line.next();

				// Start a new table row every fourth row.
				if ((lineNumber % 4) == 0 && lineNumber > 0)
				{
					// Start a new row 16
					theReturn += "</tr><tr>";
				}
				theReturn += addCell(cellContents);

			} catch (NoSuchElementException nse)
			{
				// There was no delimiter, or a bad delimiter
				System.err.println("There was no delimiter found for line: "
						+ line);
			} finally
			{
				lineNumber++;
			}
		}
		theReturn += "</tr></table>";
		return theReturn;
	}

	/**
	 * Builds the contents of a cell.
	 * 
	 * @param a_content
	 *            The content to put in the cell.
	 * @return full cell with opening and closing tags
	 */
	private String addCell(String a_content)
	{
		a_content = a_content.trim();
		if (a_content.startsWith("http"))
		{
			return "<td><img src=\"" + a_content + "\" /></td>\n";
		} else
		{
			return "<td>" + a_content + "</td>\n";
		}
	}

	public String getInputFilePath()
	{
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath)
	{
		this.inputFilePath = inputFilePath;
	}

	public String getOutputFilePath()
	{
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath)
	{
		this.outputFilePath = outputFilePath;
	}

	public String getLogoPath()
	{
		return logoPath;
	}

	public void setLogoPath(String logoPath)
	{
		this.logoPath = logoPath;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
