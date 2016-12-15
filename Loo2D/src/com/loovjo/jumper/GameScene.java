package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import com.loovjo.loo2D.entity.Entity;
import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.Logger;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.SimpleRectangle;
import com.loovjo.loo2D.utils.Vector;

public class GameScene implements Scene {

	public Level level;
	public Level nextLevel = null;

	public static int TILESIZE = 40;
	public int levelNum = -1;

	public final float MAXSPEED = 2;
	public float current_max_speed = 2;

	public Logger log = new Logger("Game");

	public boolean[] dirs = new boolean[3];

	public boolean shift = false;
	public boolean ctrl = false;

	public GameScene() {
		nextLevel = new Level(this);
		nextLevel();
	}

	public void nextLevel() {
		Level.CURRENT_LEVEL++;
		level = nextLevel;
		final GameScene g = this;
		new Thread(new Runnable() {

			@Override
			public void run() {
				log.log("Started loading level " + Level.CURRENT_LEVEL);
				nextLevel = new Level(g);
				log.log("Done loaded level " + Level.CURRENT_LEVEL);
			}
		}).start();
	}

	@Override
	public void update() {
		Player p = getPlayer();
		for (Entity e : level.entities)
			e.update(level.entities);
		float v = 0.25f;
		boolean anyDir = false;
		current_max_speed = MAXSPEED;
		if (ctrl)
			current_max_speed = MAXSPEED * 2;
		if (shift)
			current_max_speed = MAXSPEED / 2;
		if (p.vel.getX() > 0)
			p.vel = p.vel.add(new Vector(-v, 0));
		if (p.vel.getX() < 0)
			p.vel = p.vel.add(new Vector(v, 0));

		anyDir = true;

		if (p.pos.getY() > 0) {
			if (dirs[0])
				p.vel = p.vel.add(new Vector(-v * 2, 0));

			if (dirs[1]) {
				if (p.collides(p.pos.add(new Vector(0, 1)))
						&& p.vel.getY() >= 0) {
					p.vel = p.vel.add(new Vector(0, -4.5));
				}
			}
			if (dirs[2]) {
				p.vel = p.vel.add(new Vector(v * 2, 0));
				anyDir = true;
			}
		}

		p.vel.setX(Math.min(p.vel.getX(), current_max_speed));
		p.vel.setX(Math.max(p.vel.getX(), -current_max_speed));

		for (Particle p2 : level.particles) {
			if (getTile(p2.pos) != ' ' && !level.isIlluminated(p2.pos)) {
				level.particles.remove(p2);
				level.illuminated.add(p2.pos);
			}
			p2.update(level.particles);
		}
		if (p.pos.getX() > level.width || p.pos.getY() > level.height)
			p.die();

	}

	private Player getPlayer() {
		for (Entity ge : level.entities) {
			if (ge instanceof Player)
				return (Player) ge;
		}
		return null;
	}

	@Override
	public void render(Graphics g, int width, int height) {
		FastImage img = new FastImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		if (getPlayer().nightVision) {
			for (SimpleRectangle sr : level.rectangles) {
				g.setColor(Color.white);
				g.fillRect((int) sr.getPos().getX(), (int) sr.getPos().getY(),
						(int) sr.getSize().getX()-1, (int) sr.getSize().getY()-1);
			}
			g.setColor(Color.red);
			for (Vector v : level.danger) {
				g.fillRect((int) v.getX(), (int) v.getY(), 1, 1);
			}
			g.setColor(Color.blue);
			for (Vector v : level.nightVision) {
				g.fillRect((int) v.getX(), (int) v.getY(), 1, 1);
			}
		}
		for (SimpleRectangle sr : level.illuminrekt) {
			g.setColor(Color.green);
			g.fillRect((int) sr.getPos().getX(), (int) sr.getPos().getY(),
					(int) sr.getSize().getX(), (int) sr.getSize().getY());
		}
		for (Vector p : level.illuminated) {
			g.setColor(Color.GREEN);
			g.fillRect((int) p.getX(), (int) p.getY(), 1, 1);
		}
		for (Vector p : level.goal) {
			g.setColor(Color.yellow);
			g.fillRect((int) p.getX(), (int) p.getY(), 1, 1);
		}
		for (Particle p : level.particles) {
			g.setColor(Color.green);
			g.fillRect((int) p.pos.getX(), (int) p.pos.getY(), 1, 1);
			if (p.pos.getX() > width || p.pos.getY() > height
					|| p.pos.getX() < 0 || p.pos.getY() < 0)
				level.particles.remove(p);
		}
		for (Entity e : level.entities)
			e.render(g);
		Player p = getPlayer();
		g.setFont(new Font("Courier", Font.ITALIC, 1000));
		g.setFont(new Font("Courier", Font.ITALIC, (int) Math.min(50,
				RandomUtils.getGoodFontSize(g, level.wisdom, width))));
		g.setColor(Color.white);
		g.drawString(level.wisdom, 0, g.getFont().getSize());
		g.setFont(new Font("Courier", Font.PLAIN, 100));
		g.drawString("Deaths: " + p.deaths, 0, height - g.getFont().getSize());
		g.setFont(new Font("Courier", Font.PLAIN, 50));
		String time = "" + (System.currentTimeMillis() - p.startTime) / 100;
		time = time.substring(0, time.length() - 1) + "."
				+ time.substring(time.length() - 1);
		if (time.length() == 2)
			time = "0" + time;
		String text = "Time: " + time;
		int w = g.getFontMetrics().stringWidth(text);
		g.drawString(text, width - w, height - g.getFont().getSize());
	}

	@Override
	public void mousePressed(Vector pos, int button) {
		((GameEntity) level.entities.get(1)).pos = pos;
	}

	@Override
	public void mouseReleased(Vector pos, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Vector pos) {
	}

	@Override
	public void keyPressed(int keyCode) {
		if (keyCode >= KeyEvent.VK_LEFT && keyCode < KeyEvent.VK_DOWN)
			dirs[keyCode - KeyEvent.VK_LEFT] = true;
		if (keyCode == KeyEvent.VK_SPACE)
			getPlayer().die();
		if (keyCode == KeyEvent.VK_SHIFT)
			shift = true;
		if (keyCode == KeyEvent.VK_CONTROL)
			ctrl = true;
		if (keyCode == KeyEvent.VK_N)
			nextLevel();
		getPlayer().started = true;
	}

	@Override
	public void keyReleased(int keyCode) {
		if (keyCode >= KeyEvent.VK_LEFT && keyCode < KeyEvent.VK_DOWN)
			dirs[keyCode - KeyEvent.VK_LEFT] = false;
		if (keyCode == KeyEvent.VK_SHIFT)
			shift = false;
		if (keyCode == KeyEvent.VK_CONTROL)
			ctrl = false;
	}

	@Override
	public void keyTyped(char key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheal(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean isTile(Vector np) {
		return level.isWall(np);
	}

	public char getTile(Vector np) {
		return level.nightVision.contains(np) ? 'N' : level.isWall(np) ? level
				.isDanger(np) ? 'F' : level.goal.contains(np) ? 'G' : '#' : ' ';
	}

}
