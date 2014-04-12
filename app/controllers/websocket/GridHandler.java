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
			} else{
				break;
			}
			
			List<List<String>> list = TerrainStreamer.streamAllPlayerUrl(currentTribe);
			
			for(int j = 0; j < list.size(); j++){
				List<String> rida = list.get(j);
				for(int i = 0; i < rida.size(); i++){
					terrainStream += i + "," + j + "," + rida.get(i) + ";";
				}
			}
			
			session.write("t;" + terrainStream.substring(0, terrainStream.length()-1));
		}
	}
	
	public static void sendTerrainStream(){
		
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			String terrainStream = "";
			Tribe currentTribe = null;
			
			if (WebSocketSessionController.userSessions.containsKey(session)){
				currentTribe = WebSocketSessionController.userSessions.get(session).tribe;
			} else{
				break;
			}
			
			List<List<String>> list = TerrainStreamer.streamAllUrl(currentTribe);
			
			for(int j = 0; j < list.size(); j++){
				List<String> rida = list.get(j);
				for(int i = 0; i < rida.size(); i++){
					terrainStream += i + "," + j + "," + rida.get(i) + ";";
				}
			}
			session.write("r;" + terrainStream.substring(0, terrainStream.length()-1));
		}
	}
}
