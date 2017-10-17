package groupproj1.iteration2;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a generic response from the system that can be used to provide
 * information back to the UI as to whether the transaction has an ERROR or
 * whether there is further information to print.
 * 
 * @author Matt Dowell
 *
 */
public class Response {

	private static final Map<Integer, String> ERROR_STATES = new HashMap<Integer, String>(20);

	// Show
	public static final int NO_SHOW_FOUND = -1;
	public static final String NO_SHOW_ERR_MSG = "There was no show found for that name.";

	public static final int SHOW_SCHED_ERR = -2;
	public static final String SHOW_SCHED_ERR_MSG = "Sorry, those dates are unavailable. Scheduling conflict.";

	// Client
	public static final int NO_CLIENT_FOUND = -3;
	public static final String NO_CLIENT_ERR_MSG = "There was no client found with that show name.";

	// Member
	public static final int NO_MEMBER_FOUND = -4;
	public static final String NO_MEMBER_ERR_MSG = "There was no member found with that member id.";

	// Buying tickets
	public static final int INVALID_PURCHASE_DATE = -5;
	public static final String INVALID_PURCHASE_DATE_ERR_MSG = "That purchase date is not within the show date range.";
	public static final int NO_MEMBER_CC_FOUND = -8;
	public static final String NO_MEMBER_CC_FOUND_ERR_MSG = "There was no credit card found in your account, with that number.";	

	public static final int CANNOT_REM_CARD_ONE_MIN = -6;
	public static final String CANNOT_REM_CARD_ONE_MIN_ERR_MSG = "You cannot remove this credit card. A minimum of one is required.";

	public static final int SYSTEM_ERR = -7;
	public static final String SYSTEM_ERR_MSG = "Could not complete your request. Unknown system error.";

	private int errorState = 0;
	private String errorMessage;
	private boolean error = false;
	private int validState = 0;
	private String validMessage;

	public Response() {
		// ERROR STATES ARE NEGATIVE
		ERROR_STATES.put(NO_SHOW_FOUND, NO_SHOW_ERR_MSG);
		ERROR_STATES.put(SHOW_SCHED_ERR, SHOW_SCHED_ERR_MSG);
		ERROR_STATES.put(NO_CLIENT_FOUND, NO_CLIENT_ERR_MSG);
		ERROR_STATES.put(NO_MEMBER_FOUND, NO_MEMBER_ERR_MSG);
		ERROR_STATES.put(INVALID_PURCHASE_DATE, INVALID_PURCHASE_DATE_ERR_MSG);
		ERROR_STATES.put(CANNOT_REM_CARD_ONE_MIN, CANNOT_REM_CARD_ONE_MIN_ERR_MSG);
		ERROR_STATES.put(SYSTEM_ERR, SYSTEM_ERR_MSG);
		ERROR_STATES.put(NO_MEMBER_CC_FOUND, NO_MEMBER_CC_FOUND_ERR_MSG);

	}

	/**
	 * Returns the int representing the errorState
	 * @return errorState
	 */
	public int getErrorState() {
		return errorState;
	}

	/**
	 * Sets the state of the error
	 * and sets the error boolean
	 * @param errorState
	 */
	public void setErrorState(int errorState) {
		this.errorState = errorState;
		this.error = true;
		determineMessage();
	}

	/**
	 * Returns the boolean error
	 * @return error
	 */
	public boolean isError() {
		return error;
	}

	/**
	 * If the user does not want to set the error message, we can handle the
	 * defaults here.
	 */
	private void determineMessage() {
		if (errorMessage != null) {
			return;
		}

		errorMessage = ERROR_STATES.get(errorState);
	}

	/**
	 * Returns the errorMessage
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the errorMessage
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Returns validState
	 * @return validState
	 */
	public int getValidState() {
		return validState;
	}

	/**
	 * Sets the valid state
	 * @param validState
	 */
	public void setValidState(int validState) {
		this.validState = validState;
	}

	/**
	 * Returns the valid message
	 * @return validMessage
	 */
	public String getValidMessage() {
		return validMessage;
	}

	/**
	 * Sets the validMessage
	 * @param validMessage
	 */
	public void setValidMessage(String validMessage) {
		this.validMessage = validMessage;
	}

}
