package controllers.websocket;

import play.mvc.WebSocket;
import models.game.Tribe;

public class EventHandler {
	
	public static void sendSpecialEvent(Tribe tribe, String event){
		WebSocket.Out<String> session = WebSocketSessionController.getTribeSocket(tribe);
		if (session != null)
			session.write("e;" + event);
	}
	
	public static void sendWarEvent(Tribe tribe, String event){
		WebSocket.Out<String> session = WebSocketSessionController.getTribeSocket(tribe);
		if (session != null)
			session.write("w;" + event);
	}
}
