package models.game;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.db.ebean.Model;

@javax.persistence.Entity
public class TerrainObject extends Model{

	private static final long serialVersionUID = -6629892109275694783L;
	
	public static int GRASS  	= 0;
	public static int FOREST 	= 1;
	public static int LAKE		= 2;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	public int terrainType;
	
	public TerrainObject() {
		this(GRASS);
	}
	
	public TerrainObject(int terrainType) {
		this.terrainType = terrainType;
	}

	int getType(){
		return terrainType;
	}
}
