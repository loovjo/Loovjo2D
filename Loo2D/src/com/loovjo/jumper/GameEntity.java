package com.loovjo.jumper;

import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import com.loovjo.loo2D.entity.Entity;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public abstract class GameEntity implements Entity {

	public final float SMALL = 1f / Float.MAX_EXPONENT;
	public GameScene level;
	public final Vector startPos;
	public Vector pos, size = new Vector(10, 10), vel;
	public boolean dies = true;

	public GameEntity(Vector pos, GameScene level) {
		this.pos = pos;
		vel = new Vector(0, 0);
		this.level = level;
		startPos = pos;
	}

	@Override
	public void update(ArrayList<Entity> en) {
		vel = vel.add(Level.gravity); // Gravity
		checkCollision();
		if (collDown())
			vel = vel.mul(0.99f);
		if (Math.abs(vel.getX()) < 0.3)
			vel.setX(0);
		pos = pos.add(vel);
		for (GameEntity e : collisions()) {
			Vector delta = pos.sub(e.pos);
			vel = vel.add(delta.setLength(1));
		}
	}

	protected ArrayList<GameEntity> collisions() {
		ArrayList<GameEntity> colls = new ArrayList<GameEntity>();
		for (Entity ent : level.level.entities) {
			if (ent == this)
				continue;
			if (ent instanceof GameEntity) {
				GameEntity e = (GameEntity) ent;
				Line2D[] myLines = getLines(this);
				Line2D[] otherLines = getLines(e);
				boolean intersects = false;
				for (Line2D l : myLines) {
					for (Line2D l2 : otherLines) {
						if (l.intersectsLine(l2)) {
							intersects = true;
							break;
						}
					}
					if (intersects)
						break;
				}
				if (intersects) {
					colls.add(e);
				}
			}
		}
		return colls;
	}

	private Line2D[] getLines(GameEntity e) {
		Line2D[] lines = new Line2D[4];
		lines[0] = RandomUtils.createLine(e.pos,
				e.pos.add(new Vector(e.size.getX(), 0)));
		lines[1] = RandomUtils.createLine(
				e.pos.add(new Vector(e.size.getX(), 0)), e.pos.add(e.size));
		lines[2] = RandomUtils.createLine(e.pos.add(e.size),
				e.pos.add(new Vector(0, e.size.getY())));
		lines[3] = RandomUtils.createLine(
				e.pos.add(new Vector(0, e.size.getY())), e.pos);
		return lines;
	}

	@Override
	public void render(Graphics g) {

	}

	public boolean collDown() {
		return collides(pos.add(new Vector(0, 1)));
	}

	public boolean collSides() {
		return collides(pos.add(new Vector(1, 0)))
				|| collides(pos.add(new Vector(-1, 0)));
	}

	public boolean collides() {
		return collDown() || collSides();
	}

	private void checkCollision() {
		Vector pos2 = pos.add(new Vector(vel.getX(), 0));
		if (collides(pos2)) {
			vel.setX(0);
		}
		pos2 = pos.add(new Vector(0, vel.getY()));
		if (collides(pos2)) {
			vel.setY(-0.1f);
		}
		for (int j = 1; j < 3; j++) {
			boolean moved = false;
			for (int i = 0; i < 8; i += 2) {
				Vector v = new Vector(0, 0).moveInDir(i).setLength(j);
				if (collides(pos) && !collides(pos.add(v))) {
					pos = pos.add(v);
					moved = true;
					break;
				}
			}
			if (moved)
				break;
		}
	}

	public char[] checkColl(Vector pos2) {
		char[] chars = new char[4];
		for (int i = 0; i < 4; i++) {
			Vector np = pos2;
			if (i % 2 == 0)
				np = np.add(new Vector(0, size.getY() - SMALL));
			if (i / 2 == 0)
				np = np.add(new Vector(size.getX() - SMALL, 0));
			chars[i] = level.getTile(np);
		}
		return chars;
	}

	public boolean collides(Vector pos2) {
		char[] c = checkColl(pos2);
		for (int i = 0; i < 4; i++) {
			if (c[i] == '#' && dies)
				return true;
			if (c[i] != ' ' && !dies)
				return true;
		}
		return false;
	}

}
