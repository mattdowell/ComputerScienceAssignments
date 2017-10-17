package ics240.assignment5;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Your implementation should use Stacks and the algorithms you use should be
 * limited to 1 or more of these algorithms listed
 * 
 * 2-stack algorithm to solve fully qualified infix expressions
 * 
 * 1 stack algorithm to solve post-fix
 * 
 * 1 stack algorithm to convert infix to post-fix
 * 
 * You should submit only "ExpressionSolver.java" (and any other supporting
 * *.java files you used) I will use "ExpressionSolverTester.java" to evaluate
 * your assignment. No need to submit this tester.
 * 
 * @author Matt
 * 
 */
public class ExpressionSolver
{

	/**
	 * 
	 * Method to solve the expressions
	 * 
	 * Expression can be one of the three types listed here 1. Full Qualified
	 * Input Expression 2. Input Expression that needs operator precedence rules
	 * applied 3. Post Fix Expression
	 * 
	 * @param expression
	 * @param type
	 * @return
	 */
	public static double solve(String inExpression, int type)
	{
		if (type == 1)
		{
			return solveInfix(inExpression);
		} else if (type == 2)
		{
			return convertInfixAndSolve(inExpression);
		} else if (type == 3)
		{
			return solvePostfix(inExpression, false);
		} else
		{
			throw new IllegalStateException("Wrong type");
		}
	}

	/**
	 * Solves an infix expression statement with parenthesis 2-stack algorithm
	 * to solve fully qualified infix expressions
	 * http://stackoverflow.com/questions
	 * /13421424/how-to-evaluate-an-infix-expression
	 * -in-just-one-scan-using-stacks
	 * 
	 * 
	 * @param inExpression
	 * @return the value of the expression
	 */
	private static double solveInfix(String inExpression)
	{

		String expression = inExpression.replaceAll("\\s+", "");

		Stack<String> operatorStack = new Stack<String>();
		Stack<String> numberStack = new Stack<String>();

		System.out.println("Solving infix: " + expression);

		for (int i = 0; i < expression.length(); i++)
		{
			String tkn = String.valueOf(expression.charAt(i));

			// 1. if character is number or ( push on the numberStack
			if (isNumber(tkn))
			{
				numberStack.push(tkn);
			} else if (isOpeningParens(tkn))
			{
				operatorStack.push(tkn);
			} else if (isOperator(tkn))
			{

				// while the top of the operatorStack is not of smaller
				// precedence than this character
				// while token priority <= top of stack priority <-- CHECK THIS
				while (!operatorStack.empty()
						&& getPriority(tkn) >= getPriority(operatorStack.peek()))
				{

					// pop from operatorStack
					String op = operatorStack.pop();

					// pop two operands from number stack
					String numTwo = numberStack.pop();
					String numOne = numberStack.pop();

					// store op1 op op2 on the number
					double val = evaluate(new Double(numOne), op, new Double(
							numTwo));

					numberStack.push(String.valueOf(val));
				}

				System.out.println("Adding to stack: " + tkn);
				operatorStack.push(tkn);

			} else if (isClosingParens(tkn))
			{

				String lastOp = operatorStack.pop();
				// move down the stack until we come to the opening parens
				while (!isOpeningParens(lastOp))
				{
					String numTwo = numberStack.pop();
					String numOne = numberStack.pop();
					double val = evaluate(new Double(numOne), lastOp,
							new Double(numTwo));
					System.out.println("Eval: " + numOne + lastOp + numTwo);
					numberStack.push(String.valueOf(val));
					lastOp = operatorStack.pop();
				}
			} else
			{
				throw new IllegalStateException("Invalid token: " + tkn);
			}
		}

		double theReturn = Double.valueOf(numberStack.pop());
		System.out.println("Infix solution: " + theReturn);
		return theReturn;
	}

	/**
	 * Returns the prioriy of the given operator
	 * 
	 * @param inOperator
	 * @return
	 */
	private static int getPriority(String inOperator)
	{
		int thePriority = 0;
		if (inOperator.equals("+") || inOperator.equals("-"))
		{
			thePriority = 1;
		} else if (inOperator.equals("*") || inOperator.equals("/"))
		{
			thePriority = 2;
		} else if (inOperator.equals("^"))
		{
			thePriority = 3;
		} else
		{
			thePriority = 4;
		}
		return thePriority;
	}

