package com.loovjo.loo2D.terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.terrain.TerrainGenerator.TerrainPos;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.Vector;

class TerrainTest extends MainWindow {

	public TerrainTest() {
		super("Terrain", new Scene() {

			TerrainGenerator tg = new TerrainGenerator();

			@Override
			public void update() {
				// TODO Auto-generated method stub

			}

			@Override
			public void render(Graphics g, int width, int height) {
				FastImage img = new FastImage(width, height);
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						Color col = Color.white;
						if (tg.isWall(x / (float) width, y / (float) height))
							col = Color.black;
						img.setRGB(x, y, col.getRGB());
					}
				}
				g.setColor(Color.green);
				g.drawImage(img.toBufferedImage(), 0, 0, null);
				int size = 10;
				for (TerrainPos v : tg.terrain)
					g.fillRect((int) (v.pos.getX() * width) - size / 2, (int) (v.pos.getY() * height) - size / 2, size, size);
			}

			@Override
			public void mouseWheal(MouseWheelEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(Vector pos, int button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(Vector pos, int button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(Vector pos) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(char key) {
				tg.divide();
			}

			@Override
			public void keyReleased(int keyCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(int keyCode) {
				// TODO Auto-generated method stub

			}
		}, new Vector(300, 300), true);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		new TerrainTest();
	}

}
