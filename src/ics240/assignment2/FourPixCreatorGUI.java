//*******1*********2*********3*********4*********5*********6*********7*********8
// FourPixMain.java, Vitaly Sots,  Homework 2, 06/03/2014
package ics240.assignment2;

/*
 Name: Vitaly Sots
 Class: ICS 240
 Date: 06/03/2014
 Description: Assignment #2
 */

import ics240.assignment2.GameHandler.DisplayMode;
import ics240.assignment2.GameHandler.FillerCharType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * This class is the GUI builder for our application.
 * 
 * @author Vitaly
 *
 */
@SuppressWarnings("serial")
public class FourPixCreatorGUI extends JFrame
{

	private JButton jbtLoad = new JButton("Load");
	private JButton jbtCreate = new JButton("Create");
	private JButton jbtHelp = new JButton("Help");
	private JButton jbtQuit = new JButton("Quit");
	private JButton jbtGuiOut = new JButton("GUI_OUT");
	private JButton jbtInputFile = new JButton("Browse...");
	private JButton jbtOutputFile = new JButton("Browse...");
	protected JTextArea jtaMainInput = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(jtaMainInput);
	private JLabel jlbWorkSheetTitle = new JLabel("WORKSHEET_TITLE =");
	private JLabel jlbInputFileName = new JLabel("INPUT_FILE_NAME =");
	private JLabel jlbOutputFileName = new JLabel("OUTPUT_FILE_NAME =");
	private JLabel jlbNumFillerRows = new JLabel("NUMBER_OF_FILLER_ROWS =");
	private JLabel jlbNumTitlesPerRow = new JLabel("NUMBER_OF_TITLES_PER_ROW =");
	private JLabel jlbDisplayMode = new JLabel("DISPLAY_MODE =");
	private JLabel jlbFillerCharType = new JLabel("FILLER_CHARACTERS =");
	protected JTextField jtfWorksSheetTitle = new JTextField(38);
	protected JTextField jtfInputFileName = new JTextField(31);
	protected JTextField jtfOutputFileName = new JTextField(30);
	protected JTextField jtfNumFillerRows = new JTextField(2);
	protected JTextField jtfNumTitlesPerRow = new JTextField(2);
	protected JTextField jtfDisplayMode = new JTextField(10);
	protected JTextField jtfFillerCharType = new JTextField(10);
	private JComboBox<DisplayMode> jcbDisplayMode = new JComboBox<DisplayMode>(
			DisplayMode.values());
	private JComboBox<FillerCharType> jcbCharType = new JComboBox<FillerCharType>(
			FillerCharType.values());

	private GameHandler handler = null;
	private JFileChooser inputFile = new JFileChooser();

	public FourPixCreatorGUI(GameHandler inHandler)
	{
		handler = inHandler;

		// Create mainInput panel to hold the main input text area
		JPanel mainInput = new JPanel(new GridLayout(1, 1));
		mainInput.add(scrollPane, BorderLayout.CENTER);

		// Create buttons panel to hold the buttons
		JPanel buttons = new JPanel(new GridLayout(5, 1, 15, 15));
		jbtLoad.setFont(new Font("Serif", Font.BOLD, 16));
		jbtCreate.setFont(new Font("Serif", Font.BOLD, 16));
		jbtHelp.setFont(new Font("Serif", Font.BOLD, 16));
		jbtQuit.setFont(new Font("Serif", Font.BOLD, 16));
		jbtGuiOut.setFont(new Font("Serif", Font.BOLD, 16));
		buttons.add(jbtLoad);
		buttons.add(jbtCreate);
		buttons.add(jbtHelp);
		buttons.add(jbtQuit);
		buttons.add(jbtGuiOut);

		// Create configuration panel
		JPanel configuration = new JPanel(new GridLayout(7, 1));
		JPanel line1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		line1.add(jlbWorkSheetTitle);
		line1.add(jtfWorksSheetTitle);
		configuration.add(line1);
		JPanel line2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		line2.add(jlbInputFileName);
		line2.add(jtfInputFileName);
		line2.add(jbtInputFile);
		configuration.add(line2);
		JPanel line3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		line3.add(jlbOutputFileName);
		line3.add(jtfOutputFileName);
		line3.add(jbtOutputFile);
		configuration.add(line3);
		JPanel line4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		line4.add(jlbNumFillerRows);
		line4.add(jtfNumFillerRows);
		configuration.add(line4);
		JPanel line5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		line5.add(jlbNumTitlesPerRow);
		line5.add(jtfNumTitlesPerRow);
		configuration.add(line5);
		JPanel line6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jcbDisplayMode.setBackground(Color.WHITE);
		line6.add(jlbDisplayMode);
		line6.add(jcbDisplayMode);
		configuration.add(line6);
		JPanel line7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jcbCharType.setBackground(Color.WHITE);
		line7.add(jlbFillerCharType);
		line7.add(jcbCharType);
		configuration.add(line7);
		configuration.setBorder(new TitledBorder(
				"Configuration (You can update the values in this area)"));

		// add the panels to the frame
		add(mainInput, BorderLayout.CENTER);
		add(buttons, BorderLayout.EAST);
		add(configuration, BorderLayout.SOUTH);

		setTitle("Four Pix Creator");
		setSize(630, 400);

		// Create the listener
		CapListener listener1 = new CapListener();

		// Register the listeners
		jbtLoad.addActionListener(listener1);
		jbtCreate.addActionListener(listener1);
		jbtHelp.addActionListener(listener1);
		jbtQuit.addActionListener(listener1);
		jbtGuiOut.addActionListener(listener1);
		jbtInputFile.addActionListener(listener1);
		jbtOutputFile.addActionListener(listener1);

	}// end FourPixCreatorGUI

