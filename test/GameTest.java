import static org.junit.Assert.assertEquals;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import models.User;
import models.game.Tribe;
import org.junit.Test;
import controllers.game.TerrainController;
import play.libs.F.Callback;
import play.test.TestBrowser;

public class GameTest {
	
	public static void moveTestUser(TestBrowser browser, int x, int y){
		String xMove = Integer.toString(x + 5);
		String yMove = Integer.toString(y + 5);
		browser.$("#" + xMove + "on" + yMove).click();
	}

	@Test
    public void moveToSameTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {             
            	AuthenticationTest.logTestUserIn(browser, "jaan@ontime.ee", "jaan");          
                browser.goTo("http://localhost:3333/game");
                
                Tribe testTribe = User.find.byId("jaan@ontime.ee").tribe;               
                int beforeX = testTribe.x;
                int beforeY = testTribe.y;
                
                moveTestUser(browser, 0, 0);
                
                /*	Refresh, et test eelmise 
                 *	ajaxi toimimise ära ootaks.
                 *  Muidu tormab test rakendusest ette, kuna
                 *  ajax päringuid järgi ei oodata.
                 */
                browser.goTo("http://localhost:3333/game");
                
                testTribe = User.find.byId("jaan@ontime.ee").tribe;               
                int afterX = testTribe.x;
                int afterY = testTribe.y;
                
                assertEquals("User x coordinate moved when clicking on current location", beforeX, afterX);
                assertEquals("User y coordinate moved when clicking on current location", beforeY, afterY);
            }
        });
    }
	
	@Test
    public void moveToNegativeWraparoundTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                
            	AuthenticationTest.logTestUserIn(browser, "jaan@ontime.ee", "jaan");          
                browser.goTo("http://localhost:3333/game");             
                Tribe testTribe = User.find.byId("jaan@ontime.ee").tribe;          
                
                moveTestUser(browser, -1, -1);
                
                /*	Refresh, et test eelmise 
                 *	ajaxi toimimise ära ootaks.
                 *  Muidu tormab test rakendusest ette, kuna
                 *  ajax päringuid järgi ei oodata.
                 */
                browser.goTo("http://localhost:3333/game");
                
                testTribe = User.find.byId("jaan@ontime.ee").tribe;
                int afterX = testTribe.x;
                int afterY = testTribe.y;
                
                assertEquals("Movement didn't wrap arpund the map on x coordinate.", TerrainController.getWorldWidth()-1, afterX);
                assertEquals("Movement didn't wrap arpund the map on y coordinate.", TerrainController.getWorldHeight()-1, afterY);
            }
        });
    }
	
	@Test
    public void moveTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
            	// Peab olema positiivne delta! Negatiivse jaoks on wraparound test.
            	int deltaX = 4;
            	int deltaY = 4;
            	
            	AuthenticationTest.logTestUserIn(browser, "jaan@ontime.ee", "jaan");          
                browser.goTo("http://localhost:3333/game");
                
                Tribe testTribe = User.find.byId("jaan@ontime.ee").tribe;            
                int beforeX = testTribe.x;
                int beforeY = testTribe.y;
                
                moveTestUser(browser, deltaX, deltaY);
                
                /*	Refresh, et test eelmise 
                 *	ajaxi toimimise ära ootaks.
                 *  Muidu tormab test rakendusest ette, kuna
                 *  ajax päringuid järgi ei oodata.
                 */
                browser.goTo("http://localhost:3333/game");
                
                testTribe = User.find.byId("jaan@ontime.ee").tribe;         
                int afterX = testTribe.x;
                int afterY = testTribe.y;
                
                assertEquals("Moved to wrong x coordinate.", beforeX+deltaX, afterX);
                assertEquals("Moved to wrong y coordinate.", beforeY+deltaY, afterY);
            }
        });
    }
}
