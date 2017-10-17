//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.assignment3;

/**
 * @class ICS 240
 * @date 6/4/2014
 * @assignment 3
 * @author Matt Dowell
 * 
 *         Skeleton Code:
 * 
 * @see http 
 *      ://www.cs.colorado.edu/~main/edu/colorado/collections/DoubleArraySeq.
 *      java
 * 
 */
public class DoubleArraySeq implements Cloneable
{

	private static final int DEFAULT_CAPACITY = 10;
	private static final int CAPACITY_GROWTH = 5;
	private double[] items = null;
	private int currentIndex = -1;
	private int numberOfItems = 0;

	/**
	 * Initialize an empty sequence with an initial capacity of 10. Note that
	 * the addAfter and addBefore methods work efficiently (without needing more
	 * memory) until this capacity is reached.
	 * 
	 * @param - none
	 * @postcondition This sequence is empty and has an initial capacity of 10.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for: new double[10].
	 **/
	public DoubleArraySeq()
	{
		items = new double[DEFAULT_CAPACITY];
	}

	/**
	 * Initialize an empty sequence with a specified initial capacity. Note that
	 * the addAfter and addBefore methods work efficiently (without needing more
	 * memory) until this capacity is reached.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of this sequence
	 * @precondition initialCapacity is non-negative.
	 * @postcondition This sequence is empty and has the given initial capacity.
	 * @exception IllegalArgumentException
	 *                Indicates that initialCapacity is negative.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for: new
	 *                double[initialCapacity].
	 **/
	public DoubleArraySeq(int initialCapacity)
	{
		items = new double[initialCapacity];
	}

	/**
	 * Add a new element to this sequence, after the current element. If the new
	 * element would take this sequence beyond its current capacity, then the
	 * capacity is increased before adding the new element.
	 * 
	 * @param element
	 *            the new element that is being added
	 * @postcondition A new copy of the element has been added to this sequence.
	 *                If there was a current element, then the new element is
	 *                placed after the current element. If there was no current
	 *                element, then the new element is placed at the end of the
	 *                sequence. In all cases, the new element becomes the new
	 *                current element of this sequence.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for increasing the
	 *                sequence's capacity.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will
	 *       cause the sequence to fail with an arithmetic overflow.
	 **/
	public void addAfter(double element)
	{
		this.currentIndex++;
		this.addValueAtIndex(element, currentIndex);
	}

	/**
	 * Add a new element to this sequence, before the current element. If the
	 * new element would take this sequence beyond its current capacity, then
	 * the capacity is increased before adding the new element.
	 * 
	 * @param element
	 *            the new element that is being added
	 * @postcondition A new copy of the element has been added to this sequence.
	 *                If there was a current element, then the new element is
	 *                placed before the current element. If there was no current
	 *                element, then the new element is placed at the start of
	 *                the sequence. In all cases, the new element becomes the
	 *                new current element of this sequence.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for increasing the
	 *                sequence's capacity.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will
	 *       cause the sequence to fail with an arithmetic overflow.
	 **/
	public void addBefore(double element)
	{

		if (this.currentIndex > 0)
		{
			this.currentIndex--;
		}
		this.addValueAtIndex(element, currentIndex);
	}

	/**
	 * Place the contents of another sequence at the end of this sequence.
	 * 
	 * @param addend
	 *            a sequence whose contents will be placed at the end of this
	 *            sequence
	 * @precondition The parameter, addend, is not null.
	 * @postcondition The elements from addend have been placed at the end of
	 *                this sequence. The current element of this sequence
	 *                remains where it was, and the addend is also unchanged.
	 * @exception NullPointerException
	 *                Indicates that addend is null.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory to increase the size of this
	 *                sequence.
	 * @note An attempt to increase the capacity beyond Integer.MAX_VALUE will
	 *       cause an arithmetic overflow that will cause the sequence to fail.
	 **/
	public void addAll(DoubleArraySeq inSeq)
	{
		int futureSize = this.size() + inSeq.size();
		
		this.increaseCapacity(futureSize);
		
		int indexToMoveTo = futureSize;

		// Initialize
		if (this.currentIndex < 0)
		{
			this.currentIndex = 0;
		}
		
		int inSeqIndex = 0;
		for (int i = this.numberOfItems; i < indexToMoveTo; i++)
		{
		    this.addValueAtIndex(inSeq.getElementAtIndex(inSeqIndex), i);
		    inSeqIndex++;
		}
	}

	/**
	 * Increase the capacity and copy over the old items to the new storage with
	 * extra capacity
	 * 
	 * @param inNewCapacity
	 */
	private void increaseCapacity(int inNewCapacity)
	{
		if (inNewCapacity > this.getCapacity())
		{
			double[] newArr = this.buildNewTempStorage(inNewCapacity);
			for (int i = 0; i < items.length; i++)
			{
				newArr[i] = items[i];
				this.currentIndex = i;
			}

			this.items = newArr;
		}
	}

