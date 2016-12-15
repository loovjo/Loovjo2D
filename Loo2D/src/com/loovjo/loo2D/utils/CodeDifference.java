package com.loovjo.loo2D.utils;

import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Random;

import com.loovjo.loo2D.MainWindow;
import com.loovjo.loo2D.scene.Scene;

public class CodeDifference extends MainWindow {
	public static String code1;
	public static String code2;

	public static void main(String[] args) {
		FileLoader.setLoaderClass(CodeDifference.class);
		code2 = FileLoader.readFile("/Code2.txt");
		code1 = FileLoader.readFile("/Code1.txt");
		new CodeDifference();
	}

	public CodeDifference() {
		super("CodeDifference", new Scene() {
			ArrayList<String> steps = new ArrayList<String>();

			public String current = "";
			
			public int a = 0;
			
			@Override
			public void update() {
				System.out.println("hej");
				if (steps.isEmpty()) {
					steps = RandomUtils.getLevDistanceAllSteps(code1, code2);
				}
				System.out.print("san");
				a++;
			}

			@Override
			public void render(Graphics g, int width, int height) {
				for (int i = 0; i < steps.get(a).split("\n").length; i++) {
					String line = steps.get(a).split("\n")[i];
					g.drawString(line, 0, (i+1)*g.getFont().getSize());
				}
			}

			@Override
			public void mouseWheal(MouseWheelEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(Vector pos, int button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(Vector pos, int button) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseMoved(Vector pos) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(char key) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(int keyCode) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(int keyCode) {
				// TODO Auto-generated method stub

			}
		}, new Vector(640, 400), false);
	}
}
