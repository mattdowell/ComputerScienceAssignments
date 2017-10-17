//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.finalassignment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * Primary java class for GUI layout and for user interactions / action
 * listeners
 * 
 * @author Vitaly Sots
 * 
 *         NOTE: HTML used to launch an applet can embed arguments in the URL;
 *         For example "http://...../puzzle.html?id=36" is intendeing to launch
 *         a puzzle with ID = "36"
 * 
 *         Pseudo Code: 1. Process the URL and set the puzzle ID (if exists)
 *         through PuzzlePicker 2. Read the puzzle.txt and populate the
 *         PuzzleCollection 4. Get the puzzle to be played based on the
 *         puzzleID; if null, get a random puzzle 5. Create a PuzzleTracker
 *         object based on the Puzzle 6. Build the GUI gird based on puzzle grid
 *         height and width 7. Attach the Listeners to the cells to recognize
 *         the MouseUp / MouseDown / MouseMove events 8. Populate the grid
 *         content (cells and words) based on puzzle and PuzzleConfig 9. Play
 *         the game. 10. Upon completion of the game, put out the
 *         gameCompleteMsg and give the option to the user to play the next game
 *         11. Present the next puzzle in the sequence (in the same order the
 *         puzzles are present in the input file) 12. Terminate the game if the
 *         user doesn't wish to continue or kills the browser
 * 
 */
