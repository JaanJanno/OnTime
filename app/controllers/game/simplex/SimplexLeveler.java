package controllers.game.simplex;

import java.awt.Color;

import models.game.TerrainObject;

public class SimplexLeveler {
	
	static private int level1 = 110;
	static private int level1_liiv = 115;
	static private int level2 = 138;
	static private int level3 = 140;
	static private int level4 = 142;
	static private int level5 = 145;
	
	public static int levelTransformColor(int i){
		
		if (i < level1){
			return new Color(0, 0, 255).getRGB();
		} else if (i < level1_liiv){
			return new Color(255, 255, 0).getRGB();
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
	
	public static int levelTransformTerrain(int i){
		if (i < level1){
			return TerrainObject.WATER;
		} else if (i < level1_liiv){
			return TerrainObject.SAND;
		} else if (i < level2){
			return TerrainObject.GRASS;
		} else if (i < level3){
			return TerrainObject.BUSHES1;
		} else if (i < level4){
			return TerrainObject.BUSHES2;
		} else if (i < level5){
			return TerrainObject.BUSHES3;
		} else{
			return TerrainObject.FOREST;
		}
	}
	
	public static int levelAsRgb(int i){
		return new Color(i,i,i).getRGB();
	}
}
