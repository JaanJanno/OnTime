package controllers.chat;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;
import controllers.Application;

public class ChatSocket extends Application {
	
	public static WebSocket<String> chat() {
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

				in.onMessage(new Callback<String>() {
					public void invoke(String event) {
						System.out.println("tere");
         
					} 
				});

				in.onClose(new Callback0() {
					public void invoke() {   
						System.out.println("headaega");   
					}
				});
				out.write("Tere raffas, ma olen websocket!");
				System.out.println("algus");
			}  
		};
	}
}
