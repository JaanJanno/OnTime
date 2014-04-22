import play.libs.F.*;
import static play.test.Helpers.*;
import org.junit.*;
import play.test.*;
import org.openqa.selenium.Cookie;
import static org.junit.Assert.*;

public class AuthenticationTest {
	
	public static String getEmailFromCookie(Cookie cookie){ 
		return cookie.getValue().split("=")[1].replace("\"","").replace("%40", "@");
	}
	
	public static void logTestUserIn(TestBrowser browser, String email, String password){
		browser.goTo("http://localhost:3333/login");
        browser.$("#email").text(email);
        browser.$("#password").text(password);
        browser.$("#button").click();
	}

	@Test
    public void logInTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {          	
            	assertTrue("Session has cookies for some weird reason.", browser.getCookies().isEmpty());    
            	
            	// Katse korrektsete andmetega sisse logida.
            	
                logTestUserIn(browser, "jaan@ontime.ee", "jaan");              
                Cookie loginCookie = browser.getCookie("PLAY_SESSION");
                
                assertNotNull("User wasn't logged in.", loginCookie);                
                assertEquals("Wrong user was logged in.", getEmailFromCookie(loginCookie), "jaan@ontime.ee");
            }
        });
    }
	
	@Test
    public void invalidPasswordLogInTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {          	
            	assertTrue("Session has cookies for some weird reason.", browser.getCookies().isEmpty());    
            	
            	// Katse valede andmetega sisse logida.
            	
                logTestUserIn(browser, "jaan@ontime.ee", "wrongPassword");              
                Cookie loginCookie = browser.getCookie("PLAY_SESSION");
                
                assertNull("User was logged in while entering the wrong password.", loginCookie);                
            }
        });
    }
	
	@Test
    public void invalidUserLogInTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {          	
            	assertTrue("Session has cookies for some weird reason.", browser.getCookies().isEmpty());
            	
            	// Katse valede andmetega sisse logida.
            	
                logTestUserIn(browser, "iDontExist@ontime.ee", "wrongPassword");              
                Cookie loginCookie = browser.getCookie("PLAY_SESSION");
                
                assertNull("User was logged in while entering nonexistant username.", loginCookie);                
            }
        });
    }
}
