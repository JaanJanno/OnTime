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

import play.*;
import play.libs.OAuth;
import play.libs.OAuth.RequestToken;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import views.html.*;

public class TwitterController extends Application {
	
	static OAuthService service = new ServiceBuilder().provider(TwitterApi.SSL.class).callback("http://localhost:9000/auth").apiKey("t7wdeQkSRkkIgnHeeevQ").apiSecret("49tQJGLjvDtKzy4mJWSuVdDRzdVh4AwXzLZ2XN5wtg").build();
	static HashMap<String, Token> requestTokens = new HashMap<String, Token>();
	
	public static Result twitter(){
		String sid = Double.toString(Math.random());
		requestTokens.put(session().get("twitter-sid"), service.getRequestToken());
		String authUrl = service.getAuthorizationUrl(requestTokens.get(session().get("twitter-sid")));
		return redirect(
				authUrl
		    );
	}
	
	public static Result auth(){
		Verifier v = new Verifier(request().getQueryString("oauth_verifier"));
		Token accessToken = service.getAccessToken(requestTokens.get(session().get("twitter-sid")), v);
		requestTokens.remove(session().get("twitter-sid"));
		session().remove("twitter-sid");
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
		
		service.signRequest(accessToken, request); // the access token from step 4
		Response response = request.send();
		System.out.println(response.getBody());
		return ok(auth.render( 
				response.getBody()
	        )); 
	}
}
