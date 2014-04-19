package controllers.offline;

import controllers.Application;

public class OfflineController extends Application {

	public static void appcache() {
		response.setContentTypeIfNotSet("text/cache-manifest");
		renderTemplate("appcache.manifest");
	}

}
