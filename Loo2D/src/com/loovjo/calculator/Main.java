package com.loovjo.calculator;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.Vector;

public class Main extends MainWindow {

	public Main() {
		super("Calculator", new CalculatorScene(), new Vector(420, 360), false);
	}
	public static void main(String[] args) {
		new Main();
	}
}
