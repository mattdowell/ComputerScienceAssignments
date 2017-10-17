package org.ics141.demo.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

public class Stopwatch extends JFrame implements Runnable {
	Label timeDisp = new Label(" 0:0 ", Label.CENTER);
	Thread timerThread;
	int time = 0; // time in seconds.
	Button btReset = new Button("Reset");
	Button btStart = new Button("Start");
	Button btStop = new Button("Stop");

	Stopwatch() {
		super("Button Example");
		Panel p = new Panel(new GridLayout(1, 0));
		btReset.setEnabled(false);
		btStop.setEnabled(false);
		btReset.addActionListener(new ResetListener());
		btStop.addActionListener(new StopListener());
		btStart.addActionListener(new StartListener());
		p.add(btReset);
		p.add(btStart);
		p.add(btStop);
		add(p, BorderLayout.SOUTH);
		//Make the time display very large.
		timeDisp.setFont(new Font("Courier", Font.BOLD, 60));
		add(timeDisp, BorderLayout.CENTER);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//Returns only when the timerThread has terminated.
	public void stop() {
		Thread t = timerThread;
		if (t != null) {
			timerThread = null;
			try {
				t.join();
			} catch (Exception e) {
			}
		}
	}

	public void run() {
		while (timerThread == Thread.currentThread()) {
			timeDisp.setText("" + time / 10 + ":" + time % 10 + "0");
			time++;
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			;
		}
	}

	//Inner class for listeners
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			Stopwatch.this.stop();
			timeDisp.setText("0:0");
			time = 0;
			btReset.setEnabled(false);
			btStart.setEnabled(true);
			btStop.setEnabled(false);
			btStart.setLabel("Start");
		}
	}

	private class StopListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			Stopwatch.this.stop();
			btReset.setEnabled(true);
			btStart.setEnabled(true);
			btStop.setEnabled(false);
			btStart.setLabel("Continue");
		}
	}

	private class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			//Create and start the timer thread.
			timerThread = new Thread(Stopwatch.this);
			timerThread.start();
			btReset.setEnabled(false);
			btStart.setEnabled(false);
			btStop.setEnabled(true);
			btStart.setLabel("Continue");
		}
	}

	public static void main(String[] args) {
		new Stopwatch();
	}
}
