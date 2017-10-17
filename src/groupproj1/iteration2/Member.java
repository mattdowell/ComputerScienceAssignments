package groupproj1.iteration2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a member. A member is a customer/viewer of the shows, and has one or more credit cards.
 * 
 * @author Matt
 *
 */
public class Member implements Serializable, Matchable<String> {

	private static final long serialVersionUID = -3108417776809085361L;
	private String id;
	private String name;
	private String phoneNum;
	private String address;
	private List<CreditCard> creditCards = new ArrayList<CreditCard>();
	private static final String MEMBER_STRING = "M";

	// Iteration 2
	private List<AbstractTicket> ticketsPurchased = new ArrayList<AbstractTicket>();

	/**
	 * Gets the Member ID
	 * @return getID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the member ID
	 * @param id the new ID
	 */
	public void createId(int id) {
		this.id = MEMBER_STRING + id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the members name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the members name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the list of member Credit Cards
	 * @return creditCards the list of member Credit Cards
	 */
	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	/**
	 * Adds the given credit card to this members collection.
	 * Checks to make sure the new credit card doesn't
	 * already exist
	 * 
	 * @param creditCard The Card to be added
	 * @return true if the operation is successful,
	 * false if it is not
	 */
	public boolean addCreditCard(CreditCard creditCard) {
		if (MemberList.getInstance().ensureNoDuplicates(creditCard)) {
			return this.creditCards.add(creditCard);
		}
		return false;
	}

	/**
	 * Remove the given credit card number from the members collection if it is found.
	 * @param inCreditCard
	 * @return true if card is found and removed, false otherwise
	 */
	public Response removeCreditCard(int inCreditCard) {
		Response theResponse = new Response();

		CreditCard cardToRemove = getCreditCardByNumber(inCreditCard);

		if ((cardToRemove != null) && (creditCards.size() != 1)) {
			creditCards.remove(cardToRemove);
		} else {
			theResponse.setErrorState(Response.CANNOT_REM_CARD_ONE_MIN);
		}
		return theResponse;
	}
	
	/**
	 * Returns a card, if found, based on the number
	 * @param inCreditCardNumber
	 * @return
	 */
	public CreditCard getCreditCardByNumber(int inCreditCardNumber) {
		CreditCard card = null;
		for (CreditCard cc : creditCards) {
			if (cc.getNumber() == inCreditCardNumber) {
				card = cc;
				break;
			}
		}
		return card;
	}

	/**
	 * Returns a string representing the tickets in this members collection
	 * @return String
	 */
	public String displayTickets() {
		String returnString = "";
		for (AbstractTicket tick : ticketsPurchased) {
			returnString += tick.toString();
		}
		return returnString;
	}

	/**
	 * To implement the Matchable interface
	 * 
	 * @param key
	 *            the member id
	 */
	@Override
	public boolean matches(String key) {
		return id.equals(key);
	}

	/**
	 * Adds a ticket to the members collection
	 * @param myTicket
	 * @return boolean
	 * true if the add was successful,
	 * false if it was not
	 */
	public boolean addTicket(AbstractTicket myTicket) {
		return ticketsPurchased.add(myTicket);
	}

	/**
	 * returns the list of purchased tickets
	 * @return ticketsPurchased
	 */
	public List<AbstractTicket> getTickets() {
		return ticketsPurchased;
	}

	/**
	 * Converts the member to a string
	 */
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", phone=" + this.phoneNum + ", address=" + this.address
				+ " creditCards=" + creditCards + ", Tickets=" + displayTickets() + "]";
	}
}
