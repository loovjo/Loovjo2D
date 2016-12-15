package com.loovjo.loo2D.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.loovjo.loo2D.utils.Vector;

public class LLabel extends LComponent {

	public String text;
	public Color color = Color.black;
	public String fontname = "Helvetica";
	
	public LLabel(Vector pos, Vector size, String text) {
		this(pos, size, text, null);
	}

	public LLabel(Vector pos, Vector size, String text, LComponentRender lcr) {
		super(pos, size, lcr);
		this.text = text;
	}
	public void defaultRender(Graphics g) {
		g.setColor(color);
		g.setFont(new Font(fontname, Font.PLAIN, (int)size.getY()));
		g.drawString(text, (int)pos.getX(), (int)(pos.getY() + size.getY()));
	}
}
