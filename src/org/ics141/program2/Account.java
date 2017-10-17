package org.ics141.program2;

import java.text.NumberFormat;

/**
 * A bank account is either a checking account or a savings account.
 * 
 * Each type of account has an owner name (represented by a string) and a
 * balance
 * 
 * The balance of the account begins as $0.00, and can increase or decrease in
 * well-defined ways.
 * 
 * Additionally, for a savings account (but not a checking account), you can
 * earn interest. If the interest rate is i (expressed as a fraction, so 1% =
 * 0.01, for instance), then if there was m amount of money in the account
 * before interest accrues, then the amount of money after crediting interest is
 * m(1+i). The interest rate is 6% for savings account. The interest and the tax
 * are calculated only once at the end of each run using the balance at the time
 * when calculation is performed.
 * 
 * @author mjdowell
 * 
 */
public abstract class Account {

	static NumberFormat CURRENCY_FORMATTER= NumberFormat.getCurrencyInstance();
	protected String name;
	protected double balance = 0.0;

	public Account(String name, double balance) {
		super();
		this.name = name;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * For any bank account, you can deposit a sum of money. When you deposit an
	 * amount of money m, the balance in the account goes up by m.
	 * 
	 * @param m
	 */
	public void deposit(double m) {
		balance +=m;
	}

	/**
	 * For any bank account, you can withdraw money; when you withdraw an amount
	 * n, the balance goes down by n.
	 * 
	 * If you implement this feature using a method, then the method should
	 * return the amount you actually received.
	 * 
	 * If you attempt to withdraw more money than is in the account, withdraw
	 * all the money in the account and set the balance to zero.
	 * 
	 * @param n
	 * @return
	 */
	public double withdraw(double n) {
		
		double theReturn = 0.0;
		
		if (balance <= n) {
			theReturn = balance;
			balance = 0.0;
			return theReturn;			
		}
		
		theReturn = n;
		balance = balance - n;
		
		return theReturn;
	}

	public abstract double getBalance();

	public String toString() {
		return "Name: " + name + "    Balance: " + balance;
	}
}