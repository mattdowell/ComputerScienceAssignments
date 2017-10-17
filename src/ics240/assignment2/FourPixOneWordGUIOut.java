//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is responsible for output (to Java / Swing) the GUI for easy
 * testing, and extra credit!
 * 
 * @author Seretseab
 * 
 */
public class FourPixOneWordGUIOut extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JLabel txtInput1 = new JLabel("Answer");
	private JLabel txtInput2 = new JLabel();
	private JLabel txtInput3 = new JLabel();;
	private JLabel txtInput4 = new JLabel();
	private JLabel txtInput5 = new JLabel();

	private GameHandler gameHandler = null;

	public FourPixOneWordGUIOut(GameHandler inHandler)
	{
		gameHandler = inHandler;
	}

	/**
	 * call this method whenever the GUI_OUT button is clicked We have to build
	 * this gui on the fly because the users can change the input right before
	 * clicking the GUI out button, so we have to parse everything in real-time.
	 * 
	 * @throws IOException
	 */
	public void createGUIOut() throws IOException
	{

		JPanel p_1 = new JPanel(new GridLayout(2, 2));

		JPanel p_2 = new JPanel();
		// p2.setLayout(new GridLayout(1,5));

		JPanel p_3 = new JPanel();
		p_3.setLayout(new GridLayout(2, 6));

		JPanel p_4 = new JPanel(new BorderLayout());

		String[] urls = gameHandler.getUrls();
		// create a URL object for each of the for url's
		URL url1 = new URL(urls[0]);
		URL url2 = new URL(urls[1]);
		URL url3 = new URL(urls[2]);
		URL url4 = new URL(urls[3]);

		// read each url
		Image image1 = ImageIO.read(url1);
		Image image2 = ImageIO.read(url2);
		Image image3 = ImageIO.read(url3);
		Image image4 = ImageIO.read(url4);
		// resize the images to fit to the jpanel
		Image scaledImage1 = image1.getScaledInstance(170, 170,
				Image.SCALE_SMOOTH);
		Image scaledImage2 = image2.getScaledInstance(170, 170,
				Image.SCALE_SMOOTH);
		Image scaledImage3 = image3.getScaledInstance(170, 170,
				Image.SCALE_SMOOTH);
		Image scaledImage4 = image4.getScaledInstance(170, 170,
				Image.SCALE_SMOOTH);

		// create imageIcon objects to put the resized images
		ImageIcon imgIcon1 = new ImageIcon();
		ImageIcon imgIcon2 = new ImageIcon();
		ImageIcon imgIcon3 = new ImageIcon();
		ImageIcon imgIcon4 = new ImageIcon();
		// put the resized images to the image icon
		imgIcon1.setImage(scaledImage1);
		imgIcon2.setImage(scaledImage2);
		imgIcon3.setImage(scaledImage3);
		imgIcon4.setImage(scaledImage4);

		// create JLabels to display the images on the panel
		JLabel label1 = new JLabel(imgIcon1);
		JLabel label2 = new JLabel(imgIcon2);
		JLabel label3 = new JLabel(imgIcon3);
		JLabel label4 = new JLabel(imgIcon4);

		// add the JLabels to the panel
		p_1.add(label1);
		p_1.add(label2);
		p_1.add(label3);
		p_1.add(label4);
		// set the borders for the panel carrying the JLabel
		p_1.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
		// set the border for the JLabels carrying the images
		label1.setBorder(BorderFactory
				.createMatteBorder(5, 5, 5, 5, Color.gray));
		label2.setBorder(BorderFactory
				.createMatteBorder(5, 5, 5, 5, Color.gray));
		label3.setBorder(BorderFactory
				.createMatteBorder(5, 5, 5, 5, Color.gray));
		label4.setBorder(BorderFactory
				.createMatteBorder(5, 5, 5, 5, Color.gray));

		add(p_1, BorderLayout.CENTER); // add p_1 to the frame
		p_1.setBackground(Color.black);// set the background color of p_1
		p_2.setBackground(Color.black);// set the background color of p_2

		// add the JLabels for displaying the letters to p_2 and set their
		// borders and foreground colors
		txtInput1.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				Color.black));
		txtInput2.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				Color.black));
		txtInput3.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				Color.black));
		txtInput4.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				Color.black));
		txtInput5.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
				Color.black));

		txtInput1.setForeground(Color.WHITE);
		txtInput2.setForeground(Color.WHITE);
		txtInput3.setForeground(Color.WHITE);
		txtInput4.setForeground(Color.WHITE);
		txtInput5.setForeground(Color.WHITE);

		p_2.add(txtInput1);
		p_2.add(txtInput2);
		p_2.add(txtInput3);
		p_2.add(txtInput4);
		p_2.add(txtInput5);

		p_3.setBackground(Color.black);

		p_4.add(p_2, BorderLayout.NORTH);
		p_4.add(p_3, BorderLayout.SOUTH);

		add(p_4, BorderLayout.SOUTH);

		// / Add the bottons to the bottom of the main panel, like this:
		JPanel buttonsPanel = TileCreator.outputToJava(gameHandler
				.getSolutionWord(), gameHandler.getNumFillerRows(), gameHandler
				.getNumTilesPerRow(), gameHandler.getFillerCharType()
				.toString());

		// instead of creating all thoes buttons on GUIOUT i took the tiles from
		// the TilePanel and put it on p_3
		// you can create the borders for each buttons in TileCreator Class i
		// left it commented incase you want to use it
		p_3.add(buttonsPanel);

		// Build the frame and add the main panel
		setTitle("Four Pix One Word Puzzle");
		setSize(400, 500);
		setResizable(true);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}
}