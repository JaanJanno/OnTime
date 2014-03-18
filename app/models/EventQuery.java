package models;

import java.util.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import play.db.ebean.*;

@javax.persistence.Entity
public class EventQuery extends Model {

    public String title;
    public String date;
    public String korraldaja;
    
    public EventQuery(String title, String date, String korraldaja) {
      this.title = title;
      this.date = date;
      this.korraldaja = korraldaja;
    }
    
    public static List<EventQuery> createFromSqlRows(List<SqlRow> rows){
    	
    	List<EventQuery> list = new ArrayList<EventQuery>();
    	for(SqlRow row: rows){
    		EventQuery uus = new EventQuery((String)row.get("title"), (String)row.get("date"), (String)row.get("organization_name"));
    		list.add(uus);
    	}
    	return list;
    }
    
    public static List<EventQuery> getTitleDateOrganization(){
    	
    	SqlQuery q = Ebean.createSqlQuery("SELECT title, date, organization_name FROM event JOIN user ON event.user_email = user.email;");
    	List<SqlRow> list = q.findList();
    	return createFromSqlRows(list);
    }

    public static Model.Finder<Long,EventQuery> find = new Model.Finder(Long.class, EventQuery.class);
}
