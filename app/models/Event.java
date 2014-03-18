package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@javax.persistence.Entity
public class Event extends Model {

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

    public static Model.Finder<Long,Event> find = new Model.Finder(Long.class, Event.class);
}
