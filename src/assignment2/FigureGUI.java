package assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 * Main class of the figure GUI
 * 
 * @author Matt
 *
 */
public class FigureGUI extends JFrame implements ActionListener {

	// My figure storage so we know what we have written or not
	List<Figure> myFigures = new ArrayList<>();

	// POssible actiobs
	private static enum Action {
		RECTANGLE, CIRCLE
	};

	// Store which color we have clicked then convert to AWT color
	private static enum ClickedColor {
		RED, GREEN, BLUE
	};

	// Color buttons
	private JButton redButton = new JButton("Red");
	private JButton greenButton = new JButton("Green");
	private JButton blueButton = new JButton("Blue");

	// Shape buttons
	private JButton rectangleButton = new JButton("Rectangle");
	private JButton circleButton = new JButton("Circle");
	private JButton exitButton = new JButton("Exit");

	private JTextArea listArea = new JTextArea(12, 25);
	private GregorianCalendar currentDate;
	private FiguresPanel figuresPanel = new FiguresPanel();
	private Action action = Action.RECTANGLE;
	private ClickedColor color = ClickedColor.RED;
	private String formattedDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
	private Label dateHolder = new Label();

	/**
	 * Figure panel. This is separate because we are clicking in it and
	 * repainting it.
	 * 
	 * @author Matt
	 *
	 */
	public class FiguresPanel extends JPanel implements MouseListener {

		private MouseEvent firstClick = null;

		/**
		 * The figures panel has two rows, and displays the current date at the
		 * bottom.
		 * 
		 * @param arg0
		 */
		public FiguresPanel() {
			super();
		}

		/**
		 * Place the figures when the comp. is repainted.
		 * 
		 * @param g
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// draw all figures
			placeFigures(g);
		}

		/**
		 * For testing, this gives us the ability to see the border
		 * 
		 * @return
		 */
		@Override
		public Border getBorder() {
			return getDebugBorder();
		}

		/**
		 * Sets the preferred size of the drawing component.
		 * 
		 * @return
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(290, 390);
		}

		/**
		 * This method takes in a graphics context, then reads all the figures
		 * on disk, and draws them based upon their persisted dimensions and
		 * color.
		 * 
		 * @param inG
		 */
		public void placeFigures(Graphics inG) {
			try {
				List<Figure> figures = SerialUtil.readFigures();

				for (Figure f : figures) {
					debug(f);
					drawFigure(f, inG);
				}

				myFigures = figures;

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		/**
		 * Waits for the mouse click and creates the appropriate figures.
		 */
		@Override
		public void mouseClicked(MouseEvent event) {

			if (firstClick == null) {

				debug(" X: " + event.getX() + " Y: " + event.getY());
				// Just set the first mark
				firstClick = event;

			} else {

				int distance = (int) getDistance(firstClick, event);
				debug("Distance: " + distance);

				switch (action) {
				case RECTANGLE:
					int width = event.getX() - firstClick.getX();
					int height = event.getY() - firstClick.getY();
					if (height < 1) {
						height = height * -1;
					}
					if (width < 1) {
						width = width * -1;
					}

					Rectangle r = new Rectangle(firstClick.getX(), firstClick.getY(), width, height, determineColor());
					handleFigure(r);
					drawFigure(r, getGraphics());
					break;

				case CIRCLE:
					int newX = (int) (firstClick.getX() - distance / 2);
					int newY = (int) (firstClick.getY() - distance / 2);

					Circle c = new Circle(newX, newY, distance, determineColor());
					handleFigure(c);
					// Todo: Verify the center.
					drawFigure(c, getGraphics());
					break;

				default:
					break;
				}

				// Null out the first click again.
				firstClick = null;
				debug("First click: " + firstClick);
			}

		}

		/**
		 * 
		 * @return Color based upon button click
		 */
		private Color determineColor() {
			Color theColor = null;
			if (color == ClickedColor.RED) {
				theColor = Color.RED;
			} else if (color == ClickedColor.GREEN) {
				theColor = Color.GREEN;
			} else if (color == ClickedColor.BLUE) {
				theColor = Color.BLUE;
			}
			return theColor;
		}

		/**
		 * Writes the new figures.
		 * 
		 * @param inF
		 */
		private void handleFigure(Figure inF) {
			myFigures.add(inF);
			try {
				SerialUtil.writeFigures(myFigures);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}

		/**
		 * The figure draws itself given the graphics context.
		 * 
		 * @param inFig
		 * @param graphics
		 */
		public void drawFigure(Figure inFig, Graphics graphics) {
			inFig.draw(graphics);
			listArea.append(inFig.toString() + "\n");
		}

		/**
		 * 
		 * @param event1
		 * @param event2
		 * @return
		 */
		private double getDistance(MouseEvent event1, MouseEvent event2) {
			int x1 = event1.getX();
			int x2 = event2.getX();
			int y1 = event1.getY();
			int y2 = event2.getY();
			return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		}

		/**
		 * Not used
		 */
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		/**
		 * Not used
		 */
		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		/**
		 * Not used
		 */
		@Override
		public void mousePressed(MouseEvent event) {
		}

		/**
		 * Not used
		 */
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}

	/**
	 * Sets up the entire interface.
	 */
	public FigureGUI() {

		super("Figures GUI");

		JPanel buttonPanel = new JPanel(new GridLayout(2, 3));
		buttonPanel.add(redButton);
		buttonPanel.add(greenButton);
		buttonPanel.add(blueButton);
		buttonPanel.add(rectangleButton);
		buttonPanel.add(circleButton);
		buttonPanel.add(exitButton);
		buttonPanel.setSize(new Dimension(250, 300));

		buttonPanel.setBorder(getDebugBorder());

		JPanel listPanel = new JPanel();
		listPanel.add(new JScrollPane(listArea));
		listPanel.setBorder(getDebugBorder());
		listPanel.setSize(new Dimension(350, 350));

		// Mouse listeners
		figuresPanel.addMouseListener(figuresPanel);
		redButton.addActionListener(this);
		greenButton.addActionListener(this);
		blueButton.addActionListener(this);
		rectangleButton.addActionListener(this);
		circleButton.addActionListener(this);
		exitButton.addActionListener(this);

		currentDate = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		formattedDate = dateFormat.format(currentDate.getTime());

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(figuresPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		mainPanel.add(buttonPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		mainPanel.add(listPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		dateHolder.setText(formattedDate);
		dateHolder.setSize(new Dimension(300, 25));
		mainPanel.add(dateHolder, c);
		mainPanel.setBorder(getDebugBorder());

		mainPanel.setSize(new Dimension(900, 425));

		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 500);
	}

	/**
	 * Listener for all buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == this.exitButton) {
			System.exit(0);
		} else if (event.getSource() == this.rectangleButton) {
			action = Action.RECTANGLE;
		} else if (event.getSource() == this.circleButton) {
			action = Action.CIRCLE;
		} else if (event.getSource() == this.redButton) {
			color = ClickedColor.RED;
		} else if (event.getSource() == this.greenButton) {
			color = ClickedColor.GREEN;
		} else if (event.getSource() == this.blueButton) {
			color = ClickedColor.BLUE;
		}
	}

	/**
	 * The method creates an FigureGUI object
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		new FigureGUI();
	}

	private void debug(Object in) {
		// System.out.println(String.valueOf(in));
	}

	private Border getDebugBorder() {
		// return BorderFactory.createLineBorder(Color.black);
		return BorderFactory.createEmptyBorder();
	}
}