	/**
	 * 1. Print operands as they arrive.
	 * 
	 * 2. If the stack is empty or contains a left parenthesis on top, push the
	 * incoming operator onto the stack.
	 * 
	 * 3. If the incoming symbol is a left parenthesis, push it on the stack.
	 * 
	 * 4. If the incoming symbol is a right parenthesis, pop the stack and print
	 * the operators until you see a left parenthesis. Discard the pair of
	 * parentheses.
	 * 
	 * 5. If the incoming symbol has higher precedence than the top of the
	 * stack, push it on the stack.
	 * 
	 * 6. If the incoming symbol has equal precedence with the top of the stack,
	 * use association. If the association is left to right, pop and print the
	 * top of the stack and then push the incoming operator. If the association
	 * is right to left, push the incoming operator.
	 * 
	 * 7. If the incoming symbol has lower precedence than the symbol on the top
	 * of the stack, pop the stack and print the top operator. Then test the
	 * incoming operator against the new top of stack.
	 * 
	 * 8. At the end of the expression, pop and print all operators on the
	 * stack. (No parentheses should remain.)
	 * 
	 * @param inExpression
	 * @return
	 */
	private static double convertInfixAndSolve(String inExpression)
	{
		String expression = inExpression.replaceAll("\\s+", "");
		String postfixExpression = "";

		Stack<String> stack = new Stack<String>();

		System.out.println("Trying to convert: " + expression);

		for (int i = 0; i < expression.length(); i++)
		{
			String itemRead = String.valueOf(expression.charAt(i));

			if (isClosingParens(itemRead))
			{
				postfixExpression += popStackUntilOpenParen(stack);
			} else if (isOperator(itemRead) || isOpeningParens(itemRead))
			{
				postfixExpression += handleOperator(itemRead,
						getPriority(itemRead), stack);

			} else if (isNumber(itemRead))
			{
				postfixExpression += itemRead;
			} else
			{
				throw new IllegalStateException("Got an invaid string: "
						+ itemRead);
			}
			System.out.println(postfixExpression);
		}

		postfixExpression += popStackUntilOpenParen(stack);
		if (!postfixExpression.equals(""))
		{
			System.out.println(postfixExpression);
		}
		return solvePostfix(postfixExpression, true);
	}

	/**
	 * Remove all the values from the stack and convert in to a single postfix
	 * operation
	 * 
	 * @param inStack
	 * @return single postfix operation
	 */
	private static String popStackUntilOpenParen(Stack<String> inStack)
	{
		String theReturn = "";
		while (!inStack.isEmpty())
		{
			String op = inStack.pop();
			if (op.equals("("))
			{
				break;
			} else
			{
				theReturn += op;
			}
		}
		return theReturn;
	}

	/**
	 * 1. Print operands as they arrive.
	 * 
	 * 2. If the stack is empty or contains a left parenthesis on top, push the
	 * incoming operator onto the stack.
	 * 
	 * 3. If the incoming symbol is a left parenthesis, push it on the stack.
	 * 
	 * 4. If the incoming symbol is a right parenthesis, pop the stack and print
	 * the operators until you see a left parenthesis. Discard the pair of
	 * parentheses.
	 * 
	 * 5. If the incoming symbol has higher precedence than the top of the
	 * stack, push it on the stack.
	 * 
	 * 6. If the incoming symbol has equal precedence with the top of the stack,
	 * use association. If the association is left to right, pop and print the
	 * top of the stack and then push the incoming operator. If the association
	 * is right to left, push the incoming operator.
	 * 
	 * 7. If the incoming symbol has lower precedence than the symbol on the top
	 * of the stack, pop the stack and print the top operator. Then test the
	 * incoming operator against the new top of stack.
	 * 
	 * 8. At the end of the expression, pop and print all operators on the
	 * stack. (No parentheses should remain.)
	 * 
	 * @param infixOperator
	 *            +-*^ etc..
	 * @param inPrecidence
	 *            The precidence the operator takes
	 * @param inStack
	 *            The given operator stack
	 * @return the evaluated postfix portion
	 */
	public static String handleOperator(String infixOperator, int inPrecedence,
			Stack<String> inStack)
	{
		String theReturn = "";
		System.out.println("OP: " + infixOperator + " PREC: " + inPrecedence);

		// While the stack is not empty
		while (!inStack.isEmpty())
		{
			// Pop off the top operator
			String topOperator = inStack.pop();

			if (isOpeningParens(topOperator))
			{
				// If it was an open parens, just put the new operator on the
				// stack
				inStack.push(topOperator);
				break;
			} else if (isClosingParens(topOperator))
			{
				theReturn += popStackUntilOpenParen(inStack);
			} else
			{
				int topOpPrecedence = getPriority(topOperator);
				if (topOpPrecedence < inPrecedence)
				{
					inStack.push(topOperator);
					break;
				} else
				{
					theReturn += topOperator;
				}
			}
		}

		if (!isClosingParens(infixOperator))
		{
			// Always push the infix operator if not closing parens
			inStack.push(infixOperator);
		}

		if (theReturn.length() > 0)
		{
			System.out.println("Return: " + theReturn);
		}
		return theReturn;
	}

