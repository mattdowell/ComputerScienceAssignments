package org.ics141.program6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Your store must have
 * 
 * a different name and sells four different items other than the sample items
 * in the above diagram.
 * 
 * The item names in the description column are fixed and users are not allowed
 * to make changes (set the editable to false).
 * 
 * Initially the quantity for each item should be set to 0. The user should be
 * allowed to change the quantity of each item that they wish to purchase.
 * 
 * The values in the Unit Price column are also fixed and users cannot change
 * it.
 * 
 * For each item, the unit price is determined by you for any reasonable value.
 * 
 * The Order button should have the following behavior:
 * 
 * once the user clicks the order button, your program should display the order
 * line by line in the output text area. Along with the item description,
 * quantity and unit price, display the line item total. Do not display
 * information for any item the user has not ordered (where quantity is 0). At
 * the end, display the grand total of all items purchased;
 * 
 * the Clear button should have the following behavior:
 * 
 * when the user clicks the clear button, customer name field should be cleared,
 * all the quantity fields should be set to zeros, and the output text area
 * should be cleared.
 * 
 * @author mjdowell
 * 
 */
public class Program6 extends JApplet implements ActionListener {

	// Company name and logo
	JLabel companyNameLabel;
	ImageIcon logo;

	// Customer Name info
	JLabel customerNameLabel;
	JTextField customerNameField;

	// Top level labels
	JLabel descriptionLabel;
	JLabel qtyLabel;
	JLabel unitPrice;

	// Product #1
	JTextField itemField1;
	JTextField qtyTextField1;
	JTextField priceField1;

	// Product #2
	JTextField itemField2;
	JTextField qtyTextField2;
	JTextField priceField2;

	// Product #3
	JTextField itemField3;
	JTextField qtyTextField3;
	JTextField priceField3;

	// Product #4
	JTextField itemField4;
	JTextField qtyTextField4;
	JTextField priceField4;

	// Buttons
	JButton orderButton;
	JButton clearButton;

	// Order display
	JTextArea displayOrder;

	// Panels
	JPanel mainPanel;
	JPanel headerPanel;
	JPanel itemLabelsPanel;
	JPanel itemsPanel1;
	JPanel itemsPanel2;
	JPanel itemsPanel3;
	JPanel itemsPanel4;
	JPanel allItemsPanel;
	JPanel buttonPanel;
	JPanel summaryPanel;

	/**
	 * 1) put a company logo (must be constructed from an image file) on the
	 * upper left corner of your applet (you can use the Paint application that
	 * comes with the Window operating system to paint a logo and save it as
	 * either a gif or Jpeg file. You also need to find information in textbook
	 * or Internet on how to produce an image icon). The logo must be integrated
	 * into Java code (cannot be implemented with html code) and inserted at the
	 * upper left corner of the applet;
	 * 
	 * 2) set the background color of the applet to a color that you like (the
	 * color cannot be gray).
	 */
	public void init() {
		getContentPane().setLayout(new FlowLayout());

		initWidgets();
		buildMainPanel(getContentPane());

		getContentPane().add(mainPanel);

		// Listen for the button click
		orderButton.addActionListener(this);
		clearButton.addActionListener(this);
	}

