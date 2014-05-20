package models.game.events;

import java.util.Collections;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model.Finder;

import com.avaje.ebean.Ebean;

import controllers.websocket.EventHandler;
import models.game.Npc;
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
			EventHandler.sendSpecialEvent(tribe, event.text);
		}
	}
	
	private static void rollBerries(double chance, Tribe tribe){
		if (Math.random() < chance){
			tribe.food += 50;
			SpecialEvent event = new SpecialEvent("You have found a bush with delicious berries. (+50 food)", tribe, 1);
			Ebean.save(event);
			EventHandler.sendSpecialEvent(tribe, event.text);
		}
	}
	
	private static void rollCandy(double chance, Tribe tribe){
		if (Math.random() < chance){
			tribe.food += 25;
			SpecialEvent event = new SpecialEvent("Congratz! You have just found a box of candies! :)	(+25 food)", tribe, 3);
			Ebean.save(event);
			EventHandler.sendSpecialEvent(tribe, event.text);
		}
	}
	
	public static void generateDeathEvent(Tribe tribe){
		SpecialEvent event = new SpecialEvent("All the members of your tribe have perished. But it's ok! You were chosen to lead a new tribe to victory. Good luck!", tribe, 4);
		Ebean.save(event);
		EventHandler.sendSpecialEvent(tribe, event.text);
	}
	
	public static void generateKillEvent(Tribe attacker, Tribe defender) {
		SpecialEvent event = new SpecialEvent("You killed " + defender.name + " Way to go!", attacker, 5);
		Ebean.save(event);
		EventHandler.sendSpecialEvent(attacker, event.text);
	}
	
	public static Finder<Long, Tribe> find = new Finder<Long, Tribe> (Long.class, Tribe.class);
	
	public static List<SpecialEvent> findTribeEvents(Tribe tribe){
		List<SpecialEvent> events = Ebean.find(SpecialEvent.class).where().eq("tribe_id", tribe.id).orderBy("ID DESC").setMaxRows(5).findList();
		Collections.reverse(events);
		return events;
	}

	public static void generateHuntingEvent(Tribe tribe, Npc npc) {
		tribe.food += 100;
		tribe.hunting += 1;
		Ebean.update(tribe);
				
		SpecialEvent event = new SpecialEvent("You have hunted down a " + npc.getTypeString() + ". +100 food +1 hunting skill.", tribe, 6);
		EventHandler.sendSpecialEvent(tribe, event.text);
	}
}
