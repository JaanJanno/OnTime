package controllers.game;

import models.User;
import models.game.TerrainObject;
import models.game.Tribe;
import models.game.events.SpecialEvent;
import models.game.events.WarEvent;

import com.avaje.ebean.Ebean;

public class MovementController {
	
	public static void tryMove(User kasutaja, Integer x, Integer y){
		try {
		    if (x >= 0 && y >= 0 && x < 11 && x < 11){
		    	TerrainObject selected = TerrainObject.getAtLocation(x, y);
		    	Tribe muuta = kasutaja.tribe;
		    	
		    	muuta.x = (muuta.x + (x-5)) % TerrainController.getWorldWidth();
		    	muuta.y = (muuta.y + (y-5)) % TerrainController.getWorldHeight();
		    	
		    	if (muuta.x < 0){
		    		muuta.x += TerrainController.getWorldWidth();
		    	}
		    	if (muuta.y < 0){
		    		muuta.y += TerrainController.getWorldHeight();
		    	}
		    	
		    	SpecialEvent.rollSpecialEvent(muuta);
		    	for(Tribe tribe: Tribe.findEnemies(muuta)){
		    		WarEvent.rollWarEvent(muuta, tribe);
		    	}
		    	Ebean.update(muuta);
		    	Ebean.update(selected);
		    }
		} catch (Exception e) {}
	}
}
