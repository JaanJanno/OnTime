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
	public static final int FOREST1 	= 2;
	public static final int FOREST2 	= 3;
	public static final int FOREST3 	= 4;
	public static final int LAKE	= 1;
	public static final int ROCK	= 5;
	
	public static final String GRASS_URL = "assets/images/game/tiles/grass.png";
	public static final String LAKE_URL = "assets/images/game/tiles/lake.png";
	public static final String FOREST1_URL = "assets/images/game/tiles/forest.png";
	public static final String FOREST2_URL = "assets/images/game/tiles/forest2.png";
	public static final String FOREST3_URL = "assets/images/game/tiles/forest3.png";
	public static final String ROCK_URL = "assets/images/game/tiles/forest3.png";
	
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
		case ROCK:
			return ROCK_URL;
		case LAKE:
			return LAKE_URL;
		case FOREST1:
			return FOREST1_URL;
		case FOREST2:
			return FOREST2_URL;
		case FOREST3:
			return FOREST3_URL;
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
