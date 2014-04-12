package controllers.websocket;

import models.ChatEvent;
import play.mvc.WebSocket;

import com.avaje.ebean.Ebean;

public class ChatHandler {
	
	public static void sendMessage(String text){
		Ebean.save(new ChatEvent(text));
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			session.write("m;"+text);
		}
	}
	
	public static void sendPing(){
		
		//	Ping the WS session to keep Heroku WS connection open.
		
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			session.write("");
		}
	}
}
