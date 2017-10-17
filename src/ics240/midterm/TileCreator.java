//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

import ics240.midterm.GameHandler.FillerCharType;

import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.JButton;

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
	private static char[] fillArray(char[] arrayToFill, FillerCharType charType)
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

	private static char randomChar(FillerCharType charType)
	{
		// variables
		Character randomChar;
		char rC;
		Random r = new Random();

		// switch to select random vowel, constant, or either.
		switch (charType)
		{
		case ANY:
			randomChar = ALL[r.nextInt(ALL.length)];
			break;
		case VOWELS:
			randomChar = VOWELS[r.nextInt(VOWELS.length)];
			break;
		case CONSTANANTS:
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
	private static JButton[] createJavaButtons(char[] singleArray, int row, int col)
	{

		JButton[] button = new JButton[(row * col)];		
		for (int i = 0; i < singleArray.length; i++)
		{
			button[i] = new JButton();
			button[i].setText(Character.toString(singleArray[i]));
			
		}
		return button;
	}

	public static String outputToHtml(String word, int row, int col,
			FillerCharType charType) throws FileNotFoundException
	{
		word = word.toUpperCase();
		char[] charArray = wordToScatteredInRandomArray(word, row, col);
		fillArray(charArray, charType);
		String hTMLstring = createHTMLTable(charArray, row, col);
		return hTMLstring;
	}

	static JButton[] outputToJava(String word, int row, int col,
			FillerCharType charType) throws FileNotFoundException
	{
		word = word.toUpperCase();
		char[] charArray = wordToScatteredInRandomArray(word, row, col);
		fillArray(charArray, charType);
		return createJavaButtons(charArray, row, col);
	}
}
