package de.officeryoda.scribblBot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

// 83 63 image resolution
public class Painter {

	final Robot robot;
	final Color[][] pixelColors;

	public Painter(Color[][] pixelColors) throws AWTException {
		this.pixelColors = pixelColors;
		this.robot = new Robot();
		System.out.println("created painter");
	}

	public void paint() {
		System.out.println("started painting");

		Color previousColor = new Color(42, 42, 42);
		for(int y = 0; y < 63; y++) {
			for(int x = 0; x < 83; x++) {
				//get Color
				Color clr = pixelColors[x][y];

				if(!previousColor.equals(clr)) {
					//select Color
					Point point = ScribblColors.getColorLocation(clr);
					robot.mouseMove(point.x, point.y);
					leftClick();
					
					previousColor = clr;
				}

				//paint Color
				robot.mouseMove(500 + x * 10, 220 + y * 10);
				leftClick();
			}
		}

		System.out.println("End paint");
	}

	private void leftClick() {
		try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
}