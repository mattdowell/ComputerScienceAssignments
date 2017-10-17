//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Reads a game in from the file system.
 * 
 * @author Blake
 *
 */
public class GameReader
{
	private Stack<Game> gameStack = new Stack<Game>();
	private int gameCounter = 0;
	private GameHandler gameHandler = null;

	public GameReader(GameHandler inHandler)
	{
		gameHandler = inHandler;
	}

	public void readGames() throws IOException
	{

		Scanner input = new Scanner(new File(gameHandler.getInputFileName()));

		while ((input.hasNext()))
		{
			String firstLine = input.nextLine();
			if (!isDelimiter(firstLine))
			{
				String gameSec = firstLine + "\n";
				gameSec += input.nextLine() + "\n";
				gameSec += input.nextLine() + "\n";
				gameSec += input.nextLine() + "\n";
				gameSec += input.nextLine() + "\n";
				Game myGame = parseGameSection(gameSec);
				gameStack.push(myGame);
				gameCounter++;
			}

		}
		input.close();

	}

	private Game parseGameSection(String inText)
	{

		String[] line = new String[5];

		// Split the text in to 5 lines
		line = inText.split("\\r?\\n");
		String[] hold = line[0].split(":\\s?");
		String solutionWord = hold[1];
		String[] line1 = line[1].split(",\\s?");
		String[] line2 = line[2].split(",\\s?");
		String[] line3 = line[3].split(",\\s?");
		String[] line4 = line[4].split(",\\s?");

		String wordOne = line1[0];
		String wordTwo = line2[0];
		String wordThree = line3[0];
		String wordFour = line4[0];
		String urlOne = line1[1];
		String urlTwo = line2[1];
		String urlThree = line3[1];
		String urlFour = line4[1];

		return new Game(solutionWord, urlOne, wordOne, urlTwo, wordTwo,
				urlThree, wordThree, urlFour, wordFour);
	}

	public boolean isDelimiter(String a_String)
	{
		if (a_String == null || a_String.equals("") || a_String.length() < 5 || a_String.startsWith("---"))
		{
			return true;
		}
		return false;
	}

	public Game getNextGame()
	{
		return this.gameStack.pop();
	}

	public int getGameCounter()
	{
		return gameCounter;
	}

	public void setGameCounter(int gameCounter)
	{
		this.gameCounter = gameCounter;
	}

}
