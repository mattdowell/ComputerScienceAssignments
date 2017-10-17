package groupproj1.iteration2;

import java.io.Serializable;
import java.util.Calendar;

/**
 * An advance ticket costs 70% of the price of a regular ticket
 * These cannot be purchased on the same day as the showing
 * @author Matt
 *
 */
public class AdvanceTicket extends AbstractTicket implements Serializable {

	private static final long serialVersionUID = -1131872414180040970L;
	protected static final double PERCENTAGE = .7; 

	/**
	 * Constructor
	 * @param showDate  The date of the show
	 * @param inShow	The show the ticket is for
	 */
	public AdvanceTicket(Calendar showDate, Show inShow) {
		super(showDate, inShow);
		reducePrice(PERCENTAGE);
	}

	/**
	 * Appropriately Reduces the price of the show
	 * Because of the type of ticket
	 * @param inPercentage 
	 */
	protected void reducePrice(double inPercentage) {
		setPrice(getPrice() * inPercentage);
	}

	/**
	 * Returns a String message on the ticket
	 * @return String
	 */
	@Override
	public String getMessage() {
		return "";
	}

	/**
	 * Generates a String that represents the ticket
	 * @return String
	 */
	@Override
	public String toString() {
		return "AdvanceTicket [" + super.toString() + getMessage() + "] ";
	}
}
