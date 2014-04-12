package models.game;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

@javax.persistence.Entity
public class Terrain extends Model{

	private static final long serialVersionUID = -1872896291821591048L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public int width;
	public int height;
	
	public Terrain(int width, int height) {
		this.width  = width;
		this.height = height;
	}
	
	public static Terrain initTerrain(int x, int y){
		Terrain uus = new Terrain(x, y);
		Ebean.save(uus);
		initArea(uus, x, y);
		return uus;
	}
	
	public static void initArea(Terrain ala, int x, int y){
		for(int i = 0; i < y; i++){
			for(int j = 0; j < x; j++){
				TerrainObject uusJupp = new TerrainObject(ala, i, j);
				Ebean.save(uusJupp);
			}
		}
	}
	
	public static void randomizeArea(Terrain maastik){
		List<TerrainObject> alad = findTerrainObjects(maastik.id);
		for(TerrainObject ala: alad){
			if(Math.random() < 0.1){
				ala.terrainType = TerrainObject.FOREST;
			}
			if(Math.random() < 0.1){
				ala.terrainType = TerrainObject.FOREST;
			}
			if(Math.random() < 0.1){
				ala.terrainType = TerrainObject.FOREST;
			}
			if(Math.random() < 0.1){
				ala.terrainType = TerrainObject.FOREST;
			}
			Ebean.update(ala);
		}
	}
	
	public static Finder<Long, Terrain> find = new Finder<Long, Terrain> (Long.class, Terrain.class);
	
	public static List<TerrainObject> findTerrainObjects(long id) {
        return Ebean.find(TerrainObject.class).where().eq("terrain_area_id", id).orderBy("y").findList();
    }
}
