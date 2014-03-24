package models.game;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import play.db.ebean.Model;

@javax.persistence.Entity
public class Tribe extends Model {
	
	private static final long serialVersionUID = -4000284550655639759L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String name;
	
	long peopleAmount;
	
	// Skill'id
	byte fighting;
	byte fishing;
	byte hunting;
	byte tracking;
	
	// Inventar
	long food;
	
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
}
