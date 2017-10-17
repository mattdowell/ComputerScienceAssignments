package groupproj1.iteration2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A client list holds on to, and provides access to all clients through various
 * methods of searching, etc. IT is a singleton.
 * 
 * @author Andre
 *
 */
public class ClientList extends ItemList<Client, String> implements Serializable {

	private static final long serialVersionUID = 4830338115739449946L;
	private static ClientList clientList = null;

	/**
	 * Private constructor for singleton
	 */
	private ClientList() {
		//System.out.println("\n\nCreating new ClientList instance!");
	}

	/**
	 * Returns the static ClientList
	 * 
	 * @return static variable clientList
	 */
	public static ClientList getInstance() {
		if (clientList == null) {
			clientList = new ClientList();
		}
		return clientList;
	}
	public static void setInstance(ClientList inFromFile) {
		clientList = inFromFile;
	}

	/**
	 * Adds a client to the clientlist
	 * 
	 * @param client
	 *            to be added
	 * @return true if the client is successfully added and false if they
	 *         weren't added
	 */
	public boolean addClient(Client client) {
		return super.add(client);
	}

	/**
	 * Removes a client with the given String id.
	 * 
	 * @param clientID
	 * @return true if the client is successfully added and false if they
	 *         weren't added
	 */
	public boolean removeClientById(String clientID) {
		Iterator<Show> showIterator = findClientById(clientID).getShows().iterator();
		while (showIterator.hasNext()) {
			if (System.currentTimeMillis() <= showIterator.next().getEndDate().getTimeInMillis()) {
				return false;
			}
		}
		return super.remove(findClientById(clientID));
	}

	/**
	 * Finds a client using the given id.
	 * 
	 * @param clientID
	 * @return the client if it exists, otherwise null
	 */
	public Client findClientById(String clientID) {
		return super.search(clientID);
	}

	/**
	 * Returns a list of all client shows
	 * @return
	 */
	public List<Show> getAllClientShows() {
		List<Show> theReturn = new ArrayList<Show>();
		List<Client> clients = this.getAllClients();
		for (Client c : clients) {
			List<Show> shows = c.getShows();
			for (Show s : shows) {
				theReturn.add(s);
			}
		}
		return theReturn;
	}

	/**
	 * @return List of clients
	 */
	public List<Client> getAllClients() {
		return super.getList();
	}

	/**
	 * Search through all the clients' shows and find a show 
	 * with the same name;
	 * @param inName
	 * @return Show if found, null otherwise
	 */
	public Show findShowFromName(String inName) {
		Show theReturn = null;
		List<Show> shows = this.getAllClientShows();

		for (Show show : shows) {
			if (show.getName().equalsIgnoreCase(inName)) {
				theReturn = show;
				break;
			}
		}
		return theReturn;
	}

	/**
	 * Finds the Producer for the given show name;
	 * @param inShowName
	 * @return
	 */
	public Client findClientFromShowName(String inShowName) {
		Client theReturn = null;
		for (Client client : getAllClients()) {
			List<Show> shows = client.getShows();

			for (Show show : shows) {
				if (show.getName().equalsIgnoreCase(inShowName)) {
					theReturn = client;
					break;
				}
			}
		}
		return theReturn;
	}
}
