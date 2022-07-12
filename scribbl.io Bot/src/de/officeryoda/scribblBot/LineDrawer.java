package de.officeryoda.scribblBot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

// 83 63 image resolution
public class LineDrawer {

	final Robot robot;
	final Color[][] pixelColors;

	public LineDrawer(Color[][] pixelColors) throws AWTException {
		this.pixelColors = pixelColors;
		this.robot = new Robot();
		System.out.println("created painter");
	}

	public void draw() {
		System.out.println("started painting");

		Color previousColor = new Color(42, 42, 42);

		int crntY = 220;
		for(int y = 0; y < pixelColors[0].length; y++) {
			for(int x = 0; x < pixelColors.length; x++) {
				//get Color
				Color clr = pixelColors[x][y];

				//don't draw if it's white
				if(clr.equals(ScribblColors.White)) continue;

				//only switch colors if its a different color
				if(!previousColor.equals(clr) && !isNoiseByClosePixels(x, y, 2)) {
					//select Color
					Point point = ScribblColors.getColorLocation(clr);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseMove(point.x, point.y);
					leftClick();
					
					previousColor = clr;
					
					//move to position to paint or else it won't draw single pixels
					robot.mouseMove(500 + x * 10, crntY);
				}

				//paint Color
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseMove(500 + x * 10, crntY);
				try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
			}
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			crntY = 220 + (y + 1) * 10;
			robot.mouseMove(500, crntY);
		}
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		
		System.out.println("End paint");
	}

	private boolean isNoiseByClosePixels(int x, int y, int maxClosePixel) {
		Color clr = pixelColors[x][y];
		int closeColor = 0;
		closeColor += pixelColor(x - 1, y + 1).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x	  , y + 1).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x + 1, y + 1).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x + 1, y	 ).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x + 1, y - 1).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x	  , y - 1).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x - 1, y - 1).equals(clr) ? 1 : 0;
		closeColor += pixelColor(x - 1, y	 ).equals(clr) ? 1 : 0;
		return closeColor < maxClosePixel;
	}
/*
	private boolean isNoiseByAverageColor(int x, int y, int maxDistance) {
		Color clr = pixelColors[x][y];
		
		ArrayList<Color> closePixel = new ArrayList<>();
		closePixel.add(pixelColor(x - 1, y + 1));
		closePixel.add(pixelColor(x	   , y + 1));
		closePixel.add(pixelColor(x + 1, y + 1));
		closePixel.add(pixelColor(x + 1, y	  ));
		closePixel.add(pixelColor(x + 1, y - 1));
		closePixel.add(pixelColor(x	   , y - 1));
		closePixel.add(pixelColor(x - 1, y - 1));
		closePixel.add(pixelColor(x - 1, y	  ));
		/**
		 * Pixels get added in this pattern:
		 * 1 2 3
		 * 8   4
		 * 7 6 5
		 /
		
		
		int red = 0;
		int green = 0;
		int blue = 0;
		for (Color color : closePixel) {
			red += color.getRed();
			green += color.getGreen();
			blue += color.getBlue();
		}
		
		red 	/= 	8;
		green 	/= 	8;
		blue 	/= 	8;
		
		return colorDistance(clr, new Color(red, green, blue)) > maxDistance;
	}
*/
	private Color pixelColor(int x, int y) {
		if(x >= pixelColors.length) x = pixelColors.length - 1;
		if(x < 0) x = 0;
		if(y >= pixelColors[x].length) y = pixelColors[x].length - 1;
		if(y < 0) y = 0;

		return pixelColors[x][y];
	}


	private void leftClick() {
		try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
}