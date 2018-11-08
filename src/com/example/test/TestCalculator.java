package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.main.Calculator;
import com.example.main.DividByZeroException;
import com.example.main.SyntaxException;

class TestCalculator {

	Calculator cal;

	@BeforeEach
	void before() {
		cal = new Calculator();
	}

	@Test
	void testOneNumberCalculate() throws SyntaxException, DividByZeroException {
		assertEquals(4, cal.calculate("4"));
		assertEquals(-4, cal.calculate("-4"));
	}

	@Test
	void testTwoStepIntCalculate() throws SyntaxException, DividByZeroException {
		assertEquals(3, cal.calculate("1+1+1"));
		assertEquals(2, cal.calculate("1+1x1"));
		assertEquals(11, cal.calculate("1+2/2+9"));
	}

	@Test
	void testSingleCalculate() throws SyntaxException, DividByZeroException {
		assertEquals(8, cal.calculate("4 + 4"));
		assertEquals(0, cal.calculate("4 - 4"));
		assertEquals(1, cal.calculate("4 / 4"));
		assertEquals(16, cal.calculate("4 x4"));
		assertEquals(0, cal.calculate("4 % 4"));
		assertEquals(0, cal.calculate("4 +- 4"));
		assertEquals(8, cal.calculate("4 -- 4"));
		assertEquals(-16, cal.calculate("4 x- 4"));
		assertEquals(-1, cal.calculate("4 /- 4"));
		assertEquals(0, cal.calculate("4 %- 4"));
		assertEquals(4040, cal.calculate("4000 + 40"));
		assertEquals(80000, cal.calculate("2000 x 40"));
		assertEquals(0, cal.calculate("0 / 40"));
		assertEquals(0, cal.calculate("0 / 1"));
		assertEquals(0, cal.calculate("-4 -- 4"));
		assertEquals(-160, cal.calculate("-40 x 4"));
		assertEquals(-160, cal.calculate("-040 x 4"));
	}

	@Test
	void testSimpleCalculate() throws SyntaxException, DividByZeroException {
//		assertEquals(14, cal.calculate("3 x 3 + 3 - -2"));
	}

	@Test
	void testInvalidate() {
		assertEquals(Calculator.OK, cal.invalidate("3 x 3 + 3 - -2"));
		assertEquals(Calculator.OK, cal.invalidate("2 x 3 + 2 + 5 + 7 - 55 - 0 - 12 - 4 / 17 / 3 x 7 + 2 - 16"));
		assertEquals(Calculator.OK, cal.invalidate("3 x 3 + 3 - 2"));
		assertEquals(Calculator.OK, cal.invalidate("1 % 3 - 1"));
		assertEquals(Calculator.OK, cal.invalidate("234"));
		assertEquals(Calculator.OK, cal.invalidate("0/10"));
		assertEquals(Calculator.OK, cal.invalidate("1--2"));
		assertEquals(Calculator.OK, cal.invalidate("1+-2"));
		assertEquals(Calculator.OK, cal.invalidate("1x-2"));
		assertEquals(Calculator.OK, cal.invalidate("1/-2"));
		assertEquals(Calculator.OK, cal.invalidate("1%-2"));
		assertEquals(Calculator.OK, cal.invalidate("-2"));
	}

	@Test
	void testSynaError() {
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("++"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("--"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("%%"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("%/"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("xx"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("1 ++ 2 x 2 x / 4 + 0"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("++--"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("1+op"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("adop"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("1+--2"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("1---2"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("1xx90"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("+2"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate("x22"));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate(""));
		assertEquals(Calculator.ERR_SYNTAX, cal.invalidate(null));

	}

	@Test
	void testDividedByZero() {
		assertEquals(Calculator.ERR_DIV_BY_0, cal.invalidate("1 + 1 - 1 x 2 / 0"));
		assertEquals(Calculator.ERR_DIV_BY_0, cal.invalidate("234 / 0"));
	}
}
