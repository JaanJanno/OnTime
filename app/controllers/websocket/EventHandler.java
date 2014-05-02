package controllers.websocket;

import java.util.List;
import play.mvc.WebSocket;
import models.game.Tribe;

public class EventHandler {
	
	public static void sendSpecialEvent(Tribe tribe, String event){
		List<WebSocket.Out<String>> sessions = WebSocketSessionController.getTribeSockets(tribe);
		for (WebSocket.Out<String> session: sessions)
			session.write("e;" + event);
	}
	
	public static void sendWarEvent(Tribe tribe, String event){
		List<WebSocket.Out<String>> sessions = WebSocketSessionController.getTribeSockets(tribe);
		for (WebSocket.Out<String> session: sessions)
			session.write("w;" + event);
	}
}
