package groupproj1.iteration1;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * A Client is a "Producer" It represents someone that can add shows to our theater.
 * 
 * @author Andre
 *
 */
public class Client implements Serializable {

	private String name;
	private String address;
	private String phoneNumber;
	private String clientID;
	private double balance;
	private static final String CLIENT_STRING = "C";
	private LinkedList<Show> showList = new LinkedList<Show>();

	/**
	 * Create a new client with parameters.
	 * 
	 * @param clientName Name of the client
	 * @param clientAddress Address of the client
	 * @param clientPhoneNumber  Clients Phone number
	 */
	public Client(String clientName, String clientAddress, String clientPhoneNumber) {
		name = clientName;
		address = clientAddress;
		phoneNumber = clientPhoneNumber;
		balance = 0;
		clientID = CLIENT_STRING + (IdServer.getInstance()).getClientId();
	}

	/**
	 * 
	 * @param newShow The show to be added
	 * @return returns true if the show is successfully added
	 * and false if it wasn't
	 */
	public boolean addShow(Show newShow) {
		if (showList.add(newShow)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the clients ID
	 * @return the client ID
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * Gets the clients list of shows
	 * @return the list of shows
	 */
	public LinkedList<Show> getShows() {
		return showList;
	}

	/**
	 * Displays the client as a string
	 */
	@Override
	public String toString() {
		return "Client [name=" + name + ", address=" + address + ", phoneNumber=" + 
				phoneNumber + ", clientID=" + clientID + ", balance=" + balance + 
				", showList=" + showList + "]";
	}

}
