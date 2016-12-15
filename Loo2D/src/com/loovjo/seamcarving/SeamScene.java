package com.loovjo.seamcarving;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.util.Random;

import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.ImageLoader;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class SeamScene implements Scene {
	public FastImage img;

	public SeamScene() {
		img = ImageLoader.getImage("Images/Skärmavbild 2015-05-20 kl. 13.28.05.png");
	}

	@Override
	public void update() {

	}

	public FastImage seamCarve(FastImage img, int width) {
		if (img.getWidth() > width) {
			while (img.getWidth() > width) {
				img = removeSeam(img, 1);
			}
		}
		return img;
	}

	private FastImage removeSeam(FastImage img, int add) {
		int bestX = RandomUtils.RAND.nextInt(img.getWidth());
		int[] seamList = new int[img.getHeight()];
		seamList[0] = bestX;
		for (int y = 1; y < img.getHeight(); y++) {
			int x = seamList[y - 1];
			int best = Integer.MAX_VALUE;
			int bx = 0;
			for (int i = -1; i <= 1; i++) {
				int cx = x + i;
				if (cx > 0 && cx < img.getWidth()) {
					int currentEntropy = getEntropy(new Vector(cx, y), img);
					if (currentEntropy < best) {
						best = currentEntropy;
						bx = cx;
					}
				}
			}
			seamList[y] = bx;
		}
		FastImage newImage = new FastImage(img.getWidth() + add,
				img.getHeight());
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth() + add; x++) {
				newImage.setRGB(x, y,
						img.getRGB(x + (x > seamList[y] ? -add : 0), y));
			}
		}
		return newImage;
	}

	private int getEntropy(Vector pos, FastImage img) {
		Color c = new Color(img.getRGB((int) pos.getX(), (int) pos.getY()));
		int e = 0;
		int s = 0;
		for (int i = 0; i < 8; i++) {
			try {
				e += RandomUtils.colorDistance(
						c,
						new Color(img.getRGB((int) pos.moveInDir(i).getX(),
								(int) pos.moveInDir(i).getY())));
				s++;
			} catch (IndexOutOfBoundsException e1) {
			}
		}
		return e / s;
	}

	@Override
	public void render(Graphics g, int width, int height) {
		int a = 1;
		if (img.getWidth() < width - a)
			img = removeSeam(img, a);
		if (img.getWidth() > width + a)
			img = removeSeam(img, -a);
		g.drawImage(img.toBufferedImage(), 0, 0, width, height, null);
	}

	@Override
	public void mousePressed(Vector pos, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Vector pos, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Vector pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(char key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheal(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

}
