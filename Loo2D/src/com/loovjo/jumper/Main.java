package com.loovjo.jumper;

import javax.swing.JFrame;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.utils.ExpressionParser;
import com.loovjo.loo2D.utils.FileLoader;
import com.loovjo.loo2D.utils.Vector;

public class Main extends MainWindow {
	static {
		FileLoader.setLoaderClass(Main.class);
	}

	public Main() {
		super("Test game", new PlayScene(), new Vector(420, 360), true);
		frame.dispose();
		frame.setUndecorated(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}
}
