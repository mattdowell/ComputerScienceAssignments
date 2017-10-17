package groupproj3;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;

/**
 * A UI that uses the swing package
 *
 */
public class NewSwingUI implements UIContext {
	private Graphics graphics;
	private static NewSwingUI swingUI;

	/**
	 * For the singleton pattern
	 */
	private NewSwingUI() {
	}

	/**
	 * Returns the instance
	 * 
	 * @return the instance
	 */
	public static NewSwingUI getInstance() {
		if (swingUI == null) {
			swingUI = new NewSwingUI();
		}
		return swingUI;
	}

	/**
	 * The Graphics object for drawing
	 * 
	 * @param graphics
	 *            the Graphics object
	 */
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	/**
	 * Draws a label
	 * 
	 * @param label
	 *            the label
	 */
	@Override
	public void draw(Label label) {
		
		if (label.getFont() != null) {
			graphics.setFont(label.getFont());
		}
		
		if (label.getStartingPoint() != null) {
			if (label.getText() != null) {
				graphics.drawString(label.getText(), (int) label
						.getStartingPoint().getX(), (int) label
						.getStartingPoint().getY());
			}
		}
		int length = graphics.getFontMetrics().stringWidth(label.getText());
		graphics.drawString("_",
				(int) label.getStartingPoint().getX() + length, (int) label
						.getStartingPoint().getY());
	}

	/**
	 * Draws a line
	 * 
	 * @param line
	 *            the line to be drawn
	 */
	@Override
	public void draw(Line line) {
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		if (line.getPoint1() != null) {
			i1 = Math.round((float) (line.getPoint1().getX()));
			i2 = Math.round((float) (line.getPoint1().getY()));
			if (line.getPoint2() != null) {
				i3 = Math.round((float) (line.getPoint2().getX()));
				i4 = Math.round((float) (line.getPoint2().getY()));
			} else {
				i3 = i1;
				i4 = i2;
			}
			graphics.drawLine(i1, i2, i3, i4);
		}
	}
	
	/**
	 * Draws a Polygon
	 * 
	 * @param polygon
	 *            the polygon to be drawn
	 */
	@Override
	public void draw(Polygon polygon) {
		ArrayList<Point> points = polygon.getPoints();
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			if (points.get(i) != null) {
				i1 = Math.round((float) (points.get(i).getX()));
				i2 = Math.round((float) (points.get(i).getY()));
				if (points.get(i + 1) != null) {
					i3 = Math.round((float) (points.get(i + 1).getX()));
					i4 = Math.round((float) (points.get(i + 1).getY()));
				} 
				else {
					i3 = i1;
					i4 = i2;
				}
				graphics.drawLine(i1, i2, i3, i4);
			}
		}
	}

	/**
	 * Captures undefined items
	 * 
	 * @param item
	 *            the item
	 */
	@Override
	public void draw(Item item) {
		System.out.println("Cant draw unknown Item \n");
	}
/**
 * defines image, and retains file status until necessary to turn file into a 
 * BufferedImage
 */
    @Override
    public void draw(Image image) {
        BufferedImage myImage = null;
        if(image.getFile() != null  ) {
            try {
            myImage = ImageIO.read(image.getFile());
        } catch ( IOException x ) {
            x.printStackTrace();
        }
            int i1 = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            if (image.getPoint1() != null) {
                i1 = Math.round((float) (image.getPoint1().getX()));
                i2 = Math.round((float) (image.getPoint1().getY()));
                if (image.getPoint2() != null) {
                    i3 = Math.round((float) (image.getPoint2().getX()));
                    i4 = Math.round((float) (image.getPoint2().getY()));
                } else {
                    i3 = i1;
                    i4 = i2;
                }
                graphics.drawImage(myImage, i1, i2, i3, 
                    i4, 0, 0, myImage.getWidth(), myImage.getHeight(), null);
            }
        }
    }
}