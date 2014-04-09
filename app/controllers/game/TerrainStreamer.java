package controllers.game;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.game.Terrain;
import models.game.TerrainObject;
import models.game.Tribe;

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
	
	public static List<List<String>> streamAllUrl(Terrain target){
		
		List<TerrainObject> all = Terrain.findTerrainObjects(target.id);
		
		List<List<String>> tagastada = new ArrayList<List<String>>();
		for(int j = 0; j < target.height; j++){
			tagastada.add(new ArrayList<String>());
		}
		for(TerrainObject jupp: all){
			tagastada.get(jupp.y).add(jupp.getImgUrl());
		}
		return tagastada;
	}
	
	public static List<List<String>> streamAllPlayerUrl(Tribe tribe){
		
		List<Tribe> all = tribe.find.all();
		
		List<List<String>> tagastada = new ArrayList<List<String>>();
		for(int j = 0; j < 10; j++){
			List<String> rida = new ArrayList<String>();
			for(int i = 0; i < 10; i++){
				rida.add("assets/images/game/tiles/void.png");
			}	
			tagastada.add(rida);
		}
		for(Tribe jupp: all){
			tagastada.get(jupp.position.y).remove(jupp.position.x);
			tagastada.get(jupp.position.y).add(jupp.position.x, "assets/images/game/tiles/vaenlased_p.png");
		}
		tagastada.get(tribe.position.y).remove(tribe.position.x);
		tagastada.get(tribe.position.y).add(tribe.position.x, "assets/images/game/tiles/m2ngija_p.png");
		
		return tagastada;
	}
}
