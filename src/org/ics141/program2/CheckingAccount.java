package org.ics141.program2;

/**
 * Non-taxable, nor interest bearing account.
 * 
 * @author mjdowell
 * 
 */
public class CheckingAccount extends Account {

	public CheckingAccount(String name, double balance) {
		super(name, balance);
	}

	@Override
	public double getBalance() {
		return balance;
	}

}
