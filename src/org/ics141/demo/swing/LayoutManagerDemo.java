package org.ics141.demo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class LayoutManagerDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Layout Manager Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tp = new JTabbedPane();
		tp.addTab("Introduction", new IntroPanel());
		tp.addTab("Flow", new FlowPanel());
		tp.addTab("Border", new BorderPanel());
		tp.addTab("Grid", new GridPanel());
		tp.addTab("Box", new BoxPanel());
		frame.getContentPane().add(tp);
		frame.pack();
		frame.setVisible(true);
	}
}

class IntroPanel extends JPanel {
	public IntroPanel() {
		JLabel lb1 = new JLabel("Layout Manager Demo");
		JLabel lb2 = new JLabel("Choose a tab");
		add(lb1);
		add(lb2);
	}
}

class FlowPanel extends JPanel {
	public FlowPanel() {
		setLayout(new FlowLayout());
		setBackground(Color.yellow);
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		JButton b3 = new JButton("Button 3");
		JButton b4 = new JButton("Button 4");
		JButton b5 = new JButton("Button 5");
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
	}
}

class BorderPanel extends JPanel {
	public BorderPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.yellow);
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		JButton b3 = new JButton("Button 3");
		JButton b4 = new JButton("Button 4");
		JButton b5 = new JButton("Button 5");
		add(b1, BorderLayout.CENTER);
		add(b2, BorderLayout.NORTH);
		add(b3, BorderLayout.SOUTH);
		add(b4, BorderLayout.EAST);
		add(b5, BorderLayout.WEST);
	}
}

class GridPanel extends JPanel {
	public GridPanel() {
		setLayout(new GridLayout(0, 3));
		setBackground(Color.yellow);
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		JButton b3 = new JButton("Button 3");
		JButton b4 = new JButton("Button 4");
		JButton b5 = new JButton("Button 5");
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
	}
}

class BoxPanel extends JPanel {
	public BoxPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.yellow);
		JButton b1 = new JButton("Button 1");
		JButton b2 = new JButton("Button 2");
		JButton b3 = new JButton("Button 3");
		JButton b4 = new JButton("Button 4");
		JButton b5 = new JButton("Button 5");
		add(b1);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(b2);
		add(Box.createVerticalGlue());
		add(b3);
		add(b4);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(b5);
	}
}