package groupproj1.iteration2;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Regular tickets offer no discount, they cost whatever the set price
 * for the show is
 * @author Matt
 *
 */
public class RegularTicket extends AbstractTicket implements Serializable {

	private static final long serialVersionUID = 3266556805880871734L;

	/**
	 * RegularTicket Constructor
	 * @param showDate
	 * @param inShow
	 */
	public RegularTicket(Calendar showDate, Show inShow) {
		super(showDate, inShow);
	}

	/**
	 * Used for displaying any messages contained on ticket
	 * @return String
	 */
	@Override
	public String getMessage() {
		return "";
	}

	/**
	 * Generates a String representation of the ticket
	 * @return String
	 */
	@Override
	public String toString() {
		return "RegularTicket [" + super.toString() + getMessage() + "] ";
	}
}
