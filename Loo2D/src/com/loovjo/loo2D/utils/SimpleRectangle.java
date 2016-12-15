package com.loovjo.loo2D.utils;

import java.awt.geom.Line2D;

import com.loovjo.jumper.GameEntity;

public class SimpleRectangle {
	private Vector pos, size;

	public SimpleRectangle(Vector start, Vector size) {
		pos = start;
		this.size = size;
	}

	public Vector getPos() {
		return pos;
	}

	public Vector getSize() {
		return size;
	}

	public void setPosition(Vector pos) {
		this.pos = pos;
	}

	public void setSize(Vector size) {
		this.size = size;
	}

	public void setWidth(int width) {
		size.setX(width);
	}

	public void setHeight(int height) {
		size.setY(height);
	}

	public SimpleRectangle clone() {
		return new SimpleRectangle(pos, size);
	}

	public boolean isInside(Vector p) {
		if (p.getX() >= pos.getX() && p.getX() < pos.add(size).getX()
				&& p.getY() >= pos.getY() && p.getY() < pos.add(size).getY())
			return true;
		return false;
	}

	public boolean intersects(Line2D l) {
		for (Line2D line : getLines()) {
			if (line.intersectsLine(l))
				return true;
		}
		return false;
	}

	private Line2D[] getLines() {
		Line2D[] lines = new Line2D[4];
		lines[0] = RandomUtils.createLine(pos,
				pos.add(new Vector(size.getX(), 0)));
		lines[1] = RandomUtils.createLine(pos.add(new Vector(size.getX(), 0)),
				pos.add(size));
		lines[2] = RandomUtils.createLine(pos.add(size),
				pos.add(new Vector(0, size.getY())));
		lines[3] = RandomUtils.createLine(pos.add(new Vector(0, size.getY())),
				pos);
		return lines;
	}

	public String toString() {
		return "SimpleRectangle(start: " + pos + ", end: " + pos.add(size)
				+ ", size: " + size + ")";
	}
}
