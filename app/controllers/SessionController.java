package controllers;

import models.User;

public class SessionController extends Application {

	public static User getCurrentUser(){
		return User.find.byId(session().get("email"));
	}
}
