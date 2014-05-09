package controllers.game;

public class TerrainTypeController{
	
	public enum TerrainType{
		WATER,
		GRASS,
		BUSHES1,
		BUSHES2,
		BUSHES3,
		FOREST,
		SAND
	}
	
	private static final String WATER_URL	= "assets/images/game/tiles/water.png";
	private static final String GRASS_URL 	= "assets/images/game/tiles/grass.png";
	private static final String BUSHES1_URL = "assets/images/game/tiles/bushes1.png";
	private static final String BUSHES2_URL = "assets/images/game/tiles/bushes2.png";
	private static final String BUSHES3_URL = "assets/images/game/tiles/bushes3.png";
	private static final String FOREST_URL	= "assets/images/game/tiles/forest.png";
	private static final String SAND_URL	= "assets/images/game/tiles/liiv.png";
	
	public static String getImgUrl(TerrainType type){
		switch (type) {
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
