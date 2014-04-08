package controllers.websocket;

import controllers.Application;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

public class WebSocketController extends Application {
	
	public static WebSocket<String> ws() {
		return new WebSocket<String>() {
			
			final long id = WebSocketSessionController.getNewChatSocketId();

			public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

				in.onMessage(new Callback<String>() {
					public void invoke(String event) {
						for(WebSocket.Out<String> session: WebSocketSessionController.sessions.values()){
							session.write(event);
						}
					} 
				});

				in.onClose(new Callback0() {
					public void invoke() {
						WebSocketSessionController.sessions.remove(id);
					}
				});
				
				WebSocketSessionController.sessions.put(id, out);
			}  
		};
	}	
}
