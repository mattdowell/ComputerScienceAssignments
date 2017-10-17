package groupproj1.iteration1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * This is the parent facade for all Theater operations. It is a singleton.
 * 
 * @author Matt, Andre
 *
 */
public class Theater implements Serializable {

	private static final long serialVersionUID = -2152902330352528201L;
	private static Theater theater;
	private String name;
	private int capacity;

	/**
	 * Private constructor to support singleton pattern
	 * Sets the name and capacity of the theater
	 * @param theaterName  The name of the theater
	 * @param theaterCapacity  The seating capacity of the theater
	 */
	private Theater(String theaterName, int theaterCapacity) {
		name = theaterName;
		capacity = theaterCapacity;
	}

	/**
	 * Gets/Creates the static instance of the theater
	 * Passes the theater name and capacity
	 * @return theater
	 */
	public static Theater getInstance() {
		if (theater == null) {
			theater = new Theater("ABM Cinema", 2000);
		}
		return theater;
	}

	/**
	 * Adds a client to the client list.
	 * @param clientName
	 * @param clientAddress
	 * @param clientPhoneNumber
	 * @return true if the client is successfully added,
	 * false if they aren't added
	 */
	public boolean addClient(String clientName, String clientAddress, String clientPhoneNumber) {
		Client client = new Client(clientName, clientAddress, clientPhoneNumber);
		return ClientList.instance().addClient(client);
	}

	/**
	 * Removes a client with the given ID from the client list.
	 * @param clientID
	 * @return true if the client is successfully removed,
	 * false if they aren't
	 */
	public boolean removeClient(String clientID) {
		return ClientList.instance().removeClient(clientID);
	}

	/**
	 * Lists all clients.
	 */
	public void listClients() {
		ClientList.instance().displayClients();
	}

	/**
	 * Lists all shows.
	 */
	public void listShows() {
		ClientList.instance().listClientShows();
	}

	/**
	 * Adds a show to the client.
	 * Before adding it checks to make sure there is no scheduling
	 * conflict between the new show and existing shows
	 * @param clientID
	 * @param showName
	 * @param startDate
	 * @param endDate
	 * @return true if the show is successfully added,
	 * false if it is not
	 */
	public boolean addShow(String clientID, String showName, Calendar startDate, Calendar endDate) {
		Show myShow = new Show(showName, startDate, endDate);
		if (ClientList.instance().checkIfDateAvailable(myShow)) {
			return ClientList.instance().findClient(clientID).addShow(myShow);
		}
		return false;
	}

	/**
	 * Adds a member to the member list
	 * @param inName	Member name
	 * @param inCreditCard	Credit Card Number
	 * @param inExpiry  Credit Card expiration Date
	 * @return true if the member is successfully added,
	 * false if they are not
	 */
	public boolean addMember(String inName, int inCreditCard, Calendar inExpiry) {
		Member member = new Member();
		member.setName(inName);
		CreditCard myCard = new CreditCard();
		myCard.setNumber(inCreditCard);
		myCard.setExpirationDate(inExpiry);
		if (member.addCreditCard(myCard)) {
			return MemberList.getInstance().addMember(member);
		}
		return false;
	}

	/**
	 * Adds a credit card to the given memberid's member.
	 * @param inMemberId	Member's ID
	 * @param inCreditCard	CreditCard Number
	 * @param inExpiry	Credit Card expiration Date
	 * @return true if the card is successfully added,
	 * false if it is not
	 */
	public boolean addMemberCreditCard(String inMemberId, int inCreditCard, Calendar inExpiry) {
		// Find the member
		Member member = MemberList.getInstance().findMemberById(inMemberId);

		// Build the credit card
		CreditCard cc = new CreditCard();
		cc.setNumber(inCreditCard);
		cc.setExpirationDate(inExpiry);
		return member.addCreditCard(cc);
	}

	/**
	 * Finds the member, and removes the credit card if it is found in the members collection.
	 * @param inMemberId	Member's ID
	 * @param inCreditCard	Credit Card Number
	 * @return true if the card is successfully removed,
	 * false if it is not
	 */
	public boolean removeMemberCreditCard(String inMemberId, int inCreditCard) {
		// Find the member
		Member member = MemberList.getInstance().findMemberById(inMemberId);

		return member.removeCreditCard(inCreditCard);
	}

	/**
	 * Remove the member from the member list.
	 * @param inMemberId
	 * @return true if the member is successfully removed,
	 * false if it is not
	 */
	public boolean removeMember(String inMemberId) {
		boolean theResult = MemberList.getInstance().removeMember(inMemberId);
		return theResult;
	}

	/**
	 * Return a list of all members.
	 * @return theResult  List of all members
	 */
	public List<Member> getAllMembers() {
		List<Member> theResult = MemberList.getInstance().getAllMembers();
		return theResult;
	}

	/**
	 * Saves the theater data to disk
	 * @return true if the save is successful,
	 * false if it is not
	 */
	public static boolean save() {
		ObjectOutputStream output = null;
		try {
			FileOutputStream file = new FileOutputStream("TheaterData");
			output = new ObjectOutputStream(file);
			output.writeObject(theater);
			output.writeObject(IdServer.getInstance());
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
