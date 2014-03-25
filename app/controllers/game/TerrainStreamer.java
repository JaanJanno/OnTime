package controllers.game;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.game.Terrain;
import models.game.TerrainObject;

public class TerrainStreamer {
	
	public static List<List<TerrainObject>> streamAll(Terrain target){
		
		List<TerrainObject> all = Terrain.findTerrainObjects(target.id);
		
		List<List<TerrainObject>> tagastada = new ArrayList<List<TerrainObject>>();
		for(int j = 0; j < target.height; j++){
			tagastada.add(new ArrayList<TerrainObject>());
		}
		for(TerrainObject jupp: all){
			tagastada.get(jupp.y).add(jupp);
		}
		return tagastada;
	}
}
