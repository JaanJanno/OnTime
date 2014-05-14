package controllers.game.ai;

import models.game.Npc;

import com.avaje.ebean.Ebean;

import controllers.game.ObjectTypeController.ObjectType;
import controllers.websocket.GridHandler;

public class AiDirector implements Runnable{
	
	static double moveChance = 0.5;
	
	private Thread thread = new Thread(this);
	private static AiDirector instance;
	
	private int bearCount;
	private int dragonCount;
	
	private int tick = 0;

	private AiDirector() {
		bearCount 	= 0;
		dragonCount = 0;
		start();
	}
	
	public static void initAi(){
		if (instance == null){
			instance = new AiDirector();
		}
	}

	@Override
	public void run() {
		System.out.println("Ai alustas.");
		while (true){
			if (instance != this){
				break;
			}
			
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

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tick ++;
		}
	}
	
	private void start(){
		thread.start();
	}
}
