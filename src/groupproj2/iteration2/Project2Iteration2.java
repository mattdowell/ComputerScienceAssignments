package groupproj2.iteration2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * This is the main class of Project 2. It should only have a Main method that
 * instantiates our GUI.
 * 
 * @author Matt
 *
 */
public class Project2Iteration2 {

	public static void main(String args[]) {

		try {

			String file = null;

			// Remove before submitting?
			if (args.length == 0) {
				System.out.println("No config file supplied as argument. Using default");
				// Use default
				file = "refridge1.conf";
			} else {
				file = args[0];
				System.out.println("Using File: " + file);
			}

			// /////////////////////////
			// Read the config
			final RefridgeratorSystem conf = FileReaderUtil.readFromFile(file);
			conf.setInitialState();
			// ////////////////////////

			// //////////////////////////
			// Create the GUI
			JFrame frame = new JFrame("Project2Iteration1");
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			SwingUserInterface gui = new SwingUserInterface(conf);
			frame.add(gui);
			frame.pack();
			frame.setVisible(true);
			// //////////////////////////

			////////////////////////////////
			///  BUILD LISTENERS 
			List<RefridgeratorStateChangeListener> listeners = new ArrayList<>(2);
			listeners.add(StateContext.getInstance());
			listeners.add(gui);

			
			// ///////////////////////
			// Start the clock
			SystemClock clock = new SystemClock(listeners, conf);
			clock.run();
			// //////////////////////

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
