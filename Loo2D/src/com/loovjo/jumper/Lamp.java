package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import com.loovjo.loo2D.entity.Entity;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.SimpleRectangle;
import com.loovjo.loo2D.utils.Vector;

public class Lamp extends GameEntity {
	public boolean isStatic;
	
	public ArrayList<SimpleRectangle> recs = new ArrayList<SimpleRectangle>();
	
	public Lamp(Vector vector, GameScene gameScene, boolean isStatic) {
		super(vector, gameScene);
		size = new Vector(5, 5);
		dies = false;
		this.isStatic = isStatic;
	}

	public void update(ArrayList<Entity> en) {
		if (isStatic)
			return;
		super.update(en);
		vel = vel.div(new Vector(1, 1.5));
	}
	
	
	
	public void render(Graphics g) {
		super.render(g);
		if (collisions().size() > 0)
			g.setColor(Color.RED);
		int step = 10;
		ArrayList<Vector> poses = new ArrayList<Vector>();
		for (int i = 0; i < 360; i += step) {
			Vector p = pos.add(size.div(2));
			Vector vel = new Vector(0, 1).rotate(i);
			boolean draw = false;
			for (int j = 0; j < 50; j++) {
				Line2D l = RandomUtils.createLine(p, p.add(vel));
				boolean collides = false;
				for (SimpleRectangle sr : level.level.rectangles)
					if (sr.intersects(l)) {
						collides = true;
						break;
					}
				if (collides)
					break;
				if (p.getX() < 0 || p.getY() < 0)
					break;
				p = p.add(vel);
				vel = vel.setLength(vel.getLength() + 0.03f);
			}
			vel = vel.setLength(1);
			poses.add(p);

		}
		int[] xs = new int[poses.size()];
		int[] ys = new int[poses.size()];
		for (int i = 0; i < poses.size(); i++) {
			xs[i] = (int) poses.get(i).getX();
			ys[i] = (int) poses.get(i).getY();
		}

		g.setColor(new Color(0x44FFFF00, true));
		g.fillPolygon(xs, ys, xs.length);
		g.setColor(Color.yellow.brighter());
		g.fillRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(),
				(int) size.getY());
	}
}
