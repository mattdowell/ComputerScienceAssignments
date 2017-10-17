package project.step4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchAuthorsFrame extends JFrame implements ActionListener {

	Connection con;
	Statement stmt;
	PreparedStatement pStmt;
	ResultSet rs;

	// Display values from the database
	JComboBox<DropDownItem> genreDropDown = new JComboBox<>();

	// Scroll buttons
	JButton submitBtn = new JButton("Search");

	// Output Display
	JTextField firstNameBox = new JTextField(8);
	JTextField lastNameBox = new JTextField(8);
	JTextField birthDateBox = new JTextField(8);

	List<DropDownItem> items = new ArrayList<>();

	SimpleDateFormat FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	// Scroll buttons
	JButton firstBtn = new JButton("First");
	JButton nextBtn = new JButton("Next");
	JButton lastBtn = new JButton("Last");
	JButton prevBtn = new JButton("Previous");

	public SearchAuthorsFrame() {
		connect();
		initComponents();
	}

	public void initComponents() {

		JPanel p1 = new JPanel();
		GridLayout topLayout = new GridLayout(3, 2);
		p1.setLayout(topLayout);

		JPanel resultsPanel = new JPanel();
		GridLayout resultsLayout = new GridLayout(3, 2);
		resultsPanel.setLayout(resultsLayout);

		JPanel buttonsPanel = new JPanel();
		GridLayout buttonsLayout = new GridLayout(1, 4);
		buttonsPanel.setLayout(buttonsLayout);

		p1.add(new JLabel("Select authors by genre"));
		p1.add(genreDropDown);
		p1.add(submitBtn);
		add(p1, BorderLayout.NORTH);

		resultsPanel.add(new JLabel("First name"));
		firstNameBox.setText(" ");
		resultsPanel.add(firstNameBox);
		resultsPanel.add(new JLabel("Last name"));
		firstNameBox.setText(" ");
		resultsPanel.add(lastNameBox);
		resultsPanel.add(new JLabel("Birth date"));
		firstNameBox.setText(" ");
		resultsPanel.add(birthDateBox);

		add(resultsPanel, BorderLayout.SOUTH);

		submitBtn.addActionListener(this);

		firstBtn.setEnabled(false);
		prevBtn.setEnabled(false);
		nextBtn.setEnabled(false);
		lastBtn.setEnabled(false);

		buttonsPanel.add(firstBtn);
		buttonsPanel.add(prevBtn);
		buttonsPanel.add(nextBtn);
		buttonsPanel.add(lastBtn);
		add(buttonsPanel, BorderLayout.CENTER);

		nextListenerClass listener1 = new nextListenerClass();
		prevListenerClass listener2 = new prevListenerClass();
		firstListenerClass listener3 = new firstListenerClass();
		lastListenerClass listener4 = new lastListenerClass();

		nextBtn.addActionListener(listener1);
		prevBtn.addActionListener(listener2);
		firstBtn.addActionListener(listener3);
		lastBtn.addActionListener(listener4);

		populateGenreDropDown();
	}

	public void connect() {

		// Connecting to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/readers_project?user=root");

			String sqlStr = "select a.first_name, a.last_name, a.birth_date from  authors a "
					+ "  join author_genres ag on ag.author_id=a.id  join genres g on ag.genre_id=g.id "
					+ "  where g.id=?";
			pStmt = con.prepareStatement(sqlStr);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void populateGenreDropDown() {
		try {

			String sqlStr = "Select * from genres";
			PreparedStatement stmt = con.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				DropDownItem ddi = new DropDownItem(rs.getInt("id"), rs.getString("name"));
				items.add(ddi);
				genreDropDown.addItem(ddi);
			}
			rs.close();
			stmt.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void displayRowValues() {

		try {
			String first = rs.getString("first_name");
			firstNameBox.setText(first);

			String last = rs.getString("last_name");
			lastNameBox.setText(last);

			Date bd = rs.getDate("birth_date");
			birthDateBox.setText(FORMAT.format(bd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			DropDownItem genred = (DropDownItem) genreDropDown.getSelectedItem();
			pStmt.setInt(1, genred.id);
			rs = pStmt.executeQuery();
			if (rs.first()) {
				firstBtn.setEnabled(true);
				prevBtn.setEnabled(true);
				nextBtn.setEnabled(true);
				lastBtn.setEnabled(true);
				displayRowValues();
			} else {
				JOptionPane.showMessageDialog(SearchAuthorsFrame.this, "No results");
				firstBtn.setEnabled(false);
				prevBtn.setEnabled(false);
				nextBtn.setEnabled(false);
				lastBtn.setEnabled(false);

				firstNameBox.setText("");
				lastNameBox.setText("");
				birthDateBox.setText("");
			}
		} catch (Exception nextE) {
			nextE.printStackTrace();
		}
	}

	class nextListenerClass implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {

				if (rs.next()) {

					displayRowValues();

				} else {
					JOptionPane.showMessageDialog(SearchAuthorsFrame.this, "End of File");
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
					JOptionPane.showMessageDialog(SearchAuthorsFrame.this, "Begining of File");
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
					JOptionPane.showMessageDialog(SearchAuthorsFrame.this, "End of File");
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
					JOptionPane.showMessageDialog(SearchAuthorsFrame.this, "End of File");
				}
			} catch (Exception nextE) {
				nextE.printStackTrace();
			}
		}
	}
}

class DropDownItem {
	int id;
	String name;

	public DropDownItem(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
