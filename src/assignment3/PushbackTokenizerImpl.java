package assignment3;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Your task is to implement the above interface using the Adapter pattern. For
 * this, use the StringTokenizer and Stack classes. You must also submit a
 * driver program that creates an instance of the class and exercises all of its
 * methods. 
 * 
 * You can think of the tokens as of two types: 
 * 
 * 1• the ones that have been supplied to the client reading the PushbackableTokenizer 
 * and were either not pushed back or were pushed back but then reread as many times as the
 * number of pushbacks.
 * 
 * 2• the ones that have been supplied to the client reading the
 * PushbackableTokenizer and have been pushed back (perhaps multiple times), but
 * have not been read back after the last pushback. 
 * 
 * For example, 
 * 
 * suppose the tokens 1, 2, 3, 4, 5, 6 were read and then the client 
 * pushes back 6, 5 and then 4 and then rereads 4. 
 * Then 1, 2, 3, and 4 belong to the first category  and 5 and 6 belong 
 * to the second category. 
 * 
 * You must use a Stack object with the actual parameter String to store 
 * both types of tokens. 
 * 
 * The implementation must be called PushbackTokenizer. 
 * 
 * It must have the following signature for its only constructor:
 * public PushbackTokenizer(String data) { 
 * 
 * Test your implementation using assert statements. 
 * Every method should be thoroughly tested.
 *  
 * To elaborate on the functionality, assume that we create a
 * PushbackTokenizer as below. 
 * 
 * PushbackableTokenizer pushbackTokenizer = new PushbackTokenizer("Hello this is a test");
 * System.out.println(pushbackTokenizer.nextToken()); will print “Hello”
 * System.out.println(pushbackTokenizer.nextToken()); will print “this”
 * System.out.println(pushbackTokenizer.nextToken()); will print “is”
 * pushbackTokenizer.pushback(); // pushes back “is”
 * pushbackTokenizer.pushback(); // pushes back “this” Now
 * System.out.println(pushbackTokenizer.nextToken());
 * will print “this” 
 * The next System.out.println(pushbackTokenizer.nextToken());
 * will print “is” System.out.println(pushbackTokenizer.nextToken()); 
 * will print “a” System.out.println(pushbackTokenizer.nextToken());
 * will print “test” 
 * pushbackTokenizer.pushback(); // pushes back “test”
 * pushbackTokenizer.pushback(); // pushes back “a”
 * System.out.println(pushbackTokenizer.nextToken()); will print “a”
 * System.out.println(pushbackTokenizer.nextToken()); will print “test”
 * 
 * @author Matt
 *
 */
public class PushbackTokenizerImpl implements PushbackableTokenizer {

	private StringTokenizer tokenizer = null;
	private Stack<String> stack = null;
	private Stack<String> stackCache = new Stack<String>();

	/**
	 * The constructor reads in the string, tokenizes it, and reverses
	 * the order by pushing and popping on to the stack and its cache.
	 * 
	 * @param inTokenString
	 */
	public PushbackTokenizerImpl(String inTokenString) {
		tokenizer = new StringTokenizer(inTokenString);
		stack = new Stack<String>();
		while (tokenizer.hasMoreTokens()) {
			stackCache.push(tokenizer.nextToken());
		}
		// Now move to the real stack which will reverse the order
		while (!stackCache.isEmpty()) {
			stack.push(stackCache.pop());
		}
	}

	/**
	 * Pops the next token off the stack, ads it to the cache and returns it.
	 */
	@Override
	public String nextToken() {
		String val = stack.pop();
		stackCache.push(val);
		return val;
	}

	/**
	 * Checks to see if there is another token available.
	 */
	@Override
	public boolean hasMoreTokens() {
		return !stack.isEmpty();
	}

	/**
	 * Pushes a value from the cache back on to the stack.
	 */
	@Override
	public void pushback() {

		if (! stackCache.isEmpty()) {
			stack.push(stackCache.pop());
		}
	}

}