	/**
	 * Solves a post fix expression
	 * 
	 * @param inExpression
	 * @return solved expression value
	 */
	private static double solvePostfix(String inExpression, boolean isConverted)
	{
		List<String> expressions = tokenizeExpression(inExpression, isConverted);
		Stack<String> stack = new Stack<String>();
		System.out.println("Solving postfix: " + inExpression);
		
		for (String tkn : expressions)
		{
			System.out.println("Working with token: " + tkn);
			if (isOperator(tkn))
			{
				/*
				 * If the element is a operator O, pop twice and get A and B
				 * respectively. Calculate B o A and push it back to the stack
				 */
				double secondNum = Double.valueOf(stack.pop().toString());
				double firstNum = Double.valueOf(stack.pop().toString());
				double val = evaluate(firstNum, tkn, secondNum);
				System.out.println("Pushing val: " + val);
				stack.push(String.valueOf(val));
			} else if (isNumber(tkn))
			{
				System.out.println("Pushing num: " + tkn);
				stack.push(tkn);
			}

			System.out.println("Stack: " + stack);

		}

		double theResult = Double.valueOf(stack.pop().toString());
		System.out.println("Result: " + theResult);
		return theResult;
	}
	
	/**
	 * This list is needed because we have two difference kinds of expressions. Ones with spaces and ones without. We need to be able to support both.
	 * @param inExpression
	 * @return List containing the tokenzed expressions
	 */
	private static List<String> tokenizeExpression(String inExpression, boolean isConverted) {
		List<String> theReturn = new ArrayList<String>();
		
		// If we converted it, there are no spaces, tokenize by each char
		if (isConverted) {
			// tokenize by each character
			String expression = inExpression.replaceAll("\\s+", "");
			for (int i = 0; i < expression.length(); i++) {
				theReturn.add(String.valueOf(expression.charAt(i)));
			}
		} else {
			StringTokenizer st = new StringTokenizer(inExpression);
			while (st.hasMoreElements()) {
				theReturn.add(st.nextToken());
			}
		}
		return theReturn;
	}

	/**
	 * Evaluates the two numbers and the operator
	 * 
	 * @param firstNum
	 * @param operator
	 * @param secondNum
	 * @return evaluated double
	 */
	private static double evaluate(double firstNum, String operator,
			double secondNum)
	{
		if (operator.equals("+"))
		{
			System.out.println("Eval: " + firstNum + "+" + secondNum);
			return firstNum + secondNum;
		} else if (operator.equals("*"))
		{
			System.out.println("Eval: " + firstNum + "*" + secondNum);
			return firstNum * secondNum;
		} else if (operator.equals("/"))
		{
			System.out.println("Eval: " + firstNum + "/" + secondNum);
			return firstNum / secondNum;
		} else if (operator.equals("-"))
		{
			System.out.println("Eval: " + firstNum + "-" + secondNum);
			return firstNum - secondNum;
		} else if (operator.equals("^"))
		{
			System.out.println("Eval: " + firstNum + "^" + secondNum);
			return Math.pow(firstNum, secondNum);
		} else
		{
			throw new IllegalStateException("No valid operator: " + operator);
		}
	}

	/**
	 * @param inToken
	 * @return true of opening parenthesis
	 */
	private static boolean isOpeningParens(String inToken)
	{
		boolean theReturn = false;
		if (inToken.equals("("))
		{
			theReturn = true;
		}
		return theReturn;
	}

	/**
	 * @param inToken
	 * @return true of closing parenthesis
	 */
	private static boolean isClosingParens(String inToken)
	{
		boolean theReturn = false;
		if (inToken.equals(")"))
		{
			theReturn = true;
		}
		return theReturn;
	}

	/**
	 * IS the given value a valid operator
	 * 
	 * @param inToken
	 * @return
	 */
	private static boolean isOperator(String inToken)
	{
		boolean theReturn = false;
		if (inToken.equals("*") || inToken.equals("/") || inToken.equals("+")
				|| inToken.equals("-") || inToken.equals("^"))
		{
			theReturn = true;
		}
		return theReturn;
	}

	/**
	 * Is the given value a valid number
	 * 
	 * @param inToken
	 * @return
	 */
	private static boolean isNumber(String inToken)
	{
		boolean theReturn = false;
		if (inToken.matches("[0-9]+"))
		{
			theReturn = true;
		}
		return theReturn;
	}
}
