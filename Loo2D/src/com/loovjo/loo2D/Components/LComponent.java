package com.loovjo.loo2D.components;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;

import com.loovjo.loo2D.utils.Vector;

public abstract class LComponent {
	protected Vector pos, size;
	protected LComponentRender renderer; // Special renderer.

	// Corners are ordered like this: 0-Up to the right, 1-Up to the left,
	// 2-Down to the right, 3-Down to the left.

	protected HashMap<String, ArrayList<ActionListener>> actions = new HashMap<String, ArrayList<ActionListener>>();

	protected LComponent[] compConers = new LComponent[4];
	protected byte[] intCorners = new byte[4];

	public Vector getPos() {
		return pos;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public void addAction(String name, ActionListener al) {
		if (!actions.containsKey(name))
			actions.put(name, new ArrayList<ActionListener>());
		actions.get(name).add(al);
	}

	public Vector getSize() {
		return size;
	}

	public void setSize(Vector size) {
		this.size = size;
	}

	public LComponentRender getRenderObject() {
		return renderer;
	}

	public void setRenderer(LComponentRender renderObject) {
		this.renderer = renderObject;
	}
	
	public Vector getMidPoint() {
		return pos.sub(size.div(2));
	}
	public void setMidPoint(Vector v) {
		pos = v.sub(size.div(2));
	}
	
	protected void invokeAction(String name, String actionName) {
		if (actions.containsKey(name)) {
			for (ActionListener al : actions.get(name))
				al.actionPerformed(new ActionEvent(this, 1337, actionName));
		}
	}

	public LComponent(Vector pos, Vector size, LComponentRender renderer) {
		this.pos = pos;
		this.size = size;
		this.renderer = renderer;
	}

	public LComponent(Vector pos, Vector size) {
		this.pos = pos;
		this.size = size;
	}

	public void render(Graphics g) {
		if (renderer != null)
			renderer.render(this, g); // If there is a special renderer, render
										// it, else do the default rendering.
		else
			defaultRender(g);
	}

	public boolean inside(Vector mp) {
		if (mp.getX() > pos.getX() && mp.getX() < pos.getX() + size.getX()
				&& mp.getY() > pos.getY()
				&& mp.getY() < pos.getY() + size.getY())
			return true;
		else
			return false;
	}

	public void defaultRender(Graphics g) {

	}

	public void mousePressed(Vector pos, int button) {

	}

	public void mouseReleased(Vector pos, int button) {

	}

	public void mouseMoved(Vector pos) {

	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void keyTyped(char key) {

	}

	public void mouseWheal(MouseWheelEvent e) {

	}

	public void update() {

	}

}
