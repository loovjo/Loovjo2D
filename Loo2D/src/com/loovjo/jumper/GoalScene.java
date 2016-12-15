package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Graphics;

import com.loovjo.loo2D.components.LLabel;
import com.loovjo.loo2D.utils.Vector;

public class GoalScene extends MainMenuScene {
	
	public GoalScene() {
		super();
		components.add(new LLabel(new Vector(160, 20), new Vector(50,50), "vry god m2. bet u ca'nt comepleat teh nxt lvl"));
		((LLabel)components.get(components.size()-1)).color = Color.WHITE;
	}
	
	public void render(Graphics g, int width, int height) {
		super.render(g, width, height);
	}
}
