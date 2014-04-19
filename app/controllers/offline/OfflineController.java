package controllers.offline;

import controllers.Application;
import play.mvc.*;
import views.html.*;

public class OfflineController extends Application {

	public static Result appcache() {
		response().setContentType("text/cache-manifest");
		return ok(appcache.render());
	}
}