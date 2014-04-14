package models.game.events;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import play.db.ebean.Model.Finder;
import com.avaje.ebean.Ebean;
import models.game.Tribe;

@javax.persistence.Entity
public class SpecialEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String text;
	@ManyToOne
	public Tribe tribe;
	public int type;
	
	public SpecialEvent(String lugu, Tribe tribe, int type){
		this.text	= lugu;
		this.tribe 	= tribe;
		this.type 	= type;
	}
	
	public static void rollSpecialEvent(Tribe tribe){
		rollBerries		(0.1, tribe);
		rollCandy		(0.1, tribe);
		rollTravellers	(0.1, tribe);
	}
	
	private static void rollTravellers(double chance, Tribe tribe){
		if (Math.random() < chance){
			tribe.peopleAmount += 5;
			SpecialEvent event = new SpecialEvent("You have found a group 5 of travellers who join your tribe.", tribe, 2);
			Ebean.save(event);
		}
	}
	
	private static void rollBerries(double chance, Tribe tribe){
		if (Math.random() < chance){
			tribe.food += 50;
			SpecialEvent event = new SpecialEvent("You have found a bush with delicious berries. (+50 food)", tribe, 1);
			Ebean.save(event);
		}
	}
	
	private static void rollCandy(double chance, Tribe tribe){
		if (Math.random() < chance){
			tribe.food += 25;
			SpecialEvent event = new SpecialEvent("Congratz! You have just found a box of candies! :)	(+25 food)", tribe, 3);
			Ebean.save(event);
		}
	}
	
	public static Finder<Long, Tribe> find = new Finder<Long, Tribe> (Long.class, Tribe.class);
	
	public static List<SpecialEvent> findTribeEvents(Tribe tribe){
		return Ebean.find(SpecialEvent.class).where().eq("tribe_id", tribe.id).orderBy("ID DESC").setMaxRows(5).findList();
	}
}
