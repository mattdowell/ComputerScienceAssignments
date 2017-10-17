package ics240.assignment7;

/**
 * This class acts as the controller between the GUI and the back-end. ALL the
 * other classes should be very loosely coupled and not know who/what is calling
 * them. It is the controllers responsibility to make those calls and control
 * the appropriate responses.
 * 
 * @author Matt
 * 
 */
public class Controller
{

	// The URL of the puzzle config file
	private String URL = null;

	private String puzzleId = null;

	public Controller(String string)
	{
		setURL(string);
	}

	public Controller()
	{
		// TODO Auto-generated constructor stub
	}

	public String getURL()
	{
		return URL;
	}

	public void setURL(String a_url)
	{
		if (a_url.indexOf("=") > -1)
		{
			puzzleId = a_url.substring(a_url.indexOf("=") + 1);
		}
		URL = a_url;
	}

	public String getPuzzleId()
	{
		return puzzleId;
	}

	public void setPuzzleId(String an_id)
	{
		puzzleId = an_id;

	}

}
