package com.loovjo.fifteenpuzzle;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.utils.FileLoader;
import com.loovjo.loo2D.utils.Vector;

public class FifteenMain extends MainWindow {
	public FifteenMain() {
		super("Fifteen Puzzle", new FifteenScene(), new Vector(600, 500), true);
	}

	public static void main(String[] args) {
		if (args.length > 0)
			FifteenScene.SIZE = Integer.parseInt(args[0]);
		FileLoader.setLoaderClass(FifteenMain.class);
		new FifteenMain();
	}
}
