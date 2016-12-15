package com.loovjo.loo2D.utils;

import java.util.ArrayList;

public class Vector3D {

	private float x, y, z;

	public Vector3D(float x, float y, float z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public Vector3D(int x, int y, int z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public Vector3D(double x, double y, double z) {
		this.setX((float) x);
		this.setY((float) y);
		this.setZ((float)z);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getLengthTo(float x, float y, float z) {
		return (float) Math.sqrt(Math.pow(this.x - x, 2)
				+ Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
	}

	public float getLengthTo(Vector3D v) {
		return getLengthTo(v.x, v.y, v.z);
	}

	public float getLength() {
		return getLengthTo(0, 0, 0);
	}

	public Vector3D setLength(float setLength) {
		float length = getLength();
		x *= setLength / length;
		y *= setLength / length;
		z *= setLength / length;
		return this;
	}

	public Vector3D sub(Vector3D v) {
		return new Vector3D(this.x - v.getX(), this.y - v.getY(), this.z
				- v.getZ());
	}

	public Vector3D add(Vector3D v) {
		return new Vector3D(this.x + v.getX(), this.y + v.getY(), this.z
				+ v.getZ());
	}

	public Vector3D mul(Vector3D v) {
		return new Vector3D(this.x * v.getX(), this.y * v.getY(), this.z
				* v.getZ());
	}

	public Vector3D mul(float f) {
		return mul(new Vector3D(f, f, f));
	}

	public Vector3D div(Vector3D v) {
		return new Vector3D(this.x / v.getX(), this.y / v.getY(), this.z
				/ v.getZ());
	}

	public Vector3D div(float f) {
		return div(new Vector3D(f, f, f));
	}

	public String toString() {
		return "Vector3D(" + getX() + ", " + getY() + ", " + getZ() + ")";
	}

	public ArrayList<Vector3D> loop(Vector3D to, float length) {
		Vector3D delta = this.sub(to);
		float l = delta.getLength();
		ArrayList<Vector3D> loops = new ArrayList<Vector3D>();
		for (float i = length; i < l; i += length) {
			delta.setLength(i);
			loops.add(delta.add(to));
		}
		loops.add(this);

		return loops;
	}
	
	public Vector3D rotateAroundY(double rot) {
		return new Vector3D(new Vector(x, z).rotate(rot).getX(), y, new Vector(x, y).rotate(rot).getY());
	}
	
	public Vector3D copy() {
		return new Vector3D(x, y, z);
	}

	public void distort(float d) {
		x += Math.random() * d - d / 2;
		y += Math.random() * d - d / 2;
	}

	public boolean equals(Object o) {
		if (o instanceof Vector3D) {
			Vector3D v = (Vector3D) o;
			return getLengthTo(v) == 0;
		}
		return false;
	}
}
