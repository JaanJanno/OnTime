package controllers.game;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import controllers.game.simplex.SimplexStreamer;

import models.game.Terrain;
import models.game.TerrainObject;
import models.game.Tribe;

public class TerrainStreamer {
	
	public static Terrain mainTerrain;
	
	public static List<List<String>> streamAllUrl(Tribe target){
		List<List<String>> tagastada = new ArrayList<List<String>>();
		for(int j = 0; j < 11; j++){
			tagastada.add(new ArrayList<String>());
		}
		for(int j = 0; j < 11; j++){
			for(int i = 0; i < 11; i++){
				tagastada.get(j).add(TerrainObject.getImgUrl(SimplexStreamer.getPointTerrain(j+target.x, i+target.y)));
			}
		}
		return tagastada;
	}
	
	public static List<List<String>> streamAllPlayerUrl(Tribe tribe){
		
		List<Tribe> all = tribe.find.all();
		
		List<List<String>> tagastada = new ArrayList<List<String>>();
		for(int j = 0; j < 11; j++){
			List<String> rida = new ArrayList<String>();
			for(int i = 0; i < 11; i++){
				rida.add("assets/images/game/tiles/void.png");
			}	
			tagastada.add(rida);
		}
		for(Tribe jupp: all){
			if (Math.abs(getXDistance(tribe.x, jupp.x)) <= 5 && Math.abs(getYDistance(tribe.y, jupp.y)) <= 5){
				tagastada.get(getXDistance(tribe.x, jupp.x) +5).remove(getYDistance(tribe.y, jupp.y) +5);
				tagastada.get(getXDistance(tribe.x, jupp.x) +5).add	 (getYDistance(tribe.y, jupp.y) +5, "assets/images/game/tiles/vaenlased_p.png");				
			}
		}		
		tagastada.get(5).remove(5);
		tagastada.get(5).add(5, "assets/images/game/tiles/m2ngija_p.png");
		
		return tagastada;
	}
	
	public static int getXDistance(int x1, int x2){
		int distance1 = x2 - x1;
		int distance2 = (x2 + TerrainController.getWorldWidth()) - x1;
		int distance3 = (x2 - TerrainController.getWorldWidth()) - x1;
		if (Math.abs(distance2) < Math.abs(distance3)){
			if (Math.abs(distance1) < Math.abs(distance2)){
				return distance1;
			} else{
				return distance2;
			}
		} else{
			if (Math.abs(distance1) < Math.abs(distance3)){
				return distance1;
			} else{
				return distance3;
			}
		}
	}
	
	public static int getYDistance(int x1, int x2){
		int distance1 = x2 - x1;
		int distance2 = (x2 + TerrainController.getWorldWidth()) - x1;
		int distance3 = (x2 - TerrainController.getWorldWidth()) - x1;
		if (Math.abs(distance2) < Math.abs(distance3)){
			if (Math.abs(distance1) < Math.abs(distance2)){
				return distance1;
			} else{
				return distance2;
			}
		} else{
			if (Math.abs(distance1) < Math.abs(distance3)){
				return distance1;
			} else{
				return distance3;
			}
		}
	}
}
