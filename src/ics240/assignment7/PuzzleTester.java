package ics240.assignment7;

/**
 * A dummy class to test the logic of different classes
 * It does NOT involve GUI
 * It does NOT involve playing of the game through GUI
 * However, it tests the rest of the logic
 * 
 * * Pseudo Code:
 * 1. Process the URL and set the puzzle ID (if exists) through PuzzlePicker
 * 2. Read the puzzle.txt and populate the PuzzleCollection
 * 4. Get the puzzle to be played based on the puzzleID; if null, get a random puzzle
 * 5. Create a PuzzleTracker object based on the Puzzle
 * 6. Build the GUI gird based on puzzle grid height and width
 * 7. Attach the Listeners to the cells to recognize the MouseUp / MouseDown / MouseMove events
 * 8. Populate the grid content (cells and words) based on puzzle and PuzzleConfig
 * 9. Play the game.
 * 10. Upon completion of the game, put out the gameCompleteMsg and give the option to the user to play the next game
 * 11. Present the next puzzle in the sequence (in the same order the puzzles are present in the input file)
 * 12. Terminate the game if the user doesn't wish to continue or kills the browser 
 * 
 * @author srj
 *
 */
public class PuzzleTester {
	
	public static void main(String[] args)
	{
		
		// TESTS for puzzle 001
		// get 001 puzzle from the puzzlelist
		PuzzlePicker pp1 = new PuzzlePicker("http://www.dropbox.com/PuzzleLauncher.html?id=001");
		
		// populates the puzzle collection
		PuzzleCollection pc1 = new PuzzleCollection();
		
		// get the puzzle based on the id
		Puzzle p1 = pc1.getPuzzleByID(pp1.getPuzzleId());
		
		// create a puzzle tracking object
		PuzzleTracker pt1 = new PuzzleTracker(p1);
		
		// BEAN BAG, IMPRESA, LENS, MANUAL, MATT, NIKON, PRISM, ROLL, SENSIA, TONE
		
		// find these words
		pt1.isWordInTheList("BEAN BAG");
		pt1.isWordInTheList("IMPRESA");
		pt1.isWordInTheList("LENS");
		pt1.isWordInTheList("MANUAL");
		pt1.isWordInTheList("MATT");
		pt1.isWordInTheList("NIKON");
		pt1.isWordInTheList("PRISM");
		pt1.isWordInTheList("ROLL");
		pt1.isWordInTheList("SENSIA");
		
		// TONE is not found yet. So, run this test
		System.out.println("is TONE found? = " + pt1.isWordAlreadyFound("TONE"));
		// And the game is NOT complete
		System.out.println("Puzzle 001  Status" + pt1.getGameStatusMsg());
				
		
		// now find the word TONE and rerun the test
		pt1.isWordInTheList("TONE");
		System.out.println("is TONE found? = " + pt1.isWordAlreadyFound("TONE"));
		// Now the game is complete
		System.out.println("Puzzle 001 Status" + pt1.getGameStatusMsg());
		
		
		// TESTS for puzzle 003  (simulating multi-byte strings)
		// BEAN BALL, IMPRESA, LENS, MANUAL, NATE, NIKON, PRISM, ROLL, SENSIA, TONE
				PuzzlePicker pp3 = new PuzzlePicker("http://www.dropbox.com/PuzzleLauncher.html?id=003");
				
				// populates the puzzle collection
				PuzzleCollection pc3 = new PuzzleCollection();
				
				// get the puzzle based on the id
				Puzzle p3 = pc3.getPuzzleByID(pp3.getPuzzleId());
				
				// create a puzzle tracking object
				PuzzleTracker pt3 = new PuzzleTracker(p3);
				
				// BEAN BALL, IMPRESA, LENS, MANUAL, NATE, NIKON, PRISM, ROLL, SENSIA, TONE			
				// find these words
				pt3.isWordInTheList("BEAN BALL");
				pt3.isWordInTheList("IMPRESA");
				pt3.isWordInTheList("LENS");
				pt3.isWordInTheList("MANUAL");
				pt3.isWordInTheList("NATE");
				pt3.isWordInTheList("NIKON");
				pt3.isWordInTheList("PRISM");
				pt3.isWordInTheList("ROLL");
				pt3.isWordInTheList("SENSIA");
				
				// TONE is not found yet. So, run this test
				System.out.println("is TONE found? = " + pt3.isWordAlreadyFound("TONE"));
				// And the game is NOT complete
				System.out.println("Puzzle 003 Status" + pt3.getGameStatusMsg());
						
				
				// now find the word TONE and rerun the test
				pt3.isWordInTheList("TONE");
				System.out.println("is TONE found? = " + pt3.isWordAlreadyFound("TONE"));
				// Now the game is complete
				System.out.println("Puzzle 003 Status" + pt3.getGameStatusMsg());
				
	}

}
