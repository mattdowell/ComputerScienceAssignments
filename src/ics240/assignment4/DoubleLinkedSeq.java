//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.assignment4;

/******************************************************************************
 * This class is a homework assignment; A DoubleLinkedSeq</CODE> is a collection
 * of double</CODE> numbers. The sequence can have a special "current element,"
 * which is specified and accessed through four methods that are not available
 * in the sequence class (start, getCurrent, advance and isCurrent).
 * 
 * <dl>
 * <dt><b>Limitations:</b> Beyond Int.MAX_VALUE</CODE> elements, the size</CODE>
 * method does not work.
 * 
 * <dt><b>Note:</b>
 * <dd>
 * This file contains only blank implementations ("stubs") because this is a
 * Programming Project for my students.
 * 
 * <dt><b>Outline of Java Source Code for this class:</b>
 * <dd>
 * <A HREF="../../../../edu/colorado/collections/DoubleLinkedSeq.java">
 * http://www
 * .cs.colorado.edu/~main/edu/colorado/collections/DoubleLinkedSeq.java </A>
 * </dl>
 * 
 * 
 * @version Jan 24, 1999
 ******************************************************************************/
public class DoubleLinkedSeq implements Cloneable
{
	private int nodeCount = -1;
	private DoubleNode headNode = null;
	private DoubleNode tailNode = null;
	private DoubleNode preCursorNode = null;
	private DoubleNode cursorNode = null;

	// private static final double EMPTY_VALUE = -.0000001;

	/**
	 * Initialize an empty sequence.
	 * 
	 * @param - none <dt><b>Postcondition:</b>
	 *        <dd>
	 *        This sequence is empty.
	 **/
	public DoubleLinkedSeq()
	{
		this.nodeCount = 0;
	}

	public DoubleLinkedSeq(int a_initSize)
	{
		this();
	}

	/**
	 * This method sets the initial value
	 * 
	 * @param a_initial_value
	 */
	private void initNodes(double a_initial_value)
	{
		headNode = new DoubleNode(a_initial_value, null);
		cursorNode = this.headNode;
		tailNode = headNode;
		preCursorNode = null;
	}

	/**
	 * Add a new element to this sequence, after the current element.
	 * 
	 * @param element
	 *            </CODE> the new element that is being added <dt>
	 *            <b>Postcondition:</b>
	 *            <dd>
	 *            A new copy of the element has been added to this sequence. If
	 *            there was a current element, then the new element is placed
	 *            after the current element. If there was no current element,
	 *            then the new element is placed at the end of the sequence. In
	 *            all cases, the new element becomes the new current element of
	 *            this sequence.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for a new node.
	 **/
	public void addAfter(double a_val)
	{
		if (headNode == null)
		{
			initNodes(a_val);
		} else
		{
			this.cursorNode.addNodeAfter(a_val);
			this.cursorNode = this.cursorNode.getLink();
			this.tailNode = this.cursorNode;
		}
		this.nodeCount++;
	}

	/**
	 * Add a new element to this sequence, before the current element.
	 * 
	 * @param element
	 *            </CODE> the new element that is being added <dt>
	 *            <b>Postcondition:</b>
	 *            <dd>
	 *            A new copy of the element has been added to this sequence. If
	 *            there was a current element, then the new element is placed
	 *            before the current element. If there was no current element,
	 *            then the new element is placed at the start of the sequence.
	 *            In all cases, the new element becomes the new current element
	 *            of this sequence.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for a new node.
	 **/
	public void addBefore(double a_val)
	{
		if (headNode == null)
		{
			initNodes(a_val);
		} else
		{
			if (this.preCursorNode == null)
			{
				// This means its a new-ish list
				this.headNode = new DoubleNode(a_val, this.headNode);
				this.preCursorNode = this.headNode;
			} else
			{
				this.preCursorNode.addNodeAfter(a_val);
				this.cursorNode = this.preCursorNode.getLink();
			}
		}
		this.nodeCount++;
	}

	/**
	 * Place the contents of another sequence at the end of this sequence.
	 * 
	 * @param addend
	 *            </CODE> a sequence whose contents will be placed at the end of
	 *            this sequence <dt><b>Precondition:</b>
	 *            <dd>
	 *            The parameter, addend</CODE>, is not null.
	 *            <dt><b>Postcondition:</b>
	 *            <dd>
	 *            The elements from addend</CODE> have been placed at the end of
	 *            this sequence. The current element of this sequence remains
	 *            where it was, and the addend</CODE> is also unchanged.
	 * @exception NullPointerException
	 *                Indicates that addend</CODE> is null.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory to increase the size of this
	 *                sequence.
	 **/
	public void addAll(DoubleLinkedSeq addend)
	{
		this.end();
		for (int i = 0; i < addend.size(); i++)
		{
			this.addAfter(addend.getElementAtIndex(i));
		}
	}

	/**
	 * Move forward, so that the current element is now the next element in this
	 * sequence.
	 * 
	 * @param - none <dt><b>Precondition:</b>
	 *        <dd>
	 *        isCurrent()</CODE> returns true.
	 *        <dt><b>Postcondition:</b>
	 *        <dd>
	 *        If the current element was already the end element of this
	 *        sequence (with nothing after it), then there is no longer any
	 *        current element. Otherwise, the new element is the element
	 *        immediately after the original current element.
	 * @exception IllegalStateException
	 *                Indicates that there is no current element, so
	 *                advance</CODE> may not be called.
	 **/
	public void advance()
	{
		if (this.cursorNode == null)
		{
			throw new IllegalStateException("No more room to advance");
		} else
		{
			this.preCursorNode = this.cursorNode;
			this.cursorNode = this.cursorNode.getLink();
		}
	}

