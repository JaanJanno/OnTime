package models.game;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.avaje.ebean.Ebean;
import controllers.game.TerrainStreamer;
import play.db.ebean.Model;

@javax.persistence.Entity
public class TerrainObject extends Model{

	private static final long serialVersionUID = -6629892109275694783L;
	
	public static final int WATER  	= 0;
	public static final int GRASS   = 1;
	public static final int BUSHES1 = 2;
	public static final int BUSHES2 = 3;
	public static final int BUSHES3	= 4;
	public static final int FOREST	= 5;
	public static final int SAND	= 6;
	
	public static final String WATER_URL	= "assets/images/game/tiles/water.png";
	public static final String GRASS_URL 	= "assets/images/game/tiles/grass.png";
	public static final String BUSHES1_URL 	= "assets/images/game/tiles/bushes1.png";
	public static final String BUSHES2_URL 	= "assets/images/game/tiles/bushes2.png";
	public static final String BUSHES3_URL 	= "assets/images/game/tiles/bushes3.png";
	public static final String FOREST_URL	= "assets/images/game/tiles/forest.png";
	public static final String SAND_URL		= "assets/images/game/tiles/liiv.png";
	
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
		case WATER:
			return WATER_URL;
		case GRASS:
			return GRASS_URL;
		case BUSHES1:
			return BUSHES1_URL;
		case BUSHES2:
			return BUSHES2_URL;
		case BUSHES3:
			return BUSHES3_URL;
		case FOREST:
			return FOREST_URL;
		default:
			return GRASS_URL;
		}
	}
	
	public static String getImgUrl(int i){
		switch (i) {
		case WATER:
			return WATER_URL;
		case GRASS:
			return GRASS_URL;
		case BUSHES1:
			return BUSHES1_URL;
		case BUSHES2:
			return BUSHES2_URL;
		case BUSHES3:
			return BUSHES3_URL;
		case FOREST:
			return FOREST_URL;
		case SAND:
			return SAND_URL;
		default:
			return GRASS_URL;
		}
	}
	
	public static Model.Finder<Long,TerrainObject> find = new Model.Finder<Long, TerrainObject>(Long.class, TerrainObject.class);
	
	public static TerrainObject getAtLocation(int x, int y){
		return Ebean.find(TerrainObject.class).where().eq("x", x).eq("y", y).eq("terrain_area_id", TerrainStreamer.mainTerrain.id).findUnique();
	}
	
	public static TerrainObject randomLocation(){
		return Ebean.find(TerrainObject.class).where().eq("terrain_area_id", TerrainStreamer.mainTerrain.id).orderBy("random()").findList().get(0);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+Integer.toString(x)+","+Integer.toString(y)+")";
	}
}
