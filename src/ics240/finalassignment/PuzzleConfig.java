//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.finalassignment;

import java.awt.Color;
import java.awt.Font;

public class PuzzleConfig
{

	public static String defaultFile = "puzzle.txt";

	// see
	// http://www.javaprogrammingforums.com/java-swing-tutorials/39-how-change-jtextarea-font-font-size-color.html
	// see http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
	public static Font fontTitle = new Font("Verdana", Font.BOLD, 18);
	public static Color colorTitle = Color.WHITE;
	public static Font fontWords = new Font("gautami", Font.BOLD, 16);
	public static Color colorWords = Color.WHITE;
	public static Font fontGrid = new Font("gautami", Font.BOLD, 18);
	public static Color textColorUnselected = Color.BLACK;
	public static Color textColorSelected = Color.YELLOW;
	public static Color cellColorWordFound = Color.GREEN;
	public static Color cellbgColorSelected = Color.BLACK;
	public static int cellHeight = 200; // in pixels
	public static int cellWidth = 200; // in pixel

	// Note: If anything is going to change in your GUI appearance,
	// it makes sense to externalize it as a configuration variable
	public static String gameCompletedMsg = "Congratulations!"
			+ "\nWould you like to play a next game?";
	public static String gameIncompleteMsg = "You are NOT done!";

	// NOTE: The number of words can range from 5 to 20
	public static int maxNumberOfWords = 20;
	public static int minNumberofWords = 5;

	// NOTE: if there are 10 words or less, all those are in one column
	// NOTE: if there are 11 words and less than 20 words, then we will show two
	// columns
	public static int maxNumberOfWordsPerColumn = 4;

}
