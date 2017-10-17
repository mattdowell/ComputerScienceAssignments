//*******1*********2*********3*********4*********5*********6*********7*********8
package ics240.assignment3;

/**
 * 
 * @author Matt
 * 
 */
public class DoubleArraySeqTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		System.out.println("\n\nTest 1: ============================");
		System.out.println("Test 1: Creating a sequence with a capacity of 5");
		System.out
				.println("Test 1: adding 4 elements through different methods");
		System.out.println("Test 1: Creating the sequence ds1");
		DoubleArraySeq ds1 = new DoubleArraySeq(5);
		System.out.println("Test 1: Printing empty sequence --> \n" + ds1);
		System.out
				.println("Test 1: trying the add methods - addBefore, addAfter, addFront, ");
		ds1.addFront(10.0);
		System.out.println("Test 1: addFront(10.0) --> \n" + ds1);
		ds1.addBefore(20.0);
		System.out.println("Test 1: addBefore(20.0) --> \n" + ds1);
		ds1.addAfter(30.0);
		System.out.println("Test 1: addAfter(30.0) --> \n" + ds1);
		ds1.addLast(40.0);
		System.out.println("Test 1: addLast(40.0) --> \n" + ds1);

		System.out.println("\n\nTest 2: ============================");
		System.out
				.println("Test 2: Creating a sequence with default constructor");
		System.out
				.println("Test 2: adding 4 elements through different methods");
		System.out.println("Test 2: Creating the sequence ds2");
		DoubleArraySeq ds2 = new DoubleArraySeq();
		ds2.addFront(50.0);
		System.out.println("Test 2: addFront(50.0) --> \n" + ds2);
		ds2.addBefore(60.0);
		System.out.println("Test 2: addBefore(60.0) --> \n" + ds2);
		ds2.addAfter(70.0);
		System.out.println("Test 2: addAfter(70.0) --> \n" + ds2);
		ds2.addLast(80.0);
		System.out.println("Test 2: addLast(80.0) --> \n" + ds2);

		System.out.println("\n\nTest 3: ============================");
		System.out.println("Test 3: Adding ds2 to ds1 through addAll");
		ds1.addAll(ds2);
		System.out.println("Test 3: ds1.addAll(ds2) " + ds1);

		System.out.println("\n\nTest 4: ============================");
		System.out.println("Test 4: Testing the removals from ds2");
		System.out.println("Test 4: ds2 --> " + ds2);
		ds2.removeCurrent();
		System.out.println("Test 4: After removeCurrent()" + ds2);
		ds2.removeFront();
		System.out.println("Test 4: After removeFront()" + ds2);

		System.out.println("\n\nTest 5: ds3 = catenation(ds1,ds2)");
		System.out.println("Test 5: ds1 --> " + ds1);
		System.out.println("Test 5: ds2 --> " + ds2);
		DoubleArraySeq ds3 = DoubleArraySeq.catenation(ds1, ds2);
		System.out.println("Test 5: ds3 = catenation(ds1, ds2)" + ds3);

		System.out.println("\n\nTest 6: Queries on ds3");
		System.out.println("Test 6: ds3 --> " + ds3);
		System.out.println("Size of ds3 = " + ds3.size());
		System.out
				.println("Element at 3rd index = " + ds3.getElementAtIndex(3));
		System.out.println("Is Current Element present? = " + ds3.isCurrent());
		System.out.println("Current Element of ds3 = " + ds3.getCurrent());

		System.out.println("\n\nTest 7: Navigations on ds3");
		System.out.println("Test 7: ds3 --> " + ds3);
		ds3.start();
		System.out.println("start( ): Current Element of ds3 = "
				+ ds3.getCurrent());
		ds3.end();
		System.out.println("end( ): Current Element of ds3 = "
				+ ds3.getCurrent());
		ds3.setCurrent(3);
		System.out.println("setCurrent(3): Current Element of ds3 = "
				+ ds3.getCurrent());
		ds3.advance();
		System.out.println("advance(): Current Element of ds3 = "
				+ ds3.getCurrent());
		System.out.println("Test 7: ds3 after navigations --> " + ds3);
	}
}
