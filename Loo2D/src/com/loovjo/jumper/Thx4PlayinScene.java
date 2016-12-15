package com.loovjo.jumper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.loovjo.loo2D.scene.Scene;
import com.loovjo.loo2D.utils.FastImage;
import com.loovjo.loo2D.utils.FileLoader;
import com.loovjo.loo2D.utils.Vector;

public class Thx4PlayinScene implements Scene {

	private ArrayList<BufferedImage> images;
	private int tick = 0;

	public Thx4PlayinScene() {
		super();
		try {
			images = new ArrayList<BufferedImage>();
			String[] imageatt = new String[] { "imageLeftPosition",
					"imageTopPosition", "imageWidth", "imageHeight" };

			ImageReader reader = (ImageReader) ImageIO
					.getImageReadersByFormatName("gif").next();
			ImageInputStream ciis = ImageIO.createImageInputStream(FileLoader
					.getInputStream("/Images/Frog.gif"));
			reader.setInput(ciis, false);

			int noi = reader.getNumImages(true);

			BufferedImage master = null;

			for (int i = 0; i < noi; i++) {

				BufferedImage image = reader.read(i);
				IIOMetadata metadata = reader.getImageMetadata(i);

				Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");

				NodeList children = tree.getChildNodes();

				for (int j = 0; j < children.getLength(); j++) {

					Node nodeItem = children.item(j);

					if (nodeItem.getNodeName().equals("ImageDescriptor")) {

						Map<String, Integer> imageAttr = new HashMap<String, Integer>();

						for (int k = 0; k < imageatt.length; k++) {

							NamedNodeMap attr = nodeItem.getAttributes();

							Node attnode = attr.getNamedItem(imageatt[k]);

							imageAttr.put(imageatt[k],
									Integer.valueOf(attnode.getNodeValue()));

						}

						if (i == 0) {
							master = new BufferedImage(
									imageAttr.get("imageWidth"),
									imageAttr.get("imageHeight"),
									BufferedImage.TYPE_INT_ARGB);

						}
						master.getGraphics().clearRect(0, 0, master.getWidth(),
								master.getHeight());
						master.getGraphics().drawImage(image,
								imageAttr.get("imageLeftPosition"),
								imageAttr.get("imageTopPosition"), null);

					}
				}

				images.add(new FastImage(master).toBufferedImage());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		tick++;
	}

	@Override
	public void render(Graphics g, int width, int height) {
		String s = "thx 4 playin i hop u enjoid dis gaem";
		g.setFont(new Font("Helvetica", Font.BOLD, 50));
		int w = g.getFontMetrics().stringWidth(s);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);
		g.drawImage(images.get((tick) % images.size()), 0, 0, width, height,
				null);
		g.drawString(s, (width - w) / 2, (height - g.getFont().getSize()) / 2);
		g.drawString("defs " + Player.deaths, 0, height / 2
				+ g.getFont().getSize()*2);
	}

	@Override
	public void mousePressed(Vector pos, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(Vector pos, int button) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(Vector pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(char key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheal(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

}
