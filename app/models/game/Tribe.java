package models.game;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Ebean;

import controllers.SessionController;
import controllers.game.Drawable;
import controllers.game.ObjectTypeController.ObjectType;
import controllers.game.TerrainTypeController.TerrainType;
import controllers.game.simplex.SimplexStreamer;
import models.User;
import play.db.ebean.Model;

@javax.persistence.Entity
public class Tribe extends Model implements Drawable{
	
	private static final long serialVersionUID = -4000284550655639759L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String name;
	
	public long peopleAmount;
	
	// Skill'id
	public byte fighting;
	public byte fishing;
	public byte hunting;
	public byte tracking;

	public int x = 0;
	public int y = 0;
	
	// Inventar
	public long food;
	
	public Tribe(String name) {
		this(
			name,
			(long)25, // peopleAmount
			(byte)10, // fighting
			(byte)10, // fishing
			(byte)10, // hunting
			(byte)10, // tracking
			(long)100 // food
		);
	}
	
	public Tribe(String name, long peopleAmount, byte fighting, byte fishing, byte hunting, byte tracking, long food) {
		this.name 			= name;
		this.peopleAmount 	= peopleAmount;
		this.fighting 		= fighting;
		this.fishing 		= fishing;
		this.hunting 		= hunting;
		this.tracking		= tracking;
		this.food			= food;
	}
	
	public boolean isSwimming(){
		return SimplexStreamer.getPointTerrain(x, y) == TerrainType.WATER;
	}
	
	public static Finder<Long, Tribe> find = new Finder<Long, Tribe> (Long.class, Tribe.class);
	
	public static User findUser(Tribe tribe) {
        return Ebean.find(User.class).where().eq("TRIBE_ID", tribe.id).findUnique();
    }
	
	public static List<Tribe> findEnemies(Tribe tribe){
		return Ebean.find(Tribe.class).where().eq("X", tribe.x).eq("Y", tribe.y).ne("ID", tribe.id).findList();
	}
	
	public static void generateUserTribe(User user){
		Tribe uusTribe = new Tribe(user.name + "'s tribe."); 			
		Ebean.save(uusTribe);   			
		user.tribe = uusTribe;
		Ebean.update(user);
	}
	
	public static Tribe getCurrentTribe(){
		return SessionController.getCurrentUser().tribe;
	}

	@Override
	public int getXCoord() {
		return x;
	}

	@Override
	public int getYCoord() {
		return y;
	}

	@Override
	public ObjectType getObjectType() {
		if (isSwimming()){
			return ObjectType.ENEMY_SWIMMING;
		} else{
			return ObjectType.ENEMY;
		}
	}
}
