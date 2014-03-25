import play.*;
import play.libs.*;

import com.avaje.ebean.Ebean;

import controllers.GameController;
import models.*;
import models.game.Terrain;
import models.game.TerrainObject;

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
        }
        if (GameController.mainTerrain == null){
        	GameController.mainTerrain = Terrain.find.all().get(0);
        }
    }
}
