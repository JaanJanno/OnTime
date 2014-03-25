package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
System.out.println("dfsad");
        return ctx.session().get("email");
    }

    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.index());
    }
}
