package org.ics141.program2;

/**
 * Savings accounts are a form of Taxable Entity. Taxable Entities have a fixed
 * tax rate (20% of interest earned, not 20% of the total). You should model
 * this entity as a Java interface named “TaxableEntity.” This interface
 * contains just one constant: TAXRATE = 0.20. When calculate the final balance
 * for a savings account, you should include interest earned first and then have
 * the tax deducted.
 * 
 * @author mjdowell
 * 
 */
public interface TaxableEntity {

	public static final double TAXRATE = 0.20;

}
