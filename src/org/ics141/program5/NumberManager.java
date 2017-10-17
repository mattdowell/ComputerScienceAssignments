package org.ics141.program5;

import javax.swing.JLabel;

import org.ics141.program5.EventBus.EventValue;

/**
 * This class is responsible for setting/managing the display values for the phone number
 * display.
 * 
 * @author Matt
 * 
 */
public class NumberManager {

	private JLabel myLabel;

	/**
	 * The constructor takes the label that will be used for output
	 * @param inLabel
	 */
	public NumberManager(JLabel inLabel) {
		myLabel = inLabel;
	}

	
	public JLabel getLabel() {
		return myLabel;
	}


	/**
	 * This method takes the value of the number pressed. The value does not
	 * necessarily hold the values that we want to display. The value-to-acton
	 * mapping is hardcoded in the EventValues enum.
	 * 
	 * @param inVal
	 */
	public void buttonPressed(EventValue inVal) {
		if (inVal.equals(EventValue.Clear)) {
			myLabel.setText("");
		} else {
			myLabel.setText(myLabel.getText() + inVal.getCode());
		}
	}
}