	/**
	 * This method init's the widgets. If we were building this for real, this
	 * information would be stored externally in a language specific Resource
	 * file.
	 */
	private void initWidgets() {

		// First init the widgets
		Font f = new Font("serif", Font.PLAIN, 32);
		companyNameLabel = new JLabel("Matt's Merchandise");
		companyNameLabel.setFont(f);
		java.net.URL urlsmile = this.getClass().getResource("logo.png");
		logo = new ImageIcon(urlsmile);

		customerNameLabel = new JLabel("Customer name:");
		customerNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		customerNameField = new JTextField("Matt Dowell", 20);
		customerNameField.setPreferredSize(new Dimension(150, 25));

		descriptionLabel = new JLabel("  Description");
		descriptionLabel.setPreferredSize(new Dimension(220, 25));
		qtyLabel = new JLabel("Qty");
		unitPrice = new JLabel("Unit Price");
		descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		unitPrice.setHorizontalAlignment(SwingConstants.RIGHT);

		itemField1 = new JTextField("Sword", 28);
		itemField1.setEditable(false);
		qtyTextField1 = new JTextField("0", 3);
		priceField1 = new JTextField("100.00", 10);
		priceField1.setEditable(false);
		priceField1.setHorizontalAlignment(SwingConstants.RIGHT);

		itemField2 = new JTextField("Jayne Hat", 28);
		itemField2.setEditable(false);
		qtyTextField2 = new JTextField("0", 3);
		priceField2 = new JTextField("6.00", 10);
		priceField2.setEditable(false);
		priceField2.setHorizontalAlignment(SwingConstants.RIGHT);

		itemField3 = new JTextField("Serenity", 28);
		itemField3.setEditable(false);
		qtyTextField3 = new JTextField("0", 3);
		priceField3 = new JTextField("850.00", 10);
		priceField3.setEditable(false);
		priceField3.setHorizontalAlignment(SwingConstants.RIGHT);

		itemField4 = new JTextField("Firefly Poster", 28);
		itemField4.setEditable(false);
		qtyTextField4 = new JTextField("0", 3);
		priceField4 = new JTextField("7.99", 10);
		priceField4.setEditable(false);
		priceField4.setHorizontalAlignment(SwingConstants.RIGHT);

		orderButton = new JButton("Order");
		clearButton = new JButton("Clear");

		displayOrder = new JTextArea(10, 43);
	}

	/**
	 * Builds the main UI panel.
	 */
	private void buildMainPanel(Container pane) {

		GridBagConstraints c = new GridBagConstraints();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

		itemLabelsPanel = new JPanel();
		itemLabelsPanel.setLayout(new BorderLayout());

		itemsPanel1 = new JPanel();
		itemsPanel2 = new JPanel();
		itemsPanel3 = new JPanel();
		itemsPanel4 = new JPanel();
		allItemsPanel = new JPanel();
		allItemsPanel.setLayout(new BoxLayout(allItemsPanel, BoxLayout.Y_AXIS));

		buttonPanel = new JPanel();
		summaryPanel = new JPanel();

		mainPanel.setBackground(Color.WHITE);
		headerPanel.setBackground(Color.WHITE);
		allItemsPanel.setBackground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);
		summaryPanel.setBackground(Color.WHITE);
		itemsPanel1.setBackground(Color.WHITE);
		itemsPanel2.setBackground(Color.WHITE);
		itemsPanel3.setBackground(Color.WHITE);
		itemsPanel4.setBackground(Color.WHITE);
		itemLabelsPanel.setBackground(Color.WHITE);
		JPanel compInfoPanel = new JPanel(new BorderLayout());
		JPanel custNamePanel = new JPanel();
		compInfoPanel.setBackground(Color.WHITE);
		custNamePanel.setBackground(Color.WHITE);

		// Build the company logo / name row
		JLabel picLabel = new JLabel(logo);
		picLabel.setPreferredSize(new Dimension(170, 95));
		compInfoPanel.add(picLabel, BorderLayout.WEST);
		compInfoPanel.add(companyNameLabel, BorderLayout.CENTER);
		headerPanel.add(compInfoPanel);

		// Build the customer name row
		custNamePanel.add(customerNameLabel);
		custNamePanel.add(customerNameField);

