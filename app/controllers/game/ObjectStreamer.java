package controllers.game;

import java.util.ArrayList;
import java.util.List;

import controllers.game.ObjectTypeController.ObjectType;
import models.game.Tribe;

public class ObjectStreamer {
	
public static List<List<String>> streamAllPlayerUrl(Tribe tribe){
		
		List<Tribe> all 			 = Tribe.find.all();		
		List<List<String>> tagastada = initObjectGrid(11, 11);
		
		for(Tribe enemy: all){
			if (isInViewPort(tribe, enemy)){
				addEnemyForPlayer(enemy, tribe, tagastada);			
			}
		}		
		addPlayer(tribe, tagastada);
		
		return tagastada;
	}

	private static void addPlayer(Tribe player, List<List<String>> tagastada){
		tagastada.get(5).remove(5);
		
		if (player.isSwimming()) {
			tagastada.get(5).add(5, ObjectTypeController.getImgUrl(ObjectType.PLAYER_SWIMMING));
		} else {
			tagastada.get(5).add(5, ObjectTypeController.getImgUrl(ObjectType.PLAYER));
		}
	}

	private static void addEnemyForPlayer(Tribe enemy, Tribe player, List<List<String>> tagastada){
		int xCoord = getXDistance(player.x, enemy.x) + 5;
		int yCoord = getYDistance(player.y, enemy.y) + 5;
		
		if (enemy.isSwimming()){
			addTypeToCoords(xCoord, yCoord, tagastada, ObjectType.ENEMY_SWIMMING);
		} else{
			addTypeToCoords(xCoord, yCoord, tagastada, ObjectType.ENEMY);
		}
	}

	private static void addTypeToCoords(int x, int y, List<List<String>> tagastada, ObjectType type){
		tagastada.get(x).remove(y);
		tagastada.get(x).add(y, ObjectTypeController.getImgUrl(type));
	}

	private static boolean isInViewPort(Tribe player, Tribe enemy){
		return Math.abs(getXDistance(player.x, enemy.x)) <= 5 && Math.abs(getYDistance(player.y, enemy.y)) <= 5;
	}

	private static List<List<String>> initObjectGrid(int w, int h){
		List<List<String>> grid = new ArrayList<List<String>>();
		for(int j = 0; j < h; j++){
			List<String> row = new ArrayList<String>();
			for(int i = 0; i < w; i++){
				row.add(ObjectTypeController.getImgUrl(ObjectType.EMPTY));
			}	
			grid.add(row);
		}
		return grid;
	}
	
	public static int getXDistance(int x1, int x2){
		int distance1 = x2 - x1;
		int distance2 = (x2 + TerrainController.getWorldWidth()) - x1;
		int distance3 = (x2 - TerrainController.getWorldWidth()) - x1;
		return findLowestDistance(distance1, distance2, distance3);
	}
	
	public static int getYDistance(int x1, int x2){
		int distance1 = x2 - x1;
		int distance2 = (x2 + TerrainController.getWorldHeight()) - x1;
		int distance3 = (x2 - TerrainController.getWorldHeight()) - x1;
		return findLowestDistance(distance1, distance2, distance3);
	}
	
	public static int findLowestDistance(int distance1, int distance2, int distance3){
		if (Math.abs(distance2) < Math.abs(distance3)){
			return findLowestDistance(distance1, distance2);
		} else{
			return findLowestDistance(distance1, distance3);
		}
	}
	
	public static int findLowestDistance(int distance1, int distance2){
		if (Math.abs(distance1) < Math.abs(distance2)){
			return distance1;
		} else{
			return distance2;
		}
	}
}
