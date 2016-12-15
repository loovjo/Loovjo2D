package com.loovjo.loo2D.terrain;

import java.util.ArrayList;
import java.util.Random;

import com.loovjo.loo2D.utils.Vector;

public class TerrainGenerator {

	ArrayList<TerrainPos> terrain = new ArrayList<TerrainPos>();
	private float divisionLength = 0.5f;
	private float size;

	public Random rand;

	/*
	 * Creates a TerrainGenerator with a seed of new Random().nextInt().
	 */

	public TerrainGenerator() {
		this(new Random().nextInt(), 0.5f);
	}
	/*
	 * Creates a TerrainGenerator with the seed specified
	 */

	public TerrainGenerator(int seed) {
		this(seed, 0.5f);
	}

	private TerrainGenerator(int seed, float height) {
		rand = new Random(seed);
		this.size = height;
		/*
		 * terrain.add(new TerrainPos(new Vector(0, 0), 0.5f)); terrain.add(new
		 * TerrainPos(new Vector(0, 1), 0.5f)); terrain.add(new TerrainPos(new
		 * Vector(1, 0), 0.5f)); terrain.add(new TerrainPos(new Vector(1, 1),
		 * 0.5f));
		 */
		terrain.add(new TerrainPos(new Vector(0, 0), 0f));
		terrain.add(new TerrainPos(new Vector(0, 1), 0f));
		terrain.add(new TerrainPos(new Vector(1, 0), 0f));
		terrain.add(new TerrainPos(new Vector(1, 1), 0f));
		terrain.add(new TerrainPos(new Vector(0.5, 0), 0f));
		terrain.add(new TerrainPos(new Vector(0, 0.5), 0f));
		terrain.add(new TerrainPos(new Vector(0.5, 1), 0f));
		terrain.add(new TerrainPos(new Vector(1, 0.5), 0f));
		terrain.add(new TerrainPos(new Vector(0.5, 0.5), 1f));
	}

	/*
	 * Divides the map one step further. Time grows huge fast.
	 */

	public void divide() {
		for (float x = divisionLength; x < 1; x += divisionLength * 2) {
			for (float y = divisionLength; y < 1; y += divisionLength * 2) {
				Vector v = new Vector(x, y);
				TerrainPos p = getTerrain(x, y);
				if (p.pos.getLengthToSqrd(v) < divisionLength)
					continue;
				float avgDistance = 0;
				avgDistance += getTerrain(x - divisionLength, y - divisionLength).height;
				avgDistance += getTerrain(x - divisionLength, y + divisionLength).height;
				avgDistance += getTerrain(x + divisionLength, y - divisionLength).height;
				avgDistance += getTerrain(x + divisionLength, y + divisionLength).height;
				avgDistance /= 4;
				terrain.add(new TerrainPos(v, (float) (avgDistance
						+ rand.nextFloat() * (divisionLength * 30 * (size * 2)) - divisionLength * size)));
			}
		}

		divisionLength /= 2;
	}

	public float getAvrageHeight() {
		return (float) terrain.stream().mapToDouble(tp -> tp.height).average().getAsDouble();
	}

	public boolean isWall(float x, float y) {
		return getTerrain(x, y).height < getAvrageHeight();
	}

	public float getHeight(float x, float y) {
		return getTerrain(x, y).height;
	}

	protected TerrainPos getTerrain(float x, float y) {
		TerrainPos closest = null;
		float clDistance = Integer.MAX_VALUE;
		for (TerrainPos tp : terrain) {
			float distance = tp.pos.getLengthToSqrd(new Vector(x, y));
			if (distance < clDistance) {
				closest = tp;
				clDistance = distance;
			}
		}
		return closest;
	}

	protected class TerrainPos {
		public Vector pos;
		public float height;

		public TerrainPos(Vector pos, float height) {
			this.pos = pos;
			this.height = height;
		}

		public String toString() {
			return pos + ", " + height;
		}
	}

}
