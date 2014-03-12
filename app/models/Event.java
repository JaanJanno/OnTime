package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Event extends Model {

    @Id
    public Long id;
    public String title;
    public String date;
    
    public Event(String title, String date) {
      this.title = title;
      this.date = date;
    }


    public static Model.Finder<Long,Event> find = new Model.Finder(Long.class, Event.class);
}
