package ics240.assignment7;

import java.awt.Color;


public class PuzzleConfig {
	
	public static String defaultFile = "puzzle.txt";
	
	// see http://www.javaprogrammingforums.com/java-swing-tutorials/39-how-change-jtextarea-font-font-size-color.html
	// see http://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
	public static String fontName = "Times New Roman";
	public static int fontSize = 12; 
	public static Color textColorUnselected = Color.BLACK;
	public static Color textColorSelected = Color.YELLOW;
	public static Color cellColorUnselected = Color.CYAN;
	public static Color cellbgColorSelected = Color.GREEN; 
	public static int cellHeight = 200; // in pixels
	public static int cellWidth = 200; // in pixel
		
	// Note: If anything is going to change in your GUI appearance,
	// it makes sense to externalize it as a configuration variable
	public static String gameCompletedMsg = "Congratulations!";
	public static String gameIncompleteMsg = "You are NOT done!";
	
	// NOTE: The number of words can range from 5 to 20
	public static int maxNumberOfWords = 20;
	public static int minNumberofWords = 5;
	
	// NOTE: if there are 10 words or less, all those are in one column
	// NOTE: if there are 11 words and less than 20 words, then we will show two columns
	public static int maxNumberOfWordsPerColumn = 10;
	
}
