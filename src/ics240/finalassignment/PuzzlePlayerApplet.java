//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.finalassignment;

import java.awt.Dimension;

import javax.swing.JApplet;

/**
 * 
 * @author Matt
 *
 */
@SuppressWarnings("serial")
public class PuzzlePlayerApplet extends JApplet
{	
	public void init()
	{
		// Execute a job on the event-dispatching thread:
		// creating this applet's GUI.
		try
		{
			javax.swing.SwingUtilities.invokeAndWait(new Runnable()
			{
				public void run()
				{
					createGUI();
				}
			});
		} catch (Exception e)
		{
			System.err.println("createGUI didn't successfully complete: "
					+ e.toString());
		}
	}

	private void createGUI()
	{
		PuzzlePicker pp1 = new PuzzlePicker(getDocumentBase().toString());
		PuzzleCollection collection = new PuzzleCollection();
		collection.setCurrentId(pp1.getPuzzleId());
		PuzzlePlayerFrame frame = new PuzzlePlayerFrame(collection);
		this.add(frame);
		setSize(new Dimension(1020, 400));
	}
}