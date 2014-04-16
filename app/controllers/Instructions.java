package controllers;

import static play.data.Form.form;
import models.ChatEvent;
import models.User;
import models.game.Tribe;
import models.game.events.GameEventQuery;
import models.game.events.SpecialEvent;
import models.game.events.WarEvent;
import play.mvc.Result;
import controllers.game.TerrainStreamer;
import views.html.*;

public class Instructions extends Application {
	
	public static Result instructions() {	
		User kasutaja = null;
    	try{
    		kasutaja = SessionController.getCurrentUser();
    	} catch(Exception e){}
    	
		return(ok(instructions.render(
				form(Application.Login.class), 
				kasutaja
		)));
	}

}
