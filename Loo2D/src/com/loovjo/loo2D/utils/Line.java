package com.loovjo.loo2D.utils;

import java.awt.geom.Line2D;

public class Line {

	private Vector start, end;

	public Line(Vector start, Vector end) {
		this.start = start;
		this.end = end;
	}

	public float getLength() {
		return start.getLengthTo(end);
	}

	public float getLengthSqrd() {
		return start.getLengthToSqrd(end);
	}

	public float getTaxiCabLength() {
		return start.getTaxiCabLengthTo(end);
	}

	public float getChebyshevDistance() {
		return start.getChebyshevDistanceTo(end);
	}

	public Vector getIntersectionPoint(Line line) {
		if (!intersects(line))
			return null;
		float px = getStart().getX(), py = getStart().getY(), rx = getEnd().getX() - px, ry = getEnd().getY() - py;
		float qx = line.getStart().getX(), qy = line.getStart().getY(), sx = line.getEnd().getX() - qx,
				sy = line.getEnd().getY() - qy;

		float det = sx * ry - sy * rx;
		if (det == 0) {
			return null;
		} else {
			double z = (sx * (qy - py) + sy * (px - qx)) / det;
			if (z == 0 || z == 1)
				return null; // intersection at end point!
			return new Vector((float) (px + z * rx), (float) (py + z * ry));
		}
	}

	public boolean intersects(Line other) {
		return Line2D.linesIntersect(start.getX(), start.getY(), end.getX(), end.getY(), other.start.getX(),
				other.start.getY(), other.end.getX(), other.end.getY());
	}

	public Vector getStart() {
		return start;
	}

	public void setStart(Vector start) {
		this.start = start;
	}

	public Vector getEnd() {
		return end;
	}

	public void setEnd(Vector end) {
		this.end = end;
	}
}
