package com.loovjo.loo2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.loovjo.loo2D.components.LComponent;
import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class MainWindow extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
	private static final long serialVersionUID = 5396595059952285777L;

	public static Scene currentScene; // The current scene. Static so it can be
										// changed from elsewhere.

	protected static JFrame frame;

	public static int UPS = 60;

	private static int fps = 60;

	public static boolean DRAW_DEBUG = false;

	public static int getBar() {
		if (System.getProperty("os.name").toLowerCase().contains("mac") && !frame.isUndecorated()) {
			return 23;
		}
		return 0;
	}

	public MainWindow(String title, Scene currentScene, Vector size, boolean resizable) {
		MainWindow.currentScene = currentScene; // Set the scene.
		frame = new JFrame(title); // Create the window
		frame.setSize((int) size.getX(), (int) size.getY());
		frame.setResizable(resizable);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		requestFocus(); // Request focus for the keyboard events.
		frame.setVisible(true);
		new Thread(this).start(); // Start everything.
	}

	private void update() {
		currentScene.update(); // Update the current scene
	}

	public void paint(Graphics g) {
		currentScene.render(g, getWidth(), getHeight()); // Render the current
															// scene
		if (DRAW_DEBUG) {
			g.setColor(Color.white);
			g.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
			String debug = "FPS: " + getFPS() + ", UPS: " + UPS;
			g.setFont(new Font("Monospaced", Font.PLAIN, (int) RandomUtils.getGoodFontSize(g, debug, getWidth() / 4)));
			g.drawString(debug, 0, g.getFont().getSize());
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		int frames = 0;
		int updates = 0;
		int lastUpdates = 0;

		while (true) {
			final double ns = 1000000000.0 / UPS;

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta > 1) {
				update();
				delta--;
				updates++;
			}
			repaint();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				frames = 0;
				updates = 0;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	public static int getFPS() {
		return fps;
	}

	@Override
	public void mouseClicked(MouseEvent e) { // This is not intresting.
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currentScene.mousePressed(new Vector((int) e.getX(), (int) e.getY() - getBar()), e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentScene.mouseReleased(new Vector((int) e.getX(), (int) e.getY() - getBar()), e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currentScene.mouseMoved(new Vector((int) e.getX(), (int) e.getY() - getBar()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currentScene.mouseMoved(new Vector((int) e.getX(), (int) e.getY() - getBar()));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		currentScene.keyTyped(e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		currentScene.keyPressed(e.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentScene.keyReleased(e.getKeyCode());

	}

}