	/**
	 * Generate a copy of this sequence.
	 * 
	 * @param - none
	 * @return The return value is a copy of this sequence. Subsequent changes
	 *         to the copy will not affect the original, nor vice versa. Note
	 *         that the return value must be type cast to a
	 *         DoubleLinkedSeq</CODE> before it can be used.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for creating the clone.
	 **/
	public Object clone()
	{
		// Clone a DoubleLinkedSeq object.
		return this.clone();
	}

	/**
	 * Create a new sequence that contains all the elements from one sequence
	 * followed by another.
	 * 
	 * @param s1
	 *            </CODE> the first of two sequences
	 * @param s2
	 *            </CODE> the second of two sequences <dt><b>Precondition:</b>
	 *            <dd>
	 *            Neither s1 nor s2 is null.
	 * @return a new sequence that has the elements of s1</CODE> followed by the
	 *         elements of s2</CODE> (with no current element)
	 * @exception NullPointerException. Indicates
	 *                that one of the arguments is null.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for the new sequence.
	 **/
	public static DoubleLinkedSeq catenation(DoubleLinkedSeq s1,
			DoubleLinkedSeq s2)
	{
		int finalSize = s1.size() + s2.size();
		DoubleLinkedSeq theReturn = new DoubleLinkedSeq(finalSize);

		theReturn.addAll(s1);
		theReturn.addAll(s2);

		return theReturn;
	}

	/**
	 * Accessor method to get the current element of this sequence.
	 * 
	 * @param - none <dt><b>Precondition:</b>
	 *        <dd>
	 *        isCurrent()</CODE> returns true.
	 * @return the current capacity of this sequence
	 * @exception IllegalStateException
	 *                Indicates that there is no current element, so
	 *                getCurrent</CODE> may not be called.
	 **/
	public double getCurrent()
	{
		return this.cursorNode.getData();
	}

	/**
	 * Accessor method to determine whether this sequence has a specified
	 * current element that can be retrieved with the getCurrent</CODE> method.
	 * 
	 * @param - none
	 * @return true (there is a current element) or false (there is no current
	 *         element at the moment)
	 **/
	public boolean isCurrent()
	{
		if (this.cursorNode != null)
		{
			return true;
		}
		return false;
	}

	/**
	 * Remove the current element from this sequence.
	 * 
	 * @param - none <dt><b>Precondition:</b>
	 *        <dd>
	 *        isCurrent()</CODE> returns true.
	 *        <dt><b>Postcondition:</b>
	 *        <dd>
	 *        The current element has been removed from this sequence, and the
	 *        following element (if there is one) is now the new current
	 *        element. If there was no following element, then there is now no
	 *        current element.
	 * @exception IllegalStateException
	 *                Indicates that there is no current element, so
	 *                removeCurrent</CODE> may not be called.
	 **/
	public void removeCurrent()
	{
		this.cursorNode = this.cursorNode.getLink();
		this.nodeCount--;
	}

	/**
	 * Determine the number of elements in this sequence.
	 * 
	 * @param - none
	 * @return the number of elements in this sequence
	 **/
	public int size()
	{
		return nodeCount;
	}

	/**
	 * Set the current element at the front of this sequence.
	 * 
	 * @param - none <dt><b>Postcondition:</b>
	 *        <dd>
	 *        The front element of this sequence is now the current element (but
	 *        if this sequence has no elements at all, then there is no current
	 *        element).
	 **/
	public void start()
	{
		this.cursorNode = this.headNode;
	}

	/**
	 * Add an element to the front
	 * 
	 * @param a_val
	 */
	public void addFront(double a_val)
	{
		if (headNode == null)
		{
			initNodes(a_val);
		} else
		{
			start();
			this.headNode = new DoubleNode(a_val, this.headNode);
			this.cursorNode = this.headNode;

		}
		this.nodeCount++;
	}

	/**
	 * Add an element to the end
	 * @param a_val
	 */
	public void addLast(double a_val)
	{
		if (headNode == null)
		{
			initNodes(a_val);
		} else
		{
			this.tailNode.addNodeAfter(a_val);
			this.tailNode = this.tailNode.getLink();
		}
		this.nodeCount++;
	}

	/**
	 * Remove the first element
	 */
	public void removeFront()
	{
		this.start();
		this.headNode = this.headNode.getLink();
		this.nodeCount--;
	}

	/**
	 * Gets the value at the specificed index
	 * 
	 * @param inIndex
	 * @return
	 */
	public double getElementAtIndex(int inIndex)
	{
		int i = 0;
		double theReturn = 0;
		for (this.cursorNode = this.headNode; this.cursorNode != null; this.cursorNode = this.cursorNode
				.getLink())
		{
			if (inIndex == i)
			{
				theReturn = this.cursorNode.getData();
				break;
			}
			i++;
		}
		return theReturn;
	}

	/**
	 * Sets pointer to end, tail node.
	 */
	public void end()
	{
		this.cursorNode = this.tailNode;
	}

	/**
	 * Sets cursor node to given index
	 * @param inIndex
	 */
	public void setCurrent(int inIndex)
	{
		int i = 0;
		DoubleNode tempPrec = this.headNode;
		for (this.cursorNode = this.headNode; this.cursorNode != null; this.cursorNode = this.cursorNode
				.getLink())
		{

			if (inIndex == i)
			{
				break;
			}

			// Second time through, the cursor node.
			tempPrec = this.cursorNode;
			i++;
		}

		this.preCursorNode = tempPrec;
	}
	
	/**
	 * Overridden toString method
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("DoubleLinkedSeq: ");
		for (int i = 0; i < this.size(); i++)
		{
			sb.append("[");
			sb.append(String.valueOf(this.getElementAtIndex(i)));
			sb.append("]");
		}
		return sb.toString();
	}
}
