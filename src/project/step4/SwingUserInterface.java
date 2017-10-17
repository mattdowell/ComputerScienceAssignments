package project.step4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


/**
 * This is the UI Components, and the simple File copy method.
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class SwingUserInterface extends JPanel implements ActionListener {


	JMenuItem browseBooksItem = new JMenuItem("Browse Books");
	JMenuItem recommendationsItem = new JMenuItem("Browse Recommendations");
	JMenuItem showAuthorsItem = new JMenuItem("Show Authors by Genre");
	JMenuItem searchReadersItem = new JMenuItem("Select readers by id");

	/**
	 * Constructor
	 */
	public SwingUserInterface() {
		super();

		setOpaque(false);
		
		/// MENU ITEMS
		JMenuBar menuBar = new JMenuBar();
		JMenu propertyMenu = new JMenu("Options");
		propertyMenu.setMnemonic('P');
		
		// Create property items
		propertyMenu.add(browseBooksItem);
		propertyMenu.add(recommendationsItem);
		propertyMenu.add(showAuthorsItem);
		propertyMenu.add(searchReadersItem);
		
		// Set this class as the action listener
		browseBooksItem.addActionListener(this);
		recommendationsItem.addActionListener(this);
		showAuthorsItem.addActionListener(this);
		searchReadersItem.addActionListener(this);
		
		menuBar.add(propertyMenu);
		add(menuBar);
	}

	/**
	 * Mouse clicked
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JMenuItem source = (JMenuItem) event.getSource();
		if (source == browseBooksItem) {
			BrowseBooksFrame frame = new BrowseBooksFrame();
			frame.setTitle("Browse all books");
			frame.setSize(500,200);
			frame.setVisible(true);
		} else if (source == recommendationsItem) {
			BrowseRecommendationsFrame frame = new BrowseRecommendationsFrame();
			frame.setTitle("Browse all recommendations");
			frame.setSize(500,200);
			frame.setVisible(true);
		} else if (source == showAuthorsItem) {
			SearchAuthorsFrame frame = new SearchAuthorsFrame();
			frame.setTitle("Show authors by genre");
			frame.setSize(500,250);
			frame.setVisible(true);
		} else if (source == searchReadersItem) {
			SearchReadersFrame frame = new SearchReadersFrame();
			frame.setTitle("Search for a reader");
			frame.setSize(500,250);
			frame.setVisible(true);
		}
	}

}
