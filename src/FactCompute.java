import java.math.BigInteger;
import java.util.Scanner;

public class FactCompute {

	public static void main(String args[]) {
		int givenNumber;
		BigInteger nonRecursiveResult;
		BigInteger recursiveResult;

		System.out.println("Factorial of (positive int): ");
		Scanner in = new Scanner(System.in);

		givenNumber = in.nextInt();

		long startNonRecursive = System.nanoTime();
		nonRecursiveResult = factNonRecursive(givenNumber);
		System.out.println("NonRecursvie factorial result of: " + givenNumber + " is = " + nonRecursiveResult);
		System.out.println(" and took: " + (System.nanoTime() - startNonRecursive) + " nanos");

		long startRecursive = System.nanoTime();
		recursiveResult = factRecursive(new BigInteger(String.valueOf(givenNumber)));
		System.out.println("   Recursvie factorial result of: " + givenNumber + " is = " + recursiveResult);
		System.out.println(" and took: " + (System.nanoTime() - startRecursive) + " nanos");
	}

	private static BigInteger factNonRecursive(final int num) {
		int counter;
		BigInteger factorial = new BigInteger("1");
		for (counter = 1; counter <= num; counter++) {
			factorial = factorial.multiply(new BigInteger(String.valueOf(counter)));
		}
		return factorial;
	}

	private static BigInteger factRecursive(final BigInteger num) {

		if (num.longValue() <= 1) {
			return new BigInteger("1");
		} else {
			return num.multiply(factRecursive(num.subtract(new BigInteger("1"))));
		}
	}
}
