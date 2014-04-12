package controllers.websocket;

import java.util.List;
import models.game.Tribe;
import play.mvc.WebSocket;
import controllers.game.TerrainStreamer;

public class GridHandler {
	
	public static void sendObjectStream(){
	
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			
			Tribe currentTribe = null;
			
			if (WebSocketSessionController.userSessions.containsKey(session)){
				currentTribe = Tribe.find.byId(WebSocketSessionController.userSessions.get(session).tribe.id);
			} else{
				continue;
			}
			
			String terrainStream = "";
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
	
	public static void sendTerrainStream(Tribe tribe){
		
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			
			if (WebSocketSessionController.userSessions.get(session).tribe.id != tribe.id){
				continue;
			}

			String terrainStream = "";
			
			List<List<String>> list = TerrainStreamer.streamAllUrl(tribe);
			
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
