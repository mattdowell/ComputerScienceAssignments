//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.midterm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is responsible for output (to Java / Swing)
 * 
 * @author Vitaly Sots
 * 
 */
public class FourPixOneWordGUIOut extends JPanel implements ComponentListener
{
	private static final long serialVersionUID = 1L;

	private JButton jbtBack = null;
	private JButton jbtReveal = null;
	private JLabel jlbCredit = new JLabel();
	private static int coin = 500;
	private int credit = 0;
	private JLabel jlbCoin = new JLabel("   " + String.valueOf(coin));
	private JLabel[] slot = new JLabel[(0)];
	private JButton[] jbt = new JButton[(0)];
	private int slotCursor = 0;
	private int manyChar = 0;
	private Dimension topPanelSize;
	private static GameHandler gameHandler = null;
	private Game newGame = null;

	public FourPixOneWordGUIOut(GameHandler inHandler)
	{
		gameHandler = inHandler;
	}

	public FourPixOneWordGUIOut()
	{
		try
		{
			activateQuiz();
		} catch (IOException e)
		{
			throw new IllegalStateException(e.getCause());
		}
	}

	/**
	 * call this method whenever the GUI_OUT button is clicked We have to build
	 * this gui on the fly because the users can change the input right before
	 * clicking the GUI out button, so we have to parse everything in real-time.
	 * 
	 * @throws IOException
	 */