		headerPanel.add(custNamePanel);
		// headerPanel.setPreferredSize(new Dimension(400, 80));
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		// The item labels and items should be in a panel by themselves
		itemLabelsPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		itemLabelsPanel.add(descriptionLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = .5;
		c.gridx = 2;
		c.gridy = 0;
		itemLabelsPanel.add(qtyLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 3;
		c.gridy = 0;
		itemLabelsPanel.add(unitPrice, c);
		// itemLabelsPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		itemsPanel1.add(itemField1);
		itemsPanel1.add(qtyTextField1);
		itemsPanel1.add(priceField1);

		itemsPanel2.add(itemField2);
		itemsPanel2.add(qtyTextField2);
		itemsPanel2.add(priceField2);

		itemsPanel3.add(itemField3);
		itemsPanel3.add(qtyTextField3);
		itemsPanel3.add(priceField3);

		itemsPanel4.add(itemField4);
		itemsPanel4.add(qtyTextField4);
		itemsPanel4.add(priceField4);

		buttonPanel.add(orderButton);
		buttonPanel.add(clearButton);

		allItemsPanel.add(itemLabelsPanel);
		allItemsPanel.add(itemsPanel1);
		allItemsPanel.add(itemsPanel2);
		allItemsPanel.add(itemsPanel3);
		allItemsPanel.add(itemsPanel4);
		allItemsPanel.add(buttonPanel);
		mainPanel.add(allItemsPanel, BorderLayout.CENTER);

		summaryPanel.add(displayOrder);
		summaryPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		mainPanel.add(summaryPanel, BorderLayout.SOUTH);

		mainPanel.setBackground(Color.WHITE);
	}

	public void start() {
	}

	/**
	 * once the user clicks the order button, your program should display the
	 * order line by line in the output text area. Along with the item
	 * description, quantity and unit price, display the line item total. Do not
	 * display information for any item the user has not ordered (where quantity
	 * is 0). At the end, display the grand total of all items purchased;
	 * 
	 * when the user clicks the clear button, customer name field should be
	 * cleared, all the quantity fields should be set to zeros, and the output
	 * text area should be cleared.
	 */
	public void actionPerformed(ActionEvent event) {
		Object sourceObject = event.getSource();
		int qty;

		// Did the user click Order Button?
		if (sourceObject == orderButton) {
			StringBuilder summaryText = new StringBuilder("Below is the order summary for customer: " + customerNameField.getText() + "\n\n");
			summaryText.append("Item\tQty\tUnit Price\tItem Total\n\n");
			double totalCost = 0;
			qty = Integer.parseInt(qtyTextField1.getText());
			if (qty > 0) {
				summaryText.append(buildOrderSummary(itemField1, qtyTextField1, priceField1));
				totalCost += getLineItemTotal(qtyTextField1, priceField1);
			}
			qty = Integer.parseInt(qtyTextField2.getText());
			if (qty > 0) {
				summaryText.append(buildOrderSummary(itemField2, qtyTextField2, priceField2));
				totalCost += getLineItemTotal(qtyTextField2, priceField2);
			}
			qty = Integer.parseInt(qtyTextField3.getText());
			if (qty > 0) {
				summaryText.append(buildOrderSummary(itemField3, qtyTextField3, priceField3));
				totalCost += getLineItemTotal(qtyTextField3, priceField3);
			}
			qty = Integer.parseInt(qtyTextField4.getText());
			if (qty > 0) {
				summaryText.append(buildOrderSummary(itemField4, qtyTextField4, priceField4));
				totalCost += getLineItemTotal(qtyTextField4, priceField4);
			}

			summaryText.append("\n\nGrand Total:\t\t" + "$ "+totalCost);
			displayOrder.setText(summaryText.toString());
		} else {
			// Assume it's clear
			customerNameField.setText("");
			qtyTextField1.setText("0");
			qtyTextField2.setText("0");
			qtyTextField3.setText("0");
			qtyTextField4.setText("0");
			displayOrder.setText("");
		}
	}

	/**
	 * Builds an order summary for the given order line
	 * 
	 * @param inItem
	 * @param inQty
	 * @param inPrice
	 * @return
	 */
	private String buildOrderSummary(JTextField inItem, JTextField inQty, JTextField inPrice) {
		return inItem.getText() + "\t" + inQty.getText() + "\t$ " + inPrice.getText() + "\t$ " + getLineItemTotal(inQty, inPrice) + "\n";
	}

	/**
	 * Builds the line item total
	 * 
	 * @param inQty
	 * @param inPrice
	 * @return
	 */
	private Double getLineItemTotal(JTextField inQty, JTextField inPrice) {
		int qty = Integer.parseInt(inQty.getText());
		double price = Double.parseDouble(inPrice.getText());
		return qty * price;
	}
}
