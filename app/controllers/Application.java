package controllers;

import java.util.ArrayList;
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

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	
	static Token requestToken;
	static OAuthService service;

	public static Result autoriseerimine(){
		service = new ServiceBuilder().provider(TwitterApi.SSL.class).apiKey("t7wdeQkSRkkIgnHeeevQ").apiSecret("49tQJGLjvDtKzy4mJWSuVdDRzdVh4AwXzLZ2XN5wtg").build();
		requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);
		return redirect(
				authUrl
		    );
		
		
		
	}
	
	public static Result auth(String id){
		Verifier v = new Verifier(id);
		Token accessToken = service.getAccessToken(requestToken, v);
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.xml");
		service.signRequest(accessToken, request); // the access token from step 4
		Response response = request.send();
		System.out.println(response.getBody());
		return ok(auth.render( 
				response.getBody()
	        )); 
	}
	
	

    public static Result index() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(index.render( 
        	EventQuery.getTitleDateOrganization(),
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
    
    @Security.Authenticated(Secured.class)
    public static Result myevents() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(events.render(
            form(Login.class),
            form(NewEvent.class),
            Event.findUserEvents(session().get("email")),
            kasutaja,
            Event.findUserEventsCount(session().get("email")))
        ); 
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
			flash("loginfail", "Invalid email or password.");
		    return redirect(
		        routes.Application.index()
		    );
		} else {
		    session().clear();
		    session("email", loginForm.get().email);		    
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
	    		        routes.Application.register()
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
	
	public static Result newEventFormSubmit() {
		Form<NewEvent> regForm = form(NewEvent.class).bindFromRequest();
		if (regForm.hasErrors()) {
		    return redirect(
		        routes.Application.index()
		    );
		} else {
			try{
				String title = regForm.get().title;
				String date = regForm.get().date;
				if (!title.replaceAll("\\s+","").equals("") && !date.replaceAll("\\s+","").equals("")){	
					Event uusV = new Event(title, date, User.find.byId(session().get("email")));
					Ebean.save(uusV);
		    	} else{
		    		flash("nonameEvent","Event must have a name and date.");
		    	}
	    	} catch(Exception e){} finally{
	    		return redirect(
	    		        routes.Application.myevents()
	    		    );
	    	}
		}
	}
	
	public static class NewEvent {

		public String title;
		public String date;
		public User user;
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
    	public String organizationName;
		public String email;
		public String password;
	}

}
