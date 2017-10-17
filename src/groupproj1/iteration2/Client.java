package groupproj1.iteration2;

import java.io.*;
import java.util.*;

/**
 * A Client is a "Producer" It represents someone that can add shows to our
 * theater.
 * 
 * @author Andre
 *
 */
public class Client implements Serializable, Matchable<String> {

	private static final long serialVersionUID = -9162484270274718397L;
	private String name;
	private String address;
	private String phoneNumber;
	private String clientID;
	private double balance;
	private static final String CLIENT_STRING = "C";
	private List<Show> showList = new ArrayList<Show>();

	/**
	 * Create a new client with parameters.
	 * 
	 * @param clientName
	 *            Name of the client
	 * @param clientAddress
	 *            Address of the client
	 * @param clientPhoneNumber
	 *            Clients Phone number
	 */
	public Client(String clientName, String clientAddress, String clientPhoneNumber) {
		name = clientName;
		address = clientAddress;
		phoneNumber = clientPhoneNumber;
		balance = 0;
		clientID = CLIENT_STRING + (IdServer.getInstance()).getClientId();
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * 
	 * @param newShow
	 *            The show to be added
	 * @return returns true if the show is successfully added and false if it
	 *         wasn't
	 */
	public boolean addShow(Show newShow) {
		if (showList.add(newShow)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the clients ID
	 * 
	 * @return the client ID
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * Gets the clients list of shows
	 * 
	 * @return the list of shows
	 */
	public List<Show> getShows() {
		return showList;
	}

	/**
	 * Adds ONE HALF the given price of a ticket sold to the Clients balance.
	 * 
	 * @param price
	 */
	public void addToBalance(AbstractTicket inTicketSold) {
		this.balance += (inTicketSold.getPrice() / 2);
	}

	/**
	 * for dictating how payment would be received. When a client is paid (via
	 * check) their balance (owed) is reduced by this payment.
	 * 
	 * @param payment
	 */
	public void recievePayment(double payment) {
		balance -= payment;
	}

	/**
	 * Returns the clients balance
	 * 
	 * @return double
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * To implement the Matchable interface
	 * 
	 * @param key
	 *            the client id
	 */
	@Override
	public boolean matches(String key) {
		return clientID.equals(key);
	}

	/**
	 * Generates a String that represents the object
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Client [name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + ", clientID="
				+ clientID + ", balance=" + balance + ", showList=" + showList + "]";
	}

}
