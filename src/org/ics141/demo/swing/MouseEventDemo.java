package org.ics141.demo.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseEventDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("GreenDots");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GreenDotsPanel());
		frame.pack();
		frame.setVisible(true);
	}
}

class GreenDotsPanel extends JPanel {
	private final int SIZE = 6;
	private ArrayList<Point> pointList;

	public GreenDotsPanel() {
		pointList = new ArrayList<Point>();
		addMouseListener(new GreenDotsListener());
		setBackground(Color.black);
		setPreferredSize(new Dimension(300, 200));
	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		page.setColor(Color.green);
		for (Point spot : pointList) {
			page.fillOval(spot.x - SIZE, spot.y - SIZE, SIZE * 2, SIZE * 2);
		}
		page.drawString("Count: " + pointList.size(), 5, 15);
	}

	private class GreenDotsListener implements MouseListener {
		public void mousePressed(MouseEvent event) {
			pointList.add(event.getPoint());
			repaint();
		}

		public void mouseClicked(MouseEvent event) {
		}

		public void mouseReleased(MouseEvent event) {
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}
	}
}
