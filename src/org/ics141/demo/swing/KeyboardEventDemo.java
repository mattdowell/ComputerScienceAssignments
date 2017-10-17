package org.ics141.demo.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KeyboardEventDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Direction");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new DirectionPanel());
		frame.pack();
		frame.setVisible(true);
	}

}

class DirectionPanel extends JPanel {
	private final int WIDTH = 300, HEIGHT = 200;
	private final int JUMP = 10; // increment for image movement
	private final int SIZE = 6;
	private int x, y;

	public DirectionPanel() {
		addKeyListener(new DirectionListener());
		x = WIDTH / 2;
		y = HEIGHT / 2;
		setBackground(Color.black);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		page.setColor(Color.green);
		page.fillOval(x - SIZE, y - SIZE, SIZE * 2, SIZE * 2);
	}

	private class DirectionListener implements KeyListener {
		public void keyPressed(KeyEvent event) {
			switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				y -= JUMP;
				break;
			case KeyEvent.VK_DOWN:
				y += JUMP;
				break;
			case KeyEvent.VK_LEFT:
				x -= JUMP;
				break;
			case KeyEvent.VK_RIGHT:
				x += JUMP;
				break;
			}
			repaint();
		}

		public void keyTyped(KeyEvent event) {
		}

		public void keyReleased(KeyEvent event) {
		}
	}
}
