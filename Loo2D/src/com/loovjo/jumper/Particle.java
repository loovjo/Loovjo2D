package com.loovjo.jumper;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class Particle {
	public Vector pos, vel;
	public Color color;

	public Particle(Vector pos, Vector vel, Color color) {
		this.pos = pos;
		this.vel = vel;
		this.color = color;
	}

	public void update(CopyOnWriteArrayList<Particle> other) {
		vel = vel.add(Level.gravity);
		pos = pos.add(vel);
	}
}
