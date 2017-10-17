package groupproj1.iteration2;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Provides an interface for the user. This interface is command-line driven. It
 * is a singleton. The commands are encoded as integers using a number of static
 * final variables. A number of utility methods exist to make it easier to parse
 * the input.
 * 
 * @author Matt, Andre
 *
 */
public class UserInterface {

	private static Theater theater = null;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int EXIT = 0;
	private static final int ADD_CLIENT = 1;
	private static final int REMOVE_CLIENT = 2;
	private static final int LIST_CLIENTS = 3;
	private static final int ADD_MEMBER = 4;
	private static final int REMOVE_MEMBER = 5;
	private static final int ADD_CARD = 6;
	private static final int REMOVE_CARD = 7;
	private static final int LIST_MEMBERS = 8;
	private static final int ADD_SHOW = 9;
	private static final int LIST_SHOWS = 10;
	private static final int STORE_DATA = 11;
	private static final int RETRIEVE_DATA = 12;

	// Iteration 2 below
	private static final int SELL_REG_TICK = 13;
	private static final int SELL_ADV_TICK = 14;
	private static final int SELL_STUD_ADV_TICK = 15;
	private static final int PAY_CLIENT = 16;
	private static final int LIST_TICKETS = 17;
	private static final int HELP = 18;

	/**
	 * Private for singleton pattern Gets a singleton theater object
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			load();
		} else {
			theater = Theater.getInstance();
		}
	}


	/**
	 * Gets the value the user enters after prompting
	 * 
	 * @param prompt
	 *            The string that will be displayed to the user
	 * @return the token entered by the user
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to an int
	 * 
	 * @param prompt
	 *            The string displayed to the user
	 * @return The integer corresponding to the string
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.err.println("Please input a number (integer)");
			}
		} while (true);
	}

	/**
	 * Convertes the string to a double
	 * 
	 * @param prompt
	 * @return
	 */
	public double getDouble(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Double number = Double.valueOf(item);
				return number.doubleValue();
			} catch (NumberFormatException nfe) {
				System.err.println("Please input a number (double)");
			}
		} while (true);
	}

	/**
	 * Gets a date using the format mm/dd/yy
	 * 
	 * @param prompt
	 *            the prompt
	 * @return the data as a calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.err.println("Please input the date in the following format: mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prints a prompt for the user and waits for a number menu command.
	 * 
	 * @return a valid command
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("\nEnter command \"" + HELP + "\" for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.err.println("Command must be a number");
			}
		} while (true);
	}

	/**
	 * Method that adds a client Prompts the user for values and uses the
	 * theater method to add the client
	 */
	public void addClient() {
		String name = getToken("Enter client name");
		String address = getToken("Enter address");
		String phone = getToken("Enter phone");
		if (theater.addClient(name, address, phone)) {
			System.out.println("Client succesfully added");
		} else {
			System.err.println("Client was not added");
		}
	}

	/**
	 * Method that removes a client Gets all information from the user to remove
	 * a client. uses the appropriate theater method to remove the client
	 */
	public void removeClient() {
		String clientID = getToken("Enter Client ID for the Client you want removed");
		if (ClientList.getInstance().findClientById(clientID) == null) {
			System.err.println(Response.NO_CLIENT_ERR_MSG);
			return;
		}
		if (theater.removeClientById(clientID)) {
			System.out.println("Removed Client");
		} else {
			System.err.println("Unable to remove Client. Make sure there are no active shows.");
		}
	}

	/**
	 * Lists all clients.
	 */
	public void listClients() {
		List<Client> clients = theater.getAllClients();
		for (Client c : clients) {
			System.out.println(c);
		}
	}

	/**
	 * Method that adds a member Gets all information from the user to add a
	 * member. Uses the appropriate theater method to add the member
	 */
	public void addMember() {
		String memberName = getToken("Enter the name of the new member");
		String phone = getToken("Enter their phone number");
		String address = getToken("Enter their address");
		int cardNumber = getNumber("Enter the credit card number");
		Calendar expirationDate = getDate("Enter the expiration date of the card as mm/dd/yy");
		if (theater.addMember(memberName, phone, address, cardNumber, expirationDate)) {
			System.out.println("Added Member");
		} else {
			System.err.println("Could not add member");
		}
	}

	/**
	 * Method that removes members Gets all information to remove a member. Uses
	 * the appropriate theater method to remove the member
	 */
	public void removeMember() {
		String memberID = getToken("Enter Member ID for the Member you want removed");
		if (theater.removeMember(memberID)) {
			System.out.println("Removed Member");
		} else {
			System.err.println("Unable to remove Member");
		}
	}

	/**
	 * Method that adds a credit card Gets all information to remove a card Uses
	 * the appropriate theater method to add the card
	 */
	public void addCreditCard() {
		String memberID = getToken("Enter the ID of the member with the new card");
		if (MemberList.getInstance().findMemberById(memberID) == null) {
			System.err.println(Response.NO_MEMBER_ERR_MSG);
			return;
		}
		int cardNumber = getNumber("Enter the credit card number");
		Calendar expirationDate = getDate("Enter the expiration date of the card");
		if (theater.addMemberCreditCard(memberID, cardNumber, expirationDate)) {
			System.out.println("Added Credit Card");
		} else {
			System.err.println("Could not add credit card");
		}
	}

	/**
	 * Method to remove a credit card. Gets all necessary information from the
	 * user. Uses the appropriate theater method to remove the card
	 */
	public void removeCreditCard() {
		String memberID = getToken("Enter the ID of the member whose card is being removed");
		int cardNumber = getNumber("Enter the credit card number");
		if (MemberList.getInstance().findMemberById(memberID) == null) {
			System.err.println(Response.NO_MEMBER_ERR_MSG);
			return;
		}
		Response resp = theater.removeMemberCreditCard(memberID, cardNumber);

		if (resp.isError()) {
			System.err.println(resp.getErrorMessage());
		} else {
			System.out.println("Removed Credit Card");
		}
	}

	/**
	 * Lists all members.
	 */
	public void listMembers() {
		List<Member> memberList = theater.getAllMembers();
		Iterator<Member> myIterator = memberList.iterator();
		while (myIterator.hasNext()) {
			System.out.println(myIterator.next().toString());
		}
	}

	/**
	 * Method to add a show. Gets all necessary information. Calls the
	 * appropriate theater method.
	 */
	public void addShow() {
		String clientID = getToken("Enter the client's ID");
		String showName = getToken("Enter the name of the show");
		Calendar startDate = getDate("Please enter the date the play starts as mm/dd/yy");
		Calendar endDate = getDate("Please enter the date the play ends as mm/dd/yy");
		double ticketPrice = getDouble("What is the regular ticket price?");
		Response resp = theater.addShow(clientID, showName, startDate, endDate, ticketPrice);

		if (resp.isError()) {
			System.err.println(resp.getErrorMessage());
		} else {
			System.out.println("Show was added");
		}
	}

	/**
	 * Lists all shows.
	 */
	public void listShows() {
		List<Show> shows = theater.getAllShows();
		for (Show s : shows) {
			System.out.println(s);
		}
	}

	/**
	 * Method to save data. Calls the appropriate theater method.
	 */
	public void save() {
		if (Theater.save()) {
			System.out.println("The theater has been successfully saved in the file TheaterData.");
		} else {
			System.err.println(" There has been an error in saving.");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate
	 * Theater method for retrieval.
	 * 
	 */
	private void load() {

		boolean wasThere = Theater.retrieve();
		theater = Theater.getInstance();
		if (wasThere) {
			System.out.println("The theater has been successfully retrieved from the file TheaterData.");
		} else {
			System.err.println("File doesnt exist; creating new Theater instance");
		}
	}

	/**
	 * Displays all help/menu information.
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 13 as explained below:");
		System.out.println(EXIT + " to Exit");
		System.out.println(ADD_CLIENT + " to add a client");
		System.out.println(REMOVE_CLIENT + " to remove a client");
		System.out.println(LIST_CLIENTS + " to list all clients");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(REMOVE_MEMBER + " to remove a member");
		System.out.println(ADD_CARD + " to add a credit card for a member");
		System.out.println(REMOVE_CARD + " to to remove a credit card from a member");
		System.out.println(LIST_MEMBERS + " to list all members");
		System.out.println(ADD_SHOW + " to add a show");
		System.out.println(LIST_SHOWS + " to list all shows");
		System.out.println(STORE_DATA + " to save data");
		System.out.println(RETRIEVE_DATA + " to retrieve data");
		System.out.println(SELL_REG_TICK + " to sell a regular ticket");
		System.out.println(SELL_ADV_TICK + " to sell an advanced ticket");
		System.out.println(SELL_STUD_ADV_TICK + " to sell a student advanced ticket");
		System.out.println(PAY_CLIENT + " to pay a client");
		System.out.println(LIST_TICKETS + " to list the tickets sold for a certain day");
		System.out.println(HELP + " for help");
	}

	/**
	 * Given a user command, determine the correct option and call the method
	 */
	public void process() {
		int command;
		// help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ADD_CLIENT:
				addClient();
				break;
			case REMOVE_CLIENT:
				removeClient();
				break;
			case LIST_CLIENTS:
				listClients();
				break;
			case ADD_MEMBER:
				addMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case ADD_CARD:
				addCreditCard();
				break;
			case REMOVE_CARD:
				removeCreditCard();
				break;
			case LIST_MEMBERS:
				listMembers();
				break;
			case ADD_SHOW:
				addShow();
				break;
			case LIST_SHOWS:
				listShows();
				break;
			case STORE_DATA:
				save();
				break;
			case RETRIEVE_DATA:
				load();
				break;
			case SELL_REG_TICK:
				sellRegularTicket();
				break;
			case SELL_ADV_TICK:
				sellAdvancedTicket();
				break;
			case SELL_STUD_ADV_TICK:
				sellStudentAdvanceTicket();
				break;
			case LIST_TICKETS:
				listTicketsForDay();
				break;
			case PAY_CLIENT:
				payClient();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * For paying client separately from the ticket funds.
	 * 
	 * Remove the amount from their balance and cut them a check.
	 * 
	 */
	private void payClient() {
		Client client = ClientList.getInstance().findClientById(getToken("Enter Client ID"));
		if (client == null) {
			System.out.println(Response.NO_CLIENT_ERR_MSG);
			return;
		}
		System.out.println("Client balance is: " + client.getBalance());
		double payment = getDouble("Enter the amount to pay the client.");
		if (payment > client.getBalance()) {
			System.err.println("The amount entered is greater than the current balance owed.");
			return;
		} else {
			Theater.payClient(payment, client);
			System.out.println("A check has been printed for the client "
					+ "and their balance has been reduced. New balance owed: "
					+ client.getBalance());
		}

	}

	/**
	 * Method to sell Student Advanced Tickets (Tickets bought for students at
	 * least one day in advance) Prompts for show information, and then how many
	 * tickets are to be purchased
	 */
	private void sellStudentAdvanceTicket() {
		StudentAdvanceTicket ticket;
		// Member id
		String memberID = getToken("What is your member ID?");
		Member purchaser = MemberList.getInstance().findMemberById(memberID);
		if (purchaser == null) {
			System.out.println(Response.NO_MEMBER_ERR_MSG);
			return;
		}
		
		// Credit card
		int ccNumber = getNumber("Which credit card number should we use?");
		CreditCard cc = purchaser.getCreditCardByNumber(ccNumber);
		if (cc == null) {
			System.out.println(Response.NO_MEMBER_CC_FOUND_ERR_MSG);
			return;
		}		
		
		// Show name
		String showName = getToken("Enter the name of the show");
		Show s = ClientList.getInstance().findShowFromName(showName);
		if (s == null) {
			System.out.println(Response.NO_SHOW_ERR_MSG);
			return;
		}
		Calendar showDate = getDate("Please enter the date of the show as mm/dd/yy");
		Show theShow = ClientList.getInstance().findShowFromName(showName);
		Calendar current = new GregorianCalendar();
		if (theater.advanceTicketCheck(current, showDate)) {
			int numTickets = (int) getDouble("How many Student Advance tickets would you like to purchase?");
			for (int i = 0; i < numTickets; i++) {
				ticket = new StudentAdvanceTicket(showDate, theShow);
				Response resp = theater.sellTicket(ticket);
				if (!resp.isError()) {
					System.out.println("Ticket " + (i + 1) + " processed sucessfully. " + resp.getValidMessage());
					purchaser.addTicket(ticket);
				} else {
					System.err.println(resp.getErrorMessage());
				}
			}
		}
	}

	/**
	 * Can be purchased only before the day of the show. Otherwise it is nearly
	 * identical to the regular ticket, but with a small discount that is
	 * maintained within the ticket object.
	 */
	private void sellAdvancedTicket() {
		AdvanceTicket ticket;
		// Member id
		String memberID = getToken("What is your member ID?");
		Member purchaser = MemberList.getInstance().findMemberById(memberID);
		if (purchaser == null) {
			System.out.println(Response.NO_MEMBER_ERR_MSG);
			return;
		}
		
		// Credit card
		int ccNumber = getNumber("Which credit card number should we use?");
		CreditCard cc = purchaser.getCreditCardByNumber(ccNumber);
		if (cc == null) {
			System.out.println(Response.NO_MEMBER_CC_FOUND_ERR_MSG);
			return;
		}		
		
		// Show name
		String showName = getToken("Enter the name of the show");
		Show s = ClientList.getInstance().findShowFromName(showName);
		if (s == null) {
			System.out.println(Response.NO_SHOW_ERR_MSG);
			return;
		}
		Calendar showDate = getDate("Please enter the date of the show as mm/dd/yy");
		Show theShow = ClientList.getInstance().findShowFromName(showName);
		Calendar current = new GregorianCalendar();
		if (theater.advanceTicketCheck(current, showDate)) {
			int numTickets = (int) getDouble("How many Advance tickets would you like to purchase?");
			for (int i = 0; i < numTickets; i++) {
				ticket = new AdvanceTicket(showDate, theShow);
				Response resp = theater.sellTicket(ticket);
				if (!resp.isError()) {
					System.out.println("Ticket " + (i + 1) + " processed sucessfully." + resp.getValidMessage());
					purchaser.addTicket(ticket);
				} else {
					System.err.println(resp.getErrorMessage());
				}
			}
		}
	}

	/**
	 * Can be purchased on the day of the show only. Purchased at least a day in
	 * advance. Every ticket has a serial number, the date of the show, the type
	 * of ticket, and the ticket price.
	 * 
	 * The revenue from ticket sales for a show is divided equally between the
	 * theater and the client (the show�s producer). Thus, whenever a ticket
	 * is sold, you must update the balance for that client.
	 * 
	 * What we need: Show ID Date Number of tickets
	 */
	private void sellRegularTicket() {
		RegularTicket ticket;
		
		// Member id
		String memberID = getToken("What is your member ID?");
		Member purchaser = MemberList.getInstance().findMemberById(memberID);
		if (purchaser == null) {
			System.out.println(Response.NO_MEMBER_ERR_MSG);
			return;
		}
		
		// Credit card
		int ccNumber = getNumber("Which credit card number should we use?");
		CreditCard cc = purchaser.getCreditCardByNumber(ccNumber);
		if (cc == null) {
			System.out.println(Response.NO_MEMBER_CC_FOUND_ERR_MSG);
			return;
		}		
		
		// Show name
		String showName = getToken("Enter the name of the show");
		Show s = ClientList.getInstance().findShowFromName(showName);
		if (s == null) {
			System.out.println(Response.NO_SHOW_ERR_MSG);
			return;
		}
		Calendar showDate = getDate("Please enter the date of the show as mm/dd/yy");
		Show theShow = ClientList.getInstance().findShowFromName(showName);
		int numTickets = (int) getDouble("How many tickets would you like to purchase?");
		for (int i = 0; i < numTickets; i++) {
			ticket = new RegularTicket(showDate, theShow);
			Response resp = theater.sellTicket(ticket);
			if (!resp.isError()) {
				System.out.println("Ticket " + (i + 1) + " processed sucessfully." + resp.getValidMessage());
				purchaser.addTicket(ticket);
			} else {
				System.err.println(resp.getErrorMessage());
			}
		}
	}

	/**
	 * Calls the appropriate method for listing tickets for the designated day
	 */
	private void listTicketsForDay() {
		Calendar targetDate = getDate("Please enter the day you want to see ticket sales for as mm/dd/yy");
		System.out.println(MemberList.getInstance().listTicketsForDay(targetDate));
	}

	/**
	 * Launch the application. Saves before exiting.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		UserInterface ui = new UserInterface();
		ui.process();
		ui.save();
	}
}
