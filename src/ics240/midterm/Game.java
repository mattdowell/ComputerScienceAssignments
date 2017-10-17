//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

/**
 * This is a data representation of the information incorporating a game.
 * 
 * @author James
 *
 */
public class Game
{
	private String solutionWord;
	private String imageUrl1;
	private String word1;
	private String imageUrl2;
	private String word2;
	private String imageUrl3;
	private String word3;
	private String imageUrl4;
	private String word4;

	public Game(String solutionWord, String imageUrl1, String word1,
			String imageUrl2, String word2, String imageUrl3, String word3,
			String imageUrl4, String word4)
	{
		super();
		this.solutionWord = solutionWord;
		this.imageUrl1 = imageUrl1;
		this.word1 = word1;
		this.imageUrl2 = imageUrl2;
		this.word2 = word2;
		this.imageUrl3 = imageUrl3;
		this.word3 = word3;
		this.imageUrl4 = imageUrl4;
		this.word4 = word4;
	}

	public String getSolutionWord()
	{
		return solutionWord;
	}

	public void setSolutionWord(String solutionWord)
	{
		this.solutionWord = solutionWord;
	}

	public String getImageUrl1()
	{
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1)
	{
		this.imageUrl1 = imageUrl1;
	}

	public String getWord1()
	{
		return word1;
	}

	public void setWord1(String word1)
	{
		this.word1 = word1;
	}

	public String getImageUrl2()
	{
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2)
	{
		this.imageUrl2 = imageUrl2;
	}

	public String getWord2()
	{
		return word2;
	}

	public void setWord2(String word2)
	{
		this.word2 = word2;
	}

	public String getImageUrl3()
	{
		return imageUrl3;
	}

	public void setImageUrl3(String imageUrl3)
	{
		this.imageUrl3 = imageUrl3;
	}

	public String getWord3()
	{
		return word3;
	}

	public void setWord3(String word3)
	{
		this.word3 = word3;
	}

	public String getImageUrl4()
	{
		return imageUrl4;
	}

	public void setImageUrl4(String imageUrl4)
	{
		this.imageUrl4 = imageUrl4;
	}

	public String getWord4()
	{
		return word4;
	}

	public void setWord4(String word4)
	{
		this.word4 = word4;
	}

}
