package controllers;

import static play.data.Form.form;
import models.User;
import play.data.Form;
import play.mvc.Result;
import controllers.routes;
import controllers.websocket.ChatHandler;

public class ChatController extends Application {

	public static Result chatSubmit() {
		response().setHeader("Content-Type", "text/html");
		handleSubmit();
		return ok("gotcha");
	}
	
	public static Result chatSubmitNoscript() {
		handleSubmit();		
		return redirect(routes.GameController.game());
	}
	
	public static void handleSubmit(){	
		Form<NewChat> chatForm = form(NewChat.class).bindFromRequest();	
		try{
			String text = chatForm.get().text;
			String user = User.find.byId(session().get("email")).name;
			trySend(user, text);
    	} catch(Exception e){}
	}
	
	public static void trySend(String user, String text){
		if (isValidMessage(text)){	
			ChatHandler.sendMessage(user + ": " +text);
		}
	}
	
	public static boolean isValidMessage(String text){
		
		//	Check if message only contains whitespace.
		
		return !text.replaceAll("\\s+","").equals("");
	}
	
	public static class NewChat {

		public String text;
	}
	
}
