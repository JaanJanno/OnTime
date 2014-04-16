package controllers;

import static play.data.Form.form;
import models.User;
import play.mvc.Result;
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
