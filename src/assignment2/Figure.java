package assignment2;

/**
 * Author Brahma Dathan
 * Date: January 15, 2015
 */
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Serves as a type for all figures in the simple drawing program. Some
 * implementation might be added at a future date.
 * 
 * @author Brahma Dathan
 * 
 */
public abstract class Figure implements Serializable {

	private Color color;

	/**
	 * All figures have a color, so this is the common constructor
	 * @param color
	 */
	public Figure(Color color) {
		super();
		this.color = color;
	}

	/**
	 * Draws the figure using the given Graphics parameter
	 * 
	 * @param graphics
	 *            the Graphics object for drawing the figure
	 */
	public void draw(Graphics graphics) {
		graphics.setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
