//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.assignment2;

import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class is responsible for creating the tile portions of each GUI
 * 
 * @author Langdon
 * 
 */
public class TileCreator
{

	private static final char[] VOWELS = { 'A', 'E', 'I', 'O', 'U' };
	private static final char[] CONSTANANTS = { 'B', 'C', 'D', 'F', 'G', 'H',
			'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X',
			'Y', 'Z' };
	private static final char[] ALL = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	// takes word found in findword method puts in array randomly.
	private static char[] wordToScatteredInRandomArray(String word, int row,
			int col)
	{
		char[] randomArrayWithScatteredWord = new char[(row * col)];
		char[] wordAsArray = word.toCharArray();
		if (randomArrayWithScatteredWord.length >= wordAsArray.length)
		{
			for (int i = 0; i < wordAsArray.length; i++)
			{
				int loc = (int) Math.round(Math.random()
						* (randomArrayWithScatteredWord.length - 1));
				if (randomArrayWithScatteredWord[loc] == 0)
				{
					randomArrayWithScatteredWord[loc] = wordAsArray[i];
				} else
					i--;
			}
		} else
			System.out
					.println("Word is too long. Pick new word or more rows/tiles");
		return randomArrayWithScatteredWord;
	}

	// fills array with random letters
	private static char[] fillArray(char[] arrayToFill, String charType)
	{
		for (int i = 0; i < arrayToFill.length; i++)
		{
			if (arrayToFill[i] == 0)
			{
				arrayToFill[i] = randomChar(charType);
			} else
				continue;
		}
		return arrayToFill;
	}

	private static char randomChar(String charType)
	{
		// variables
		Character randomChar;
		char rC;
		Random r = new Random();

		// switch to select random vowel, constant, or either.
		switch (charType.toLowerCase())
		{
		case "any":
			randomChar = ALL[r.nextInt(ALL.length)];
			break;
		case "vowels":
			randomChar = VOWELS[r.nextInt(VOWELS.length)];
			break;
		case "constanants":
			randomChar = CONSTANANTS[r.nextInt(CONSTANANTS.length)];
			break;
		default:
			randomChar = 0;
			break;
		}

		// return char
		rC = randomChar;
		return rC;
	}

	// outputs array to designated htmlfile
	private static String createHTMLTable(char[] singleArray, int row, int col)
			throws FileNotFoundException
	{
		String out = "";
		int k = 0;
		for (int i = 0; i < row; i++, out += ("</tr>"))
		{
			out += ("<tr align= \"center\">");
			for (int j = 0; j < col; j++, k++)
			{
				out += ("\t<td>");
				out += (singleArray[k]);
				out += ("</td>");
			}
		}
		return out;
	}

	// outputs array to Java Buttons
	private static JPanel createJavaButtons(char[] singleArray, int row, int col)
	{

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(row, col));
		for (int i = 0; i < singleArray.length; i++)
		{
			JButton button = new JButton();
			button.setText(Character.toString(singleArray[i]));
			panel.add(button);
		}
		return panel;
	}

	public static String outputToHtml(String word, int row, int col,
			String charType) throws FileNotFoundException
	{
		word = word.toUpperCase();
		char[] charArray = wordToScatteredInRandomArray(word, row, col);
		fillArray(charArray, charType);
		String hTMLstring = createHTMLTable(charArray, row, col);
		return hTMLstring;
	}

	public static JPanel outputToJava(String word, int row, int col,
			String charType) throws FileNotFoundException
	{
		word = word.toUpperCase();
		char[] charArray = wordToScatteredInRandomArray(word, row, col);
		fillArray(charArray, charType);
		return createJavaButtons(charArray, row, col);
	}
}
