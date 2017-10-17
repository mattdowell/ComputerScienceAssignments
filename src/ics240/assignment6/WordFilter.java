package ics240.assignment6;

import java.util.ArrayList;
import java.util.List;

/**
 * Takes a filter String and a collection of words and returns the filtered
 * collection
 * 
 * @author Matt
 * 
 */
public class WordFilter
{
	String filter = null;
	List<Word> words = null;

	public WordFilter(String filter, List<Word> words)
	{
		super();
		this.filter = filter;
		this.words = words;
	}

	/**
	 * Checks each word for the filter text (contains) and returns the filtered
	 * List
	 * 
	 * @return List of filtered words
	 */
	public List<Word> getFilteredList()
	{
		if (filter == null)
		{
			return words;
		} else
		{
			List<Word> theReturn = new ArrayList<>();
			for (Word word : words)
			{
				if (buildCompleteTextToSearch(word).contains(filter))
				{
					theReturn.add(word);
				}
			}
			return theReturn;
		}
	}
	
	/**
	 * Concats all the sections of a word we should use for searching.
	 * @param inWord
	 * @return
	 */
	private String buildCompleteTextToSearch(Word inWord) {
		return inWord.getEnglishInEnglish() + " " + inWord.getTheme();
	}

}
