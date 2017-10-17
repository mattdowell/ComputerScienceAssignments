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
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchReadersFrame extends JFrame implements ActionListener {

	Connection con;
	Statement stmt;
	PreparedStatement pStmt;
	ResultSet rs;

	// Display values from the database
	JTextField idField = new JTextField();

	// Scroll buttons
	JButton submitBtn = new JButton("Search");

	// Output Display
	JTextField idBox = new JTextField(8);
	JTextField nameBox = new JTextField(8);
	JTextField ageBox = new JTextField(8);


	public SearchReadersFrame() {
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

		p1.add(new JLabel("Reader ID:"));
		p1.add(idField);
		p1.add(submitBtn);
		add(p1, BorderLayout.NORTH);

		resultsPanel.add(new JLabel("ID"));
		idBox.setText(" ");
		resultsPanel.add(idBox);
		resultsPanel.add(new JLabel("Name"));
		idBox.setText(" ");
		resultsPanel.add(nameBox);
		resultsPanel.add(new JLabel("Age"));
		idBox.setText(" ");
		resultsPanel.add(ageBox);

		add(resultsPanel, BorderLayout.SOUTH);

		submitBtn.addActionListener(this);
	}

	public void connect() {

		// Connecting to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/readers_project?user=root");

			String sqlStr = "SELECT * FROM readers_project.readers where id=?";
			pStmt = con.prepareStatement(sqlStr);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public void displayRowValues() {

		try {
			int first = rs.getInt("id");
			idBox.setText(String.valueOf(first));

			String last = rs.getString("name");
			nameBox.setText(last);

			int bd = rs.getInt("age");
			ageBox.setText(String.valueOf(bd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			pStmt.setInt(1, Integer.parseInt(idField.getText()));
			rs = pStmt.executeQuery();
			if (rs.first()) {
				displayRowValues();
			} else {
				JOptionPane.showMessageDialog(SearchReadersFrame.this, "No results");

				idBox.setText("");
				nameBox.setText("");
				ageBox.setText("");
			}
		} catch (Exception nextE) {
			nextE.printStackTrace();
		}
	}
}
