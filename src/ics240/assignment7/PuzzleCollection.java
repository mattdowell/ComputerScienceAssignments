package ics240.assignment7;
/**
 * A class representing a collection of puzzles
 * Reads the input file and adds the puzzle to the collection
 * 
 * @author Vitaly Sots
 * 
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PuzzleCollection
{

	// for maintaining a puzzle collection
	private static ArrayList<Puzzle> allPuzzles = new ArrayList<Puzzle>();
	
	// For any other things this class might need
	private Controller myController;

	/*
	 * No-arg constructor uses the default file name
	 */
	public PuzzleCollection()
	{
		
		try {
			readPuzzle(PuzzleConfig.defaultFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Constructor takes the input file name and * Reads each set of the puzzles
	 * * Creates a Puzzle using Puzzle.java * and adds the Puzzle to the
	 * collection allPuzzles * NOTE: Please make sure that you remove * from
	 * puzzle.txt before using it * NOTE: If you are keeping it, then you have
	 * to handle it here (additional complexity) * That is there in the puzzle
	 * text file for representation only The file is a UTF-8 file so that
	 * multi-byte characters can be handled
	 */
	public PuzzleCollection(String a_file_name) 
	{
		try {
			readPuzzle(a_file_name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PuzzleCollection(Controller controller)
	{
		myController = controller;
	}

	/**
	 * Method for adding a puzzle to a puzzle collection
	 * 
	 * @param a_puzzle
	 */

	public void add(Puzzle a_puzzle)
	{
		allPuzzles.add(a_puzzle);
	}

	/*
	 * Method that returns a puzzle based on an Id from the puzzle collection
	 * Ifthere is no puzzle matching that id, then a random puzzle will be
	 * returned
	 */
	public Puzzle getPuzzleByID(String an_id)
	{
		
		for(int i = 0; i < allPuzzles.size(); i++) {
			
			 String current = allPuzzles.get(i).getId();
			 
			 if (current.contains(an_id))
				 return allPuzzles.get(i);
		 }
		
		return null;

	}

	/*
	 * Method that returns a puzzle based on an Id from the puzzle collection
	 * 
	 * If there are multiple puzzles matching the title, then only the first
	 * match will be returned.
	 * 
	 * If there is no match found, then a random puzzle will be returned
	 */
	public Puzzle getPuzzleByTitle(String a_title)
	{

		for(int i = 0; i < allPuzzles.size(); i++) {
			
			 String current = allPuzzles.get(i).getTitle();
			 
			 if (current.contains(a_title))
				 return allPuzzles.get(i);
		 }
		return null;

	}

	/*
	 * Method that returns a a random puzzle from allPuzzles
	 */

	public static Puzzle getRandomPuzzle()
	{
		int randomIndex = (int)(Math.random() * allPuzzles.size());
				
		return allPuzzles.get(randomIndex);
	}
	
	public boolean isDelimiter(String a_String)
	{
		if (a_String.startsWith("---") || a_String == null || a_String.equals("") || a_String.length() < 5)
		{
			return true;
		}
		return false;
	}
	
	public void readPuzzle(String a_pathName) throws IOException
	{

		
		//FileInputStream in = new FileInputStream(a_pathName);
		BufferedReader in = new BufferedReader(new FileReader(a_pathName));
		Scanner input = new Scanner(in);
		String line2 = "";

		while ((input.hasNext()))
		{
			String line = input.nextLine();
			if (!(isDelimiter(line)))
			{
				
				line2 += line + "\n";
			}
			else if (isDelimiter(line))
			{
				//Puzzle myPuzzle = parseGameSection(gameSec);
				//allPuzzles.add(myPuzzle);
				parsePuzzle(line2);
				line2 = "";
			}
		}
		
		
		input.close();
		
	}
	
	private void parsePuzzle(String a_text)
	{
		String id = new String();
		String title = new String();
		ArrayList<String> wordList = new ArrayList<String>();
		String[][] puzzleGrid;
		ArrayList<String[]> charGrid = new ArrayList<String[]>();
		
		Scanner input = new Scanner(a_text);
		
		while (input.hasNext())
		{
			
			
			String line = input.nextLine();
			
			if (line.contains("ID:"))
			{
				String[] split = line.split("ID:");
				id = split[1].trim();
				
			}
			else if (line.contains("Title:"))
			{
				String[] split = line.split("Title:");
				title = split[1].trim();
				
			}
			else if (line.contains("Words:"))
			{
				String[] split = line.split("Words:");
				String words = split[1].trim();
				String[] list = words.split("\\,");
				for (int i = 0; i < list.length; i++)
				{
					wordList.add(list[i].trim());					
				}			
				
			}
			
			else 
			{
				String[] charecters = line.split("\\,");
				charGrid.add(charecters);
			}
		}
		
		
		int gridWidth = charGrid.get(0).length; 
		int gridHeight = charGrid.size(); 
		puzzleGrid = new String[gridWidth][gridHeight];
		
		for (int row = 0; row < gridHeight; row++)
		{
			String[] string = charGrid.get(row);
			if (string.length != gridWidth) 
			{
				System.out.println("Length of rows not equal");
				return;
			}
			for (int col = 0; col < gridWidth; col++)
			{				
				puzzleGrid[row][col] = string[col];
				//System.out.print(puzzleGrid[row][col] + " ");
			}
			//System.out.println("");
		}
		input.close();
		Puzzle puzzle = new Puzzle(id, title, wordList, puzzleGrid, gridWidth, gridHeight);
		allPuzzles.add(puzzle);
		
	}
	
	public static void main(String[] args)
	{
		PuzzlePicker pp1 = new PuzzlePicker("http://www.dropbox.com/PuzzleLauncher.html?id=001");
		PuzzleCollection pc1 = new PuzzleCollection();
		Puzzle p1 = pc1.getPuzzleByID(pp1.getPuzzleId());
		
		System.out.println(p1.getId());
		
	}

}
