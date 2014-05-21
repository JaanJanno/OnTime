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
public class WarEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	public String text;
	@ManyToOne
	public Tribe tribe;
	
	public WarEvent(String lugu, Tribe tribe){
		this.text	= lugu;
		this.tribe 	= tribe;
	}
	
	public static void rollWarEvent(Tribe tribe1, Tribe tribe2){
		long deaths1 = generateRandomDeaths(tribe1, 5);
		long deaths2 = generateRandomDeaths(tribe2, 5);
		long foodStolen	= generateRandomStolenFood(tribe2, 15);
		
		handleWarResults(tribe1, deaths1, foodStolen);
		handleWarResults(tribe2, deaths2, -foodStolen);
		
		attackerEventGen(tribe1, tribe2, deaths1, deaths2, foodStolen);
		defenderEventGen(tribe2, tribe1, deaths2, deaths1, foodStolen);
	}
	
	private static long generateRandomStolenFood(Tribe tribe, int max){
		return Math.min((int)(Math.random() * max),tribe.food);
	}
	
	private static long generateRandomDeaths(Tribe tribe, int max){
		return Math.min((int)(Math.random() * max), tribe.peopleAmount);
	}
	
	private static void handleWarResults(Tribe tribe, long deaths, long foodStolen){
		tribe.peopleAmount	-= deaths;
		tribe.food 		+= foodStolen;
		Ebean.update(tribe);
	}
	
	private static void attackerEventGen(Tribe tribe, Tribe tribeEnemy, long deaths, long kills, long foodStolen){
		String deathMsg = Long.toString(deaths);
		String killMsg  = Long.toString(kills);
		String foodMsg  = Long.toString(foodStolen);
		WarEvent event = new WarEvent("You have attacked a tribe called "+tribeEnemy.name+". You lost "+deathMsg+" warriors and killed "+killMsg+". You tried to steal some food: (+"+foodMsg+" food).", tribe);
		Ebean.save(event);
		EventHandler.sendWarEvent(tribe, event.text);
	}
	
	private static void defenderEventGen(Tribe tribe, Tribe tribeEnemy, long deaths, long kills, long foodStolen){
		String deathMsg = Long.toString(deaths);
		String killMsg  = Long.toString(kills);
		String foodMsg  = Long.toString(foodStolen);
		WarEvent event = new WarEvent("You were attacked by a tribe called "+tribeEnemy.name+". You lost "+deathMsg+" warriors and killed "+killMsg+". They tried to steal yout food: (-"+foodMsg+" food).", tribe);
		Ebean.save(event);
		EventHandler.sendWarEvent(tribe, event.text);
	}
	
	public static void generateHuntingEvent(Tribe tribe, Npc npc) {
		tribe.food += 100;
		tribe.hunting += 1;
		Ebean.update(tribe);
				
		WarEvent event = new WarEvent("You have hunted down a " + npc.getTypeString() + ". +100 food +1 hunting skill.", tribe);
		Ebean.save(event);
		EventHandler.sendWarEvent(tribe, event.text);
	}
	
	public static void generateDeathEvent(Tribe tribe){
		WarEvent event = new WarEvent("All the members of your tribe have perished. But it's ok! You were chosen to lead a new tribe to victory. Good luck!", tribe);
		Ebean.save(event);
		EventHandler.sendWarEvent(tribe, event.text);
	}
	
	public static void generateKillEvent(Tribe attacker, Tribe defender) {
		WarEvent event = new WarEvent("You killed " + defender.name + " Way to go!", attacker);
		Ebean.save(event);
		EventHandler.sendWarEvent(attacker, event.text);
	}
	
	public static Finder<Long, Tribe> find = new Finder<Long, Tribe> (Long.class, Tribe.class);
	
	public static List<WarEvent> findTribeWarEvents(Tribe tribe){
		List<WarEvent> events = Ebean.find(WarEvent.class).where().eq("tribe_id", tribe.id).orderBy("ID DESC").setMaxRows(5).findList();
		Collections.reverse(events);
		return events;
	}
}
