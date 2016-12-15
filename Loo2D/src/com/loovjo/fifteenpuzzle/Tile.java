package com.loovjo.fifteenpuzzle;

import java.awt.Color;

import com.loovjo.loo2D.utils.Vector;

public class Tile {
	public Vector renderPosition = new Vector(0, 0);
	public int value = 0;

	public Tile(int value) {
		this.value = value;
	}

	public void update(Tile[][] around) {
		float speed = 0.8f;
		for (int x = 0; x < around.length; x++) {
			for (int y = 0; y < around.length; y++) {
				if (around[x][y] == this) {
					Vector delta = new Vector(x, y).sub(renderPosition);
					if (delta.getLength() > speed)
						delta.setLength(speed);
					renderPosition = renderPosition.add(delta);
				}
			}
		}
	}

	public Color getColor(int width) {
		int v = Math.min((value - 1) % width, (value - 1) / width);
		if ((float) (v) / width > (width - 2))
			return new Color(Color.HSBtoRGB(
					((float) (v % width) / width) - 1f / width, 0.5f, 1));
		return new Color(Color.HSBtoRGB(((float) v / width) % 1, 1, 1));
	}
}
