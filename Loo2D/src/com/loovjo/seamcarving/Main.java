package com.loovjo.seamcarving;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.FileLoader;
import com.loovjo.loo2D.utils.Vector;

public class Main extends MainWindow {
	public Main() {
		super("Seam Carving", new SeamScene(), new Vector(420, 360), true);
		FastImage i = ((SeamScene) currentScene).img;
		frame.setSize(i.getWidth(), i.getHeight());
		frame.setLocationRelativeTo(null);

	}

	public static void main(String[] args) {
		FileLoader.setLoaderClass(Main.class);
		new Main();
	}
}
