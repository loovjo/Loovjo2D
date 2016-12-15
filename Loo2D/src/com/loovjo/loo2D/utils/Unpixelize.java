package com.loovjo.loo2D.utils;

import java.awt.Color;
import java.awt.PageAttributes.OriginType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Unpixelize {
	public static void main(String[] args) throws IOException {
		FileLoader.setLoaderClass(Unpixelize.class);
		int scale = 10;
		Logger log = new Logger("Unpixelize");

		FastImage orginal = ImageLoader.getImage("/Images/pixels.png");
		FastImage rescale = new FastImage(orginal.getWidth() * scale,
				orginal.getHeight() * scale);
		for (int x = 0; x < rescale.getWidth(); x++) {
			for (int y = 0; y < rescale.getHeight(); y++) {
				int amount = 0;
				int r = 0;
				int g = 0;
				int b = 0;
				for (int x1 = x - scale; x1 < x + scale; x1++) {
					for (int y1 = y - scale; y1 < y + scale; y1++) {
						try {
							Color c = new Color(orginal.getRGB(x1 / scale, y1
									/ scale));
							r += c.getRed();
							g += c.getGreen();
							b += c.getBlue();
							amount++;
						} catch (IndexOutOfBoundsException e) {
						}
					}
				}
				rescale.setRGB(x, y, new Color(r / amount, g / amount, b
						/ amount).getRGB());
			}
			log.log(((float) x / rescale.getWidth()) * 100 + "%");
		}
		log.log("Step 1 done");
		ArrayList<Color> colors = new ArrayList<Color>();

		for (int x = 0; x < orginal.getWidth(); x++) {
			for (int y = 0; y < orginal.getHeight(); y++) {
				if (!colors.contains(new Color(orginal.getRGB(x, y))))
					colors.add(new Color(orginal.getRGB(x, y)));
			}
			log.log(((float) x / orginal.getWidth()) * 100 + "%");
		}
		log.log("Step 2 done");
		ImageIO.write(rescale.toBufferedImage(), "PNG", new File("output1.png"));
		for (int x = 0; x < rescale.getWidth(); x++) {
			for (int y = 0; y < rescale.getHeight(); y++) {
				int distance = Integer.MAX_VALUE;
				Color col = null;
				for (Color c : colors) {
					if (RandomUtils.colorDistance(c,
							new Color(rescale.getRGB(x, y))) < distance) {
						distance = RandomUtils.colorDistance(c, new Color(
								rescale.getRGB(x, y)));
						col = c;
					}
				}
				rescale.setRGB(x, y, col.getRGB());
			}
			log.log(((float) x / rescale.getWidth()) * 100 + "%");
		}
		ImageIO.write(rescale.toBufferedImage(), "PNG", new File("output.png"));
		System.out.println("done");
	}
}
