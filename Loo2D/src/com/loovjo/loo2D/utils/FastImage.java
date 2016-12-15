package com.loovjo.loo2D.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class FastImage {
	
	
	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public static int[] SUPPORTED_TYPES = new int[] {BufferedImage.TYPE_INT_ARGB, BufferedImage.TYPE_INT_RGB};
	
	private int[] pixels;
	private int width;
	private int type = BufferedImage.TYPE_INT_RGB;

	public FastImage(int width, int height) {
		this(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public FastImage(BufferedImage img) {
		boolean in = false;
		for (int type : SUPPORTED_TYPES)
			if (type == img.getType())
				in = true;
		if (!in) {
			BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(),SUPPORTED_TYPES[0]);
			img2.getGraphics().drawImage(img, 0, 0, null);
			img = img2;
		}
		type = img.getType();
		width = img.getWidth();
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData()
				.clone();
	}


	public FastImage(int width, int height, int imageType) {
		pixels = new int[width * height];
		this.width = width;
		setType(imageType);
	}

	public int getRGB(int x, int y) {
		return pixels[x + y * width];
	}

	public boolean setRGB(int x, int y, int color) {
		if (x >= width || x < 0 || y >= getHeight() || y < 0)
			return false;
		int colB4 = pixels[x + y * width];
		pixels[x + y * width] = color;
		return colB4 != color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return pixels.length / width;
	}

	public BufferedImage toBufferedImage() {
		BufferedImage img = new BufferedImage(width, getHeight(),
				type);
		int[] p = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < pixels.length; i++)
			p[i] = pixels[i];
		return img;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public FastImage clone() {
		return new FastImage(toBufferedImage());
	}
}
