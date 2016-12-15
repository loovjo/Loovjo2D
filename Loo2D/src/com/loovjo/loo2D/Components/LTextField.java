package com.loovjo.loo2D.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class LTextField extends LComponent {

	public int index;
	private String text = "";
	public boolean active, passfield;

	public LTextField(Vector pos, Vector size, boolean passwordfield) {
		super(pos, size);
		passfield = passwordfield;
	}

	public LTextField(Vector pos, Vector size) {
		this(pos, size, false);
	}

	public LTextField(Vector pos, Vector size, String string) {
		this(pos,size);
		text = string;
	}

	public void defaultRender(Graphics g) {
		String renderText = getText();

		g.setColor(Color.white);

		g.fillRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(),
				(int) size.getY()); // Fill the button

		g.setColor(Color.black);
		if (active)
			g.setColor(Color.blue);
		g.drawRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(),
				(int) size.getY()); // Draw the outline

		g.setFont(new Font("Helvetica", Font.PLAIN, (int) size.getY() - 2));
		g.drawString(renderText, (int) pos.getX(), (int) pos.getY()
				+ g.getFont().getSize()); // Draw text
		if (active && index > 0) {
			g.setColor(Color.black);
			g.drawRect(
					(int) pos.getX()
							+ g.getFontMetrics().stringWidth(
									renderText.substring(0, index)),
					(int) pos.getY(), 0, (int) size.getY());
		}
	}

	public String getText() {
		if (passfield)
			return new String(new char[text.length()]).replace("\0", "*");
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void mousePressed(Vector pos, int button) {
		active = inside(pos);
	}

	public void keyTyped(char key) {
		if (RandomUtils.isPrintableChar(key))
			if (active) {
				text = text.substring(0, index) + key + text.substring(index);
				index++;
			}
		if (key == '\n') {
			invokeAction("activated", getText());
		}
		else {
			invokeAction("key", key + "");
		}
	}

	public void keyPressed(int keyCode) {
		if (!active)
			return;
		if (keyCode == KeyEvent.VK_LEFT) {
			index = Math.max(0, index - 1);
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			index = Math.min(text.length(), index + 1);
		}
		if (keyCode == KeyEvent.VK_BACK_SPACE) {
			if (index > 0) {
				text = text.substring(0, index - 1) + text.substring(index);
				index--;
			}
		}
	}

	public String toString() {
		return "LTextField(pos: " + pos + ", size: " + size + ", text: \""
				+ getText() + "\", password holder: " + passfield + ")";
	}
}
