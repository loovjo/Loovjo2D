package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.loovjo.loo2D.entity.Entity;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class Player extends GameEntity {

	public long startTime = System.currentTimeMillis();
	public static boolean started = false;

	public static int deaths = 0;

	public float rotation = 0;
	private float rotVel = 0;

	public boolean nightVision = false;

	public Player(Vector pos, GameScene level) {
		super(pos, level);
	}

	@Override
	public void update(ArrayList<Entity> e) {
		super.update(e);
		if (vel.getX() > 0) {
			rotVel = Math.max(rotVel, vel.getX() * 2);
		}
		if (vel.getX() < 0) {
			rotVel = Math.min(rotVel, vel.getX() * 2);
		}
		rotation += rotVel;
		if (!started)
			startTime = System.currentTimeMillis();
		if (collides())
			rotation = rotVel = 0;
		for (char c : checkColl(pos))
			if (c == 'F') {
				die();
			} else if (c == 'G') {
				die();
				deaths--;
				level.nextLevel();
			}
		boolean a = false;
		for (char c : super.checkColl(pos)) {
			if (c == 'N')
				a = true;
		}
		nightVision = a;
	}

	public void die() {
		for (int i = 0; i < 1; i++)
			for (int x = 0; x < size.getX(); x++) {
				for (int y = 0; y < size.getY(); y++) {
					Particle p = new Particle(new Vector(pos.getX() + x,
							pos.getY() + y), new Vector(
							RandomUtils.RAND.nextFloat() - 0.5,
							RandomUtils.RAND.nextFloat() - 0.5).setLength(
							RandomUtils.RAND.nextFloat() * 10).add(vel),
							Color.GREEN);
					level.level.particles.add(p);
				}
			}
		pos = startPos;
		vel = new Vector(0, -3);
		deaths++;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green.darker().darker());
		int[] xp = new int[4], yp = new int[4];

		Vector center = pos.add(size.div(2));
		for (int i = 0; i < 4; i++) {
			Vector v = pos;
			if (i % 2 == 0) {
				v = v.add(new Vector(0, size.getY()));
			}
			if (i / 2 == 0) {
				v = v.add(new Vector(size.getX(), 0));
			}
			v = v.sub(center).rotate(rotation).add(center);
			xp[i] = (int) v.getX();
			yp[i] = (int) v.getY();
		}
		int t = xp[0];
		xp[0] = xp[1];
		xp[1] = t;
		t = yp[0];
		yp[0] = yp[1];
		yp[1] = t;
		g.fillPolygon(xp, yp, xp.length);
	}

}
