package controllers.game.simplex;

import java.awt.Color;

import models.game.TerrainObject;

public class SimplexLeveler {
	
	static private int level1 = 110;
	static private int level1_liiv = 115;
	static private int level2 = 136;
	static private int level3 = 138;
	static private int level4 = 140;
	static private int level5 = 142;
	
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
	
	public static int levelTransformColorAdvanced(int i){
		
		if (i < 140){
			return new Color((int)(Math.pow(( (double)i / (140.0)),8)*75), (int)(Math.pow(( (double)i / (140.0)),8)*155), (int)(Math.pow(( (double)i / (140.0)),2)*255)).getRGB();
		}else if (i < 150){
			return new Color((int)(Math.pow((1- ( double)(i-140.0) / (10.0)),0.5)*255), 255, (int)(Math.pow((1- ( double)(i-140.0) / (10.0)),1)*155)).getRGB();
		} else if (i < 256){
			return new Color((int)(Math.pow((( double)(i - 150.0) / (105.0)),0.7)*105), 100+(int)(Math.pow((1- ( double)(i - 150.0) / (105.0)),7)*155), (int)(Math.pow((( double)(i - 150.0) / (105.0)),0.7)*25)).getRGB();
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
