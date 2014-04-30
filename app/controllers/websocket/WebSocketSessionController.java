package controllers.websocket;

import java.util.HashMap;
import java.util.Map;

import models.User;
import models.game.Tribe;
import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;

public class WebSocketSessionController {
	
	static Map<Long, Out<String>> sessions = new HashMap<Long, WebSocket.Out<String>>();
	static Map<Out<String>, User> userSessions = new HashMap<WebSocket.Out<String>, User>();
	static long webSocketCount = 0;
	
	public static long getNewWebSocketId() {
		return (webSocketCount++);
	}
	
	public static WebSocket.Out<String> getTribeSocket(Tribe tribe){
		WebSocket.Out<String> socket = null;
		for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){			
			if (WebSocketSessionController.userSessions.get(session).tribe.id == tribe.id)
				socket = session;
		}
		return socket;
	}
	
	static Pinger pinger = new Pinger();

	private static class Pinger extends Thread{
		
		public Pinger(){
			this.start();
		}
		
		private void doPing(){
			ChatHandler.sendPing();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
		
		@Override
		public synchronized void run() {
			while(true){
				doPing();
			}
		}
	}
}
