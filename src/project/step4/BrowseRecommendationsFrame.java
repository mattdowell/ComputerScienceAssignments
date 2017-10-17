package project.step4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BrowseRecommendationsFrame extends JFrame {

	Connection con;
	Statement stmt;
	ResultSet rs;

	// Display values from the database
	JTextField titleBox = new JTextField(15);
	JTextField locationBox = new JTextField(15);
	JTextField delMethodBox = new JTextField(10);
	JTextField readsBox = new JTextField(4);

	// Scroll buttons
	JButton firstBtn = new JButton("First");
	JButton nextBtn = new JButton("Next");
	JButton lastBtn = new JButton("Last");
	JButton prevBtn = new JButton("Previous");

	final String QUERY = "select b.title as bookName, fromreader.name as recommendedFrom, toreader.name as recommendedTo, rec.reason as reason "
			+ " from recommendations rec " + " join books b on b.id=rec.book_id "
			+ " join readers toreader on toreader.id=rec.to_reader_id "
			+ " join readers fromreader on fromreader.id=rec.from_reader_id";

	public BrowseRecommendationsFrame() {

		initComponents();
		connect();
	}

	public void initComponents() {

		JPanel p1 = new JPanel();
		GridLayout textFieldsLayout = new GridLayout(4, 2);
		p1.setLayout(textFieldsLayout);

		JPanel p2 = new JPanel();
		GridLayout buttonsLayout = new GridLayout(1, 4);
		p2.setLayout(buttonsLayout);

		p1.add(new JLabel("Title"));
		titleBox.setText("0");
		p1.add(titleBox);

		p1.add(new JLabel("Recommended From"));
		locationBox.setText(" ");
		p1.add(locationBox);

		p1.add(new JLabel("Recommended To"));
		delMethodBox.setText(" ");
		p1.add(delMethodBox);

		p1.add(new JLabel("Reason"));
		readsBox.setText(" ");
		p1.add(readsBox);

		add(p1, BorderLayout.NORTH);

		p2.add(firstBtn);
		p2.add(prevBtn);
		p2.add(nextBtn);
		p2.add(lastBtn);
		add(p2, BorderLayout.SOUTH);

		nextListenerClass listener1 = new nextListenerClass();
		prevListenerClass listener2 = new prevListenerClass();
		firstListenerClass listener3 = new firstListenerClass();
		lastListenerClass listener4 = new lastListenerClass();

		nextBtn.addActionListener(listener1);
		prevBtn.addActionListener(listener2);
		firstBtn.addActionListener(listener3);
		lastBtn.addActionListener(listener4);
	}

	public void connect() {

		// Connecting to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/readers_project?user=root");
			System.out.println("Connection Object Created : " + con);

			stmt = con.createStatement();
			rs = stmt.executeQuery(QUERY);

			// Move the cursor to the first record and get the data
			rs.next();

			displayRowValues();

			// con.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * select b.title as bookName, fromreader.name as recommendedFrom, toreader.name as recommendedTo, rec.reason as reason "
	 */
	public void displayRowValues() {

		try {
			String title = rs.getString("bookName");
			titleBox.setText(title);

			String fname_col = rs.getString("recommendedFrom");
			locationBox.setText(fname_col);

			String lname_col = rs.getString("recommendedTo");
			delMethodBox.setText(lname_col);

			String reason = rs.getString("reason");
			readsBox.setText(String.valueOf(reason));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class nextListenerClass implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				if (rs.next()) {

					displayRowValues();

				} else {
					JOptionPane.showMessageDialog(BrowseRecommendationsFrame.this, "End of File");
				}
			} catch (Exception nextE) {
				nextE.printStackTrace();
			}
		}
	}

	class prevListenerClass implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				if (rs.previous()) {

					displayRowValues();

				} else {
					JOptionPane.showMessageDialog(BrowseRecommendationsFrame.this, "Begining of File");
				}
			} catch (Exception nextE) {
				nextE.printStackTrace();
			}
		}
	}

	class firstListenerClass implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				if (rs.first()) {

					displayRowValues();

				} else {
					JOptionPane.showMessageDialog(BrowseRecommendationsFrame.this, "End of File");
				}
			} catch (Exception nextE) {
				nextE.printStackTrace();
			}
		}
	}

	class lastListenerClass implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				if (rs.last()) {

					displayRowValues();

				} else {
					JOptionPane.showMessageDialog(BrowseRecommendationsFrame.this, "End of File");
				}
			} catch (Exception nextE) {
				nextE.printStackTrace();
			}
		}
	}

}
