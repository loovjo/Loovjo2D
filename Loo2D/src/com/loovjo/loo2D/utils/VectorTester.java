package com.loovjo.loo2D.utils;

import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.scene.Scene;

public class VectorTester extends MainWindow {

	public VectorTester() {
		super("Vector playground", new Scene() {

			Vector v1 = new Vector(0, 0), v2 = new Vector(0, 0), v3 = new Vector(0, 0), v4 = new Vector(0, 0);

			int move = 0;

			@Override
			public void update() {
				// TODO Auto-generated method stub

			}

			@Override
			public void render(Graphics g, int width, int height) {
				Vector v = new Vector(width / 2, height / 2);
				FastImage img = new FastImage(width, height);
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						img.setRGB(x, y, 0x00FF00);
						if (v.getFastLengthTo(new Vector(x, y)) < width / 4)
							img.setRGB(x, y, 0xFF00FF);
						else if (v.getLengthTo(new Vector(x, y)) < width / 4)
							img.setRGB(x, y, 0xFF0000);

						Vector diff = new Vector(x, y).sub(v).absIfBoth();
						if (diff.getX() > 10 && diff.getY() > 10)
							img.setRGB(x, y, 0xFFFF00);
					}
				}
				g.drawImage(img.toBufferedImage(), 0, 0, null);

				Line l1 = new Line(v1, v2);
				Line l2 = new Line(v3, v4);

				g.drawLine((int) l1.getStart().getX(), (int) l1.getStart().getY(), (int) l1.getEnd().getX(),
						(int) l1.getEnd().getY());
				g.drawLine((int) l2.getStart().getX(), (int) l2.getStart().getY(), (int) l2.getEnd().getX(),
						(int) l2.getEnd().getY());

				g.drawString("0", (int)v1.getX(), (int)v1.getY());
				g.drawString("1", (int)v2.getX(), (int)v2.getY());
				g.drawString("2", (int)v3.getX(), (int)v3.getY());
				g.drawString("3", (int)v4.getX(), (int)v4.getY());

				Vector intersection = l1.getIntersectionPoint(l2);

				if (intersection != null) {

					int raduis = 10;

					g.fillOval((int) intersection.getX() - raduis / 2, (int) intersection.getY() - raduis / 2, raduis,
							raduis);
				}
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
				if (move == 0)
					v1 = pos;
				if (move == 1)
					v2 = pos;
				if (move == 2)
					v3 = pos;
				if (move == 3)
					v4 = pos;
			}

			@Override
			public void keyTyped(char key) {
				move = key - '0';
			}

			@Override
			public void keyReleased(int keyCode) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(int keyCode) {
				// TODO Auto-generated method stub

			}
		}, new Vector(420, 420), true);
	}

	public static void main(String[] args) {
		MainWindow.DRAW_DEBUG = true;
		new VectorTester();
	}

}
