package controllers.game.ai;

import org.h2.jdbc.JdbcSQLException;
import models.game.Npc;
import controllers.game.ObjectTypeController.ObjectType;
import controllers.websocket.GridHandler;

public class AiDirector implements Runnable{
	
	static double moveChance = 0.5;
	
	private Thread thread = new Thread(this);
	private static AiDirector instance = new AiDirector();
	
	private static int npcCount = Npc.find.all().size();
	private static final int maxAnimals = 20;

	private int tick = 0;

	private AiDirector() {

	}
	
	public static void initAi(){
		instance = new AiDirector();
		instance.start();
	}
	
	private void generateNpcs(){
		while (npcCount < maxAnimals){
			new Npc(ObjectType.BEAR);	
			new Npc(ObjectType.DRAGON);
			npcCount += 2;
		}
	}
	
	private void moveNpcs(){
		for(Npc actor: Npc.find.all()){
			if (Math.random() < moveChance)
				actor.handleMove();
		}
		GridHandler.sendObjectStream();
	}
	
	private void aiTick() throws JdbcSQLException{
		
		generateNpcs();	
		if (tick % 2 == 0){
			moveNpcs();
		}
	}

	@Override
	public void run() {
		System.out.println("Ai alustas.");
		while (true){
			if (instance != this){
				break;
			}
			try {
				Thread.sleep(1000);
				aiTick();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (JdbcSQLException e){
				System.out.println("Ai paused - going through DB restart.");
			}
			tick ++;
		}
	}
	
	private void start(){
		thread.start();
	}

	public static void reportDeath() {
		npcCount --;
	}
}
