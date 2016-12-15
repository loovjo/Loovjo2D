package com.loovjo.loo2D.utils;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomUtils {
	public static final Random RAND = new Random();

	public static boolean isPrintableChar(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		return (!Character.isISOControl(c)) && c != KeyEvent.CHAR_UNDEFINED
				&& block != null && block != Character.UnicodeBlock.SPECIALS;
	}

	public static float map(float x, float in_min, float in_max, float out_min,
			float out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static Line2D createLine(Vector pos1, Vector pos2) {
		return new Line2D.Float(pos1.getX(), pos1.getY(), pos2.getX(),
				pos2.getY());
	}

	public static ArrayList<SimpleRectangle> fillRectangles(
			ArrayList<Vector> walls) {
		ArrayList<SimpleRectangle> rectangles = new ArrayList<SimpleRectangle>();
		ArrayList<Vector> p = (ArrayList<Vector>) walls.clone();
		while (p.size() > 0) {
			SimpleRectangle r = new SimpleRectangle(p.get(0), new Vector(1, 1));
			while (true) {
				SimpleRectangle backup = r.clone();
				r.setWidth((int) r.getSize().getX() + 1);
				r.setHeight((int) r.getSize().getY() + 1);
				boolean br = false;
				for (int x = (int) r.getPos().getX(); x < r.getPos()
						.add(r.getSize()).getX(); x++) {
					for (int y = (int) r.getPos().getY(); y < r.getPos()
							.add(r.getSize()).getY(); y++) {
						if (!p.contains(new Vector(x, y)))
							br = true;
					}
				}
				if (br) {
					r = backup;
					break;
				}
			}
			ArrayList<Vector> np = new ArrayList<Vector>();
			for (Vector v : p) {
				if (!r.isInside(v))
					np.add(v);
			}
			p = np; // CONFIRMED!
			rectangles.add(r);
		}
		return rectangles;
	}

	public static int colorDistance(Color c1, Color c2) {
		return (int) (Math.abs(c1.getRed() - c2.getRed())
				+ Math.abs(c1.getGreen() - c2.getGreen()) + Math.abs(c1
				.getBlue() - c2.getBlue()));
	}

	public static int getLevDistance(String s0, String s1) {
		int len0 = s0.length() + 1;
		int len1 = s1.length() + 1;

		// the array of distances
		int[] cost = new int[len0];
		int[] newcost = new int[len0];

		// initial cost of skipping prefix in String s0
		for (int i = 0; i < len0; i++)
			cost[i] = i;

		// dynamically computing the array of distances

		// transformation cost for each letter in s1
		for (int j = 1; j < len1; j++) {
			// initial cost of skipping prefix in String s1
			newcost[0] = j;

			// transformation cost for each letter in s0
			for (int i = 1; i < len0; i++) {
				// matching current letters in both strings
				int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

				// computing cost for each transformation
				int cost_replace = cost[i - 1] + match;
				int cost_insert = cost[i] + 1;
				int cost_delete = newcost[i - 1] + 1;

				// keep minimum cost
				newcost[i] = Math.min(Math.min(cost_insert, cost_delete),
						cost_replace);
			}

			// swap cost/newcost arrays
			int[] swap = cost;
			cost = newcost;
			newcost = swap;
		}

		// the distance is the cost for transforming all letters in both strings
		return cost[len0 - 1];
	}

	/*
	 * Returns every step in the levenstein distance. Example:
	 * getLevDistanceAllSteps("Loovjo", "Levenstein")->[levenstein, levensteio,
	 * levenstejo, levenstvjo, levensovjo, levenoovjo, leveoovjo, levoovjo,
	 * leoovjo, loovjo]
	 */
	public static ArrayList<String> getLevDistanceAllSteps(String s1, String s2) {
		if (s1.length() < s2.length()) {
			String temp = s1;
			s1 = s2;
			s2 = temp;
		}

		String allChars = "";
		for (char c : s1.toCharArray()) {
			if (!allChars.contains("" + c))
				allChars += c;
		}
		for (char c : s2.toCharArray()) {
			if (!allChars.contains("" + c))
				allChars += c;
		}
		System.out.println(allChars);
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(s1);
		while (!strings.get(strings.size() - 1).equals(s2)) {
			String last = strings.get(strings.size() - 1);
			String add = last;
			System.out.println(last);
			for (int i = 1; i <= last.length(); i++) {
				System.out.println(i);
				String newString = last.substring(0, i - 1) + last.substring(i);
				// System.out.println(newString + ", " + distance(newString, s2)
				// + ", " + distance(last, s2));
				if (getLevDistance(newString, s2) < getLevDistance(last, s2)) {
					add = newString;
					break;
				}
				for (char c : allChars.toCharArray()) {
					newString = last.substring(0, i - 1) + c
							+ last.substring(i);
					if (getLevDistance(newString, s2) < getLevDistance(last, s2)) {
						add = newString;
						break;
					}
				}
			}
			System.out.println(strings.size());
			strings.add(add);
		}
		return strings;
	}

	public static String reverse(String s) {
		if (s.length() <= 1)
			return s;
		return s.charAt(s.length() - 1)
				+ reverse(s.substring(1, s.length() - 1)) + s.charAt(0);
	}

	public static int colorDistanceHSB(Color c, Color c1) {
		float[] hsb1 = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(),
				null);
		float[] hsb2 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(),
				null);
		float hDiff = Math.abs(hsb1[0] - hsb2[0]);
		float sDiff = Math.abs(hsb1[1] - hsb2[1]);
		float bDiff = Math.abs(hsb1[2] - hsb2[2]);
		return (int) ((hDiff + sDiff + bDiff) * (256 / 3));
	}

	public static float getGoodFontSize(Graphics g, String text, float width) {
		FontMetrics fm = g.getFontMetrics();
		float swidth = fm.stringWidth(text.substring(0));
		float fSize = width / swidth;
		return fm.getFont().getSize() * fSize;
	}
}
