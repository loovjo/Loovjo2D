package com.loovjo.loo2D.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static HashMap<String, FastImage> CACHE = new HashMap<String, FastImage>();

	public static int MAX_SIZE = 1000;

	public static FastImage getImage(String path) {
		if (!CACHE.containsKey(path))
			load(path);
		return CACHE.get(path);
	}

	public static void reloadAll() {
		CACHE.clear();
	}

	private static void load(String path) {
		try {
			FastImage img = new FastImage(ImageIO.read(FileLoader.loaderClass
					.getResourceAsStream(path)));
			CACHE.put(path, img);
		} catch (IOException e) {
			CACHE.put(path, null);
		}
	}

}
