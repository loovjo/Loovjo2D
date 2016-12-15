package com.loovjo.loo2D.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.loovjo.loo2D.utils.Vector;

public class LButton extends LComponent {

	public String text;
	public boolean active;

	public LButton(Vector pos, Vector size, String text) {
		this(pos, size, text, null);
	}

	public LButton(Vector pos, Vector size, String text, ActionListener al) {
		super(pos, size);
		this.text = text;
		addAction("pressed", al);
	}

	public void defaultRender(Graphics g) {
		String strText = text + " "; // Striped text. Clone check if it's bigger
										// than
		// the width.
		g.setColor(Color.white);
		if (active)
			g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(),
				(int) size.getY(), 20, 20); // Fill the button
		g.setColor(Color.black);
		g.drawRoundRect((int) pos.getX(), (int) pos.getY(), (int) size.getX(),
				(int) size.getY(), 20, 20); // Draw the outline
		g.setFont(new Font("Helvetica", Font.PLAIN, (int) size.getY() - 2));
		int width = (int) size.getX() + 1;
		int resizes = 0;
		while (width > size.getX() - 10) {
			strText = strText.substring(0, strText.length() - 1); // Remove last
																	// char
			width = g.getFontMetrics().stringWidth(strText + "..."); // Get new
																		// width
			resizes++;
		}

		if (resizes > 1)
			strText += "..."; // Add ... to the end if it's bigger than the
								// width
		else
			strText = text; // Else set it to the original text

		int padding = (int) (size.getX() - g.getFontMetrics().stringWidth(
				strText)) / 2; // Calculate padding so the text can be in the
		// center.
		g.drawString(strText, (int) pos.getX() + padding, (int) pos.getY()
				+ g.getFont().getSize()); // Draw text
	}

	public void mouseMoved(Vector mp) {
		active = inside(mp);
	}

	public void mousePressed(Vector mp, int button) {
		if (active) {
			invokeAction("pressed", mp + "," + button);
		}
	}

	public String toString() {
		return "LButton(pos: " + pos + ", size: " + size + ", text: " + text
				+ ")";
	}
}
