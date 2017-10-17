package groupproj1.iteration2;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Represents a show, or a run of performances. It has a given name and a
 * start/end date range.
 * 
 * @author Andre
 *
 */
public class Show implements Serializable {

	private static final SimpleDateFormat DATE_FORMATTER =new SimpleDateFormat("MM/dd/yyyy");
	private static final long serialVersionUID = -7362183999463384891L;
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private double regularTicketPrice;

	/**
	 * Create a show instance using the given parameters.
	 * 
	 * @param showName
	 *            Name of the show
	 * @param showStartDate
	 *            The date the show begins playing
	 * @param showEndDate
	 *            The date the show ends it's run
	 */
	public Show(String showName, Calendar showStartDate, Calendar showEndDate, double tickPrice) {
		name = showName;
		startDate = showStartDate;
		endDate = showEndDate;
		regularTicketPrice = tickPrice;
	}

	/**
	 * This is theticket price of the show. Any subclasses shou
	 * @return
	 */
	public double getTicketPrice() {
		return regularTicketPrice;
	}

	/**
	 * Returns the name of the show
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for the show
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the last day the show plays
	 * 
	 * @return endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * Gets the first day the show plays
	 * 
	 * @return startDate
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * Checks all existing shows to make sure this show 
	 * doesn't create a scheduling conflict with their runs
	 *
	 * @return true if there are no conflicts, false if there is a conflict
	 */
	public boolean checkIfDateAvailable() {
		Iterator<Client> clientsIterator = ClientList.getInstance().getAllClients().iterator();
		Iterator<Show> showIterator;
		boolean theResult = true;
		Show cursor = null;

		// Ensures that if the new show starts before another show, it also ends
		// before the next show
		while ((clientsIterator.hasNext()) && (theResult == true)) {
			showIterator = clientsIterator.next().getShows().listIterator();
			while (showIterator.hasNext()) {
				cursor = showIterator.next();
				if (getStartDate().getTimeInMillis() < cursor.getStartDate().getTimeInMillis()) {
					if (getEndDate().getTimeInMillis() > cursor.getStartDate().getTimeInMillis()) {
						theResult = false;
					}
				}
			}
		}

		clientsIterator = ClientList.getInstance().getAllClients().iterator();

		// Ensures that a show doesn't start during another shows run
		while ((clientsIterator.hasNext()) && (theResult == true)) {
			showIterator = clientsIterator.next().getShows().listIterator();
			while (showIterator.hasNext()) {
				cursor = showIterator.next();
				if (getStartDate().getTimeInMillis() > cursor.getStartDate().getTimeInMillis()) {
					if (getStartDate().getTimeInMillis() < cursor.getEndDate().getTimeInMillis()) {
						theResult = false;
					}
				}
			}
		}
		return theResult;
	}

	/**
	 * Checks to see if the given date is in between the Start and End dates
	 * @param date
	 * @return true if in between
	 */
	public boolean isDateValid(Calendar inDate) {
		return !(inDate.before(startDate.getTime()) || inDate.after(endDate.getTime()));
	}

	/**
	 * Generates a String representing the show
	 * @return String
	 */
	@Override
	public String toString() {
		return "Show [name=" + name + ", startDate=" + DATE_FORMATTER.format(startDate.getTime()) + ", endDate=" + DATE_FORMATTER.format(endDate.getTime()) + ", regularTicketPrice="
				+ regularTicketPrice + "]";
	}

}
