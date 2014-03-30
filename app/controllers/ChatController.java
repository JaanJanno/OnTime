package controllers;

import static play.data.Form.form;
import models.User;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import controllers.routes;
import controllers.chat.ChatSocket;

public class ChatController extends Application {

	@Security.Authenticated(Secured.class)
	public static Result chatSubmit() {
		Form<NewChat> chatForm = form(NewChat.class).bindFromRequest();
		if (chatForm.hasErrors()) {
		    return redirect(
		        routes.GameController.game()
		    );
		} else {
			try{
				String text = chatForm.get().text;
				String user = User.find.byId(session().get("email")).name;
				if (!text.replaceAll("\\s+","").equals("")){	
					ChatSocket.sendMessage(user + ": " +text);
				}
	    	} catch(Exception e){} finally{
	    		return ok();
	    	}
		}
	}
	
	public static class NewChat {

		public String text;
	}
	
}