	/**
	 * Move forward, so that the current element is now the next element in this
	 * sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @postcondition If the current element was already the end element of this
	 *                sequence (with nothing after it), then there is no longer
	 *                any current element. Otherwise, the new element is the
	 *                element immediately after the original current element.
	 * @exception IllegalStateException
	 *                Indicates that there is no current element, so advance may
	 *                not be called.
	 **/
	public void advance()
	{
		if (isCurrent())
		{
			if (this.currentIndex < (this.numberOfItems-1))
			{
				this.currentIndex++;
			} else {
				this.currentIndex = -1;
			}
		} else
		{
			throw new IllegalStateException("There is no current element.");
		}
	}

	/**
	 * Create a new sequence that contains all the elements from one sequence
	 * followed by another.
	 * 
	 * @param s1
	 *            the first of two sequences
	 * @param s2
	 *            the second of two sequences
	 * @precondition Neither s1 nor s2 is null.
	 * @return a new sequence that has the elements of s1 followed by the
	 *         elements of s2 (with no current element)
	 * @exception NullPointerException. Indicates
	 *                that one of the arguments is null.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for the new sequence.
	 * @note An attempt to create a sequence with a capacity beyond
	 *       Integer.MAX_VALUE will cause an arithmetic overflow that will cause
	 *       the sequence to fail.
	 **/
	public static DoubleArraySeq catenation(DoubleArraySeq seq1,
			DoubleArraySeq seq2)
	{
		if (seq1 == null || seq2 == null)
		{
			throw new NullPointerException("Neither sequence can be null");
		}
		DoubleArraySeq theReturn = new DoubleArraySeq(seq1.size() + seq2.size());
		theReturn.addAll(seq1);
		theReturn.addAll(seq2);
		return theReturn;
	}

	/**
	 * Accessor method to get the current capacity of this sequence. The add
	 * method works efficiently (without needing more memory) until this
	 * capacity is reached.
	 * 
	 * @param - none
	 * @return the current capacity of this sequence
	 **/
	public int getCapacity()
	{
		return items.length;
	}

	/**
	 * Accessor method to get the current element of this sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @return the current element of this sequence
	 * @exception IllegalStateException
	 *                Indicates that there is no current element, so getCurrent
	 *                may not be called.
	 **/
	public double getCurrent()
	{
		if (isCurrent())
		{
			return items[this.currentIndex];
		} else
		{
			throw new IllegalStateException("There is no current element.");
		}
	}

	/**
	 * Accessor method to determine whether this sequence has a specified
	 * current element that can be retrieved with the getCurrent method.
	 * 
	 * @param - none
	 * @return true (there is a current element) or false (there is no current
	 *         element at the moment)
	 **/
	public boolean isCurrent()
	{
		return (this.currentIndex > -1) ? true : false;
	}

	/**
	 * Remove the current element from this sequence.
	 * 
	 * @param - none
	 * @precondition isCurrent() returns true.
	 * @postcondition The current element has been removed from this sequence,
	 *                and the following element (if there is one) is now the new
	 *                current element. If there was no following element, then
	 *                there is now no current element.
	 * @exception IllegalStateException
	 *                Indicates that there is no current element, so
	 *                removeCurrent may not be called.
	 **/
	public void removeCurrent()
	{
		if (isCurrent())
		{
			removeValueAtIndex(this.currentIndex);
		} else
		{
			throw new IllegalStateException("There is no current element.");
		}
	}

	/**
	 * Determine the number of elements in this sequence.
	 * 
	 * @param - none
	 * @return the number of elements in this sequence
	 **/
	public int size()
	{
		return this.numberOfItems;
	}

	/**
	 * Set the current element at the front of this sequence.
	 * 
	 * @param - none
	 * @postcondition The front element of this sequence is now the current
	 *                element (but if this sequence has no elements at all, then
	 *                there is no current element).
	 **/
	public void start()
	{
		this.currentIndex = 0;
	}

	/**
	 * Adds a value at the specified index.
	 * 
	 * @param a_value value to add
	 * @param a_idx index to add value at
	 */
	private void addValueAtIndex(double a_value, int a_idx)
	{
		if (ensureCapacity(a_idx))
		{

			// Build temp storage
			double[] tempArr = buildNewTempStorage();

			// Loop through current storage until we hit our index
			int tIdx = 0;
			int i = 0;
			boolean added = false;
			for (; i <= items.length;)
			{
				if (i == a_idx & !added)
				{
					// add the new double at the specified index
					tempArr[tIdx] = a_value;
					// Back up the items index one, since we did not copy its
					// value over yet.
					added = true;
				} else if (i < items.length)
				{
					tempArr[tIdx] = items[i];
					i++;
				} else
				{
					i++;
				}
				tIdx++;
			}

			// Set the marker at the specified index.
			this.currentIndex = a_idx;

			// set the items to our new storage
			this.items = tempArr;
			
			// Increment the number of items.
			this.numberOfItems ++;

		} else
		{
			throw new IllegalStateException("That index is beyond capacity");
		}
	}

