package controllers.chat;

import java.util.HashMap;
import java.util.Map;

import models.ChatEvent;

import com.avaje.ebean.Ebean;

import controllers.Application;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;

public class ChatSocket extends Application {
	
	static Map<Long, Out<String>> sessions = new HashMap<Long, WebSocket.Out<String>>();
	static long chatSocketCount = 0;
	static Pinger pinger = new Pinger();
	
	public static long getNewChatSocketId() {
		chatSocketCount += 1;
		return (chatSocketCount);
	}
	
	public static void sendMessage(String text){
		if(!isPing(text)){
			Ebean.save(new ChatEvent(text));
		}
		for(WebSocket.Out<String> session: sessions.values()){
			session.write(text);
		}
	}

	public static WebSocket<String> chat() {
		return new WebSocket<String>() {
			
			final long id = getNewChatSocketId();

			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

				in.onMessage(new Callback<String>() {
					public void invoke(String event) {
						for(WebSocket.Out<String> session: sessions.values()){
							session.write(event);
						}
					} 
				});

				in.onClose(new Callback0() {
					public void invoke() {
						sessions.remove(id);
					}
				});
				
				sessions.put(id, out);
			}  
		};
	}
	
	private static boolean isPing(String s){
		if(s.equals("")){
			return true;
		} else{
			return false;
		}
	}
	
	private static class Pinger extends Thread{
		
		public Pinger(){
			this.start();
		}
		@Override
		public synchronized void run() {
			while(true){
				sendMessage("");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
