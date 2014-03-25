package models.game;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.Event;

import com.avaje.ebean.Ebean;

import controllers.GameController;
import play.db.ebean.Model;

@javax.persistence.Entity
public class TerrainObject extends Model{

	private static final long serialVersionUID = -6629892109275694783L;
	
	public static final int GRASS  	= 0;
	public static final int FOREST 	= 1;
	public static final int LAKE	= 2;
	
	public static final String GRASS_URL = "assets/images/game/tiles/grass.png";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	public int x;
	public int y;
	public int terrainType;
	@ManyToOne
	public Terrain terrainArea;
	
	public TerrainObject(Terrain terrainArea, int x, int y) {
		this(terrainArea, GRASS, x, y);
	}
	
	public TerrainObject(Terrain terrainArea, int terrainType, int x, int y) {
		this.terrainArea = terrainArea;
		this.terrainType = terrainType;
		this.x = x;
		this.y = y;
	}

	int getType(){
		return terrainType;
	}
	
	public String getImgUrl(){
		switch (getType()) {
		case GRASS:
			return GRASS_URL;
		default:
			return GRASS_URL;
		}
	}
	
	public static Model.Finder<Long,TerrainObject> find = new Model.Finder(Long.class, TerrainObject.class);
	
	public static TerrainObject getAtLocation(int x, int y){
		return Ebean.find(TerrainObject.class).where().eq("x", x).eq("y", y).eq("terrain_area_id", GameController.mainTerrain.id).findUnique();
	}
	
	public static TerrainObject randomLocation(){
		return Ebean.find(TerrainObject.class).where().eq("terrain_area_id", GameController.mainTerrain.id).orderBy("random()").findList().get(0);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+Integer.toString(x)+","+Integer.toString(y)+")";
	}
}
