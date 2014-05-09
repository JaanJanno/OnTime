package controllers.websocket;

import java.util.List;
import models.game.Tribe;
import play.mvc.WebSocket;
import controllers.game.ObjectStreamer;
import controllers.game.TerrainStreamer;

public class GridHandler {
	
	public static void sendObjectStream(){	
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){				
			Tribe currentTribe = Tribe.find.byId(WebSocketSessionController.userSessions.get(session).tribe.id);		
			String objectStream = generateStreamFromList(ObjectStreamer.streamAllPlayerUrl(currentTribe));			
			session.write("t;" + objectStream.substring(0, objectStream.length()-1));
		}
	}
	
	public static void sendTerrainStream(Tribe tribe){
		List<WebSocket.Out<String>> sessions = WebSocketSessionController.getTribeSockets(tribe);
		for (WebSocket.Out<String> session: sessions){
			String terrainStream = generateStreamFromList(TerrainStreamer.streamAllUrl(tribe));
			session.write("r;" + terrainStream.substring(0, terrainStream.length()-1));
		}
	}
	
	public static String generateStreamFromList(List<List<String>> list){
		String stream = "";
		for(int j = 0; j < list.size(); j++){
			List<String> rida = list.get(j);
			for(int i = 0; i < rida.size(); i++){
				stream += i + "," + j + "," + rida.get(i) + ";";
			}
		}
		return stream;
	}
}