public class PuzzlePlayerFrame extends JPanel implements MouseListener,
		ComponentListener
{

	private static final long serialVersionUID = 1L;
	private JLabel jlbTitle = new JLabel();
	private JLabel jlbTime;
	private JButton[][] jlbGrid;
	private ArrayList<JLabel> jlbWord = new ArrayList<JLabel>();
	private ArrayList<JButton> foundWords = new ArrayList<JButton>();
	private PuzzleTracker tracker;
	private JPanel sidePanel;
	private BackgroundPanel wordsPanel;
	private BackgroundPanel timerPanel;
	private Font gidugu_font;
	private boolean leftButtonIsDown = false;
	Stack<JButton> selectedButtons = new Stack<JButton>();
	private String selectedWord = "";

	// a component location on the grid
	int[] xy1 = new int[2];
	int[] xy2 = new int[2];

	// directions
	private static final int NORTH = 0;
	private static final int NORTH_WEST = 1;
	private static final int WEST = 2;
	private static final int SOUTH_WEST = 3;
	private static final int SOUTH = 4;
	private static final int SOUTH_EAST = 5;
	private static final int EAST = 6;
	private static final int NORTH_EAST = 7;
	// timer

	private final ClockListener cl = new ClockListener();
	private final Timer time = new Timer(1000, cl);
	private final JLabel jlbTimer = new JLabel("00:00:00");

	// a grid size
	private int gridWidth;
	private int gridHeight;

	private PuzzleCollection myPuzzleCollection = null;

	public PuzzlePlayerFrame(PuzzleCollection inPuzzleCollection)
	{
		myPuzzleCollection = inPuzzleCollection;
		buildGui(myPuzzleCollection.getPuzzleByID());
	}

	/**
	 * This method creates the GUI and builds the frame with the given puzzle.
	 * 
	 * @param a_puzzle
	 */
	private void buildGui(Puzzle a_puzzle)
	{
		this.removeAll();

		tracker = new PuzzleTracker(a_puzzle);

		gridWidth = a_puzzle.getGridWidth();
		gridHeight = a_puzzle.getGridHeight();

		BufferedImage background = null;
		try
		{
			background = ImageIO.read(PuzzlePlayerFrame.class
					.getResourceAsStream("b10.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// Create a
		// gridPanel************************************************************
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(gridHeight, gridWidth, 2, 2));
		gridPanel.setBackground(Color.BLACK);
		gridPanel.addComponentListener(this);
		jlbGrid = new JButton[gridHeight][gridWidth];
		String[][] grid = a_puzzle.getPuzzleGrid();

		for (int x = 0; x < gridHeight; x++)
		{
			for (int y = 0; y < gridWidth; y++)
			{

				jlbGrid[x][y] = new JButton(grid[x][y]); // creates a new label
				gridPanel.add(jlbGrid[x][y]); // adds labels to the grid
				jlbGrid[x][y].addMouseListener(this);
				//jlbGrid[x][y].setFont(PuzzleConfig.fontGrid);
				jlbGrid[x][y].setFont(PuzzleConfig.fontGrid);
			}
		}

		// Create a
		// sidePanel************************************************************
		sidePanel = new JPanel();
		sidePanel.setLayout(null);
		sidePanel.setPreferredSize(new Dimension(300, 400));

		// Create a
		// titlePanel***********************************************************
		BackgroundPanel titlePanel = new BackgroundPanel(background, 1);
		jlbTitle.setText(a_puzzle.getTitle());
		jlbTitle.setFont(PuzzleConfig.fontTitle);
		jlbTitle.setForeground(PuzzleConfig.colorTitle);
		titlePanel.add(jlbTitle);
		sidePanel.add(titlePanel);
		Insets insets = sidePanel.getInsets();
		titlePanel.setBounds(insets.left, insets.top, 300, 50);
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));

		// Create a
		// wordsPanel***********************************************************
		wordsPanel = new BackgroundPanel(background, 1);
		// check how many columns will be shown
		int col = 1;
		int row = 5;
		int numWords = a_puzzle.getWordList().size();

		if (numWords == PuzzleConfig.maxNumberOfWordsPerColumn)
		{
			col = 1;
			row = numWords;
		} else if (numWords > PuzzleConfig.maxNumberOfWordsPerColumn)
		{
			row = PuzzleConfig.maxNumberOfWordsPerColumn;
			col = 2;
			
			if (numWords%PuzzleConfig.maxNumberOfWordsPerColumn > 0 && PuzzleConfig.maxNumberOfWordsPerColumn/2>0) {
				col =3;
			}
		}

		wordsPanel.setLayout(new GridLayout(row, col));

		for (int x = 0; x < a_puzzle.getWordList().size(); x++)
		{
			JLabel label = new JLabel();
			label.setText(a_puzzle.getWordList().get(x));
			label.setFont(PuzzleConfig.fontWords);
			label.setForeground(PuzzleConfig.colorWords);
			label.setHorizontalAlignment(JLabel.CENTER);
			jlbWord.add(label);
			wordsPanel.add(label);
		}

		sidePanel.add(wordsPanel);
		wordsPanel.setBounds(0, 50, 300, 280);

		// Create a
		// timerPanel***********************************************************
		timerPanel = new BackgroundPanel(background, 1);
		jlbTime = new JLabel("time");
		jlbTime.setForeground(Color.YELLOW);
		jlbTime.setFont(new Font("Serif", Font.BOLD, 36));
		jlbTimer.setFont(new Font("Serif", Font.BOLD, 36));
		jlbTimer.setForeground(Color.WHITE);
		timerPanel.add(jlbTime);
		timerPanel.add(jlbTimer);
		sidePanel.add(timerPanel);
		timerPanel.setBounds(0, 330, 300, 70);
		time.start();

		// add panels to the frame
		setLayout(new BorderLayout());
		add(gridPanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);
	}

	/**
	 * 
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
		if (leftButtonIsDown)
		{

			selectedWord = "";
			unMarkButton();

			xy2 = getPosition(e.getComponent());
			getDirection();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		leftButtonIsDown = true;
		xy1 = getPosition(e.getComponent());
	}

	/**
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		leftButtonIsDown = false;

		if (tracker.wordHasAlreadyBeenFound(selectedWord)) {
			return;
		}
		
		// checks is the word in the list
		if (tracker.isWordInTheList(selectedWord) )
		{
			
			while (selectedButtons.isEmpty() == false)
			{
				JButton button = selectedButtons.pop();
				button.setBackground(PuzzleConfig.cellColorWordFound);
				foundWords.add(button);
			}

			for (int i = 0; i < jlbWord.size(); i++)
			{
				String string = jlbWord.get(i).getText().replaceAll("\\s+", "");
				if (string.equals(selectedWord.replaceAll("\\s+", "")))
					jlbWord.get(i).setForeground(Color.GREEN);
			}
			// Checks whether all words have been found by the user
			if (tracker.isGameComplete())
			{
				endGame();
			}
		}

		else
		{
			unMarkButton();
		}

		selectedWord = "";
	}

	/**
	 * 
	 * @param a_component
	 * @return
	 */
	private int[] getPosition (Component a_component) {
		
		int[] xy = new int[2];
		for(int x = 0; x < gridHeight; x++){
            for(int y = 0; y < gridWidth; y++){
                    
            	if (jlbGrid[x][y] == a_component) {            		
            		xy[0] = x;
            		xy[1] = y;
            	}
            }
		}
		return xy;
	}// end getPosition

	public void getDirection()
	{

		int x1, x2, y1, y2;
		x1 = xy1[0];
		y1 = xy1[1];
		x2 = xy2[0];
		y2 = xy2[1];

		// horizontal
		if (x1 == x2)
		{
			if (y1 < y2)/* East */
			{
				selectButton(x1, y1, x2, y2, EAST);
			} else if (y1 > y2)/* West */
				selectButton(x1, y1, x2, y2, WEST);
		}

		// vertical
		else if (y1 == y2)
		{
			if (x1 < x2)/* South */
				selectButton(x1, y1, x2, y2, SOUTH);
			else if (x1 > x2)/* North */
				selectButton(x1, y1, x2, y2, NORTH);
		}

		// diagonal
		else if (x1 > x2 && y1 > y2) /* North West */
			selectButton(x1, y1, x2, y2, NORTH_WEST);

		else if (x1 > x2 && y1 < y2) /* North East */
			selectButton(x1, y1, x2, y2, NORTH_EAST);

		else if (x1 < x2 && y1 > y2) /* South West */
			selectButton(x1, y1, x2, y2, SOUTH_WEST);

		else if (x1 < x2 && y1 < y2) /* South East */
			selectButton(x1, y1, x2, y2, SOUTH_EAST);
	}// end getDirection

	public void selectButton(int x1, int y1, int x2, int y2, int a_direction)
	{

		switch (a_direction)
		{
		case NORTH:
			while (x1 >= x2)
			{
				markButton(x1, y1);
				x1--;
			}
			break;
		case SOUTH:
			while (x1 <= x2)
			{
				markButton(x1, y1);
				x1++;
			}
			break;
		case WEST:
			while (y1 >= y2)
			{
				markButton(x1, y1);
				y1--;
			}
			break;
		case EAST:
			while (y1 <= y2)
			{
				markButton(x1, y1);
				y1++;
			}
			break;
		case NORTH_WEST:
			while (y1 >= y2 && x1 > -1)
			{
				markButton(x1, y1);
				x1--;
				y1--;
			}
			break;
		case NORTH_EAST:
			while (y1 <= y2 && x1 > -1)
			{
				markButton(x1, y1);
				x1--;
				y1++;
			}
			break;
		case SOUTH_WEST:
			while (y1 >= y2 && x1 < jlbGrid.length)
			{
				markButton(x1, y1);
				x1++;
				y1--;
			}
			break;
		case SOUTH_EAST:
			while (y1 <= y2 && x1 < jlbGrid.length)
			{
				markButton(x1, y1);
				x1++;
				y1++;
			}
			break;
		}// end switch

	}// end markButton

	public void markButton(int row, int col)
	{

		jlbGrid[row][col].setForeground(PuzzleConfig.textColorSelected);
		jlbGrid[row][col].setBackground(PuzzleConfig.cellbgColorSelected);
		selectedButtons.push(jlbGrid[row][col]);
		selectedWord += jlbGrid[row][col].getText();

	}// end markButton

	public void unMarkButton()
	{

		while (selectedButtons.isEmpty() == false)
		{

			JButton button = selectedButtons.pop();
			if (isFound(button))
			{
				button.setForeground(PuzzleConfig.textColorSelected);
				button.setBackground(PuzzleConfig.cellColorWordFound);
			} else
			{
				button.setForeground(PuzzleConfig.textColorUnselected);
				button.setBackground(UIManager.getColor("Button.background"));
			}
		}

	}

	public boolean isFound(JButton a_button)
	{
		for (int i = 0; i < foundWords.size(); i++)
		{
			if (a_button == foundWords.get(i))
				return true;
		}
		return false;
	}

	public void endGame()
	{

		time.stop();
		int answer = JOptionPane.showConfirmDialog(null,
				tracker.getGameStatusMsg(), "Awesome!!!",
				JOptionPane.YES_NO_OPTION);
		switch (answer)
		{
		case JOptionPane.OK_OPTION:
			buildGui(myPuzzleCollection.nextPuzzle());
			revalidate();
			repaint();
			break;
		case JOptionPane.NO_OPTION:
			System.exit(0);
			break;
		}// end switch

	}

	public static void main(String[] args)
	{

		// TESTS for puzzle 001
		// get 001 puzzle from the puzzlelist
		PuzzlePicker pp1 = new PuzzlePicker(
				"http://www.dropbox.com/PuzzleLauncher.html?id=001");

		// populates the puzzle collection
		PuzzleCollection collection = new PuzzleCollection();
		collection.setCurrentId(pp1.getPuzzleId());
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PuzzlePlayerFrame ppframe = new PuzzlePlayerFrame(collection);
		frame.setContentPane(ppframe);
		frame.pack();
		// Centers the frame
		frame.setLocationRelativeTo(null);

		// Makes it visible
		frame.setVisible(true);
	}

	private class ClockListener implements ActionListener
	{

		private int hours;
		private int minutes;
		private int seconds;
		private String hour;
		private String minute;
		private String second;
		private static final int N = 60;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			NumberFormat formatter = new DecimalFormat("00");
			if (seconds == N)
			{
				seconds = 00;
				minutes++;
			}

			if (minutes == N)
			{
				minutes = 00;
				hours++;
			}
			hour = formatter.format(hours);
			minute = formatter.format(minutes);
			second = formatter.format(seconds);
			jlbTimer.setText(String.valueOf(hour + ":" + minute + ":" + second));
			seconds++;
		}
	}

	@Override
	public void componentResized(ComponentEvent arg0)
	{

		Dimension sidePanelSize = sidePanel.getSize();
		wordsPanel.setBounds(0, 50, sidePanelSize.width,
				sidePanelSize.height - 50 - 70);
		Dimension wordsPanelSize = wordsPanel.getSize();
		timerPanel.setBounds(0, wordsPanelSize.height + 50,
				sidePanelSize.width, 70);
	}

	public static void errorMessage(String a_string)
	{

		JOptionPane.showMessageDialog(null, a_string, "Error",
				JOptionPane.ERROR_MESSAGE);
		System.exit(0);

	}

	@Override
	public void componentHidden(ComponentEvent arg0)
	{
	}

	@Override
	public void componentMoved(ComponentEvent arg0)
	{
	}

	@Override
	public void componentShown(ComponentEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
}
