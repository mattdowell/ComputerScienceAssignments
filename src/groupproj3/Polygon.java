package groupproj3;

/**
 * 
 * @author Andre Stockling
 */
import java.awt.Point;
import java.util.ArrayList;

/**
 * Represents a line
 *
 */
public class Polygon extends Item {
	private static final long serialVersionUID = 2L;
	private ArrayList<Point> points = new ArrayList<Point>();

	/**
	 * Creates a Polygon with the given endpoints
	 * 
	 * @param myPoints
	 *      An arraylist containing all of the endpoints of the polygon
	 */
	public Polygon(ArrayList<Point> myPoints) {
		for (int i = 0; i < myPoints.size(); i++) {
			points.add(myPoints.get(i));
		}
	}

//	/**
//	 * Creates a Polygon with one endpoint
//	 * 
//	 * @param point1
//	 *            one endpoint
//	 */
//	public Polygon(Point point1) {
//		ArrayList<Point> tempList = new ArrayList<Point>();
//		tempList.add(point1);
//		this(tempList);
//	}
//
//	/**
//	 * Creates a line with no specific endpoints
//	 */
//	public Line() {
//	}

	/**
	 * Checks whether the given point falls within the line
	 * 
	 * @return true iff the given point is close to one of the endpoints
	 */
	@Override
	public boolean includes(Point point) {
		for (int i = 0; i < points.size(); i ++) {
			if (distance(point, points.get(i)) < 10.0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Displays the line
	 */
	@Override
	public void render() {
		uiContext.draw(this);
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void setPoints(ArrayList<Point> newPoints) {
		points = newPoints;
	}

//	/**
//	 * Sets one of the endpoints
//	 * 
//	 * @param point
//	 *            an endpoint
//	 */
//	public void setPoint1(Point point) {
//		point1 = point;
//	}
//
//	/**
//	 * Sets one of the endpoints
//	 * 
//	 * @param point
//	 *            an endpoint
//	 */
//	public void setPoint2(Point point) {
//		point2 = point;
//	}
//
//	/**
//	 * Returns one of the endpoints
//	 * 
//	 * @return an endpoint
//	 */
//	public Point getPoint1() {
//		return point1;
//	}
//
//	/**
//	 * Returns one of the endpoints
//	 * 
//	 * @return an endpoint
//	 */
//	public Point getPoint2() {
//		return point2;
//	}

	/**
	 * Returns a string representation of the line
	 * 
	 * @return a string representation
	 */
	@Override
	public String toString() {
		return "Polygon  from " + "point1" + " to " + "point2";
	}
}