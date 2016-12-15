package com.loovjo.loo2D.scene;

import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;

import com.loovjo.loo2D.utils.Vector;

public interface Scene {
	public void update();
	public void render(Graphics g, int width, int height);
	public void mousePressed(Vector pos, int button);
	public void mouseReleased(Vector pos, int button);
	public void mouseMoved(Vector pos);
	public void keyPressed(int keyCode);
	public void keyReleased(int keyCode);
	public void keyTyped(char key);
	public void mouseWheal(MouseWheelEvent e);
}
