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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import play.*;
import play.libs.OAuth;
import play.libs.OAuth.RequestToken;
import play.mvc.*;
import play.mvc.BodyParser.Json;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class TwitterController extends Application {
	
	static OAuthService service;
	static HashMap<String, Token> requestTokens = new HashMap<String, Token>();
	
	static boolean isLocal = true;
	
	static{
		if (Play.isDev()){
			service = new ServiceBuilder().provider(TwitterApi.SSL.class).callback("http://localhost:9000/auth").apiKey("t7wdeQkSRkkIgnHeeevQ").apiSecret("49tQJGLjvDtKzy4mJWSuVdDRzdVh4AwXzLZ2XN5wtg").build();
		} else{
			service = new ServiceBuilder().provider(TwitterApi.SSL.class).callback("https://on-time.herokuapp.com/auth").apiKey("t7wdeQkSRkkIgnHeeevQ").apiSecret("49tQJGLjvDtKzy4mJWSuVdDRzdVh4AwXzLZ2XN5wtg").build();
		}
	}
	
	public static Result twitter(){
		String sid = Double.toString(Math.random());
		session().put("twitter-sid", sid);
		requestTokens.put(session().get("twitter-sid"), service.getRequestToken());
		String authUrl = service.getAuthorizationUrl(requestTokens.get(session().get("twitter-sid")));
		return redirect(
				authUrl
		    );
	}
	
	public static Result auth(){
		try{
			Verifier v = new Verifier(request().getQueryString("oauth_verifier"));
			Token accessToken = service.getAccessToken(requestTokens.get(session().get("twitter-sid")), v);
			requestTokens.remove(session().get("twitter-sid"));
			session().remove("twitter-sid");
			OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
			
			service.signRequest(accessToken, request); // the access token from step 4
			Response response = request.send();
			JsonObject responseJson = new JsonParser().parse(response.getBody()).getAsJsonObject();
			
			RegistrationController.handleTwitterUser(responseJson.get("name").getAsString(), responseJson.get("id_str").getAsString());
		} catch(Exception e){} finally{
			return redirect(
					"/"
		    ); 
		}
	}
}
