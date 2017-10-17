package org.ics141.program5;

import javax.swing.JFrame;

/**
 * Design and implement a program that displays a numeric keypad that may appear
 * on a phone. Above the keypad buttons, show a label that displays the numbers
 * as they are picked. To the right of the keypad buttons, include another
 * button to clear the display. You should use a frame to implement the outmost
 * window with the border layout to manage the overall layout;
 * 
 * <pre>
 *  - you may use three panels as organizers 
 *  - You can use one panel with the grid layout to manage the keypad buttons
 *  - put a border around the keypad buttons to group them visually
 *  - you can use another panel to organize the display label and put a border around the display
 *  - you can use the third panel to help layout the clear button
 * </pre>
 * 
 * @author mjdowell
 * 
 */
public class PhoneMain {

	/**
	 * This class's responsibilities are to call the builder, acquire the
	 * properly built Phone, set the location and make it visible.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		PhoneLayoutBuilder builder = new PhoneLayoutBuilder();
		JFrame mainFrame = builder.build();

		// Centers the frame
		mainFrame.setLocationRelativeTo(null);

		// Makes it visible
		mainFrame.setVisible(true);
	}

}
