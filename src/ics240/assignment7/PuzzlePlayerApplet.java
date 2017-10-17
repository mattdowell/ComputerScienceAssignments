package ics240.assignment7;

/**
 * Primary java class for GUI layout and for user interactions / action
 * listeners
 * 
 * @author srj
 * 
 *         NOTE: HTML used to launch an applet can embed arguments in the URL;
 *         For example "http://...../puzzle.html?id=36" is intendeing to launch
 *         a puzzle with ID = "36"
 * 
 *         Pseudo Code: 1. Process the URL and set the puzzle ID (if exists)
 *         through PuzzlePicker 2. Read the puzzle.txt and populate the
 *         PuzzleCollection 4. Get the puzzle to be played based on the
 *         puzzleID; if null, get a random puzzle 5. Create a PuzzleTracker
 *         object based on the Puzzle 6. Build the GUI gird based on puzzle grid
 *         height and width 7. Attach the Listeners to the cells to recognize
 *         the MouseUp / MouseDown / MouseMove events 8. Populate the grid
 *         content (cells and words) based on puzzle and PuzzleConfig 9. Play
 *         the game. 10. Upon completion of the game, put out the
 *         gameCompleteMsg and give the option to the user to play the next game
 *         11. Present the next puzzle in the sequence (in the same order the
 *         puzzles are present in the input file) 12. Terminate the game if the
 *         user doesn't wish to continue or kills the browser
 * 
 */
public class PuzzlePlayerApplet
{

}
