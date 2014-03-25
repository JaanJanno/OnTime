package models.game;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@javax.persistence.Entity
public class Tribe extends Model {
	
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
	@ManyToOne
	public TerrainObject position;
	
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
		this.position		= TerrainObject.randomLocation();
	}
}
