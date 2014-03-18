package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@javax.persistence.Entity
public class EventQuery extends Model {

    public String title;
    public String date;
    public String korraldajaEmail;
    
    public EventQuery(String title, String date, String korraldajaEmail) {
      this.title = title;
      this.date = date;
      this.korraldajaEmail = korraldajaEmail;
    }

    public static Model.Finder<Long,EventQuery> find = new Model.Finder(Long.class, EventQuery.class);
}
