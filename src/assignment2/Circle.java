package assignment2;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Circle: This extends Figure and implements the functionality needed to draw
 * filled circles with a given color.
 * 
 * @author Matt Dowell
 * 
 */
public class Circle extends Figure {

	private int x;
	private int y;
	private int radius;

	/**
	 * Constructor takes in all the required parameters.
	 * 
	 * @param x upper left corder x coord
	 * @param y upper left corder y coord
	 * @param radius of circle
	 * @param inColor color
	 */
	public Circle(int x, int y, int radius, Color inColor) {
		super(inColor);
		this.x = x;
		this.y = y;
		this.radius = radius;
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
		
		int r = (int) (getRadius() * 1.5);

		/*
		 * , Graph x - the x coordinate of the upper left corner of the oval
		 * to be filled. y - the y coordinate of the upper left corner of
		 * the oval to be filled.
		 */
		graphics.fillOval(getX(), getY(), r, r);
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

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public String toString() {
		return "Circle [x=" + x + ", y=" + y + ", radius=" + radius + "]";
	}

	
}
