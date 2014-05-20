package models.game;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

import controllers.game.Drawable;
import controllers.game.ObjectTypeController.ObjectType;
import controllers.game.TerrainController;
import controllers.game.TerrainTypeController.TerrainType;
import controllers.game.ai.AiDirector;
import controllers.game.simplex.SimplexStreamer;
import play.db.ebean.Model;

@javax.persistence.Entity
public class Npc extends Model implements Drawable{

	private static final long serialVersionUID = 8823676069045897421L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public ObjectType type;
	
	public int x;
	public int y;

	public Npc(ObjectType type) {
		this.type = type;
		initPlace();
	}
	
	private void initPlace(){
		do {
			x = (int)(Math.random() * TerrainController.getWorldWidth());
			y = (int)(Math.random() * TerrainController.getWorldHeight());
		} while (SimplexStreamer.getPointTerrain(x, y) == TerrainType.WATER);
		Ebean.save(this);
	}
	
	public void handleMove(){
		int xMove = x -1 + (int)(Math.random()*2) * 2;
		int yMove = y -1 + (int)(Math.random()*2) * 2;
		
		if (SimplexStreamer.getPointTerrain(xMove, yMove) != TerrainType.WATER){
			x = xMove;
			y = yMove;
			Ebean.update(this);
		}
	}
	
	public void handleDeath(){
		Ebean.delete(this);
		AiDirector.reportDeath();
	}
	
	public static List<Npc> findEnemies(Tribe tribe){
		return Ebean.find(Npc.class).where().eq("X", tribe.x).eq("Y", tribe.y).findList();
	}
	
	public String getTypeString(){
		switch (type) {
		case BEAR:
			return "bear";
		case DRAGON:
			return "dragon";
		default:
			return "";
		}
	}

	@Override
	public ObjectType getObjectType() {
		return type;
	}

	@Override
	public int getXCoord() {
		return x;
	}

	@Override
	public int getYCoord() {
		return y;
	}
	
	public static Finder<Long, Npc> find = new Finder<Long, Npc> (Long.class, Npc.class);
}