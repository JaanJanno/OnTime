import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Event;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ApplicationTest {

    @Test
    public void htmlOlemas() {
        Content html = views.html.index.render(new ArrayList<Event>());
        assertThat(contentType(html)).isEqualTo("text/html");
    }
    
    @Test
    public void pealkiriOlemas() {
        Content html = views.html.index.render(new ArrayList<Event>());
        assertThat(contentAsString(html)).contains("OnTime");
    }
}
