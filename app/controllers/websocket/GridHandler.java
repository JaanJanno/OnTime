package controllers.websocket;

import java.util.List;

import org.h2.engine.Session;

import models.ChatEvent;
import models.User;
import models.game.Tribe;
import play.mvc.WebSocket;

import com.avaje.ebean.Ebean;

import controllers.GameController;
import controllers.game.TerrainStreamer;

public class GridHandler {
	
	public static void sendObjectStream(){
	
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			String terrainStream = "";
			Tribe currentTribe = null;
			
			if (WebSocketSessionController.userSessions.containsKey(session)){
				currentTribe = WebSocketSessionController.userSessions.get(session).tribe;
			}
			
			for (Tribe tribe: Tribe.find.all()){
				if (currentTribe != null && tribe.id == currentTribe.id){
					terrainStream += tribe.position.x + "," + tribe.position.y + "," + "assets/images/game/tiles/m2ngija_p.png" + ";";
				} else{
					terrainStream += tribe.position.x + "," + tribe.position.y + "," + "assets/images/game/tiles/vaenlased_p.png" + ";";	
				}
			}
			session.write("t;" + terrainStream.substring(0, terrainStream.length()-1));
		}
	}
}
