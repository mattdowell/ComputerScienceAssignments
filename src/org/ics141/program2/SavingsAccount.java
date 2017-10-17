/**
 * 
 */
package org.ics141.program2;

/**
 * @author mjdowell
 * 
 */
public class SavingsAccount extends Account implements TaxableEntity {

	public SavingsAccount(String name, double balance) {
		super(name, balance);
	}

	protected static final double INTERESTRATE = 0.06;

	/**
	 * 
	 * When calculate the final balance for a savings account, you should
	 * 
	 * 1) include interest earned first and then 2) have the tax deducted.
	 * 
	 */
	@Override
	public double getBalance() {
		return balance;
	}

	/**
	 * For a savings account you can earn interest.
	 * 
	 * If the interest rate is i (expressed as a fraction, so 1%= 0.01, for
	 * instance), then if there was m amount of money in the account before
	 * interest accrues, then the amount of money after crediting interest is
	 * m(1+i).
	 * 
	 * The interest rate is 6% for savings account. The interest and the tax are
	 * calculated only once at the end of each run using the balance at the time
	 * when calculation is performed.
	 * 
	 * @return return a double value that represents the interest earned, this
	 *         method should not change the balance.
	 */
	public double interestAmount() {
		return balance * INTERESTRATE;
	}

	/**
	 * Savings accounts are a form of Taxable Entity. Taxable Entities have a
	 * fixed tax rate (20% of interest earned, not 20% of the total).
	 * 
	 * @return a double value that represents the tax amount, this method should
	 *         not change the balance.
	 */
	public double taxAmount() {
		return interestAmount() * TAXRATE;
	}

	/**
	 * The toString() method in the SavingsAccount class is polymorphic. It
	 * overrides the toString() method in its parent class Account. You are
	 * required to write the method in such a way that the output of the balance
	 * field by this method is formatted currency number with a prefix dollar
	 * sign followed by a double number with two digits after the decimal point;
	 * the value of the balance should be the new value that reflects the
	 * interest and the tax.
	 * 
	 * Updates the balance with the interest/tax amount then outputs
	 * 
	 * @return formatted currency number with a prefix dollar sign followed by a
	 *         double number with two digits after the decimal point
	 */
	public String toString() {
		
		double newBalance = balance;
		
		// Add the interest amount
		newBalance += interestAmount();
		
		// Remove the tax amount
		newBalance -= taxAmount();
		
		// We have to do this here so we don't mess up the balance after the interest amount call
		balance = newBalance;
		
		// Format and return the new balance
		return CURRENCY_FORMATTER.format(balance);
	}

}