	public void activateQuiz() throws IOException
	{
		jbtBack = new JButton(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("ic_back.png"))));
		jbtReveal = new JButton(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("reveal.png"))));
		
		jbtReveal.addActionListener(new HintButtonListener());
		
		// gameHandler = new GameHandler(new Configuration());
		// read the configuration
		int row = Configuration.NUMBER_OF_FILLER_ROWS;
		int col = Configuration.NUMBER_OF_TITLES_PER_ROW;
		newGame = gameHandler.getNextGame();
		credit = newGame.getSolutionWord().length();

		// Create the listener
		ButtonListener listener1 = new ButtonListener();

		// Create a
		// topPanel*************************************************************************************************************************
		//Image top = ImageIO.read(new File("resources/topBar.png"));
		//Image top = ImageIO.read(ClassLoader.getSystemResource( "resources/topBar.png" ) );
		Image top = ImageIO.read(getClass().getClassLoader().getResourceAsStream("topBar.png"));
		BackgroundPanel topPanel = new BackgroundPanel(top, 1);
		topPanel.setMinimumSize(new Dimension(720, 82));
		topPanel.setPreferredSize(new Dimension(720, 82));
		topPanel.addComponentListener(this);

		// Create a back button
		topPanel.setLayout(null);
		jbtBack.setContentAreaFilled(false);
		jbtBack.setBorderPainted(false);
		topPanel.add(jbtBack);
		Insets insets = topPanel.getInsets();
		topPanelSize = topPanel.getSize();
		Dimension size = jbtBack.getPreferredSize();
		jbtBack.setBounds(insets.left, 1 + insets.top, size.width, size.height);
		jbtBack.setAlignmentX(LEFT_ALIGNMENT);

		// Create a JLabel for display credits
		String string = "";
		string += credit;
		jlbCredit.setIcon(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("credit.png"))));
		jlbCredit.setIconTextGap(0);
		jlbCredit.setHorizontalTextPosition(AbstractButton.CENTER);
		jlbCredit.setText(string);
		jlbCredit.setFont(new Font("Serif", Font.BOLD, 36));
		jlbCredit.setForeground(Color.WHITE);
		topPanel.add(jlbCredit);
		size = jlbCredit.getPreferredSize();
		jlbCredit.setBounds(topPanelSize.width + 150, insets.top, size.width,
				size.height);

		// Create a JLabel for display coins
		jlbCoin.setFont(new Font("Serif", Font.BOLD, 32));
		jlbCoin.setForeground(Color.WHITE);
		jlbCoin.setIcon(new ImageIcon("coins2.png"));
		jlbCoin.setIconTextGap(0);		
		jlbCoin.setHorizontalTextPosition(AbstractButton.CENTER);
		topPanel.add(jlbCoin);
		size = jlbCoin.getPreferredSize();
		jlbCoin.setBounds(insets.left + 667, 12 + insets.top, size.width,
				size.height);

		// Create an
		// imagePanel*************************************************************************
		Image image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("bg.png"));
		BufferedImage photoFrame = ImageIO.read(getClass().getClassLoader().getResourceAsStream("photo.png"));
		BackgroundPanel imagePanel = new BackgroundPanel(image, 1);
		imagePanel.setLayout(new GridLayout(2, 2, 3, 3));
		imagePanel.setMinimumSize(new Dimension(720, 500));

		/*
		 * for (int x = 1; x < 5; x++) { imagePanel.add(new JLabel(new
		 * ImageIcon("photo.png"))); }
		 */
		switch (Configuration.displayMode)
		{

		case IMAGE:

			// String[] urls = gameHandler.getUrls();
			// create a URL object for each of the for url's
			URL url1 = new URL(newGame.getImageUrl1());
			URL url2 = new URL(newGame.getImageUrl2());
			URL url3 = new URL(newGame.getImageUrl3());
			URL url4 = new URL(newGame.getImageUrl4());

			// read each url
			BufferedImage image1 = ImageIO.read(url1);
			BufferedImage image2 = ImageIO.read(url2);
			BufferedImage image3 = ImageIO.read(url3);
			BufferedImage image4 = ImageIO.read(url4);

			// Combine images
			BufferedImage combined1 = new BufferedImage(photoFrame.getWidth(),
					photoFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
			BufferedImage combined2 = new BufferedImage(photoFrame.getWidth(),
					photoFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
			BufferedImage combined3 = new BufferedImage(photoFrame.getWidth(),
					photoFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
			BufferedImage combined4 = new BufferedImage(photoFrame.getWidth(),
					photoFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);

			// paint both images, preserving the alpha channels
			Graphics g = combined1.getGraphics();
			g.drawImage(photoFrame, 0, 0, null);
			g.drawImage(image1, 17, 17, 198, 199, null);

			g = combined2.getGraphics();
			g.drawImage(photoFrame, 0, 0, null);
			g.drawImage(image2, 17, 17, 198, 199, null);

			g = combined3.getGraphics();
			g.drawImage(photoFrame, 0, 0, null);
			g.drawImage(image3, 17, 17, 198, 199, null);

			g = combined4.getGraphics();
			g.drawImage(photoFrame, 0, 0, null);
			g.drawImage(image4, 17, 17, 198, 199, null);

			// create imageIcon objects to put the resized images
			ImageIcon imgIcon1 = new ImageIcon();
			ImageIcon imgIcon2 = new ImageIcon();
			ImageIcon imgIcon3 = new ImageIcon();
			ImageIcon imgIcon4 = new ImageIcon();
			// put the resized images to the image icon
			imgIcon1.setImage(combined1);
			imgIcon2.setImage(combined2);
			imgIcon3.setImage(combined3);
			imgIcon4.setImage(combined4);

			imagePanel.add(new JLabel(imgIcon1));
			imagePanel.add(new JLabel(imgIcon2));
			imagePanel.add(new JLabel(imgIcon3));
			imagePanel.add(new JLabel(imgIcon4));
			break;

		case TEXT:

			String[] words = new String[4];
			words[0] = newGame.getWord1();
			words[1] = newGame.getWord2();
			words[2] = newGame.getWord3();
			words[3] = newGame.getWord4();

			for (int x = 0; x < 4; x++)
			{
				JLabel label = new JLabel(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("photo.png"))));
				label.setText(words[x]);
				label.setIconTextGap(0);
				label.setForeground(Color.WHITE);
				label.setFont(new Font("Serif", Font.BOLD, 32));
				label.setHorizontalTextPosition(AbstractButton.CENTER);
				imagePanel.add(label);

			}
			break;
		}

		// Create a
		// guessPanel*****************************************************************************************************************************
		BackgroundPanel guessPanel = new BackgroundPanel(image, 1);
		slot = new JLabel[(credit)];
		guessPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		for (int x = 0; x < newGame.getSolutionWord().length(); x++)
		{
			slot[x] = new JLabel(new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("slot.png"))));
			slot[x].setIconTextGap(0);
			slot[x].setForeground(Color.WHITE);
			slot[x].setFont(new Font("Serif", Font.BOLD, 32));
			slot[x].setHorizontalTextPosition(AbstractButton.CENTER);
			guessPanel.add(slot[x]);
			slot[x].addMouseListener(new MouseListener()
			{

				@Override
				public void mouseClicked(MouseEvent e)
				{
					// TODO Auto-generated method stub
					JLabel labelSource = (JLabel) e.getSource();

					if (labelSource.getText() != null)
					{
						String labelText = labelSource.getText();
						labelSource.setText(null);
						slotCursor = 0;
						manyChar--;

						// return the disable button
						for (int i = 0; i < jbt.length; i++)
						{
							String buttonText = jbt[i].getText();
							if (jbt[i].isEnabled() == false
									&& labelText.equals(buttonText))
							{
								jbt[i].setEnabled(true);
								i = jbt.length;
							}
						}// end for
					}// end if

				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
				}

				@Override
				public void mousePressed(MouseEvent e)
				{
				}

				@Override
				public void mouseReleased(MouseEvent e)
				{
				}

			});
		}// end For

		// Create a
		// buttonPanel***********************************************************************
		BackgroundPanel buttonPanel = new BackgroundPanel(image, 1);
		jbt = new JButton[(row * col)];

		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		jbt = TileCreator.outputToJava(newGame.getSolutionWord(), row, col,
				Configuration.charType);

		for (int y = 0, i = 0; y < row; y++)
		{

			for (int x = 0; x < col; x++)
			{

				jbt[i].setFont(new Font("Serif", Font.BOLD, 32));
				// jbt[i].setSize(new Dimension(85, 85));
				jbt[i].setPreferredSize(new Dimension(55, 55));
				jbt[i].setMinimumSize(new Dimension(55, 55));
				jbt[i].setMargin(new Insets(0, 0, 0, 0));
				c.gridx = x;
				c.gridy = y;
				c.insets = new Insets(5, 5, 5, 5);
				buttonPanel.add(jbt[i], c);
				jbt[i].addActionListener(listener1);
				i++;
			}
		}
		jbtReveal.setMargin(new Insets(0, 0, 0, 0));
		jbtReveal.setPreferredSize(new Dimension(55, 55));
		jbtReveal.setMinimumSize(new Dimension(55, 55));
		jbtReveal.setContentAreaFilled(false);
		c.gridx = col + 1;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(jbtReveal, c);

		// Create a
		// bottomPanel************************************************************************
		JPanel bottomPanel = new JPanel();
		bottomPanel.setMinimumSize(new Dimension(720, 350));
		bottomPanel.setPreferredSize(new Dimension(720, 300));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.add(guessPanel);
		bottomPanel.add(buttonPanel);

		// Register the listeners
		jbtBack.addActionListener(listener1);

		// add panels to the frame
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(imagePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

	}// end activateQuiz

	// add a character to the empty slot
	private void addChar(JButton button, int index)
	{

		if (slot[slotCursor].getText() == null)
		{
			slot[slotCursor].setText(button.getText());
			manyChar++;
			slotCursor++;
			jbt[index].setEnabled(false);
		}

		else
		{
			do
			{
				slotCursor++;

			} while (slot[slotCursor].getText() != null);

			slot[slotCursor].setText(button.getText());
			manyChar++;
			slotCursor++;
			jbt[index].setEnabled(false);
		}

	}

	// check the answer
	public void checkAnswer()
	{
		String guessWord = "";
		String correctAnswer = newGame.getSolutionWord().toUpperCase();
		for (int i = 0; i < slot.length; i++)
		{
			// System.out.println(slot[i].getText());
			guessWord += slot[i].getText();
		}

		if (correctAnswer.equals(guessWord))
		{
			String string = "         AWESOME!" + "\nYou've guessed the word!";
			JOptionPane.showMessageDialog(null, string, "AWESOME!",
					JOptionPane.INFORMATION_MESSAGE);
			coin += credit;
			removeAll();
			add(new FourPixOneWordGUIOut());
			revalidate();
			repaint();

		} else
		{

			JOptionPane.showMessageDialog(null,
					"You have not guessed the word", "TRY AGAIN",
					JOptionPane.ERROR_MESSAGE);

			for (int x = 0; x < newGame.getSolutionWord().length(); x++)
			{

				slot[x].setForeground(Color.RED);
				slot[x].repaint();

			}

		}
	} // end chaeckAnswer

	// Listeners************************************************************************************************************************************************
	/**
	 * If the frame resize put jlbCoin and jlbCredit in the right place
	 */
	@Override
	public void componentHidden(ComponentEvent arg0)
	{
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
	}

	@Override
	public void componentResized(ComponentEvent e)
	{

		JPanel p = (JPanel) e.getComponent();
		Insets insets = p.getInsets();
		// Dimension s = p.getSize();
		topPanelSize = p.getSize();
		Dimension size = jlbCredit.getPreferredSize();
		jlbCredit.setBounds((int) (topPanelSize.width / 2) - 40, insets.top,
				size.width, size.height);
		size = jlbCoin.getPreferredSize();
		jlbCoin.setBounds(topPanelSize.width - 150, 12 + insets.top,
				size.width, size.height);

	}

	@Override
	public void componentShown(ComponentEvent arg0)
	{
	}

	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{

			if (e.getSource() instanceof JButton && e.getSource() != jbtBack)
			{

				// Determine the end of the game
				if (newGame.getSolutionWord().length() == manyChar)
				{
					checkAnswer();
				} 
				else
				{
					JButton buttonSource = (JButton) e.getSource();
					int index = java.util.Arrays.asList(jbt).indexOf(
							buttonSource);
					addChar(buttonSource, index);
				}
				// Determine the end of the game
				if (newGame.getSolutionWord().length() == manyChar)
				{
					checkAnswer();
				}
			}

			// call the configuration panel
			else if (e.getSource() == jbtBack)
			{
				// JPanel frame = (JPanel) getContentPane();
				removeAll();
				setPreferredSize(new Dimension(300, 200));
				add(new ConfigurationPanel());
				revalidate();
				repaint();
			}

		}// end actionPerformed
	}// end class ButtonListener
	
	class HintButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (newGame.getSolutionWord().length() == manyChar)
			{
				checkAnswer();
			}
			else
			{
			
			char[] a = newGame.getSolutionWord().toCharArray();
						
			slot[slotCursor].setText(String.valueOf(a[slotCursor]).toUpperCase());
			slot[slotCursor].setForeground(Color.GREEN);
			slot[slotCursor].repaint();
			coin -= 50;
			jlbCoin.setText("   " + String.valueOf(coin));
			manyChar++;
			slotCursor++;
			
			}
		}// end actionPerformed
	}// end class ButtonListener

}// end class

