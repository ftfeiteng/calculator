package com.example.main;

import java.util.IllegalFormatException;

public class Calculator {

	public static int OK = 0;
	public static int ERR_SYNTAX = -1;
	public static int ERR_DIV_BY_0 = -2;

	private static final char ADDITION = '+';
	private static final char SUBTRACTION = '-';
	private static final char MULTIPLICATION = 'x';
	private static final char DIVISION = '/';
	private static final char MODULUS = '%';

	/**
	 * This class can calculate a string than give a result of the input txt.
	 */
	public Calculator() {

	}

	/**
	 * Return result of a sentence, if input's syntax is not right, it will throw an
	 * expection.
	 * 
	 * 
	 * @param input
	 * @return
	 */
	public int calculate(String input) throws SyntaxException, DividByZeroException {
		String pured = removeSpace(input);
		int ret = invalidate(pured);
		if (ret == ERR_SYNTAX) {
			throw new SyntaxException("Syntax Error");
		}

		String calTmp = pured;
		while (hasNext(calTmp)) {
			int opIndex = nextOperatorIndex(calTmp);
			char curOperator = calTmp.charAt(opIndex);
			float left = getLeftNumber(calTmp, opIndex);
			float right = getRightNumber(calTmp, opIndex);
			float result = 0;
			switch (curOperator) {
			case SUBTRACTION:
				result = left - right;
				break;
			case ADDITION:
				result = left + right;
				break;
			case DIVISION:
				result = left / right;
				break;
			case MULTIPLICATION:
				result = left * right;
				break;
			case MODULUS:
				result = left % right;
				break;
			}
			// copose new string
			int leftEdge = getLeftNumberEdge(calTmp, opIndex);
			int rightEdge = getRightNmuberEdge(calTmp, opIndex);
			String tmp = "";
			for (int i = 0; i < leftEdge; i++) {
				tmp += calTmp.charAt(i);
			}
			tmp += result;
			for (int i = rightEdge + 1; i < calTmp.length(); i++) {
				tmp += calTmp.charAt(i);
			}
			calTmp = tmp;
		}

		float result = Float.parseFloat(calTmp);
		return (int) result;
	}

	/**
	 * Return if this input can be calculated
	 * 
	 * @param input: the txt to be calculated
	 * @return whether this input can be calculated
	 */
	private boolean hasNext(String input) {
		return nextOperatorIndex(input) > 0;
	}

	/**
	 * Return the number to the left the target operator. If it has an subtraction,
	 * it will return the operator before the subtraction. forexample: 5+-3-2 input
	 * 1,return 5 input 4, return -3
	 * 
	 * @param currentOperatorIndex: the text we look into
	 * @param end: the index of current operator and it is the end index where we
	 *        stop search (not include that index)
	 * @return: the number to the left of the index
	 */
	private float getLeftNumber(String input, int currentOperatorIndex) {
		int leftOperatorIndex = getLeftNumberEdge(input, currentOperatorIndex);
		String leftNumber = "";
		for (int i = leftOperatorIndex; i < currentOperatorIndex; i++) {
			leftNumber += input.charAt(i);
		}
		return Float.parseFloat(leftNumber);
	}

	/**
	 * Return the left edge of number to the left the target operator. If it has an
	 * subtraction, it will return the index before the subtraction. forexample:
	 * 5+-3-2 input 1,return 0 input 4, return 1
	 * 
	 * @param input: the text we look into
	 * @param currentOperatorIndex: the index of current operator and it is the end
	 *        index where we stop search (not include that index)
	 * @return the index to the left of current operator
	 */
	private int getLeftNumberEdge(String input, int currentOperatorIndex) {
		int leftOperatorIndex = -1;
		for (int i = currentOperatorIndex - 1; i > 0; i--) {
			if (isOperator(input.charAt(i))) {
				leftOperatorIndex = i;
			}
		}
		return leftOperatorIndex + 1;
	}

	/**
	 * Return the number to the right of the target operator. If it has an
	 * subtraction, it will return the operator before the subtraction. forexample:
	 * 5+-3-2 input 1,return -3 input 4, return 2
	 * 
	 * @param input: the text we look into
	 * @param currentOperatorIndex: the index of current operator and it is the
	 *        begin index where we stop search (exclude that index)
	 * @return: the number to the left of the index
	 */
	private float getRightNumber(String input, int currentOperatorIndex) {
		int rightEdge = getRightNmuberEdge(input, currentOperatorIndex);
		String rightNumber = "";
		for (int i = (currentOperatorIndex + 1); i <= rightEdge; i++) {
			rightNumber += input.charAt(i);
		}
		return Float.parseFloat(rightNumber);
	}

