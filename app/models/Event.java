package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Ebean;
import play.db.ebean.*;

@javax.persistence.Entity
public class Event extends Model {

	private static final long serialVersionUID = 2011193620557634857L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    public String title;
    public String date;
    @ManyToOne
    public User user;
    
    public Event(String title, String date, User user) {
      this.title = title;
      this.date = date;
      this.user = user;
    }
    
    public static List<Event> findUserEvents(String email) {
        return Ebean.find(Event.class).where().eq("user.email", email).findList();
    }
    
    public static int findUserEventsCount(String email) {
        return (int)(Ebean.find(Event.class).where().eq("user.email", email).findRowCount());
    }

    public static Model.Finder<Long,Event> find = new Model.Finder<Long, Event>(Long.class, Event.class);
}
