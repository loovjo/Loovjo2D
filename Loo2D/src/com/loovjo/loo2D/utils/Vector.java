package com.loovjo.loo2D.utils;

import java.util.ArrayList;

public class Vector {
	private static final float SMALL = 1f / Float.MAX_EXPONENT * 100;
	private float x, y;

	public Vector(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public Vector(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public Vector(double x, double y) {
		this.setX((float) x);
		this.setY((float) y);
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

	/*
	 * Gets the length ^ 2 This is faster than getting the length.
	 */
	public float getLengthToSqrd(float x, float y) {
		return (float) ((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
	}

	public float getLengthToSqrd(Vector v) {
		return getLengthToSqrd(v.x, v.y);
	}

	public float getLengthSqrd() {
		return getLengthToSqrd(0, 0);
	}

	public float getLengthTo(float x, float y) {
		return (float) Math.sqrt(getLengthToSqrd(x, y));
	}

	public float getLengthTo(Vector v) {
		return getLengthTo(v.x, v.y);
	}

	public float getLength() {
		return getLengthTo(0, 0);
	}

	public Vector setLength(float setLength) {
		float length = getLength();
		x *= setLength / length;
		y *= setLength / length;
		return this;
	}

	public float getFastLengthTo(float x, float y) {
		return getFastLengthTo(new Vector(x, y));
	}

	public float getFastLengthTo(Vector v) {
		float taxiLength = getTaxiCabLengthTo(v);
		float chebyDist = getChebyshevDistanceTo(v);
		return Float.min(taxiLength * 0.7f, chebyDist);
	}

	public float getFastLength() {
		return getLengthTo(0, 0);
	}

	public Vector setFastLength(float setLength) {
		float length = getFastLength();
		x *= setLength / length;
		y *= setLength / length;
		return this;
	}

	public float getTaxiCabLengthTo(float x, float y) {
		return Math.abs(this.x - x) + Math.abs(this.y - y);
	}

	public float getTaxiCabLengthTo(Vector v) {
		return getTaxiCabLengthTo(v.x, v.y);
	}

	public float getTaxiCabLength() {
		return getTaxiCabLengthTo(0, 0);
	}

	public Vector setTaxiCabLength(float setLength) {
		float length = getTaxiCabLength();
		x *= setLength / length;
		y *= setLength / length;
		return this;
	}

	public Vector absIfBoth() {
		if (x < 0 && y < 0)
			return new Vector(-x, -y);
		return this;
	}

	public Vector abs() {
		return new Vector(x < 0 ? -x : x, y < 0 ? -y : y);
	}

	public float getChebyshevDistanceTo(float x, float y) {
		return Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
	}

	public float getChebyshevDistanceTo(Vector v) {
		return getChebyshevDistanceTo(v.x, v.y);
	}

	public float getChebyshevDistance() {
		return getChebyshevDistanceTo(0, 0);
	}

	public Vector setChebyshevLength(float setLength) {
		float length = getChebyshevDistance();
		x *= setLength / length;
		y *= setLength / length;
		return this;
	}

	public Vector sub(Vector v) {
		return new Vector(this.x - v.getX(), this.y - v.getY());
	}

	public Vector add(Vector v) {
		return new Vector(this.x + v.getX(), this.y + v.getY());
	}

	public Vector mul(Vector v) {
		return new Vector(this.x * v.getX(), this.y * v.getY());
	}

	public Vector mul(float f) {
		return mul(new Vector(f, f));
	}

	public Vector div(Vector v) {
		return new Vector(this.x / v.getX(), this.y / v.getY());
	}

	public Vector div(float f) {
		return div(new Vector(f, f));
	}

	public Vector mod(Vector v) {
		return new Vector(this.x % v.getX(), this.y % v.getY());
	}

	public Vector mod(int a, int b) {
		return mod(new Vector(a, b));
	}

	public Vector mod(int a) {
		return mod(a, a);
	}

	public String toString() {
		return "Vector(" + getX() + ", " + getY() + ")";
	}

	/*
	 * Returns a list with vectors, starting with this, ending with to, and each
	 * one having length between them
	 */
	public ArrayList<Vector> loop(Vector to, float length) {
		Vector delta = this.sub(to);
		float l = delta.getLength();
		ArrayList<Vector> loops = new ArrayList<Vector>();
		for (float i = length; i < l; i += length) {
			delta.setLength(i);
			loops.add(delta.add(to));
		}
		loops.add(this);

		return loops;
	}
	
	public boolean intersects(Vector pos, Vector size) {
		pos.sub(this);
		if (pos.getX() < getX())
			return false;
		if (pos.getY() < getY())
			return false;
		return true;
	}
	
	public Vector copy() {
		return new Vector(x, y);
	}
	
	public void distort(float d) {
		x += Math.random() * d - d / 2;
		y += Math.random() * d - d / 2;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			Vector v = (Vector) o;
			return getLengthToSquared(v) < SMALL * SMALL;
		}
		return false;
	}

	private float getLengthToSquared(Vector v) {
		return sub(v).getLengthSquared();
	}

	private float getLengthSquared() {
		return x * x + y * y;
	}

	public boolean kindaEquals(Vector o, int i) {
		if (o.x + i < x)
			return false;
		if (o.x - i > x)
			return false;
		if (o.y + i < y)
			return false;
		if (o.y - i > y)
			return false;
		return true;
	}
	/*
	 * Gets the direction, from 0 to 8.
	 */
	public int getDirection() {
		return (getDirectionInDegrees()) / (360 / 8);
	}
	/*
	 * Gets the direction in degrees.
	 */
	public int getDirectionInDegrees() {
		return (int) positize((float) Math.toDegrees(Math.atan2(x, -y)), 360f);
	}

	private float positize(float f, float base) {
		while (f < 0)
			f += base;
		return f;
	}
	// 0 = north,
			// 1 = northeast,
			// 2 = east,
			// 3 = southeast,
			// 4 = south,
			// 5 = southwest,
			// 6 = west,
			// 7 = northwest
	public Vector moveInDir(int d) {
		d = d % 8;
		d = (int) positize(d, 8);
		
		if (d == 0)
			return this.add(new Vector(0, -1));
		if (d == 1)
			return this.add(new Vector(1, -1));
		if (d == 2)
			return this.add(new Vector(1, 0));
		if (d == 3)
			return this.add(new Vector(1, 1));
		if (d == 4)
			return this.add(new Vector(0, 1));
		if (d == 5)
			return this.add(new Vector(-1, 1));
		if (d == 6)
			return this.add(new Vector(-1, 0));
		if (d == 7)
			return this.add(new Vector(-1, -1));
		return this;
	}
	/*
	 * Gets the angle in degrees to o.
	 */
	public float getRotationTo(Vector o) {
		float d = (float) Math.toDegrees((Math.atan2(y - o.y, -(x - o.x))));
		while (d < 0)
			d += 360;
		while (d > 360)
			d -= 360;
		return d;
	}
	public float getRotation() {
		return getRotationTo(new Vector(0, 0));
	}
	/*
	 * In degrees
	 */
	public Vector rotate(double n) {
		n = Math.toRadians(n);
		float rx = (float) ((this.x * Math.cos(n)) - (this.y * Math.sin(n)));
		float ry = (float) ((this.x * Math.sin(n)) + (this.y * Math.cos(n)));
		return new Vector(rx, ry);
	}

	public int hashCode() {
		return (int) x + (int) y * 46340;
	}

	public boolean isPositive() {
		return x >= 0 && y >= 0;
	}

	public Vector clone() {
		return new Vector(x, y);
	}
}