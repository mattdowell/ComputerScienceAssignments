package groupproj1.iteration1;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A client list holds on to, and provides access to all clients through various methods of searching, etc.
 * IT is a singleton.
 * 
 * @author Andre
 *
 */
public class ClientList implements Serializable {

	private static final long serialVersionUID = 4830338115739449946L;
	private static ClientList clientList;
	private LinkedList<Client> clients = new LinkedList<Client>();

	/**
	 * Private constructor for singleton
	 */
	private ClientList() {
	}

	/**
	 * Returns the static ClientList
	 * @return static variable clientList
	 */
	public static ClientList instance() {
		if (clientList == null) {
			clientList = new ClientList();
		}
		return clientList;

	}

	/**
	 * Adds a client to the clientlist
	 * @param client to be added
	 * @return true if the client is successfully added
	 * and false if they weren't added
	 */
	public boolean addClient(Client client) {
		return clients.add(client);
	}

	/**
	 * Removes a client wit the given String id.
	 * @param clientID
	 * @return true if the client is successfully added
	 * and false if they weren't added
	 */
	public boolean removeClient(String clientID) {
		Iterator<Show> showIterator = findClient(clientID).getShows().iterator();
		while (showIterator.hasNext()) {
			if (System.currentTimeMillis() <= showIterator.next().getEndDate().getTimeInMillis()) {
				return false;
			}
		}
		return clients.remove(findClient(clientID));
	}

	/**
	 * Finds a client using the given id.
	 * @param clientID
	 * @return the client if it exists, otherwise null
	 */
	public Client findClient(String clientID) {
		Iterator<Client> clientsIterator = clients.iterator();
		while (clientsIterator.hasNext()) {
			Client cursor = clientsIterator.next();
			if (cursor.getClientID().equals(clientID)) {
				return cursor;
			}
		}
		return null;
	}

	/**
	 * Lists all the shows that all clients have.
	 */
	public void listClientShows() {
		Iterator<Client> clientsIterator = clients.listIterator();
		Iterator<Show> showIterator;

		while (clientsIterator.hasNext()) {
			showIterator = clientsIterator.next().getShows().listIterator();
			while (showIterator.hasNext()) {
				showIterator.next().display();
			}
		}
	}

	/**
	 * Checks all existing shows to make sure the passed show
	 * doesn't conflict with their runs
	 * @param myShow the new show that may conflict
	 * @return true if there are no conflicts,
	 * false if there is a conflict
	 */
	public boolean checkIfDateAvailable(Show myShow) {
		Iterator<Client> clientsIterator = clients.listIterator();
		Iterator<Show> showIterator;
		boolean theResult = true;
		Show cursor = null;

		// Ensures that if the new show starts before another show, it also ends before the next show
		while ((clientsIterator.hasNext()) && (theResult == true)) {
			showIterator = clientsIterator.next().getShows().listIterator();
			while (showIterator.hasNext()) {
				cursor = showIterator.next();
				if (myShow.getStartDate().getTimeInMillis() < cursor.getStartDate().getTimeInMillis()) {
					if (myShow.getEndDate().getTimeInMillis() > cursor.getStartDate().getTimeInMillis()) {
						theResult = false;
					}
				}
			}
		}

		clientsIterator = clients.listIterator();

		// Ensures that a show doesn't start during another shows run
		while ((clientsIterator.hasNext()) && (theResult == true)) {
			showIterator = clientsIterator.next().getShows().listIterator();
			while (showIterator.hasNext()) {
				cursor = showIterator.next();
				if (myShow.getStartDate().getTimeInMillis() > cursor.getStartDate().getTimeInMillis()) {
					if (myShow.getStartDate().getTimeInMillis() < cursor.getEndDate().getTimeInMillis()) {
						theResult = false;
					}
				}
			}
		}
		return theResult;
	}

	/**
	 * Displays all clients
	 */
	public void displayClients() {
		Iterator<Client> clientsIterator = clients.listIterator();
		System.out.println("ClientList:");
		while (clientsIterator.hasNext()) {
			System.out.println(clientsIterator.next());
		}
	}
}
