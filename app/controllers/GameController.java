package controllers;

import controllers.game.MovementController;
import controllers.game.ObjectStreamer;
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
    		kasutaja = SessionController.getCurrentUser();
    		if (kasutaja.tribe == null){
    			Tribe.generateUserTribe(kasutaja);
    		}
    	} catch(Exception e){}
    	
		return(ok(grid.render(
				ChatEvent.findChatEvents(),
				GameEventQuery.getEventsStatistics(),
				WarEvent.findTribeWarEvents(kasutaja.tribe),
				SpecialEvent.findTribeEvents(kasutaja.tribe),
				ObjectStreamer.streamAllPlayerUrl(kasutaja.tribe),
				kasutaja.tribe,
				TerrainStreamer.streamAllUrl(kasutaja.tribe),
				form(Application.Login.class), 
				kasutaja
		)));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result move() {
		response().setHeader("Content-Type", "text/html");	
		try {
			Form<MoveSubmit> moveForm = form(MoveSubmit.class).bindFromRequest();
		    MovementController.tryMove(Tribe.getCurrentTribe(), moveForm.get().x, moveForm.get().y);
		    GridHandler.sendObjectStream();
		    GridHandler.sendTerrainStream(Tribe.getCurrentTribe());
		} catch (Exception e) {}   		
	    return(ok("movin' on"));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result moveNoscript(Integer x, Integer y) {
		try {
		    MovementController.tryMove(Tribe.getCurrentTribe(), x, y);
		    GridHandler.sendObjectStream();
		} catch (Exception e) {}		
		return(redirect("/game"));
	}
	
	public static class MoveSubmit {
		public int x;
		public int y;
	}
}