package com.loovjo.jumper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.entity.Entity;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.FileLoader;
import com.loovjo.loo2D.utils.ImageLoader;
import com.loovjo.loo2D.utils.RandomUtils;
import com.loovjo.loo2D.utils.SimpleRectangle;
import com.loovjo.loo2D.utils.Vector;

public class Level {

	public static int CURRENT_LEVEL = 1;

	public static Vector gravity = new Vector(0, 0.2);

	public ArrayList<Vector> walls = new ArrayList<Vector>();
	public ArrayList<Vector> danger = new ArrayList<Vector>();
	public ArrayList<Vector> illuminated = new ArrayList<Vector>();
	public ArrayList<Vector> goal = new ArrayList<Vector>();
	public ArrayList<Vector> nightVision = new ArrayList<Vector>();

	public ArrayList<SimpleRectangle> rectangles = new ArrayList<SimpleRectangle>();
	public ArrayList<SimpleRectangle> illuminrekt = new ArrayList<SimpleRectangle>();

	public CopyOnWriteArrayList<Particle> particles = new CopyOnWriteArrayList<Particle>();

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public int width = 0, height = 0;

	public GameScene gs;
	public String wisdom = "";

	public Level(GameScene gs) {
		this.gs = gs;
		entities.add(new Player(new Vector(0, -16), gs));
		try {
			FastImage img = ImageLoader.getImage("/Images/Level"
					+ CURRENT_LEVEL + ".png");
			for (int x = 0; x < img.getWidth(); x++) {
				for (int y = 0; y < img.getHeight(); y++) {
					Color c = getClosest(new Color(img.getRGB(x, y)));
					Vector v = new Vector(x, y);
					if (c.hashCode() != Color.white.hashCode()) {
						walls.add(v);
						if (c.hashCode() == Color.red.hashCode())
							danger.add(v);
						if (c.hashCode() == Color.YELLOW.hashCode())
							goal.add(v);
						width = (int) Math.max(width, v.getX());
						height = (int) Math.max(height, v.getY());
						if (c.hashCode() == new Color(128, 128, 0).hashCode()) {
							entities.add(new Lamp(v.sub(new Lamp(null, null,
									false).size.div(2)), gs, true));
							walls.remove(v);
						}
						if (c.hashCode() == Color.blue.hashCode()) {
							nightVision.add(v);
							walls.remove(v);
						}
					}
				}
			}
			fillRectangles();
			width += 40;
			height += 40;// Padding
			String[] allWisdom = FileLoader.readFile("/Wisdom/wisdom.txt")
					.split("\n");
			wisdom = allWisdom[CURRENT_LEVEL - 1];
		} catch (Exception e) {
		}
	}

	public Color getClosest(Color col) {
		Color[] clrs = new Color[] { Color.black, Color.WHITE, Color.red,
				Color.yellow, Color.GREEN, Color.blue, new Color(128, 128, 0) };
		Color closest = null;
		int cls = Integer.MAX_VALUE;
		for (Color c : clrs) {
			int cl = Math.abs(c.getRed() - col.getRed())
					+ Math.abs(c.getGreen() - col.getGreen())
					+ Math.abs(c.getBlue() - col.getBlue());
			if (cl < cls) {
				cls = cl;
				closest = c;
			}
		}
		return closest;
	}

	public void fillRectangles() {
		rectangles = RandomUtils.fillRectangles(walls);
		illuminrekt = RandomUtils.fillRectangles(illuminated);
	}

	public boolean isWall(Vector pos) {
		if (pos.getX() < 0)
			return true;
		return walls.contains(pos);
	}

	public boolean isDanger(Vector pos) {
		return danger.contains(pos);
	}

	public boolean isIlluminated(Vector pos) {
		return illuminated.contains(pos);
	}

	protected int getInt(Vector pos) {
		if (isWall(pos)) {
			if (isIlluminated(pos))
				return 1;
			return 0;
		}
		return -1;
	}
}
