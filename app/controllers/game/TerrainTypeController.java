package controllers.game;

public class TerrainTypeController{
	
	public static final int WATER  	= 0;
	public static final int GRASS   = 1;
	public static final int BUSHES1 = 2;
	public static final int BUSHES2 = 3;
	public static final int BUSHES3	= 4;
	public static final int FOREST	= 5;
	public static final int SAND	= 6;
	
	public static final String WATER_URL	= "assets/images/game/tiles/water.png";
	public static final String GRASS_URL 	= "assets/images/game/tiles/grass.png";
	public static final String BUSHES1_URL 	= "assets/images/game/tiles/bushes1.png";
	public static final String BUSHES2_URL 	= "assets/images/game/tiles/bushes2.png";
	public static final String BUSHES3_URL 	= "assets/images/game/tiles/bushes3.png";
	public static final String FOREST_URL	= "assets/images/game/tiles/forest.png";
	public static final String SAND_URL		= "assets/images/game/tiles/liiv.png";
	
	public static String getImgUrl(int i){
		switch (i) {
		case WATER:
			return WATER_URL;
		case GRASS:
			return GRASS_URL;
		case BUSHES1:
			return BUSHES1_URL;
		case BUSHES2:
			return BUSHES2_URL;
		case BUSHES3:
			return BUSHES3_URL;
		case FOREST:
			return FOREST_URL;
		case SAND:
			return SAND_URL;
		default:
			return GRASS_URL;
		}
	}
}