	/**
	 * Return the right edge of to the right of the target operator. If it has an
	 * subtraction, it will return the operator before the subtraction. forexample:
	 * 5+-3-2 input 1,return 3 input 4, return 5
	 * 
	 * @param input: the text we look into
	 * @param currentOperatorIndex: the index of current operator and it is the
	 *        begin index where we stop search (exclude that index)
	 * @return: the number to the left of the index
	 */
	private int getRightNmuberEdge(String input, int currentOperatorIndex) {
		int rightOperatorIndex = input.length();
		for (int i = currentOperatorIndex + 2; i < input.length(); i++) {
			if (isOperator(input.charAt(i))) {
				rightOperatorIndex = i;
			}
		}

		return rightOperatorIndex - 1;
	}

	/**
	 * Return the index of next operator or -1 if it is the last step
	 * 
	 * @param input: the txt to be calculated
	 * @return the index of next operator or -1 if it is the last step
	 */
	private int nextOperatorIndex(String input) {
		int ret = -1;
		for (int i = 0; i < input.length(); i++) {
			// override the pos of operator with high precedenceoperator's index
			if (ret > 0 && isHighPrecedenceOperator(input.charAt(i))) {
				ret = i;
				break;
			}

			// set the index of first operator
			if (ret < 0 && isOperator(input.charAt(i)) && i > 0) {
				ret = i;
			}
		}
		return ret;
	}

	/**
	 * Return if the operator is one of the HighPrecedenceOperator (x / %)
	 * 
	 * @return if the operator is one of the HighPrecedenceOperator
	 */
	private boolean isHighPrecedenceOperator(char c) {
		return c == MULTIPLICATION || c == DIVISION || c == MODULUS;
	}

	private boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

	private boolean isOperator(char c) {
		return c == ADDITION || c == SUBTRACTION || c == MULTIPLICATION || c == DIVISION || c == MODULUS;
	}

	/**
	 * Return a string whose value is this string, with space removed from it.
	 * 
	 * @param txt: input text
	 * @return: the input string, free of space. If the input is null, will return
	 *          an empty string.
	 */
	private String removeSpace(String txt) {
		String ret = "";
		if (txt == null) {
			return ret;
		}
		for (int i = 0; i < txt.length(); i++) {
			if (txt.charAt(i) != ' ') {
				ret += txt.charAt(i);
			}
		}
		return ret;
	}

	/**
	 * Check weather this txt could be calculated There are two kinds of err,
	 * {@link #ERR_SYNTAX} or {@link #ERR_DIV_BY_0} If it has correct format, will
	 * return {@link #OK}
	 * 
	 * For example 1 ++ 2 x 2 x / 4 + 0 will return {@link #ERR_SYNTAX} 1 + 1 - 1 x
	 * 2 / 0 will return {@link #ERR_DIV_BY_0}
	 * 
	 * @param txt
	 * @return Err or OK.
	 */
	public int invalidate(String txt) {
		if (txt == null || txt.isEmpty()) {
			return ERR_SYNTAX;
		}

		String pured = removeSpace(txt);
		if (pured.isEmpty()) {
			return ERR_SYNTAX;
		}
		// check all chars in txt
		for (int i = 0; i < pured.length(); i++) {
			char c = pured.charAt(i);
			boolean isValidChar = isOperator(c) || isNumber(c);
			if (!isValidChar) {
				return ERR_SYNTAX;
			}
		}

		boolean lastIsOperator = false;
		// two operators in a roll (+- or *- etc)
		boolean lastIsConjOperator = false;
		boolean lastDivision = false;
		for (int i = 0; i < pured.length(); i++) {
			char c = pured.charAt(i);
			// numbers
			if (isNumber(c)) {
				if (c == '0' && lastDivision == true) {
					return ERR_DIV_BY_0;
				}
				lastIsOperator = false;
				lastIsConjOperator = false;
				lastDivision = false;
			}
			// operators
			else if (isOperator(c)) {
				// three operators in a roll
				if (lastIsConjOperator) {
					return ERR_SYNTAX;
				}

				// two operators in a roll but this is not subtraction
				if (c != SUBTRACTION && lastIsOperator) {
					return ERR_SYNTAX;
				}
				// start with an operator other than subtraction
				if (c != SUBTRACTION && i == 0) {
					return ERR_SYNTAX;
				}
				// must end with number
				if (i == (pured.length() - 1)) {
					return ERR_SYNTAX;
				}

				if (lastIsOperator == true) {
					lastIsConjOperator = true;
				}

				if (c == DIVISION) {
					lastDivision = true;
				}
				lastIsOperator = true;
			}
		}

		return OK;
	}
}
