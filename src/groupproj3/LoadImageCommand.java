package groupproj3;

/**
 * this is nearly an exact copy of the image command
 */
import java.awt.*;
import java.io.*;

/**
 * For undoing image creations
 *
 */
public class LoadImageCommand extends Command {
	private Image image;

	public LoadImageCommand(Point point) {
		image = new Image(point);
	}

	/**
	 * Sets the second end point
	 * 
	 * @param point
	 *            the second point
	 */
	public void setImagePoint(Point point) {
		image.setPoint2(point);
	}
	/**
	 * sets the file tied to the image
	 * 
	 * @param file
	 */
	public void setFile(File file) {
	    image.setFile(file);
	}
	/**
	 * Executes the command to add the item to the model
	 */
	@Override
	public void execute() {
		model.addItem(image);
	}

	/**
	 * Undoes the command by removing the item from the model
	 */
	@Override
	public boolean undo() {
		model.removeItem(image);
		return true;
	}

	/**
	 * Brings the image back by calling execute
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
		if (image.getPoint2() == null) {
			image.setPoint2(image.getPoint1());
		}
	}
}
