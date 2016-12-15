package com.loovjo.loo2D.entity;

import java.awt.Graphics;
import java.util.ArrayList;

public interface Entity {
	public void update(ArrayList<Entity> other);
	public void render(Graphics g);
}
