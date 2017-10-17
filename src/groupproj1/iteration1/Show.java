package groupproj1.iteration1;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents a show, or a run of performances. 
 * It has a given name and a start/end date range.
 * 
 * @author Andre
 *
 */
public class Show implements Serializable {

	private static final long serialVersionUID = -7362183999463384891L;
	private String name;
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * Create a show instance using the given parameters.
	 * 
	 * @param showName Name of the show
	 * @param showStartDate The date the show begins playing
	 * @param showEndDate The date the show ends it's run
	 */
	public Show(String showName, Calendar showStartDate, Calendar showEndDate) {
		name = showName;
		startDate = showStartDate;
		endDate = showEndDate;
	}

	/**
	 * Gets the last day the show plays
	 * @return endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * Gets the first day the show plays
	 * @return startDate
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * Prints out the information regarding a show
	 */
	public void display() {
		System.out.println("Show [name=" + name + ", Start Date=" + (startDate.get(Calendar.MONTH) + 1) + 
				"/" + startDate.get(Calendar.DATE) + "/" +  startDate.get(Calendar.YEAR) + ","
				+ " End Date=" + (endDate.get(Calendar.MONTH)+ 1) + "/" + endDate.get(Calendar.DATE)
				+ "/" + endDate.get(Calendar.YEAR)  + "]");
	}

}
