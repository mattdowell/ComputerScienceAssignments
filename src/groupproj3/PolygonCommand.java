package groupproj3;

/**
 * 
 * @author Andre Stockling
 */
import java.awt.Point;
import java.util.ArrayList;

/**
 * For undoing polygon creations
 *
 */
public class PolygonCommand extends Command {
	private Polygon polygon;

	public PolygonCommand(ArrayList<Point> points) {
		polygon = new Polygon(points);
	}
	
	public void addPoint(Point newPoint) {
		polygon.getPoints().add(newPoint);
	}
	
//	/**
//	 * Creates a polygon command with one end point.
//	 * 
//	 * @param point
//	 *            one of the end points
//	 */
//	public LineCommand(Point point) {
//		line = new Line(point);
//	}

//	/**
//	 * Sets the second end point
//	 * 
//	 * @param point
//	 *            the second point
//	 */
//	public void setLinePoint(Point point) {
//		line.setPoint2(point);
//	}

	/**
	 * Executes the command to add the item to the model
	 */
	@Override
	public void execute() {
		model.addItem(polygon);
	}

	/**
	 * Undoes the command by removing the item from the model
	 */
	@Override
	public boolean undo() {
		model.removeItem(polygon);
		return true;
	}

	/**
	 * Brings the polygon back by calling execute
	 */
	@Override
	public boolean redo() {
		execute();
		return true;
	}

	/**
	 * Ends the command by setting the second end point the same as the first,
	 * if needed
	 */
	@Override
	public void end() {

	}
}
