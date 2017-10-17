package groupproj1.iteration1;

import java.io.IOException;
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
	private static IdServer server;
	private int ClientIDCounter;
	private int MemberIDCounter;

	/**
	 * Private constructor to support the singleton pattern
	 */
	private IdServer() {
		ClientIDCounter = 1;
		MemberIDCounter = 1;
	}

	/**
	 * Return the singleton instance of this object.
	 * @return the singleton
	 */
	public static IdServer getInstance() {
		if (server == null) {
			return (server = new IdServer());
		} else {
			return server;
		}
	}

	/**
	 * Gets a client ID and increments the counter
	 * @return ClientIDCounter the client ID
	 */
	public int getClientId() {
		return ClientIDCounter++;
	}

	/**
	 * Gets a member ID and increments the counter
	 * @return MemberIDCounter the member ID
	 */
	public int getMemberId() {
		return MemberIDCounter++;
	}

	/**
	 * Writes this object to the given output stream.
	 * @param output
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream output) throws IOException {
		try {
			output.defaultWriteObject();
			output.writeObject(server);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Reads this object to the given input stream.
	 * @param input
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream input) throws IOException, ClassNotFoundException {
		try {
			input.defaultReadObject();
			if (server == null) {
				server = (IdServer) input.readObject();
			} else {
				input.readObject();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
