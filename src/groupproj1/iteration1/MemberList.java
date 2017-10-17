package groupproj1.iteration1;

import java.io.Serializable;
import java.util.ArrayList;
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
public class MemberList implements Serializable {

	private List<Member> memberList = new ArrayList<Member>();
	private static final MemberList INSTANCE = new MemberList();

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
		return INSTANCE;
	}

	/**
	 * Return the full list of members.
	 * @return memberList
	 */
	public List<Member> getAllMembers() {
		return memberList;
	}

	/**
	 * Sets the unique ID on the member then adds it to the list
	 * @param inMember
	 */
	public boolean addMember(Member inMember) {
		inMember.setId(IdServer.getInstance().getMemberId());
		return memberList.add(inMember);
	}

	/**
	 * Finds a member by the given id.
	 * @param inMemberId
	 * @return Returns the member if it is found, otherwise null
	 */
	public Member findMemberById(String inMemberId) {
		Member theReturn = null;
		for (Member mem : memberList) {
			if (inMemberId.equals(mem.getId())) {
				theReturn = mem;
				break;
			}
		}
		return theReturn;
	}

	/**
	 * Ensures there is not already a credit card that equals the one being added.
	 * 
	 * @param cc The card we want to make sure is not a duplicate
	 * @return true if there are no duplicates, false if there are duplicates
	 */
	public boolean ensureNoDuplicates(CreditCard cc) {
		Iterator<Member> memberIterator = memberList.iterator();
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
		boolean theReturn = memberList.remove(mem);
		return theReturn;

	}

}