package org.ics141.program5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicBorders;

import org.ics141.program5.EventBus.EventValue;

/**
 * This class builds the parent level frame, and all of its components.
 * 
 * A Frame is a top-level window with a title and a border. The size of the
 * frame includes any area designated for the border. The dimensions of the
 * border area may be obtained using the getInsets method. Since the border area
 * is included in the overall size of the frame, the border effectively obscures
 * a portion of the frame, constraining the area available for rendering and/or
 * displaying subcomponents to the rectangle which has an upper-left corner
 * location of (insets.left, insets.top), and has a size of width - (insets.left
 * + insets.right) by height - (insets.top + insets.bottom).
 * 
 * @author Matt
 * 
 */
public class PhoneLayoutBuilder {

	private NumberManager myNumberManager;
	private EventBus myEventBus;


	public PhoneLayoutBuilder() {

		// Build the output label
		JLabel myOutputLabel = new JLabel("     ", JLabel.CENTER);
		myOutputLabel.setVerticalTextPosition(JLabel.BOTTOM);
		myOutputLabel.setHorizontalTextPosition(JLabel.CENTER);
		myOutputLabel.setSize(new Dimension(300, 40));
		myOutputLabel.setMaximumSize(new Dimension(300, 40));
		myOutputLabel.setMinimumSize(new Dimension(300, 40));
		myOutputLabel.setBorder(BasicBorders.getTextFieldBorder());
		myOutputLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 22));
		
		// Build the NumberManager
		myNumberManager = new NumberManager(myOutputLabel);

		// Build the EventBus
		myEventBus = new EventBus(myNumberManager);
	}

	/**
	 * This is the method any outside calling classes must call. This method
	 * returns a fully-build phone layout
	 * 
	 * @return
	 */
	public JFrame build() {

		JFrame frame = new JFrame("Phone");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(buildMainPanel());
		frame.setResizable(false);
		frame.pack();
		return frame;
	}

	/**
	 * The main panel will have two zones, north and south The southern panel
	 * will have two zones, east and west
	 * 
	 * @return
	 */
	private JPanel buildMainPanel() {
		class BorderPanel extends JPanel {
			public BorderPanel() {
				setLayout(new BorderLayout());
				setBackground(Color.black);
				add(buildNumberDisplayPanel(), BorderLayout.NORTH);
				add(buildSouthernButtonsPanel(), BorderLayout.SOUTH);
			}
		}
		return new BorderPanel();
	}

	/**
	 * This panel will have two "zones" a west and east zone. The west zone will
	 * contain the phone dialing pad while the east zone had a "clear button"
	 * 
	 * @return
	 */
	private Component buildSouthernButtonsPanel() {
		class BorderPanel extends JPanel {
			public BorderPanel() {
				setLayout(new BorderLayout());
				setBackground(Color.black);
				add(buildNumberDialPad(), BorderLayout.WEST);
				add(buildClearButtonPanel(), BorderLayout.EAST);
			}
		}
		return new BorderPanel();
	}

	/**
	 * This panel oriented with a single button in the middle with the text
	 * "Clear"
	 * 
	 * @return
	 */
	private Component buildClearButtonPanel() {
		class P extends JPanel {
			public P() {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				setPreferredSize(new Dimension(110, 200));
				
				// Build and add the button
				JButton jButt = new JButton("Clear");
				jButt.setPreferredSize(new Dimension(80, 30));
				jButt.setMaximumSize(new Dimension(80, 30));
				
				EventValue theVal = getEventValue("Clear");
				jButt.addActionListener(myEventBus);
				myEventBus.addSourceMapping(jButt, theVal);
				jButt.setHorizontalAlignment(SwingConstants.CENTER);
				jButt.setVerticalAlignment(SwingConstants.CENTER);
				
				add(Box.createRigidArea(new Dimension(0,80)));
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
				buttonPane.add(jButt);
				add(buttonPane);
			}

		}
		return new P();
	}

	/**
	 * This component must have numbers 0-9 orientented in a standard phone
	 * layout. On the bottom row, surrounding the "0" will be * on the left, and
	 * # on the right. The number buttons start with 1 in the upper left and
	 * descend to 9 on the lower right.
	 * 
	 * The dial-pad must have a border around it.
	 * 
	 * @return
	 */
	private Component buildNumberDialPad() {

		class P extends JPanel {
			public P() {
				GridLayout gl = new GridLayout(4, 3);
				
				// Set the button gap
				gl.setHgap(8);
				gl.setVgap(13);
				setBorder(BorderFactory.createLineBorder(Color.black));
				
				setLayout(gl);
				add(buildButton("1"));
				add(buildButton("2"));
				add(buildButton("3"));
				add(buildButton("4"));
				add(buildButton("5"));
				add(buildButton("6"));
				add(buildButton("7"));
				add(buildButton("8"));
				add(buildButton("9"));
				add(buildButton("*"));
				add(buildButton("0"));
				add(buildButton("#"));				
			}
		}
		return new P();
	}

	/**
	 * Builds a button, finds the associated "Event" and delegates to the
	 * EventManager.
	 * 
	 * @param in
	 * @return
	 */
	private JButton buildButton(String in) {
		JButton jButt = new JButton(in);
		jButt.setPreferredSize(new Dimension(60, 30));
		EventValue theVal = getEventValue(in);
		jButt.addActionListener(myEventBus);
		myEventBus.addSourceMapping(jButt, theVal);
		return jButt;
	}

	
	/**
	 * Get an event value for the given string
	 * @param in
	 * @return
	 */
	private EventValue getEventValue(String in) {
		EventValue theVal = null;

		for (EventValue val : EventBus.EventValue.values()) {
			if (in.equalsIgnoreCase(val.getCode())) {
				theVal = val;
				break;
			}
		}
		return theVal;
	}

	/**
	 * This panel has one component in it, the number display label
	 * 
	 * @return
	 */
	private Component buildNumberDisplayPanel() {
		class BorderPanel extends JPanel {
			public BorderPanel() {
				setLayout(new BorderLayout());
				add(myNumberManager.getLabel(), BorderLayout.CENTER);
				setPreferredSize(new Dimension(300, 45));
				setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		return new BorderPanel();
	}
}
