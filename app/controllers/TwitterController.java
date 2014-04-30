package controllers;

import java.util.HashMap;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.model.Response;
import org.scribe.oauth.OAuthService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import play.*;
import play.mvc.*;

public class TwitterController extends Application {
	
	static OAuthService service;
	static HashMap<String, TwitterLogin> requestTokens = new HashMap<String, TwitterLogin>();
	
	static boolean isLocal = true;
	
	static{
		if (Play.isDev()){
			service = new ServiceBuilder().provider(TwitterApi.SSL.class).callback("http://localhost:9000/auth").apiKey("t7wdeQkSRkkIgnHeeevQ").apiSecret("49tQJGLjvDtKzy4mJWSuVdDRzdVh4AwXzLZ2XN5wtg").build();
		} else{
			service = new ServiceBuilder().provider(TwitterApi.SSL.class).callback("https://tribal-age.herokuapp.com/auth").apiKey("t7wdeQkSRkkIgnHeeevQ").apiSecret("49tQJGLjvDtKzy4mJWSuVdDRzdVh4AwXzLZ2XN5wtg").build();
		}
	}
	
	public static Result twitter(String redir){
		String sid = Double.toString(Math.random());
		session().put("twitter-sid", sid);
		requestTokens.put(session().get("twitter-sid"), new TwitterLogin(service.getRequestToken(), redir));
		String authUrl = service.getAuthorizationUrl(requestTokens.get(session().get("twitter-sid")).token);
		return redirect(
				authUrl
		    );
	}
	
	public static Result auth(){
		try{
			Verifier v = new Verifier(request().getQueryString("oauth_verifier"));
			Token accessToken = service.getAccessToken(requestTokens.get(session().get("twitter-sid")).token, v);
			String redir = requestTokens.get(session().get("twitter-sid")).redir;
			
			session().remove("twitter-sid");
			OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
			
			service.signRequest(accessToken, request);
			Response response = request.send();
			JsonObject responseJson = new JsonParser().parse(response.getBody()).getAsJsonObject();
			
			RegistrationController.handleTwitterUser(responseJson.get("name").getAsString(), responseJson.get("id_str").getAsString());
			
			return redirect("/"+redir); 
		} catch(Exception e){
			return redirect("/"); 
		}
	}
	
	private static class TwitterLogin{
		
		Token token;
		String redir;
		
		public TwitterLogin(Token token, String redir){
			this.token = token;
			this.redir = redir;
		}
	}
}
