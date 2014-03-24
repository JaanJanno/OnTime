package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.model.Response;
import org.scribe.oauth.OAuthService;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.SqlUpdate;
import com.google.gson.JsonObject;

import play.*;
import play.libs.OAuth;
import play.libs.OAuth.RequestToken;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class RegistrationController extends Application {
    
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
	
	public static Result registerFormSubmit() {
		Form<Register> regForm = form(Register.class).bindFromRequest();
		if (regForm.hasErrors()) {
		    return redirect(
		        routes.Application.index()
		    );
		} else {
			try{
				String email = regForm.get().email;
				String password = regForm.get().password;
				boolean valid = true;
				if (email.replaceAll("\\s+","").equals("")){
					flash("noMail","Please enter email to register.");
					valid = false;
				}
				if (password.equals("")){
					flash("noPass","User must have a password.");
					valid = false;
				}
				if (valid){
		    		User uusK = new User(email, regForm.get().firstName+" "+regForm.get().lastName, regForm.get().organizationName, password);
		    		Ebean.save(uusK);
		    	} else{
		    		return redirect(
	    		        routes.RegistrationController.register()
	    		    );
		    	}
		    	return redirect(
	    		        routes.Application.index()
	    		    );
		    	
	    	} catch(Exception e){return redirect(
	    		        routes.Application.index()
	    		    );}
		}
	}
	
	public static void handleTwitterUser(String nameN, String idN){
		String   id = idN;
		String name = nameN;
		
		try{
			User uusK = new User(id, name, "", Double.toString(Math.random()));
    		Ebean.save(uusK);
		} catch(Exception e){
			
		} finally{
			session().clear();
			session().put("email", id);
		}
	}
    
    public static class Register {

    	public String firstName;
    	public String lastName;
    	public String organizationName;
		public String email;
		public String password;
	}

}
