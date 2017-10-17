package groupproj3;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 * 
 *            Redistribution and use with or without modification, are permitted provided that the following conditions
 *            are met:
 *
 *            - the use is for academic purpose only - Redistributions of source code must retain the above copyright
 *            notice, this list of conditions and the following disclaimer. - Neither the name of Brahma Dathan or
 *            Sarnath Ramnath may be used to endorse or promote products derived from this software without specific
 *            prior written permission.
 *
 *            The authors do not make any claims regarding the correctness of the code in this module and are not
 *            responsible for any loss or damage resulting from its use.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.*;

import javax.swing.*;

/**
 * The view of the MC architecture
 *
 */
public class View extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private UIContext uiContext;
	private JPanel drawingPanel;
	private JPanel buttonPanel;
	private JPanel topPanel;
	private JButton lineButton;
	private JButton deleteButton;
	private JButton labelButton;
	private JButton selectButton;
	private JButton saveButton;
	private JButton openButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton imageButton;
	private JButton polygonButton;

	private JPanel fontPanel;
	private JComboBox<String> fontSelector;
	private JComboBox<Integer> fontSizeSelector;
	private JCheckBox boldBox;
	private JCheckBox italicBox;
	private JCheckBox underlineBox;

	private String fileName;
	// other buttons to be added as needed;
	private static Model model;

	/**
	 * Returns the UIContext
	 * 
	 * @return the UIContext
	 */
	public UIContext getUI() {
		return uiContext;
	}

	/**
	 * Sets the UIContext
	 * 
	 * @param uiContext
	 *            the UIContext to be used
	 */
	private void setUI(UIContext uiContext) {
		this.uiContext = uiContext;
	}

	/**
	 * Sets the mode
	 * 
	 * @param model
	 *            the model
	 */
	public static void setModel(Model model) {
		View.model = model;
	}

	/**
	 * The panel where drawing occurs
	 *
	 */
	private class DrawingPanel extends JPanel {
		private MouseListener currentMouseListener;
		private KeyListener currentKeyListener;
		private FocusListener currentFocusListener;

		/**
		 * Paints the panel
		 * 
		 * @param graphics
		 *            the Graphics object
		 */
		@Override
		public void paintComponent(Graphics graphics) {
			model.setUI(NewSwingUI.getInstance());
			super.paintComponent(graphics);
			(NewSwingUI.getInstance()).setGraphics(graphics);
			graphics.setColor(Color.BLUE);
			Iterator<Item> iterator = model.getItems();
			while (iterator.hasNext()) {
				iterator.next().render();
			}
			graphics.setColor(Color.RED);
			iterator = model.getSelectedItems();
			while (iterator.hasNext()) {
				iterator.next().render();
			}
		}

		/**
		 * Makes the given object the listener to mouse events
		 * 
		 * @param newListener
		 *            the new listener
		 */
		@Override
		public void addMouseListener(MouseListener newListener) {
			removeMouseListener(currentMouseListener);
			currentMouseListener = newListener;
			super.addMouseListener(newListener);
		}

		/**
		 * Makes the given object the listener to keyboard events
		 * 
		 * @param newListener
		 *            the new listener
		 */
		@Override
		public void addKeyListener(KeyListener newListener) {
			removeKeyListener(currentKeyListener);
			currentKeyListener = newListener;
			super.addKeyListener(newListener);
		}

		/**
		 * When the component gains or loses the keyboard focus, the relevant
		 * method in the listener object is invoked
		 * 
		 * @param newListener
		 *            the new listener
		 */
		@Override
		public void addFocusListener(FocusListener newListener) {
			removeFocusListener(currentFocusListener);
			currentFocusListener = newListener;
			super.addFocusListener(newListener);
		}
	}

	/**
	 * Remembers the file name for storing in the title
	 * 
	 * @param fileName
	 *            file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
		setTitle("Drawing Program 1.1  " + fileName);
	}

	/**
	 * returns the file name
	 * 
	 * @return file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Initializes the view by creating all the widgets
	 */
	public View() {
		super("Drawing Program 1.2");
		fileName = null;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		model.setUI(NewSwingUI.getInstance());
		drawingPanel = new DrawingPanel();
		buttonPanel = new JPanel();
		fontPanel = new JPanel();
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		Container contentpane = getContentPane();

		contentpane.add(topPanel, "North");
		drawingPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		contentpane.add(drawingPanel);

		lineButton = new LineButton(this, drawingPanel);
		polygonButton = new PolygonButton(this, drawingPanel);
		labelButton = new LabelButton(this, drawingPanel);
		selectButton = new SelectButton(this, drawingPanel);
		imageButton = new LoadImageButton(this, drawingPanel);
		deleteButton = new DeleteButton();
		saveButton = new SaveButton(this);
		openButton = new OpenButton(this);
		undoButton = new UndoButton();
		redoButton = new RedoButton();

		buttonPanel.add(lineButton);
		buttonPanel.add(polygonButton);
		buttonPanel.add(labelButton);
		buttonPanel.add(imageButton);
		buttonPanel.add(selectButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(openButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);

		topPanel.add(buttonPanel);
		topPanel.add(fontPanel);
		fontSelector = new JComboBox<String>();
		fontSizeSelector = new JComboBox<Integer>();
		boldBox = new JCheckBox();
		italicBox = new JCheckBox();
		underlineBox = new JCheckBox();
		fontPanel.add(new JLabel("Select font:"));
		fontPanel.add(fontSelector);
		fontPanel.add(new JLabel("Size:"));
		fontPanel.add(fontSizeSelector);
		fontPanel.add(new JLabel("Bold:"));
		fontPanel.add(boldBox);
		fontPanel.add(new JLabel("Italic:"));
		fontPanel.add(italicBox);
		fontPanel.add(new JLabel("Underline:"));
		fontPanel.add(underlineBox);

		fontSelector.setEditable(false);
		Font[] fonts = getAllAvailableFonts();
		for (int i = 0; i < fonts.length; i++) {
			fontSelector.addItem(fonts[i].getFontName());
		}

		fontSizeSelector.setEditable(false);

		fontSizeSelector.addItem(new Integer(10));
		fontSizeSelector.addItem(new Integer(12));
		fontSizeSelector.addItem(new Integer(14));
		fontSizeSelector.addItem(new Integer(18));
		fontSizeSelector.addItem(new Integer(20));

		this.setSize(600, 400);
	}

	private Font[] getAllAvailableFonts() {
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = e.getAllFonts(); // Get the fonts
		return fonts;
	}

	/**
	 * Catches updates from the controller
	 */
	public void refresh() {
		// code to access the Model update the contents of the drawing panel.
		drawingPanel.repaint();
	}

	/**
	 * maps a point on the drawing panel to a point on the figure being created.
	 * 
	 * @param point
	 *            the GUI point
	 * @return translated point
	 */
	public static Point mapPoint(Point point) {
		return point;
	}

	/**
	 * Catches updates from model
	 */
	@Override
	public void update(Observable model, Object info) {
		refresh();
	}

	public JComboBox<String> getFontSelector() {
		return fontSelector;
	}

	public JComboBox<Integer> getFontSizeSelector() {
		return fontSizeSelector;
	}

	public JCheckBox getBoldBox() {
		return boldBox;
	}

	public JCheckBox getUnderlineBox() {
		return underlineBox;
	}

	public JCheckBox getItalicBox() {
		return italicBox;
	}

}