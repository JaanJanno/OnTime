package controllers;

import com.avaje.ebean.Ebean;
import controllers.game.MovementController;
import controllers.game.TerrainStreamer;
import controllers.websocket.GridHandler;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import models.game.Tribe;
import models.game.events.GameEventQuery;
import models.game.events.SpecialEvent;
import models.game.events.WarEvent;
import views.html.*;

public class GameController extends Application {
	
	
	
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
    	
		return(ok(grid.render(
				ChatEvent.findChatEvents(),
				GameEventQuery.getEventsStatistics(),
				WarEvent.findTribeWarEvents(kasutaja.tribe),
				SpecialEvent.findTribeEvents(kasutaja.tribe),
				TerrainStreamer.streamAllPlayerUrl(kasutaja.tribe),
				kasutaja.tribe,
				TerrainStreamer.streamAllUrl(kasutaja.tribe),
				form(Application.Login.class), 
				kasutaja
		)));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result move() {
		response().setHeader("Content-Type", "text/html");
		Form<MoveSubmit> moveForm = form(MoveSubmit.class).bindFromRequest();
		
	    MovementController.tryMove(User.find.byId(session().get("email")), moveForm.get().x, moveForm.get().y);
	    
	    GridHandler.sendObjectStream();
	    GridHandler.sendTerrainStream(User.find.byId(session().get("email")).tribe);
	    	    
	    return(ok("movin' on"));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result moveNoscript(Integer x, Integer y) {
	    MovementController.tryMove(User.find.byId(session().get("email")), x, y);
	    GridHandler.sendObjectStream();
		return(redirect("/game"));
	}
	
	public static class MoveSubmit {
		public int x;
		public int y;
	}
}