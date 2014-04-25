package controllers.game;

import models.game.Tribe;
import models.game.events.SpecialEvent;
import models.game.events.WarEvent;
import com.avaje.ebean.Ebean;

public class MovementController {
	
	public static void tryMove(Tribe muuta, Integer x, Integer y){
	    if (isValidMove(x, y)){
	    	handleMove(muuta, x, y);
	    	generateEvents(muuta);
	    }
	}
	
	private static void handleMove(Tribe muuta, int x, int y){
		muuta.x = (muuta.x + (x-5)) % TerrainController.getWorldWidth();
    	muuta.y = (muuta.y + (y-5)) % TerrainController.getWorldHeight();
    	handleMoveWraparound(muuta);
	}
	
	private static void handleMoveWraparound(Tribe muuta){
    	if (muuta.x < 0){
    		muuta.x += TerrainController.getWorldWidth();
    	}
    	if (muuta.y < 0){
    		muuta.y += TerrainController.getWorldHeight();
    	}
	}
	
	private static boolean isValidMove(int x, int y){
		return x >= 0 && y >= 0 && x < 11 && x < 11;
	}
	
	private static void generateEvents(Tribe muuta){
		SpecialEvent.rollSpecialEvent(muuta);
    	for(Tribe tribe: Tribe.findEnemies(muuta)){
    		WarEvent.rollWarEvent(muuta, tribe);
    	}
    	Ebean.update(muuta);
	}
}