	/**
	 * Removes a value from our storage at the given index.
	 * @param a_idx
	 */
	private void removeValueAtIndex(int a_idx)
	{
		if (ensureCapacity(a_idx))
		{

			// Build temp storage
			double[] tempArr = buildNewTempStorage();

			// Loop through current storage until we hit our index
			int tIdx = 0;
			for (int i = 0; i < this.numberOfItems; i++)
			{
				// If we don't hit the specified index
				if (i != a_idx)
				{
					// Add double at the specified index
					// Which means we will NOT be adding the value at i
					tempArr[tIdx] = items[i];
					tIdx++;
				}
			}
			
			// Decrement 
			this.numberOfItems --;
			
			// Set our new items storage
			items = tempArr;
			
			// Set the marker at the requested index
			this.currentIndex = a_idx;
			
			// If currentIndex = numberOfItems, set currentIndex = -1;
			if (this.currentIndex == this.numberOfItems) {
				this.currentIndex = -1;
			}

		} else
		{
			throw new IllegalStateException("That index does not exist");
		}
	}

	/**
	 * Builds a new temporary storage array with the additional capacity if
	 * needed.
	 * 
	 * @return int[] at current size, or with capacity added if needed.
	 */
	private double[] buildNewTempStorage()
	{
		if (numberOfItems < (items.length - CAPACITY_GROWTH))
		{
			return new double[items.length + 1];
		} else
		{
			return new double[items.length + CAPACITY_GROWTH];
		}
	}

	/**
	 * 
	 * @return
	 */
	private double[] buildNewTempStorage(int inNewCap)
	{
		return new double[inNewCap];
	}

	private boolean ensureCapacity(int a_size)
	{
		//System.out.println("is: " + a_size + " <= " + items.length);
		return (a_size <= items.length);
	}

	/**
	 * Adds value to the front.
	 * @param d
	 */
	public void addFront(double d)
	{
		this.addValueAtIndex(d, 0);
	}

	/**
	 * Adds value to the end of the storage
	 * @param d
	 */
	public void addLast(double d)
	{
		this.addValueAtIndex(d, this.numberOfItems);
	}

	/**
	 * Removes value at the front, sets current to 0
	 */
	public void removeFront()
	{
		this.removeValueAtIndex(0);
	}

	/**
	 * Returns a value at the give index.
	 * @param i
	 * @return
	 */
	public double getElementAtIndex(int i)
	{
		if (this.ensureCapacity(i))
		{
			return this.items[i];
		} else
		{
			throw new IllegalStateException("That index does not exist");
		}
	}

	/**
	 * Move the index to the last item
	 */
	public void end()
	{
		this.currentIndex = (this.numberOfItems - 1);
	}

	/**
	 * Sets the current index.
	 * @param i
	 */
	public void setCurrent(int i)
	{
		this.currentIndex = i;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder("DoubleArraySeq: ");
		for (int i = 0; i < this.numberOfItems; i++)
		{
			sb.append("[");
			sb.append(String.valueOf(items[i]));
			sb.append("]");
		}
		return sb.toString();
	}

	/**
	 * Generate a copy of this sequence.
	 * 
	 * @param - none
	 * @return The return value is a copy of this sequence. Subsequent changes
	 *         to the copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for creating the clone.
	 **/
	public DoubleArraySeq clone()
	{
		DoubleArraySeq answer;

		try
		{
			answer = (DoubleArraySeq) super.clone();
		} catch (CloneNotSupportedException e)
		{
			// clause at the start of this class.
			throw new RuntimeException(
					"This class does not implement Cloneable");
		}

		answer.items = items.clone();

		return answer;
	}

	/**
	 * Reduce the current capacity of this sequence to its actual size (i.e.,
	 * the number of elements it contains).
	 * 
	 * @param - none
	 * @postcondition This sequence's capacity has been changed to its current
	 *                size.
	 * @exception OutOfMemoryError
	 *                Indicates insufficient memory for altering the capacity.
	 **/
	public void trimToSize()
	{
		double[] trimmedArray;

		if (items.length != numberOfItems)
		{
			trimmedArray = new double[numberOfItems];
			System.arraycopy(items, 0, trimmedArray, 0, numberOfItems);
			items = trimmedArray;
		}
	}

}
