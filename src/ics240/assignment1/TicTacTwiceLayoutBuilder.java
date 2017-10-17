//*******1*********2*********3*********4*********5*********6*********7*********8
// TicTacTwice.java, Matt Dowell, mdowell@gmail.com, Homework 1, May 18,2014
package ics240.assignment1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * [1] You will build a simple GUI (TicTacTwiceGUI.java) with four text fields –
 * Logo, Title, Input, Output and one action button called “Create”
 * 
 * [2] Upon starting the program, all four text fields will be populated with
 * some defaults which users can change as needed.
 * 
 * [3] Upon clicking “Create” button, the output HTML file will be created at
 * the location user has specified. It will also display the HTML in the
 * browser.
 * 
 * @author Matt
 * 
 */
public class TicTacTwiceLayoutBuilder
{

	private JTextField logoField = new JTextField(
			"http://www.logicd.com/wp-content/uploads/2013/12/Final-Logo-2.jpg",
			150);
	private JTextField titleField = new JTextField("Tick Tac Twice", 150);
	private JTextField inputField = new JTextField(
			"C:/temp/tic_tac_twice.txt", 150);
	private JTextField outputField = new JTextField(
			"C:/temp/tic_tac_twice.html", 150);
	private JButton button = new JButton("Create");

	/**
	 * This is the method any outside calling classes must call. This method
	 * returns a fully-build phone layout
	 * 
	 * @return
	 */
	public JFrame build()
	{

		JFrame frame = new JFrame("Generate Tic Tac Twice Tables");
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
	private JPanel buildMainPanel()
	{
		@SuppressWarnings("serial")
		class BorderPanel extends JPanel
		{
			public BorderPanel()
			{
				setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
				setBackground(Color.black);
				add(buildLogoPanel());
				add(buildTitlePanel());
				add(buildInputPanel());
				add(buildOutputPanel());
				add(buildButtonPanel());
			}
		}
		return new BorderPanel();
	}

	/**
	 * Builds the visual component for the user to specify the output path.
	 * 
	 * @return BorderPanel with label and Button
	 */
	public Component buildButtonPanel()
	{
		@SuppressWarnings("serial")
		class BorderPanel extends JPanel
		{
			public BorderPanel()
			{
				setLayout(new BorderLayout());
				// add(new JLabel("Click Go "), BorderLayout.NORTH);
				add(button, BorderLayout.SOUTH);
				setPreferredSize(new Dimension(500, 60));
				setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		return new BorderPanel();
	}

	/**
	 * Builds the visual component for the user to specify the output path.
	 * 
	 * @return BorderPanel with label and text field
	 */
	public Component buildOutputPanel()
	{
		@SuppressWarnings("serial")
		class BorderPanel extends JPanel
		{
			public BorderPanel()
			{
				setLayout(new BorderLayout());
				add(new JLabel("Path to output file"), BorderLayout.NORTH);
				add(outputField, BorderLayout.SOUTH);
				setPreferredSize(new Dimension(500, 60));
				setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		return new BorderPanel();
	}

	/**
	 * Builds the BorderPanel for the user to specify an input file
	 * 
	 * @return BorderPanel with label and text field
	 */
	public Component buildInputPanel()
	{
		@SuppressWarnings("serial")
		class BorderPanel extends JPanel
		{
			public BorderPanel()
			{
				setLayout(new BorderLayout());
				add(new JLabel("Path to input file"), BorderLayout.NORTH);
				add(inputField, BorderLayout.SOUTH);
				setPreferredSize(new Dimension(500, 60));
				setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		return new BorderPanel();
	}

	/**
	 * Builds the title panel for the user to speficy the title
	 * 
	 * @return BorderPanel with label and text field
	 */
	public Component buildTitlePanel()
	{
		@SuppressWarnings("serial")
		class BorderPanel extends JPanel
		{
			public BorderPanel()
			{
				setLayout(new BorderLayout());
				add(new JLabel("Title of Screen"), BorderLayout.NORTH);
				add(titleField, BorderLayout.SOUTH);
				setPreferredSize(new Dimension(500, 60));
				setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		return new BorderPanel();
	}

	/**
	 * Builds the panel for the user to speficy the path to the logo
	 * 
	 * @return BorderPanel with label and text field
	 */
	public Component buildLogoPanel()
	{
		@SuppressWarnings("serial")
		class BorderPanel extends JPanel
		{
			public BorderPanel()
			{
				setLayout(new BorderLayout());
				add(new JLabel("Path to logo"), BorderLayout.NORTH);
				add(logoField, BorderLayout.SOUTH);
				setPreferredSize(new Dimension(500, 60));
				setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		return new BorderPanel();
	}

	public JTextField getLogoField()
	{
		return logoField;
	}

	public void setLogoField(JTextField logoField)
	{
		this.logoField = logoField;
	}

	public JTextField getTitleField()
	{
		return titleField;
	}

	public void setTitleField(JTextField titleField)
	{
		this.titleField = titleField;
	}

	public JTextField getInputField()
	{
		return inputField;
	}

	public void setInputField(JTextField inputField)
	{
		this.inputField = inputField;
	}

	public JTextField getOutputField()
	{
		return outputField;
	}

	public void setOutputField(JTextField outputField)
	{
		this.outputField = outputField;
	}

	public JButton getButton()
	{
		return button;
	}

	public void setButton(JButton button)
	{
		this.button = button;
	}

}
