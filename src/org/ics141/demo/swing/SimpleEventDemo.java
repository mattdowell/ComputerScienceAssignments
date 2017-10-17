package org.ics141.demo.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SimpleEventDemo extends JFrame implements ActionListener { 
																		

	private JLabel inputPrompt = new JLabel("Input an integer number ");
	private JTextField inputNumber = new JTextField(10);
	private JButton actionButton = new JButton("Action Button");
	private JTextArea output = new JTextArea(3, 20);

	public static void main(String args[]) {
		JFrame frame = new SimpleEventDemo();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);		
	}
	
	public SimpleEventDemo() {
		JPanel upperPanel = new JPanel();
		upperPanel.add(inputPrompt);
		upperPanel.add(inputNumber);
		JPanel lowPanel = new JPanel();
		lowPanel.setLayout(new GridLayout(0, 1, 5, 5));
		lowPanel.add(actionButton);
		lowPanel.add(output);
		getContentPane().setLayout(new BorderLayout(5, 10));
		getContentPane().add(upperPanel, BorderLayout.NORTH);
		getContentPane().add(lowPanel, BorderLayout.SOUTH);
		inputNumber.requestFocus();
		actionButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) { // line 25
		String inputStr = inputNumber.getText();
		double outputFractionalNb;
		NumberConverter nC = new NumberConverter();
		Object sourceObject = event.getSource();
		if (sourceObject == actionButton) {
			double inputNb = Double.parseDouble(inputStr);
			outputFractionalNb = nC.toConvert(inputNb);
			output.setText(" The output fractional number is: " + outputFractionalNb);
		}
		inputNumber.requestFocus();
	}
}

class NumberConverter {
	private static final double CONVERT_FACTOR = 1.5;

	public double toConvert(double input) {
		if (input < 0)
			throw new IllegalArgumentException("Argument " + input + " is negative.");
		return input * CONVERT_FACTOR;
	}
}