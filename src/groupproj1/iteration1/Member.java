package groupproj1.iteration1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a member. A member is a customer/viewer of the shows, and has one or more credit cards.
 * 
 * @author Matt
 *
 */
public class Member implements Serializable {

	private static final long serialVersionUID = -3108417776809085361L;
	private String id;
	private String name;
	private List<CreditCard> creditCards = new ArrayList<CreditCard>();
	private static final String MEMBER_STRING = "M";

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
	public void setId(int id) {
		this.id = MEMBER_STRING + id;
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
	 * Converts the member to a string
	 */
	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", creditCards="
				+ creditCards + "]";
	}

	/**
	 * Remove the given credit card number from the members collection if it is found.
	 * @param inCreditCard
	 * @return true if card is found and removed, false otherwise
	 */
	public boolean removeCreditCard(int inCreditCard) {
		boolean theResult = false;

		CreditCard cardToRemove = null;
		for (CreditCard cc : creditCards) {
			if (cc.getNumber() == inCreditCard) {
				cardToRemove = cc;
			}
		}

		if ((cardToRemove != null) && (creditCards.size() != 1)) {
			creditCards.remove(cardToRemove);
			theResult = true;
		}
		return theResult;

	}

}
