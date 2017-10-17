package org.ics141.program1;

/**
 * Specific monetary coin implementation of the Coin parent class
 * 
 * The MonetaryCoin will have an extra instance variable called value to store
 * the value of a coin.
 * 
 * The variable value is the type of integer and has the range of 0-10.
 * 
 * The derived class should also have an extra method getValue() that returns
 * the value. T
 * 
 * he new class should have two constructors: one is the no-argument constructor
 * MonetaryCoin(), the other takes one parameter aValue of integer that is used
 * to initialize the instance variable value.
 * 
 * According to good practice, in the body of constructor, the first line should
 * call its super class’ no-arg constructor.
 * 
 * @author mjdowell
 * 
 */
public class MonetaryCoin extends Coin {
	
	private int value;

	
	public MonetaryCoin() {
		super();
	}


	public MonetaryCoin(int value) {
		super();
		this.value = value;
	}


	public int getValue() {
		return value;
	}


	/**
	 * The specification for the new toString() is:
	 * 
	 * Returns: a string that takes one of the following value:
	 * 
	 * If the face is 0, value is x (here it represents an integer in the range 0..10).
	 * The returned string should be “The coin has head and value is x.”
	 * 
	 * If the face is 1, value is x, The returned string should be 
	 * “The coin has tail and value is x.”
	 */
	public String toString() {
		return "The coin has " + super.toString() + " and the value is " + getValue();
	}
}
