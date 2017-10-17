package groupproj1.iteration2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * This is the parent facade for all Theater operations. It is a singleton.
 * 
 * The revenue from ticket sales for a show is divided equally between the
 * theater and the client (the shows producer). Thus, whenever a ticket is
 * sold, you must update the balance for that client.
 * 
 * @author Matt, Andre
 *
 */
public class Theater implements Serializable {

	private static final String DATA_FILE_NAME = "TheaterData";
	private static final long serialVersionUID = -2152902330352528201L;
	private static Theater theater;
	private static String name;
	private static int capacity;
	private static double balance;

	// These are persisted along with the Theater and once we retrieve
	// the Theater instance, we have to put THESE instances on the static
	// variables for each singleton class.
	private ClientList clientList;
	private MemberList memberList;
	private IdServer    idServer;

	/**
	 * Private constructor to support singleton pattern Sets the name and
	 * capacity of the theater
	 * 
	 * @param theaterName
	 *            The name of the theater
	 * @param theaterCapacity
	 *            The seating capacity of the theater
	 */
	private Theater(String theaterName, int theaterCapacity) {
		clientList = ClientList.getInstance();
		memberList = MemberList.getInstance();
		idServer = IdServer.getInstance();
		name = theaterName;
		capacity = theaterCapacity;
	}

	/**
	 * Gets/Creates the static instance of the theater Passes the theater name
	 * and capacity
	 * 
	 * @return theater
	 */
	public static Theater getInstance() {
		if (theater == null) {
			theater = new Theater("ABM Cinema", 2000);
			ClientList.setInstance(theater.getClientListInstance());
			MemberList.setInstance(theater.getMemberListInstance());
			IdServer.setInstance(theater.getIdServerInstance());
		}
		return theater;

	}


	/**
	 * Adds a client to the client list.
	 * 
	 * @param clientName
	 * @param clientAddress
	 * @param clientPhoneNumber
	 * @return true if the client is successfully added, false if they aren't
	 *         added
	 */
	public boolean addClient(String clientName, String clientAddress, String clientPhoneNumber) {
		Client client = new Client(clientName, clientAddress, clientPhoneNumber);
		return clientList.addClient(client);
	}

	/**
	 * Removes a client with the given ID from the client list.
	 * 
	 * @param clientID
	 * @return true if the client is successfully removed, false if they aren't
	 */
	public boolean removeClientById(String clientID) {
		return clientList.removeClientById(clientID);
	}

	/**
	 * Lists all clients.
	 */
	public List<Client> getAllClients() {
		return clientList.getAllClients();
	}

	/**
	 * Lists all shows.
	 * @return 
	 */
	public List<Show> getAllShows() {
		return clientList.getAllClientShows();
	}

	/**
	 * Adds a show to the client. Before adding it checks to make sure there is
	 * no scheduling conflict between the new show and existing shows
	 * 
	 * @param clientID
	 * @param showName
	 * @param startDate
	 * @param endDate
	 * @return true if the show is successfully added, false if it is not
	 */
	public Response addShow(String clientID, String showName, Calendar startDate, Calendar endDate, double ticketPrice) {
		Show myShow = new Show(showName, startDate, endDate, ticketPrice);
		Response resp = new Response();
		if (myShow.checkIfDateAvailable()) {
			Client client = clientList.findClientById(clientID);
			if (client == null) {
				resp.setErrorState(Response.NO_CLIENT_FOUND);
				return resp;
			} else {
				boolean valid = client.addShow(myShow);
				if (!valid) {
					resp.setErrorState(Response.SYSTEM_ERR);
				}
			}
		} else {
			resp.setErrorState(Response.SHOW_SCHED_ERR);
		}
		return resp;
	}

	/**
	 * Adds a member to the member list
	 * 
	 * @param inName
	 *            Member name
	 * @param inCreditCard
	 *            Credit Card Number
	 * @param inExpiry
	 *            Credit Card expiration Date
	 * @return true if the member is successfully added, false if they are not
	 */
	public boolean addMember(String inName, String inPhone, String inAddress, int inCreditCard, Calendar inExpiry) {
		Member member = new Member();
		member.setName(inName);
		member.createId(IdServer.getInstance().getMemberId());
		member.setPhoneNum(inPhone);
		member.setAddress(inAddress);
		CreditCard myCard = new CreditCard();
		myCard.setNumber(inCreditCard);
		myCard.setExpirationDate(inExpiry);
		if (member.addCreditCard(myCard)) {
			return memberList.addMember(member);
		}
		return false;
	}

	/**
	 * Adds a credit card to the given memberid's member.
	 * 
	 * @param inMemberId
	 *            Member's ID
	 * @param inCreditCard
	 *            CreditCard Number
	 * @param inExpiry
	 *            Credit Card expiration Date
	 * @return true if the card is successfully added, false if it is not
	 */
	public boolean addMemberCreditCard(String inMemberId, int inCreditCard, Calendar inExpiry) {
		// Find the member
		Member member = memberList.findMemberById(inMemberId);

		// Build the credit card
		CreditCard cc = new CreditCard();
		cc.setNumber(inCreditCard);
		cc.setExpirationDate(inExpiry);
		return member.addCreditCard(cc);
	}

