package assignment3;

public class Driver {

	/**
	 * I just made a copy of the JUnit class (included) and put everything
	 * in here so you have a runnable driver class in case you needed it.
	 * @param args
	 */
	public static void main(String args[]) {

		PushbackableTokenizer pushbackTokenizer = new PushbackTokenizerImpl("Hello this is a test");
		assertEquals("Hello", pushbackTokenizer.nextToken()); // will print “Hello”
		assertEquals("this", pushbackTokenizer.nextToken()); // will print “this”
		assertEquals("is", pushbackTokenizer.nextToken()); // will print “is”

		pushbackTokenizer.pushback(); // pushes back “is”
		pushbackTokenizer.pushback(); // pushes back “this” Now

		assertEquals("this", pushbackTokenizer.nextToken());// will print “this”
		assertEquals("is", pushbackTokenizer.nextToken()); // will print “is”
		assertEquals("a", pushbackTokenizer.nextToken()); // will print “a”
		assertEquals("test", pushbackTokenizer.nextToken());// will print “test”

		pushbackTokenizer.pushback(); // pushes back “test”
		pushbackTokenizer.pushback(); // pushes back “a”

		assertEquals("a", pushbackTokenizer.nextToken()); // will print “a”
		assertEquals("test", pushbackTokenizer.nextToken()); // will print “test”

		pushbackTokenizer = new PushbackTokenizerImpl("Hello this is a test");
		assertEquals("Hello", pushbackTokenizer.nextToken()); // will print “Hello”
		assertEquals("this", pushbackTokenizer.nextToken()); // will print “this”
		assertEquals("is", pushbackTokenizer.nextToken()); // will print “is”
		assertEquals("a", pushbackTokenizer.nextToken()); // will print “is”
		assertEquals("test", pushbackTokenizer.nextToken()); // will print “is”

		pushbackTokenizer.pushback(); // pushes back “test”
		pushbackTokenizer.pushback(); // pushes back “a”
		pushbackTokenizer.pushback(); // pushes back “test”
		pushbackTokenizer.pushback(); // pushes back “a”
		pushbackTokenizer.pushback(); // pushes back “test”
		pushbackTokenizer.pushback(); // pushes back “a”
		pushbackTokenizer.pushback(); // pushes back “test”
		pushbackTokenizer.pushback(); // pushes back “a”
	}

	/**
	 * This is just a copy of the JUnit assertEquals method for strings.
	 * @param one
	 * @param two
	 */
	private static void assertEquals(String one, String two) {
		if (!one.equals(two)) {
			throw new IllegalStateException("Error: " + one + " does not equal " + two);
		}
	}

}
