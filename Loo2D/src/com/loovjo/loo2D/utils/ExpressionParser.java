package com.loovjo.loo2D.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

public class ExpressionParser {
	public String expression;

	public static void main(String[] args) {
		System.out.println(new ExpressionParser("(1+3)/(1+2)").evaluate());

	}

	public static MathContext mc = new MathContext(50);

	public static String findPattern(Float[] floats) {
		BigDecimal[] bigPattern = new BigDecimal[floats.length];
		for (int i = 0; i < floats.length; i++)
			bigPattern[i] = new BigDecimal(floats[i]);
		return findPattern(bigPattern);
	}

	// *VERY* slow.
	public static String findPattern(BigDecimal[] bigPattern) {
		Random rand = new Random();
		String numbers = "1234567890n";
		String ops = "+-*%/^()";
		long lastTime = System.currentTimeMillis();
		long tests = 0;
		while (true) {
			String current = "";
			for (int i = 0; i < rand.nextInt(10) + 1; i++) {
				current += numbers.charAt(rand.nextInt(numbers.length()));
				current += ops.charAt(rand.nextInt(ops.length()));
			}
			current = current.substring(0, current.length() - 1);
			if (!current.contains("n"))
				continue;
			boolean fail = false;
			for (int i = 0; i < bigPattern.length; i++) {
				ExpressionParser ep = new ExpressionParser(current);
				ep.setVariable("n", i);
				try {
					BigDecimal bd = ep.evaluate();
					if (!bd.equals(bigPattern[i])) {
						fail = true;
						break;
					}
				} catch (Exception e) {
					fail = true;
					break;
				}
			}
			tests++;
			if (System.currentTimeMillis() - lastTime > 1000) {
				/*System.out.println("Tesing " + tests
						+ " tests per seconds. Latest fail: " + current);*/
				lastTime = System.currentTimeMillis();
				tests = 0;
			}
			if (fail)
				continue;
			return current;
		}
	}

	private void setVariable(String string, int i) {
		expression = expression.replace(string, "" + i);
	}

	public ExpressionParser(String exp) {
		expression = exp;
	}

	public BigDecimal evaluate() {
		if (expression.startsWith("(") && expression.endsWith(")")) {
			int p = 0;
			boolean a = true;
			for (int c = 0; c < expression.length(); c++) {
				if (expression.charAt(c) == '(')
					p++;
				if (expression.charAt(c) == ')')
					p--;
				if (p == 0 && c != expression.length()-1) {
					a = false;
				}
				if (p < 0)
					throw new ArithmeticException("Unmatched parenthases");
			}
			if (p != 0) {
				throw new ArithmeticException("Unmatched parenthases");
			}
			if (a) {
				expression = expression.substring(1, expression.length() - 1);
			}
		}

		expression = expression.replace(" ", "");

		String e = expression; // Less typing! Yay!
		try {
			return new BigDecimal(e); // If expression is just a number,
										// return it.
		} catch (NumberFormatException ex) {
		}

		Object[] o = breakDown(e);
		BigDecimal firstPart = (BigDecimal) o[0];
		char ch = (char) o[1];
		BigDecimal secondPart = (BigDecimal) o[2];
		if (ch == '+')
			return firstPart.add(secondPart, mc);
		else if (ch == '-')
			return firstPart.subtract(secondPart, mc);
		else if (ch == '*')
			return firstPart.multiply(secondPart, mc);
		else if (ch == '/') {
			return firstPart.divide(secondPart, mc);
		} else if (ch == '%')
			return firstPart.remainder(secondPart, mc);
		else if (ch == '^')
			return new BigDecimal(Math.pow(firstPart.doubleValue(),
					secondPart.doubleValue()));

		return new BigDecimal(0);
	}

	public Object[] breakDown(String e) {

		String order = "+-*%/^"; // Define order
		for (int c = 0; c < order.length(); c++) {
			int parenDepth = 0;
			for (int i = 0; i < expression.length(); i++) {
				char ch = e.charAt(i);
				if (ch == '(')
					parenDepth++;
				if (ch == ')')
					parenDepth--;
				if (parenDepth == 0) {
					if (ch == order.charAt(c)) {
						BigDecimal firstPart = new ExpressionParser(
								e.substring(0, i)).evaluate();
						BigDecimal secondPart = new ExpressionParser(
								e.substring(i + 1)).evaluate();
						return new Object[] { firstPart, ch, secondPart };
					}
				}
			}
		}
		return new Object[] { new BigDecimal(0), ' ', new BigDecimal(0) };
	}
}
