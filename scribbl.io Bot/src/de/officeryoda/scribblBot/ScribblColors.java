package de.officeryoda.scribblBot;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class ScribblColors {

	public static final Color White = new Color(255, 255, 255);
	public static final Color Black = new Color(0, 0, 0);

	public static final Color Light_Gray = new Color(193, 193, 193);
	public static final Color Dark_Gray = new Color(76, 76, 76);

	public static final Color Light_Red = new Color(239, 19, 11);
	public static final Color Dark_Red = new Color(116, 11, 7);

	public static final Color Light_Orange = new Color(255, 113, 0);
	public static final Color Dark_Orange = new Color(194, 56, 0);

	public static final Color Light_Yellow = new Color(255, 228, 0);
	public static final Color Dark_Yellow = new Color(232, 162, 0);

	public static final Color Light_Green = new Color(0, 204, 0);
	public static final Color Dark_Green = new Color(0, 85, 16);

	public static final Color Light_Light_Blue = new Color(0, 178, 255);
	public static final Color Dark__Light_Blue = new Color(0, 86, 158);

	public static final Color Light_Dark_Blue = new Color(35, 31, 211);
	public static final Color Dark_Dark_Blue = new Color(14, 8, 101);

	public static final Color Light_Purple = new Color(163, 0, 186);
	public static final Color Dark_Purple = new Color(85, 0, 105);

	public static final Color Light_Pink = new Color(211, 124, 170);
	public static final Color Dark_Pink = new Color(167, 85, 116);

	public static final Color Light_Brown = new Color(160, 82, 45);
	public static final Color Dark_Brown = new Color(99, 48, 13);


	public static final Color[] allColors = {
			White, Black,
			Light_Gray, Dark_Gray,
			Light_Red, Dark_Red,
			Light_Orange, Dark_Orange,
			Light_Yellow, Dark_Yellow,
			Light_Green, Dark_Green,
			Light_Light_Blue, Dark__Light_Blue,
			Light_Dark_Blue, Dark_Dark_Blue,
			Light_Purple, Dark_Purple,
			Light_Pink, Dark_Pink,
			Light_Brown, Dark_Brown
			};
	
	private static Map<Color, Point> colorPointMap = new HashMap<>();
	
	public static Point getColorLocation(Color color) {
		if(colorPointMap.isEmpty()) createMap();
		return colorPointMap.getOrDefault(color, new Point());
	}

	private static void createMap() {
		colorPointMap.put(White, new Point(608, 862));
		colorPointMap.put(Black, new Point(608, 886));
		
		colorPointMap.put(Light_Gray, new Point(632, 862));
		colorPointMap.put(Dark_Gray, new Point(632, 886));
		
		colorPointMap.put(Light_Red, new Point(656, 862));
		colorPointMap.put(Dark_Red, new Point(656, 886));
		
		colorPointMap.put(Light_Orange, new Point(680, 862));
		colorPointMap.put(Dark_Orange, new Point(680, 886));
		
		colorPointMap.put(Light_Yellow, new Point(704, 862));
		colorPointMap.put(Dark_Yellow, new Point(704, 886));
		
		colorPointMap.put(Light_Green, new Point(728, 862));
		colorPointMap.put(Dark_Green, new Point(728, 886));
		
		colorPointMap.put(Light_Light_Blue, new Point(752, 862));
		colorPointMap.put(Dark__Light_Blue, new Point(752, 886));
		
		colorPointMap.put(Light_Dark_Blue, new Point(776, 862));
		colorPointMap.put(Dark_Dark_Blue, new Point(776, 886));
		
		colorPointMap.put(Light_Purple, new Point(800, 862));
		colorPointMap.put(Dark_Purple, new Point(800, 886));
		
		colorPointMap.put(Light_Pink, new Point(824, 862));
		colorPointMap.put(Dark_Pink, new Point(824, 886));
		
		colorPointMap.put(Light_Brown, new Point(848, 862));
		colorPointMap.put(Dark_Brown, new Point(848, 886));
	}
}
