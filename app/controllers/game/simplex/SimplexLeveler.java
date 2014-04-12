package controllers.game.simplex;

import java.awt.Color;

public class SimplexLeveler {
	
	static private int level1 = 105;
	static private int level2 = 138;
	static private int level3 = 140;
	static private int level4 = 142;
	static private int level5 = 145;
	
	public static int levelTransformColor(int i){
		
		if (i < level1){
			return new Color(0, 0, 255).getRGB();
		} else if (i < level2){
			return new Color(0, 255, 0).getRGB();
		} else if (i < level3){
			return new Color(0, 55, 0).getRGB();
		} else if (i < level4){
			return new Color(0, 75, 0).getRGB();
		} else if (i < level5){
			return new Color(0, 95, 0).getRGB();
		} else{
			return new Color(0, 25, 0).getRGB();
		}
	}
}
