package controllers.game;

public class ObjectTypeController {

	enum ObjectType{
		EMPTY,
		PLAYER,
		PLAYER_SWIMMING,
		ENEMY,
		ENEMY_SWIMMING
	}
	
	private static final String EMPTY_URL			= "assets/images/game/tiles/void.png";
	private static final String PLAYER_URL 			= "assets/images/game/tiles/m2ngija_p.png";
	private static final String PLAYER_SWIMMING_URL = "assets/images/game/tiles/m2ngija-ujub.png";	
	private static final String ENEMY_URL 			= "assets/images/game/tiles/vaenlased_p.png";
	private static final String ENEMY_SWIMMING_URL 	= "assets/images/game/tiles/vastane-ujub.png";

	public static String getImgUrl(ObjectType type){
		switch (type) {
		case EMPTY:
			return EMPTY_URL;
		case PLAYER:
			return PLAYER_URL;
		case PLAYER_SWIMMING:
			return PLAYER_SWIMMING_URL;
		case ENEMY:
			return ENEMY_URL;
		case ENEMY_SWIMMING:
			return ENEMY_SWIMMING_URL;
		default:
			return EMPTY_URL;
		}
	}
}
