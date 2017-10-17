package groupproj3;

/**
 * 
 * @author Andre Stockling 
 */
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The button to create polygons. Processes the mouse movements and clicks calling
 * the appropriate methods of controller.
 *
 */
public class PolygonButton extends JButton implements ActionListener {
	protected JPanel drawingPanel;
	protected View view;
	private MouseHandler mouseHandler;
	private KeyHandler keyHandler;
	private PolygonCommand polygonCommand;

	/**
	 * Creates the button for the polygon
	 * 
	 * @param jFrame
	 *            the frame where the label is put
	 * @param jPanel
	 *            the panel within the frame
	 */
	public PolygonButton(View jFrame, JPanel jPanel) {
		super("Polygon");
		addActionListener(this);
		view = jFrame;
		drawingPanel = jPanel;
		mouseHandler = new MouseHandler();
		keyHandler = new KeyHandler();
	}

	/**
	 * Handle click for creating a new Polygon
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		drawingPanel.addMouseListener(mouseHandler);
	}

	/**
	 * Handles mouse click so that the points can now be captured.
	 * 
	 */
	private class MouseHandler extends MouseAdapter {
		ArrayList<Point> points = new ArrayList<Point>();
		boolean started = false;
		
		
		@Override
		public void mouseClicked(MouseEvent event) {
			if (started) {
				points.add(View.mapPoint(event.getPoint()));
				polygonCommand.addPoint(View.mapPoint(event.getPoint()));
			}
			else {
				points.add(View.mapPoint(event.getPoint()));
				polygonCommand = new PolygonCommand(points);
				UndoManager.instance().beginCommand(polygonCommand);
				drawingPanel.addFocusListener(keyHandler);
				drawingPanel.requestFocusInWindow();
				drawingPanel.addKeyListener(keyHandler);
				started = true;
			}
		}
		
		public void resetBoolean() {
			started = false;
		}
		
		public void resetArray() {
			points = new ArrayList<Point>();
		}
	}

	private class KeyHandler extends KeyAdapter implements FocusListener {
		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				drawingPanel.removeMouseListener(mouseHandler);
				drawingPanel.removeKeyListener(keyHandler);;
				view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				UndoManager.instance().endCommand(polygonCommand);
				mouseHandler.resetBoolean();
				mouseHandler.resetArray();
				view.refresh();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * When the panel gets the focus, starts listening for key strokes.
		 */
		@Override
		public void focusGained(FocusEvent event) {
			drawingPanel.addKeyListener(this);
		}

		/**
		 * When the panel loses focus, wraps up the command. Stops listening to
		 * key strokes.
		 */
		@Override
		public void focusLost(FocusEvent event) {
			view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			drawingPanel.removeMouseListener(mouseHandler);
			drawingPanel.removeKeyListener(keyHandler);
			UndoManager.instance().endCommand(polygonCommand);
			mouseHandler.resetBoolean();
			mouseHandler.resetArray();
			view.refresh();
		}
	}

}