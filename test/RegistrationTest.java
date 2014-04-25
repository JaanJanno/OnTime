import play.libs.F.*;
import static play.test.Helpers.*;
import org.junit.*;
import play.test.*;
import models.*;
import static org.junit.Assert.*;

public class RegistrationTest {
	
	@Test
    public void registrationTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                              
                // Üritab leida olematut kasutajat.
                
                User registeredUser = User.find.byId("testibar@ontime.ee");               
                assertNull(registeredUser);
                
                // Registreerib uue kasutaja.
                
                browser.goTo("http://localhost:3333/register");
                browser.$("#firstName")			.text("Testibar");
                browser.$("#lastName")			.text("McQueen");
                browser.$("#organizationName")	.text("Pies Inc.");
                browser.$("#email")				.text("testibar@ontime.ee");
                browser.$("#password")			.text("testibarsPass");
                browser.$("#button").click();
                
                /*	Otsib registreeritud kasutjaa andmebaasist üles ja
                 *	kontrollib selle andmete õigsust.
                 */
                
                registeredUser = User.find.byId("testibar@ontime.ee");               
                assertNotNull("User was not registered.", registeredUser);
                assertEquals("Wrong name in database.", registeredUser.name, "Testibar McQueen");
                assertEquals("Wrong organization in database.", registeredUser.organizationName, "Pies Inc.");
                assertEquals("Wrong email in database.", registeredUser.email, "testibar@ontime.ee");
                assertEquals("Wrong password in database.", registeredUser.password, "testibarsPass");
            }
        });
    }
	
	@Test
    public void doubleRegistrationTest() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                
                // Registreerib uue kasutaja.
                
                browser.goTo("http://localhost:3333/register");
                browser.$("#firstName")			.text("Testibar");
                browser.$("#lastName")			.text("McQueen");
                browser.$("#organizationName")	.text("Pies Inc.");
                browser.$("#email")				.text("testibar@ontime.ee");
                browser.$("#password")			.text("testibarsPass");
                browser.$("#button").click();
                
                /*	Üritab uut samade andmetega aga erineva parooliga
                 *	registreerida.
                 */
                
                browser.goTo("http://localhost:3333/register");
                browser.$("#firstName")			.text("Testibar");
                browser.$("#lastName")			.text("McQueen");
                browser.$("#organizationName")	.text("Pies Inc.");
                browser.$("#email")				.text("testibar@ontime.ee");
                browser.$("#password")			.text("myPiratePassword"); //Erinev!
                browser.$("#button").click();
                
                User registeredPirateUser = User.find.byId("testibar@ontime.ee");               
                assertEquals("Password overide by second registration.", "testibarsPass", registeredPirateUser.password);
            }
        });
    }
}
