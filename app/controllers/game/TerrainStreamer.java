package controllers.game;

import java.util.ArrayList;
import java.util.List;

import controllers.game.TerrainTypeController.TerrainType;
import controllers.game.simplex.SimplexStreamer;
import models.game.Tribe;

public class TerrainStreamer {
	
	public static List<List<String>> streamAllUrl(Tribe target){
		List<List<String>> view = initTerrainGrid(11);
		
		for(int j = 0; j < 11; j++){
			for(int i = 0; i < 11; i++){
				addTerrainToPlayerViewCoords(i, j, view, target);
			}
		}
		return view;
	}
	
	private static void addTerrainToPlayerViewCoords(int i, int j, List<List<String>> tagastada, Tribe target){
		int xCoord = j+target.x-5;
		int yCoord = i+target.y-5;
		TerrainType atCoords = SimplexStreamer.getPointTerrain(xCoord, yCoord);
		tagastada.get(j).add(TerrainTypeController.getImgUrl(atCoords));
	}
	
	private static List<List<String>> initTerrainGrid(int h){
		List<List<String>> tagastada = new ArrayList<List<String>>();
		for(int j = 0; j < h; j++){
			tagastada.add(new ArrayList<String>());
		}
		return tagastada;
	}
}
