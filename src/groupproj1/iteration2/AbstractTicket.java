package groupproj1.iteration2;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Represents an abstract ticket object. All tickets have the same data, and
 * different prices.
 * 
 * @author Matt
 *
 */
public abstract class AbstractTicket implements Serializable {

	private static final long serialVersionUID = -8236771761542522725L;
	private String serialNumber;
	private double price;
	private Calendar showDate;
	private String showName;

	/**
	 * AbstractTicket Constructor
	 * Sets fields and generates an ID number
	 * @param showDate	The date of the show
	 * @param inShow	The Show the ticket is for
	 */
	public AbstractTicket(Calendar showDate, Show inShow) {
		super();
		this.serialNumber = String.valueOf(IdServer.getInstance().getTicketId());
		this.price = inShow.getTicketPrice();
		this.showDate = showDate;
		this.showName = inShow.getName();
	}

	public abstract String getMessage();


	/**
	 * Updates the price sold of this ticket to the Clients balance.
	 * 
	 * @param inClient
	 */
	public void updateBalance(Client inClient) {
		inClient.addToBalance(this);
	}

	/**
	 * Returns the tickets serial Number
	 * @return serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the tickets serial Number
	 * @param serialNumber
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Returns the tickets price
	 * @return price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the tickets price
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns the date of the show the ticket is for
	 * @return showDate
	 */
	public Calendar getShowDate() {
		return showDate;
	}

	/**
	 * Sets the date of the show the ticket is for
	 * @param showDate
	 */
	public void setShowDate(Calendar showDate) {
		this.showDate = showDate;
	}

	/**
	 * Returns the shows name
	 * @return showName
	 */
	public String getShowName() {
		return showName;
	}

	/**
	 * Sets the shows name
	 * @param showName
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}

	/**
	 * Checks if a ticket is set for the same day as the passed calendar day
	 * @param date you want to compare the ticket against
	 * @return boolean
	 * true if the ticket is on the same day as the target date,
	 * false if they are different days
	 */
	public boolean matchesDate(Calendar date) {
		if (date.get(Calendar.DATE) == showDate.get(Calendar.DATE)) {
			if (date.get(Calendar.MONTH) == showDate.get(Calendar.MONTH)) {
				if (date.get(Calendar.YEAR) == showDate.get(Calendar.YEAR)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Generates a String that represents the ticket
	 * @return String
	 */
	@Override
	public String toString() {
		return "serialNumber=" + serialNumber + ", price="
				+ price + ", showDate=" + (showDate.get(Calendar.MONTH) + 1) + "/" + showDate.get(Calendar.DATE) 
				+ "/" + showDate.get(Calendar.YEAR) + ", showName=" + showName + ", ";
	}
}
