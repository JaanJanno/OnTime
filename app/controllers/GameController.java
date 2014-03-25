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

import controllers.game.TerrainStreamer;
import play.*;
import play.libs.OAuth;
import play.libs.OAuth.RequestToken;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import models.game.Terrain;
import models.game.TerrainObject;
import models.game.Tribe;
import views.html.*;

public class GameController extends Application {
	
	public static Terrain mainTerrain;
	
	@Security.Authenticated(Secured.class)
	public static Result game() {
		
		User kasutaja = null;
    	try{
    		kasutaja = User.find.byId(session().get("email"));
    		if (kasutaja.tribe == null){
    			Tribe uusTribe = new Tribe(kasutaja.name + "'s tribe."); 			
    			Ebean.save(uusTribe);   			
    			kasutaja.tribe = uusTribe;
    			Ebean.update(kasutaja);
    		}
    	} catch(Exception e){}
    	
    	System.out.println(kasutaja.tribe.name);
    	
		return(ok(grid.render(
				Tribe.find.all(),
				kasutaja.tribe,
				TerrainStreamer.streamAllUrl(mainTerrain),
				form(Application.Login.class), 
				kasutaja
		)));
	}
}