import play.*;
import play.libs.*;

import com.avaje.ebean.Ebean;

import controllers.GameController;
import models.*;
import models.game.Terrain;
import models.game.TerrainObject;
import models.game.Tribe;
import models.game.events.SpecialEvent;

import java.util.*;

public class Global extends GlobalSettings {
	
    @Override
    public void onStart(Application app) {
        // Check if the database is empty
        if (Event.find.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("test-data.yml"));
        }
        if (Terrain.find.findRowCount() == 0) {
        	GameController.mainTerrain = Terrain.initTerrain(10, 10);
        	Terrain.randomizeArea(GameController.mainTerrain);
        	
        	User aurelius = User.find.byId("aurelius@rome.ee");
        	Tribe uusTribe = new Tribe(aurelius.name + "'s tribe.");		
			Ebean.save(uusTribe);   			
			aurelius.tribe = uusTribe;
			Ebean.update(aurelius);
			for (int i = 0; i < 15; i++){
				SpecialEvent.rollSpecialEvent(uusTribe);
			}		
        }
        if (GameController.mainTerrain == null){
        	GameController.mainTerrain = Terrain.find.all().get(0);
        }
    }
}
