package controllers;

import play.mvc.*;
import play.mvc.Http.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    public Result onUnauthorized(Context ctx) {
    	Application.flash("loginToPlay", "Please log in to play the game. :)");
        return redirect(routes.Application.login("game"));
    }
}
