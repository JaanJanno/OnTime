package controllers;

import static play.data.Form.form;
import models.User;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import controllers.routes;
import controllers.chat.ChatSocket;

public class ChatController extends Application {

	public static Result chatSubmit() {
		Form<NewChat> chatForm = form(NewChat.class).bindFromRequest();
		response().setHeader("Content-Type", "text/html");
		try{
			String text = chatForm.get().text;
			String user = User.find.byId(session().get("email")).name;
			if (!text.replaceAll("\\s+","").equals("")){	
				ChatSocket.sendMessage(user + ": " +text);
			}
    	} catch(Exception e){}
		return (ok("gotcha"));
	}
	
	public static Result chatSubmitNoscript() {
		Form<NewChat> chatForm = form(NewChat.class).bindFromRequest();

		try{
			String text = chatForm.get().text;
			String user = User.find.byId(session().get("email")).name;
			if (!text.replaceAll("\\s+","").equals("")){	
				ChatSocket.sendMessage(user + ": " +text);
			}
    	} catch(Exception e){}
		
		return redirect(
		        routes.GameController.game()
		    );
	}
	
	public static class NewChat {

		public String text;
	}
	
}
