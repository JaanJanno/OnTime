package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }

    public Result onUnauthorized(Context ctx) {
    	Application.flash("loginToPlay", "Please log in to play the game. :)");
        return redirect(routes.Application.index());
    }
}
