package groupproj3;


import java.awt.*;
import java.io.*;

/**
 * Represents a image
 *
 */
public class Image extends Item {
	private static final long serialVersionUID = 1L;
	private Point point1;
	private Point point2;
	private File file;
	public Image(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	/**
	 * Creates a image with one endpoint
	 * 
	 * @param point1
	 *            one endpoint
	 */
	public Image(Point point1) {
		this(point1, null);
	}

	/**
	 * Creates a image with no specific endpoints
	 */
	public Image() {
	}

	/**
	 * Checks whether the given point falls within the image
	 * 
	 * @return true iff the given point is close to one of the endpoints
	 */
	@Override
	public boolean includes(Point point) {
		return ((distance(point, point1) < 10.0) || (distance(point, point2) < 10.0));
	}

	/**
	 * Displays the image
	 */
	@Override
	public void render() {
		uiContext.draw(this);
	}

	/**
	 * Sets one of the endpoints
	 * 
	 * @param point
	 *            an endpoint
	 */
	public void setPoint1(Point point) {
		point1 = point;
	}

	/**
	 * Sets one of the endpoints
	 * 
	 * @param point
	 *            an endpoint
	 */
	public void setPoint2(Point point) {
		point2 = point;
	}

	public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    /**
	 * Returns one of the endpoints
	 * 
	 * @return an endpoint
	 */
	public Point getPoint1() {
		return point1;
	}

	/**
	 * Returns one of the endpoints
	 * 
	 * @return an endpoint
	 */
	public Point getPoint2() {
		return point2;
	}

    /**
	 * Returns a string representation of the image
	 * 
	 * @return a string representation
	 */
	@Override
	public String toString() {
		return "Image  from " + point1 + " to " + point2;
	}
}