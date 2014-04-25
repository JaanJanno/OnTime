package controllers.game.simplex;

import java.awt.Color;
import controllers.game.TerrainTypeController;

public class SimplexLeveler {
	
	static private int level1 = 110;
	static private int level1_liiv = 115;
	static private int level2 = 126;
	static private int level3 = 128;
	static private int level4 = 130;
	static private int level5 = 132;
	
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
			return TerrainTypeController.WATER;
		} else if (i < level1_liiv){
			return TerrainTypeController.SAND;
		} else if (i < level2){
			return TerrainTypeController.GRASS;
		} else if (i < level3){
			return TerrainTypeController.BUSHES1;
		} else if (i < level4){
			return TerrainTypeController.BUSHES2;
		} else if (i < level5){
			return TerrainTypeController.BUSHES3;
		} else{
			return TerrainTypeController.FOREST;
		}
	}
	
	public static int levelAsRgb(int i){
		return new Color(i,i,i).getRGB();
	}
}
