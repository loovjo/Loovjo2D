package com.loovjo.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.loovjo.loo2D.components.LButton;
import com.loovjo.loo2D.components.LLabel;
import com.loovjo.loo2D.components.LTextField;
import com.loovjo.loo2D.scene.MenuScene;
import com.loovjo.loo2D.utils.ExpressionParser;
import com.loovjo.loo2D.utils.Vector;

public class CalculatorScene extends MenuScene {
	public CalculatorScene() {
		components.add(new LTextField(new Vector(20, 20), new Vector(120, 20)));
		components.add(new LLabel(new Vector(20, 60), new Vector(0, 12),
				"Result:"));
		components
				.add(new LLabel(new Vector(20, 100), new Vector(120, 20), ""));
		components.add(new LTextField(new Vector(20, 140), new Vector(120, 20)));
		components.add(new LButton(new Vector(20, 180), new Vector(150, 20),
				"Find Pattern!"));
		components.get(components.size()-1).addAction("pressed", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Float> p = new ArrayList<Float>();
				for (String t : ((LTextField)components.get(3)).getText().split(",")) {
					p.add(Float.parseFloat(t));
				}
				((LLabel) components.get(5)).text = ExpressionParser.findPattern((Float[]) p.toArray(new Float[p.size()]));
			}
		});
		components
				.add(new LLabel(new Vector(20, 220), new Vector(120, 20), ""));
	}

	public void update() {
		super.update();
		try {
			((LLabel) components.get(2)).text = (new ExpressionParser(
					((LTextField) components.get(0)).getText()).evaluate()
					.toString());
		} catch (ArithmeticException e) {
		}
	}
}
