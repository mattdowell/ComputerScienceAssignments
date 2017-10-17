//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;


import ics240.midterm.GameHandler.DisplayMode;
import ics240.midterm.GameHandler.FillerCharType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;



@SuppressWarnings("serial")
public class ConfigurationPanel extends JPanel
{
	
	private JButton jbtSave = new JButton("Save");
	private JButton jbtQuit = new JButton("Quit");
	private JButton jbtBack = new JButton("Back");
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
	
	
	public  ConfigurationPanel ()
	{
		handler = new GameHandler(new Configuration());
		
/*
		// Create mainInput panel to hold the main input text area
		JPanel mainInput = new JPanel(new GridLayout(1, 1));
		mainInput.add(scrollPane, BorderLayout.CENTER);
*/
		// Create buttons panel to hold the buttons
		JPanel buttons = new JPanel();
		
		jbtSave.setFont(new Font("Serif", Font.BOLD, 16));
		jbtBack.setFont(new Font("Serif", Font.BOLD, 16));
		jbtQuit.setFont(new Font("Serif", Font.BOLD, 16));
		
		buttons.add(jbtSave);
		buttons.add(jbtBack);		
		buttons.add(jbtQuit);
		

		// Create configuration panel
		JPanel configuration = new JPanel(new GridLayout(5, 1));
		JPanel line1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		line1.add(jlbWorkSheetTitle);
		line1.add(jtfWorksSheetTitle);
		configuration.add(line1);
		//JPanel line2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//line2.add(jlbInputFileName);
		//line2.add(jtfInputFileName);
		//line2.add(jbtInputFile);
		//configuration.add(line2);
		//JPanel line3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		//line3.add(jlbOutputFileName);
		//line3.add(jtfOutputFileName);
		//line3.add(jbtOutputFile);
		//configuration.add(line3);
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
		
		setLayout(new BorderLayout());
		add(buttons, BorderLayout.SOUTH);
		add(configuration, BorderLayout.CENTER);

		setName("Configuration");
		setPreferredSize(new Dimension(300, 200));
		setVisible(true);

		// Create the listener
		CapListener listener1 = new CapListener();

		// Register the listeners
		jbtBack.addActionListener(listener1);
		jbtSave.addActionListener(listener1);
		
		jbtQuit.addActionListener(listener1);
		
		jbtInputFile.addActionListener(listener1);
		jbtOutputFile.addActionListener(listener1);
		
		//
		setWorkSheetTitle(Configuration.WORKSHEET_TITLE);
		setNumFillerRows(Configuration.NUMBER_OF_FILLER_ROWS);
		setNumTilesPerRow(Configuration.NUMBER_OF_TITLES_PER_ROW);

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

	public void setWorkSheetTitle(String inTitle)
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

			if (e.getSource() == jbtSave)
			{
				Configuration.WORKSHEET_TITLE = getWorksheetTitle();
				Configuration.NUMBER_OF_TITLES_PER_ROW = getNumTilesPerRow();
				Configuration.NUMBER_OF_FILLER_ROWS = getNumFillerRows();
				Configuration.charType = getFillerCharType();
				Configuration.displayMode = getDisplayMode();
			}

			else if (e.getSource() == jbtBack)
			{
				removeAll();						
				add(new FourPixOneWordGUIOut());				
				revalidate();						
				repaint();
			}

			else if (e.getSource() == jbtQuit)
			{
				System.exit(0);
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

}// end class ConfigutationPanel

