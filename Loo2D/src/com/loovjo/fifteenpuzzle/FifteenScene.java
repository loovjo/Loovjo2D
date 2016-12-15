package com.loovjo.fifteenpuzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;

import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.ImageLoader;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class FifteenScene implements Scene {

	public static int SIZE = 4;

	public int WIDTH = SIZE, HEIGHT = SIZE;

	public FastImage img;

	public Tile[][] tiles = new Tile[WIDTH][];

	private int w, h, time = 0;
	private long lastTime = System.currentTimeMillis();

	public boolean clicking = false;
	public boolean scrambling = false, timing = false;

	public FifteenScene() {
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = new Tile[HEIGHT];
		for (int i = 0; i < WIDTH * HEIGHT - 1; i++) {
			tiles[i % WIDTH][i / HEIGHT] = new Tile(i + 1);
		}
		img = new FastImage(10, 10);
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		Arrays.fill(pixels, 0xFFFFFF);
		img.setPixels(pixels);

		//scramble();
	}

	@Override
	public void update() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				getTile(new Vector(x, y)).update(tiles);
			}
		}
		if (timing) {
			long currentTime = System.currentTimeMillis();
			time += currentTime - lastTime;
			lastTime = currentTime;
		}
		if (won()) {
			timing = false;
		}
	}

	public BufferedImage getImgPart(int tile) {
		BufferedImage image = img.toBufferedImage();
		int x = tile % tiles.length;
		int y = tile / tiles[0].length;
		int w = img.getWidth() / tiles.length;
		int h = img.getHeight() / tiles[0].length;
		return image.getSubimage(x * w, y * h, w, h);
	}

	@Override
	public void render(Graphics g, int width, int height) {
		w = width / tiles.length;
		h = height / (tiles[0].length + 1);

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles.length; y++) {
				Vector v = getTile(new Vector(x, y)).renderPosition;
				if (getTile(new Vector(x, y)).value == 0)
					v = new Vector(x, y);

				g.setColor(Color.red);
				if (getTile(new Vector(x, y)).value > 0) {
					/*
					 * g.drawImage( getImgPart(getTile(new Vector(x, y)).value -
					 * 1), (int) (v.getX() * w), (int) (v.getY() * h), w, h,
					 * null);
					 */
				}
				if (getTile(new Vector(x, y)).value != 0) {
					Color col = getTile(new Vector(x, y)).getColor(WIDTH);
					if (won()) {
						int brighter = 100;
						col = new Color(Math.min(col.getRed() + brighter, 255),
								Math.min(col.getGreen() + brighter, 255), Math.min(col.getBlue() + brighter, 255));
					}
					g.setColor(col);
					g.fillRect((int) (v.getX() * w), (int) (v.getY() * h), w, h);
					g.setColor(Color.black);
					g.drawRect((int) (v.getX() * w), (int) (v.getY() * h), w, h);
					g.setFont(new Font("Helvetica", Font.PLAIN, h / 2));
					int space = w - g.getFontMetrics().stringWidth(getTile(new Vector(x, y)).value + "");
					g.drawString(getTile(new Vector(x, y)).value + "", (int) (v.getX() * w) + space / 2,
							(int) (v.getY() * h) + g.getFont().getSize());
				}
			}
		}
		g.setColor(Color.green);
		g.drawString("Time: " + (float) time / 1000, 0, height);

	}

	private boolean won() {
		for (int i = 0; i < tiles.length * tiles[0].length - 1; i++) {
			if (getTile(new Vector(i % tiles.length, i / tiles[0].length)).value != i + 1)
				return false;
		}
		return true;
	}

	public boolean moveTile(Vector v) {
		for (int i = 0; i < 8; i += 2) {
			for (int j = 0; j < WIDTH; j++) {
				Vector dir = new Vector(0, 0).moveInDir(i).setLength(j);
				Vector v2 = v.add(dir);
				if (getTile(v2).value == 0 && v2.getX() > 0 && v2.getY() > 0 && v2.getX() < WIDTH
						&& v2.getY() < WIDTH) {
					moveTile(v, i);
					return true;
				}
			}
		}
		return false;
	}

	private void move(int dir) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				Vector pos = new Vector(x, y);
				Vector move = pos.moveInDir(dir).add(new Vector(1e-5, 1e-5));
				if (getTile(pos).value == 0) {
					moveTile(move);
					return;
				}
			}
		}
	}

	private void moveTile(Vector v, int dir) {
		if (getTile(v).value == 0)
			return;
		Vector v2 = v.moveInDir(dir);
		moveTile(v2, dir);
		tiles[(int) v2.getX()][(int) v2.getY()] = getTile(v);
		tiles[(int) v.getX()][(int) v.getY()] = new Tile(0);
	}

	public Tile getTile(Vector v) {
		if (v.getX() < 0 || v.getY() < 0 || v.getX() >= tiles.length || v.getY() >= tiles[(int) v.getX()].length)
			return new Tile(0);
		if (tiles[(int) v.getX()][(int) v.getY()] != null)
			return tiles[(int) v.getX()][(int) v.getY()];
		return new Tile(0);
	}

	@Override
	public void mousePressed(Vector pos, int button) {
		clicking = true;
		moveTile(pos.div(new Vector(w, h)));
		if (!timing) {
			timing = true;
			time = 0;
			lastTime = System.currentTimeMillis();
		}
	}

	@Override
	public void mouseReleased(Vector pos, int button) {
		clicking = false;
	}

	@Override
	public void mouseMoved(Vector pos) {
		if (clicking)
			moveTile(pos.div(new Vector(w, h)));
	}

	public void scramble() {
		for (int i = 0; i < 2000; i++)
			while (!moveTile(
					new Vector(RandomUtils.RAND.nextInt(tiles.length), RandomUtils.RAND.nextInt(tiles[0].length))
							.add(new Vector(0.1, 0.1))))
				;
	}

	@Override
	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_S) {
			scramble();
			timing = false;
			time = 0;
			lastTime = System.currentTimeMillis();
		}
		if (keyCode == KeyEvent.VK_F) {
			for (int i = 0; i < 1000; i++)
				while (!moveTile(
						new Vector(RandomUtils.RAND.nextInt(tiles.length), RandomUtils.RAND.nextInt(tiles[0].length))
								.add(new Vector(0.1, 0.1))))
					;
			time = 0;
		}

		if (keyCode == KeyEvent.VK_SPACE) {
			time = 0;
			lastTime = System.currentTimeMillis();
		}
		if (!won()) {
			if (keyCode == KeyEvent.VK_UP) {
				move(0);
				timing = true;
			}
			if (keyCode == KeyEvent.VK_RIGHT) {
				move(2);
				timing = true;
			}
			if (keyCode == KeyEvent.VK_DOWN) {
				move(4);
				timing = true;
			}
			if (keyCode == KeyEvent.VK_LEFT) {
				move(6);
				timing = true;
			}
		}
	}

	@Override
	public void keyReleased(int keyCode) {
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
