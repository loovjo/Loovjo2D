package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.components.LButton;
import com.loovjo.loo2D.components.LComponent;
import com.loovjo.loo2D.components.LLabel;
import com.loovjo.loo2D.scene.MenuScene;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.Vector;

public class PlayScene extends MenuScene {

	public int w, h;

	public PlayScene() {
		components.add(new LLabel(new Vector(0, 0), new Vector(500, 100),
				"Jumper"));

		components.add(new LButton(new Vector(20, 20), new Vector(300, 40),
				"Play!"));
		components.get(1).addAction("pressed", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.currentScene = new GameScene();
			}
		});
		components.add(new LButton(new Vector(20, 80), new Vector(300, 40),
				"Update Game"));
		components.get(2).addAction("pressed", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				URL website;
				components.add(new LLabel(new Vector(0, 0),
						new Vector(300, 40), "Updating... May take a while."));
				((LLabel) components.get(components.size() - 1)).color = Color.white;
				update();
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {

							File t = new File(getClass().getProtectionDomain()
									.getCodeSource().getLocation().toURI()
									.getPath());
							URL website = new URL(
									"https://github.com/loovjo/Loovjo2D/blob/master/Game.jar?raw=true");
							ReadableByteChannel rbc = Channels
									.newChannel(website.openStream());
							FileOutputStream fos = new FileOutputStream(t);
							fos.getChannel().transferFrom(rbc, 0,
									Long.MAX_VALUE);
							((LLabel) components.get(components.size() - 1)).text = "Done. Please restart the game.";
							fos.close();

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}).start();
			}
		});
	}

	public void render(Graphics g, int w, int h) {
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);
		super.render(g, w, h);
		this.w = w;
		this.h = h;
		LLabel l = (LLabel) components.get(0);
		l.color = Color.green;
		l.fontname = "courier";
		l.setSize(new Vector(l.getSize().getX(), RandomUtils.getGoodFontSize(g,
				l.text, l.getSize().getX())));
	}

	public void update() {
		for (int i = 0; i < components.size(); i++) {
			LComponent c = components.get(i);
			c.setMidPoint(new Vector(w / 2, h / 2
					+ ((i * 2 - 1) * c.getSize().getY())));
		}
	}
}
