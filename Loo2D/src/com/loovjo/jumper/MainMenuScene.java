package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.components.LButton;
import com.loovjo.loo2D.components.LLabel;
import com.loovjo.loo2D.components.LTextField;
import com.loovjo.loo2D.scene.MenuScene;
import com.loovjo.loo2D.utils.Vector;

public class MainMenuScene extends MenuScene {

	public Vector pos = new Vector(0, 0);
	public static String level = "Level1";

	public MainMenuScene() {
		components.add(new LTextField(new Vector(40, 40), new Vector(120, 20), "Level3"));
		components.add(new LLabel(new Vector(40, 20), new Vector(120, 16), "Level:"));
		((LLabel)components.get(1)).color = Color.white;
		components.add(new LButton(new Vector(20, 70), new Vector(120, 20),
				"Play!", new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						level = ((LTextField)components.get(0)).getText();
						MainWindow.currentScene = new GameScene();
					}
				}));
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Graphics g, int width, int height) {
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		super.render(g, width, height);
	}

}
