package com.loovjo.loo2D.scene;

import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import com.loovjo.loo2D.components.LComponent;
import com.loovjo.loo2D.utils.Vector;

public abstract class MenuScene implements Scene {

	protected ArrayList<LComponent> components = new ArrayList<LComponent>();

	public Vector screenSize = new Vector(0, 0);

	@Override
	public void update() {
		if (screenSize.getLength() > 0)
			for (LComponent lc : components)
				lc.update();
	}

	@Override
	public void render(Graphics g, int width, int height) {
		for (LComponent lc : components)
			lc.render(g);
		screenSize = new Vector(width, height);
	}

	@Override
	public void mousePressed(Vector pos, int button) {
		for (LComponent lc : components)
			lc.mousePressed(pos, button);
	}

	@Override
	public void mouseReleased(Vector pos, int button) {
		for (LComponent lc : components)
			lc.mouseReleased(pos, button);
	}

	@Override
	public void mouseMoved(Vector pos) {
		for (LComponent lc : components)
			lc.mouseMoved(pos);
	}

	@Override
	public void keyPressed(int keyCode) {
		for (LComponent lc : components)
			lc.keyPressed(keyCode);
	}

	@Override
	public void keyReleased(int keyCode) {
		for (LComponent lc : components)
			lc.keyReleased(keyCode);
	}

	@Override
	public void keyTyped(char key) {
		for (LComponent lc : components)
			lc.keyTyped(key);
	}

	@Override
	public void mouseWheal(MouseWheelEvent e) {

	}

}
