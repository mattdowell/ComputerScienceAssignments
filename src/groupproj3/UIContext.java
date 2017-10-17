package groupproj3;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath, Modified by Ben, Matt, Andre
 *       
 *
 */
public interface UIContext {
	// public abstract void drawCircle(Circle circle);
	/**
	 * Draw the line
	 * 
	 * @param line
	 *            the line
	 */
	public abstract void draw(Line line);

	/**
	 * Draw the Polygon
	 * 
	 * @param Polygon
	 *            the polygon
	 */
	public abstract void draw(Polygon polygon);
	
	/**
	 * Draw the label
	 * 
	 * @param label
	 *            the label
	 */
	public abstract void draw(Label label);
	/**
	 * Draw the Image
	 * 
	 * @param image
	 *           the image
	 */
	public abstract void draw(Image image);
	/**
	 * Draws unspecified items
	 * 
	 * @param item
	 *            the item
	 */
	public abstract void draw(Item item);
}
