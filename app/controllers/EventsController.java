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

import play.*;
import play.libs.OAuth;
import play.libs.OAuth.RequestToken;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class EventsController extends Application {
    
    @Security.Authenticated(Secured.class)
    public static Result myevents() {
    	User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    	} catch(Exception e){}
        return ok(events.render(
            form(Application.Login.class),
            form(NewEvent.class),
            Event.findUserEvents(session().get("email")),
            kasutaja,
            Event.findUserEventsCount(session().get("email")))
        ); 
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
	    			routes.EventsController.myevents()
	    		);
	    	}
		}
	}
	
	public static class NewEvent {

		public String title;
		public String date;
		public User user;
	}
}
