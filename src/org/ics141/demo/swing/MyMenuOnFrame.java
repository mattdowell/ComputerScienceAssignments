package org.ics141.demo.swing;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MyMenuOnFrame extends JFrame implements ActionListener {
	private JTextField messageTextField;
	private JMenuItem guessMenu, blankMenu, closeMenu;

	public static void main(String args[]) {
		MyMenuOnFrame frame = new MyMenuOnFrame();
	}

	public MyMenuOnFrame() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu animationMenu = new JMenu("Animation");
		guessMenu = new JMenuItem("Guess");
		blankMenu = new JMenuItem("Blank");
		closeMenu = new JMenuItem("Close");
		gameMenu.setMnemonic('G');
		animationMenu.setMnemonic('A');
		this.setJMenuBar(menuBar); //add menu bar to current frame
		menuBar.add(gameMenu);
		menuBar.add(animationMenu);
		gameMenu.add(guessMenu);
		gameMenu.add(blankMenu);
		gameMenu.add(closeMenu);
		JLabel messageLabel = new JLabel("Guess What:");
		messageTextField = new JTextField(15);
		Container c = this.getContentPane(); //do something to the frame
		c.setLayout(new FlowLayout());
		c.add(messageLabel);
		c.add(messageTextField);
		guessMenu.addActionListener(this); //subscribe events
		blankMenu.addActionListener(this);
		closeMenu.addActionListener(this);
		this.setSize(350, 170); //set the size of the frame
		this.setTitle("My Menu on Frame");
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				shutDown();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		Object sourceObject = e.getSource();
		if (sourceObject == guessMenu) {
			guessMessage();
		} else if (sourceObject == blankMenu) {
			blankMessage();
		} else if (sourceObject == closeMenu) {
			shutDown();
		}
	}

	public void guessMessage() {
		messageTextField.setText("Big Boo!");
	}

	public void blankMessage() {
		messageTextField.setText(" ");
	}

	public void shutDown() {
		System.exit(0);
	}
}