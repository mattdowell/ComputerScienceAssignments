package org.ics141.demo.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Rocket {
	public static void main(String[] args) {

		JFrame frame = new JFrame("Rocket");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new RocketPanel());
		frame.pack();
		frame.setVisible(true);
	}
}

class RocketPanel extends JPanel {
	private int[] xRocket = { 100, 120, 120, 130, 130, 70, 70, 80, 80 };
	private int[] yRocket = { 15, 40, 115, 125, 150, 150, 125, 115, 40 };
	private int[] xFlame = { 70, 70, 75, 80, 90, 100, 110, 115, 120, 130, 130 };
	private int[] yFlame = { 155, 170, 165, 190, 170, 175, 160, 185, 160, 175, 155 };

	public RocketPanel() {
		setBackground(Color.black);
		setPreferredSize(new Dimension(200, 200));
	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		page.setColor(Color.yellow);
		page.fillPolygon(xRocket, yRocket, xRocket.length);
		page.setColor(Color.red);
		page.drawPolyline(xFlame, yFlame, xFlame.length);
	}
}