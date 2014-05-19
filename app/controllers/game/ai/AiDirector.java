package controllers.game.ai;

import org.h2.jdbc.JdbcSQLException;

import models.game.Npc;

import com.avaje.ebean.Ebean;

import controllers.game.ObjectTypeController.ObjectType;
import controllers.websocket.GridHandler;

public class AiDirector implements Runnable{
	
	static double moveChance = 0.5;
	
	private Thread thread = new Thread(this);
	private static AiDirector instance = new AiDirector();
	
	private static int bearCount = 0;
	private static int dragonCount = 0;
	
	private int tick = 0;

	private AiDirector() {

	}
	
	public static void initAi(){
		instance = new AiDirector();
		instance.start();
	}
	
	private void aiTick() throws JdbcSQLException{
		while (bearCount < 10){
			Ebean.save(new Npc(ObjectType.BEAR));			
			bearCount ++;
		}
		while (dragonCount < 10){
			Ebean.save(new Npc(ObjectType.DRAGON));
			dragonCount ++;
		}
		
		if (tick % 2 == 0){
			for(Npc actor: Npc.find.all()){
				if (Math.random() < moveChance)
					actor.handleMove();
			}
			GridHandler.sendObjectStream();
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
}