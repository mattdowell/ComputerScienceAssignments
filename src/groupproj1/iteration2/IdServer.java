package groupproj1.iteration2;

import java.io.Serializable;

/**
 * This IDServer provides unique (and incremented) id's for Clients and Members.
 * It is a singleton.
 * 
 * @author Andre
 *
 */
public class IdServer implements Serializable {

	private static final long serialVersionUID = -2950672258542885733L;
	private static IdServer server = null;
	private int clientIDCounter;
	private int memberIDCounter;
	private int ticketIdCounter;

	/**
	 * Private constructor to support the singleton pattern
	 */
	private IdServer() {
		//System.out.println("Creating new instance of ID Server!");
		clientIDCounter = 1;
		memberIDCounter = 1;
		ticketIdCounter = 1;
	}

	/**
	 * Return the singleton instance of this object.
	 * 
	 * @return the singleton
	 */
	public static IdServer getInstance() {
		if (server == null) {
			server = new IdServer();
		}
		return server;
	}

	/**
	 * Sets the Id Server instance to equal the passed IdServer
	 * @param inInstance
	 */
	public static void setInstance(IdServer inInstance) {
		server = inInstance;
	}

	/**
	 * Gets a client ID and increments the counter
	 * 
	 * @return ClientIDCounter the client ID
	 */
	public int getClientId() {
		return clientIDCounter++;
	}

	/**
	 * Gets a member ID and increments the counter
	 * 
	 * @return MemberIDCounter the member ID
	 */
	public int getMemberId() {
		return memberIDCounter++;
	}

	/**
	 * @return a unique Ticket serial number
	 */
	public int getTicketId() {
		return ticketIdCounter++;
	}
}
