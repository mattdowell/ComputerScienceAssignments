package groupproj1.iteration2;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * This class is responsible for maintaining and handling Member classes. 
 * It holds on to the data and provides method access to the full Member roster.
 * It is a singleton.
 * 
 * @author Matt
 *
 */
public class MemberList extends ItemList<Member, String> implements Serializable {

	private static final long serialVersionUID = 508806469272075306L;
	private static MemberList memberList = null;

	/**
	 * Private constructor to support singleton pattern
	 */
	private MemberList() {
	}

	/**
	 * returns the static memberList
	 * @return INSTANCE
	 */
	public static MemberList getInstance() {
		if (memberList == null) {
			memberList = new MemberList();
		}
		return memberList;
	}

	/**
	 * Used for setting instance
	 * @param inFromFile  the memberList that will replace the current list
	 */
	public static void setInstance(MemberList inFromFile) {
		memberList = inFromFile;
	}

	/**
	 * Return the full list of members.
	 * @return memberList
	 */
	public List<Member> getAllMembers() {
		return super.getList();
	}

	/**
	 * Sets the unique ID on the member then adds it to the list
	 * @param inMember
	 */
	public boolean addMember(Member inMember) {
		return super.add(inMember);
	}

	/**
	 * Finds a member by the given id.
	 * @param inMemberId
	 * @return Returns the member if it is found, otherwise null
	 */
	public Member findMemberById(String inMemberId) {
		return super.search(inMemberId);
	}

	/**
	 * Ensures there is not already a credit card that equals the one being added.
	 * 
	 * @param cc The card we want to make sure is not a duplicate
	 * @return true if there are no duplicates, false if there are duplicates
	 */
	public boolean ensureNoDuplicates(CreditCard cc) {
		Iterator<Member> memberIterator = super.iterator();
		Iterator<CreditCard> cardIterator;

		while (memberIterator.hasNext()) {
			cardIterator = memberIterator.next().getCreditCards().iterator();
			while (cardIterator.hasNext()) {
				if (cardIterator.next().equals(cc)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Removes a member from the list using the given ID
	 * @param inMemberId
	 * @return true if the member is successfully removed
	 * false if the member is not removed
	 */
	public boolean removeMember(String inMemberId) {
		Member mem = findMemberById(inMemberId);
		boolean theReturn = super.remove(mem);
		return theReturn;
	}

	/**
	 *  Checks for all tickets purchased that are set for a certain day
	 * @param targetDate  The day you want the tickets for
	 * @return  A string representing all of the qualifying tickets
	 */
	public String listTicketsForDay(Calendar targetDate) {
		String returnString = "";
		Iterator<Member> memberIterator = memberList.iterator();
		while (memberIterator.hasNext()) {
			Member tempMember = memberIterator.next();
			Iterator<AbstractTicket> ticketIterator = tempMember.getTickets().iterator();
			while (ticketIterator.hasNext()) {
				AbstractTicket tempTicket = ticketIterator.next();
				if (tempTicket.matchesDate(targetDate)) {
					returnString = returnString + tempTicket.toString() + "\n";
				}
			}
		}
		return returnString;
	}

}