	/**
	 * Show a error message
	 */

	public void showErrorMessage(String a_text, String a_errorType)
	{
		JOptionPane.showMessageDialog(null, a_text, a_errorType,
				JOptionPane.ERROR_MESSAGE);
	}

	public String getWorksheetTitle()
	{
		return jtfWorksSheetTitle.getText();
	}

	public void setWorkseetTitle(String inTitle)
	{
		jtfWorksSheetTitle.setText(inTitle);
	}

	public String getMainText()
	{
		return jtaMainInput.getText();
	}

	public void setMainText(String a_text)
	{
		jtaMainInput.setText(a_text);
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return String with input file name
	 */
	public String getInputFileName()
	{
		return jtfInputFileName.getText();
	}

	public void setInputFileName(String inName)
	{
		jtfInputFileName.setText(inName);
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return String with output filename
	 */
	public String getOutputFileName()
	{
		return jtfOutputFileName.getText();
	}

	public void setOutputFileName(String inName)
	{
		jtfOutputFileName.setText(inName);
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return int representing number of filler rows
	 */
	public int getNumFillerRows()
	{
		return Integer.parseInt(jtfNumFillerRows.getText());
	}

	public void setNumFillerRows(int inRows)
	{
		jtfNumFillerRows.setText(String.valueOf(inRows));
	}

	/**
	 * This method will call the proper JTextBox to get the configuation values.
	 * 
	 * @return int representing number of tiles per row
	 */
	public int getNumTilesPerRow()
	{
		return Integer.parseInt(jtfNumTitlesPerRow.getText());
	}

	public void setNumTilesPerRow(int inNum)
	{
		jtfNumTitlesPerRow.setText(String.valueOf(inNum));
	}

	/**
	 * This methods will call the proper JComboBox to get the configuation
	 * values.
	 * 
	 * @return DisplayMode
	 */
	public DisplayMode getDisplayMode()
	{
		return (DisplayMode) jcbDisplayMode.getSelectedItem();
	}

	public FillerCharType getFillerCharType()
	{
		return (FillerCharType) jcbCharType.getSelectedItem();
	}

	class CapListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource() == jbtLoad)
			{
				handler.processLoadEvent();
			}

			else if (e.getSource() == jbtCreate)
			{
				handler.processCreateEvent();
			}

			else if (e.getSource() == jbtHelp)
			{

			}

			else if (e.getSource() == jbtQuit)
			{
				System.exit(0);
			}

			else if (e.getSource() == jbtGuiOut)
			{
				handler.processGuiOutEvent();
			}

			else if (e.getSource() == jbtInputFile)
			{
				if (inputFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					java.io.File file = inputFile.getSelectedFile();
					setInputFileName(file.getPath());
				}
			}

			else if (e.getSource() == jbtOutputFile)
			{
				if (inputFile.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					java.io.File file = inputFile.getSelectedFile();
					setOutputFileName(file.getPath());
				}
			}

		}// end actionPerformed

	}// end class CapListner

}// end class FourPixCreatorGUI
