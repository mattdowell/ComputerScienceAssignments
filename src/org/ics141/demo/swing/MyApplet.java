package org.ics141.demo.swing;

import javax.swing.JApplet;
import java.awt.*;

public class MyApplet extends JApplet {
	public void paint(Graphics page) {
		page.drawRect(50, 50, 40, 40);
		page.drawRect(60, 80, 225, 30);
		page.drawOval(75, 65, 20, 20);
		page.drawLine(35, 60, 100, 120);
		page.drawString("Math is a science, so as computers.", 110, 70);
		page.drawString("-- Goldmen Art", 130, 100);
	}
}
