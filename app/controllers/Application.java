package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(index.render( 
            Event.find.all(),
            form(Login.class),
            kasutaja
        )); 
    }
    
    public static Result register() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(register.render( 
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
	
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
		    return redirect(
		        routes.Application.index()
		    );
		} else {
		    session().clear();
		    session("email", loginForm.get().email);
		    System.out.println(session().toString());
		    
		    return redirect(
		        routes.Application.index()
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
