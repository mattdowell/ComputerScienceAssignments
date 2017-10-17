//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

import ics240.midterm.GameHandler.DisplayMode;
import ics240.midterm.GameHandler.FillerCharType;

/**
 * This class holds the default configuration values.
 * 
 * @author Matt
 *
 */
public class Configuration
{
	public static String WORKSHEET_TITLE = "4 Pix 1 Word : Find the word";
	public static String INPUT_FILE_NAME = "c:/temp/4pix1word.txt";
	public static String OUTPUT_FILE_NAME = "c:/temp/4pix1word.html";
	public static int NUMBER_OF_FILLER_ROWS = 2; /*
														 * this indicates the
														 * number of rows at the
														 * bottom of the
														 * pictures
														 */
	public static int NUMBER_OF_TITLES_PER_ROW = 6; /*
														 * this indicates the
														 * number of titles per
														 * row
														 */
	public static DisplayMode displayMode = DisplayMode.IMAGE; /*
														 		* “Image” = show 4
														 		* images; “Text” = show
														 		* 4 words
														 		*/
	public static  FillerCharType charType = FillerCharType.ANY;
														 			/* Any = any letter;
														 			 * Vowel = only vowels;
														 			 * Consonant =only
														 			 * consonants
														 			 */
}
