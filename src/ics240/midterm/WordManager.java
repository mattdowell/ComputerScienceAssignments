//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

/******************************************************************************
 Course:      ICS 240 Programming With Elementary Data Structure
 Semester:    Summer 2014
 Instructor:  Silva Jasthi
 Student:     James Lindstrom
 Assignment:  Midterm
 Compiler:    JDK 1.7 with Eclipse
 Due date:    July 9,2014
******************************************************************************/
public class WordManager {

	private String solution;
	private String solutionArray[];
	private String userArray[] = new String[30];
	private int length;
	private boolean letterAdded = false;
	
	public WordManager(String solutionWord)
	{
		solution = solutionWord;
		length = solution.length();
		solutionArray = solution.split("(?!^)");
		for (int i=0;i<length;i++)
		{
			userArray[i] = "";
		}
	}
	
	/*
	 *Removes a letter from the user Array at a given position
	 *@param - position of letter to remove
	 *Postcondition:
	 *Given letter has been removed from Array 
	 */
	public void removeLetter(int position)
	{
		if (position <= length)
		{
			userArray[position-1] = "";
			
		}
	}
	
	/*
	 * Adds a letter at the next available open position
	 * @param - letter to add 
	 * Postcondition:
	 * The new letter is at the next open spot
	 */
	public void addLetter(String letter)
	{
		
		for (int i=0;i<length;i++)
		{
			if ((userArray[i] == "" )&& (letterAdded == false))
			{
				userArray[i] = letter;
				letterAdded = true;
			}
		}
		letterAdded = false;
		
	}
	
	/*
	 * Tests if the userArray is equal to the solutionArray
	 * @param - none
	 * Postcondition:
	 * returns true if equal, false if not
	 */
	public boolean isSolved()
	{
		boolean solved = true;
		for (int i=0;i<length;i++)
		{
			if (!(userArray[i].equals(solutionArray[i])))
			{
				solved = false;
				
			}		
		}
		
		return solved;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for (int i=0;i<length;i++)
		{
			s = s + userArray[i];
		}
		return s;
	}
}

