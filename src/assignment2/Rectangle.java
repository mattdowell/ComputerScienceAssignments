package assignment2;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This extends Figure and implements the functionality needed to draw filled
 * rectangles with a given color.
 * 
 * @author Matt Dowell
 * 
 */
public class Rectangle extends Figure {

	int length;
	int width;
	int x;
	int y;

	/**
	 * 
	 * @param x upper left x coord
	 * @param y upper left y coord
	 * @param length length of rect
	 * @param width of rect
	 * @param color
	 */
	public Rectangle(int x, int y, int length, int width, Color color) {
		super(color);
		this.length = length;
		this.width = width;
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws the figure using the given Graphics parameter
	 * 
	 * @param graphics
	 *            the Graphics object for drawing the figure
	 */
	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		graphics.fillRect(getX(), getY(), getLength(), getWidth());
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Rectangle [length=" + length + ", width=" + width + ", x=" + x + ", y=" + y + "]";
	}

}
