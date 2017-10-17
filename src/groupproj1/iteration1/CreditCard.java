package groupproj1.iteration1;

import java.io.Serializable;
import java.util.Calendar;

/**
 * A credit card that is owned by a Member
 * 
 * @author Matt
 *
 */
public class CreditCard implements Serializable {

	private static final long serialVersionUID = -3192738526213882798L;
	private int number;
	private Calendar expirationDate;

	/**
	 * Sets the credit card number
	 * @param number The new credit card Number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Gets the expiration date of the Credit Card
	 * @return the expiration date
	 */
	public Calendar getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the expiration date of the Credit Card
	 * @param expirationDate the new expiration date
	 */
	public void setExpirationDate(Calendar expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Gets the credit cards Number
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Converts a CreditCard into a string
	 */
	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", expirationDate=" + 
				(expirationDate.get(Calendar.MONTH) + 1) + "/" + expirationDate.get(Calendar.DATE) 
				+ "/" + expirationDate.get(Calendar.YEAR) + "]";
	}

	/**
	 * Checks for equivalence relation between this Credit Card
	 * and the passed Object
	 * @param obj the object being compared to this CreditCard
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

}
