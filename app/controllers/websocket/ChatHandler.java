package controllers.websocket;

import models.ChatEvent;
import play.mvc.WebSocket;

import com.avaje.ebean.Ebean;

public class ChatHandler {
	
	public static void sendMessage(String text){
		if(!isPing(text)){
			Ebean.save(new ChatEvent(text));
		}
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
			session.write(text);
		}
	}
	
	private static boolean isPing(String s){
		if(s.equals("")){
			return true;
		} else{
			return false;
		}
	}
}
