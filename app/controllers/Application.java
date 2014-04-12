package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import models.game.Tribe;
import views.html.*;

public class Application extends Controller {
	
    public static Result index() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(index.render(
        	Tribe.find.all(),
        	EventQuery.getTitleDateOrganization(),
            form(Login.class),
            kasutaja
        )); 
    }

    public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(
		    routes.Application.index()
		);
	}
    
    public static Result login(String redir) {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(login.render(
        	redir,
            form(Login.class),
            kasutaja
        )); 
    }
	
	public static Result authenticate(String redir) {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			flash("loginfail", "Invalid email or password.");
		    return redirect(
		        routes.Application.login(redir)
		    );
		} else {
		    session().clear();
		    session("email", loginForm.get().email);		    
		    return redirect(
		        "/"+redir
		    );
		}
	}
	
    public static class Login {

		public String email;
		public String password;
		
		public String validate() {
			if (User.authenticate(email, password) == null) {
			  return "Invalid user or password";
			}
			return null;
		}
	}
}
