package groupproj1.iteration2;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Student advance tickets costs 50% of the price of a regular advance ticket
 * Can't be purchased the same day as the show
 * @author Matt
 *
 */
public class StudentAdvanceTicket extends AdvanceTicket implements Serializable {

	private static final long serialVersionUID = 6375955294451806827L;
	private static final String WARNING = "Must show valid student id.";

	// 1/2 of a regular ticket
	protected static final double PERCENTAGE = .5; 

	/**
	 * Constructor
	 * @param showDate  The day the show is to be watched
	 * @param inShow    The show the ticket is for
	 */
	public StudentAdvanceTicket(Calendar showDate, Show inShow) {
		super(showDate, inShow);
		reducePrice(PERCENTAGE);
	}

	/**
	 * Returns a string for any messages on the ticket
	 * @return returns the ticket Warning
	 */
	@Override
	public String getMessage() {
		return WARNING;
	}

	/**
	 * Generates a String that represents the ticket
	 */
	@Override
	public String toString() {
		return "StudentAdvanceTicket [" + "serialNumber=" + getSerialNumber() + ", price="
				+ getPrice() + ", showDate=" + (getShowDate().get(Calendar.MONTH) + 1) + "/" + getShowDate().get(Calendar.DATE) + "/" + getShowDate().get(Calendar.YEAR) + ", showName=" + getShowName() + ", " + getMessage() + "] ";
	}
}
