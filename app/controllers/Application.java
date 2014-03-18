package controllers;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {

/*    	SqlQuery q = Ebean.createSqlQuery("SELECT * FROM User");
    	List<SqlRow> l = q.findList();
    	System.out.println(l);*/
    	
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
            form(Register.class),
            kasutaja
        )); 
    }
    
    public static Result myevents() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(events.render(
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
	
	public static Result registerFormSubmit() {
		Form<Register> regForm = form(Register.class).bindFromRequest();
		if (regForm.hasErrors()) {
		    return redirect(
		        routes.Application.index()
		    );
		} else {
			try{
		    	User uusK = new User(regForm.get().email, regForm.get().firstName+" "+regForm.get().lastName, regForm.get().companyName, regForm.get().password);
		    	Ebean.save(uusK);
	    	} catch(Exception e){} finally{
	    		return redirect(
	    		        routes.Application.index()
	    		    );
	    	}
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
    
    public static class Register {

    	public String firstName;
    	public String lastName;
    	public String companyName;
		public String email;
		public String password;
	}

}
