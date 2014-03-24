package models.game;

import java.util.ArrayList;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.ebean.Model;

@javax.persistence.Entity
public class Terrain extends Model{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	public ArrayList<ArrayList<Integer>> terrainGrid;

	public static int tere(){
		return 3;
	}
}
