import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    @Test
    public void andmebaasiAndmedListis() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource()).contains("Suusatamine");
                assertThat(browser.pageSource()).contains("Kelgutamine");
                assertThat(browser.pageSource()).contains("Uisutamine");
            }
        });
    }

}
