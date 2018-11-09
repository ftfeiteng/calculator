package com.example.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Calculator\nPlease input your text:");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String text = null;
		try {
			text = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Calculator cal = new Calculator();
		try {
			int ret = cal.calculate(text);
			System.out.println("" + ret);
		} catch (SyntaxException e) {
			System.out.println("Error: Syntax Error");
		} catch (DividByZeroException e) {
			System.out.println("Error: Division by zero");
		}

	}

}