	/**
	 * Finds the member, and removes the credit card if it is found in the
	 * members collection.
	 * 
	 * @param inMemberId
	 *            Member's ID
	 * @param inCreditCard
	 *            Credit Card Number
	 * @return true if the card is successfully removed, false if it is not
	 */
	public Response removeMemberCreditCard(String inMemberId, int inCreditCard) {
		// Find the member
		Member member = memberList.findMemberById(inMemberId);
		return member.removeCreditCard(inCreditCard);
	}

	/**
	 * Remove the member from the member list.
	 * 
	 * @param inMemberId
	 * @return true if the member is successfully removed, false if it is not
	 */
	public boolean removeMember(String inMemberId) {
		boolean theResult = memberList.removeMember(inMemberId);
		return theResult;
	}

	/**
	 * Return a list of all members.
	 * 
	 * @return theResult List of all members
	 */
	public List<Member> getAllMembers() {
		List<Member> theResult = memberList.getAllMembers();
		return theResult;
	}

	/**
	 * Saves the theater data to disk
	 * 
	 * @return true if the save is successful, false if it is not
	 */
	public static boolean save() {
		ObjectOutputStream output = null;
		try {
			FileOutputStream file = new FileOutputStream(DATA_FILE_NAME);
			output = new ObjectOutputStream(file);
			output.writeObject(theater);
			output.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Loads the theater data from disk
	 * 
	 * @return true if the save is successful, false if it is not
	 */
	public static boolean retrieve() {
		boolean theReturn = false;
		ObjectInputStream input = null;
		try {
			FileInputStream file = new FileInputStream(DATA_FILE_NAME);
			input = new ObjectInputStream(file);
			theater = (Theater) input.readObject();

			theReturn = true;
		} catch (IOException | ClassNotFoundException cnfe) {

			// We have to create new instances of our singletons since
			// they were not saved on to disk
			theater = Theater.getInstance();
			theater.memberList = MemberList.getInstance();
			theater.clientList = ClientList.getInstance();
			theater.idServer = IdServer.getInstance();

		} finally {
			try {
				input.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

		// Set the saved instances (if they are there) but if we caught an exception
		// while trying to read data, then these are new instances but thats ok.
		MemberList.setInstance(theater.memberList);
		ClientList.setInstance(theater.clientList);
		IdServer.setInstance(theater.idServer);

		return theReturn;
	}


	/**
	 * Sells and processes the given ticket. It updates the balance on the
	 * Theater and the Client and verifies the data.
	 * 
	 * @param ticket
	 * @return null if good, otherwise error
	 */
	public Response sellTicket(AbstractTicket ticket) {

		Response theResponse = new Response();
		Show theShow = clientList.findShowFromName(ticket.getShowName());

		if (theShow == null) {
			theResponse.setErrorState(Response.NO_SHOW_FOUND);
			return theResponse;
		}

		Client theClient = clientList.findClientFromShowName(ticket.getShowName());

		if (theClient == null) {
			// should not get this far if true
			theResponse.setErrorState(Response.NO_CLIENT_FOUND);
			return theResponse;
		}

		// Now check the dates
		if (theShow.isDateValid(ticket.getShowDate())) {
			// Sell the ticket
			theClient.addToBalance(ticket);
			theater.addToBalance(ticket);
		} else {
			theResponse.setErrorState(Response.INVALID_PURCHASE_DATE);
			return theResponse;
		}

		theResponse.setValidMessage(ticket.getMessage());

		return theResponse;
	}

	/**
	 * Add's ONE HALF the value of the ticket to the Theaters balance.
	 * 
	 * @param inTicket
	 */
	private void addToBalance(AbstractTicket inTicket) {
		balance += (inTicket.getPrice() / 2);
	}

	/**
	 * Returns the balance of the Theater
	 * @return  balance   the balance of the Theater
	 */
	public static double getBalance() {
		return balance;
	}

	/**
	 * Returns the capacity of the Theater
	 * @return capacity  double that represents that maximum number of occupants
	 */
	public double getCapacity() {
		return capacity;
	}

	/**
	 * Returns the name of the Theater
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * for use in paying client without allowing userInterface to directly effect how it's done.
	 */
	public static void payClient(double payment, Client client) {
		client.recievePayment(payment);
	}

	/**
	 * Returns the ClientList
	 * @return clientList
	 */
	public ClientList getClientListInstance() {
		return clientList;
	}

	/**
	 * Returns the MemberList
	 * @return memberList
	 */
	public MemberList getMemberListInstance() {
		return memberList;
	}

	/**
	 * Determines whether you can buy an advance ticket for a specific show date
	 * @return boolean
	 * true if the ticket can be sold
	 * false if today is the day of the show and you can't purchase advance tickets
	 */
	public boolean advanceTicketCheck(Calendar current, Calendar showDate) {
		if(current.get(Calendar.DAY_OF_MONTH) == showDate.get(Calendar.DAY_OF_MONTH)) {
			if (current.get(Calendar.MONTH) == showDate.get(Calendar.MONTH)) {
				if (current.get(Calendar.YEAR)== showDate.get(Calendar.YEAR)) {
					System.out.println("Sorry, but Advanced Tickets are unavailable " +
							"for same day purchase.");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the ID Server
	 * @return idServer
	 */
	public IdServer getIdServerInstance() {
		return idServer;
	}
}
