package controllers.websocket;

import java.util.HashMap;
import java.util.Map;

import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;

public class WebSocketSessionController {
	
	static Map<Long, Out<String>> sessions = new HashMap<Long, WebSocket.Out<String>>();
	static long webSocketCount = 0;
	
	public static long getNewWebSocketId() {
		return (webSocketCount++);
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
