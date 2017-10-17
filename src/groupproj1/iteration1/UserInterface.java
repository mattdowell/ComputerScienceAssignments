package groupproj1.iteration1;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Provides an interface for the user. This interface is command-line driven.
 * It is a singleton.
 * The commands are encoded as integers using a number of
 * static final variables. A number of utility methods exist to
 * make it easier to parse the input.
 * @author Matt, Andre
 *
 */
public class UserInterface {

	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Theater theater;
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
	private static final int HELP = 13;

	/**
	 * Private for singleton pattern
	 * Gets a singleton theater object 
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			System.out.println("Retrieving Data has not been implemented");
			theater = Theater.getInstance();
		} else {
			theater = Theater.getInstance();
		}
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			userInterface = new UserInterface();
		}
		return userInterface;
	}

	/**
	 * Gets the value the user enters after prompting
	 * 
	 * @param prompt The string that will be displayed to the user
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
	 * @param prompt The string to be prepended to the yes/no prompt
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
	 * Converts the string to a number
	 * @param prompt The string displayed to the user
	 * @return The integer corresponding to the string
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Gets a date using the format mm/dd/yy
	 * 
	 * @param prompt the prompt
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
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prints a prompt for the user and waits for a number menu command.
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
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Method that adds a client
	 * Prompts the user for values and
	 * uses the theater method to add the client
	 */
	public void addClient() {
		String name = getToken("Enter client name");
		String address = getToken("Enter address");
		String phone = getToken("Enter phone");
		if (theater.addClient(name, address, phone)) {
			System.out.println("Client succesfully added");
		} else {
			System.out.println("Client was not added");
		}
	}

	/**
	 * Method that removes a client
	 * Gets all information from the user to remove a client.
	 * uses the appropriate theater method to remove the client
	 */
	public void removeClient() {
		String clientID = getToken("Enter Client ID for the Client you want removed");
		if (theater.removeClient(clientID)) {
			System.out.println("Removed Client");
		} else {
			System.out.println("Unable to remove Client");
		}
	}

	/**
	 * Lists all clients.
	 */
	public void listClients() {
		theater.listClients();
	}

	/**
	 * Method that adds a member
	 * Gets all information from the user to add a member.
	 * Uses the appropriate theater method to add the member
	 */
	public void addMember() {
		String memberName = getToken("Enter the name of the new member");
		int cardNumber = getNumber("Enter the credit card number");
		Calendar expirationDate = getDate("Enter the expiration date of the card");
		if (theater.addMember(memberName, cardNumber, expirationDate)) {
			System.out.println("Added Member");
		} else {
			System.out.println("Could not add member");
		}
	}

	/**
	 * Method that removes members
	 * Gets all information to remove a member.
	 * Uses the appropriate theater method to remove the member
	 */
	public void removeMember() {
		String memberID = getToken("Enter Member ID for the Member you want removed");
		if (theater.removeMember(memberID)) {
			System.out.println("Removed Member");
		} else {
			System.out.println("Unable to remove Member");
		}
	}

	/**
	 * Method that adds a credit card
	 * Gets all information to remove a card
	 * Uses the appropriate theater method to add the card
	 */
	public void addCreditCard() {
		String memberID = getToken("Enter the name of the member with the new card");
		int cardNumber = getNumber("Enter the credit card number");
		Calendar expirationDate = getDate("Enter the expiration date of the card");
		if (theater.addMemberCreditCard(memberID, cardNumber, expirationDate)) {
			System.out.println("Added Credit Card");
		} else {
			System.out.println("Could not add credit card");
		}
	}

	/**
	 * Method to remove a credit card.
	 * Gets all necessary information from the user.
	 * Uses the appropriate theater method to remove the card
	 */
	public void removeCreditCard() {
		String memberID = getToken("Enter the name of the member whose card is being removed");
		int cardNumber = getNumber("Enter the credit card number");
		if (theater.removeMemberCreditCard(memberID, cardNumber)) {
			System.out.println("Removed Credit Card");
		} else {
			System.out.println("Could not remove credit card");
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
	 * Method to add a show.
	 * Gets all necessary information.
	 * Calls the appropriate theater method.
	 */
	public void addShow() {
		String clientID = getToken("Enter the client's ID");
		String showName = getToken("Enter the name of the show");
		Calendar startDate = getDate("Please enter the date the play starts as mm/dd/yy");
		Calendar endDate = getDate("Please enter the date the play ends as mm/dd/yy");
		if (theater.addShow(clientID, showName, startDate, endDate)) {
			System.out.println("Show was added");
		} else {
			System.out.println("Unable to add show");
		}
	}

	/**
	 * Lists all shows.
	 */
	public void listShows() {
		theater.listShows();
	}

	/**
	 * Method to save data.
	 * Calls the appropriate theater method.
	 */
	public void save() {
		if (theater.save()) {
			System.out.println(" The library has been successfully saved in the file TheaterData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Does not need to be implemented
	 */
	public void load() {
		System.out.println("Retrieving Data has not been implemented");
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
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * Launch the application. Saves before exiting.
	 * @param args
	 */
	public static void main(String args[]) {
		UserInterface.instance().process();
		Theater.save();
	}
}
