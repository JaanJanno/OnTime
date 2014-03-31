package models.game.events;

import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

@javax.persistence.Entity
public class GameEventQuery {
	
	public String msg;
	public long count;
	
	public GameEventQuery(String msg, long count){
		this.msg = msg;
		this.count = count;
	}
	
	public static String msgSwitch(String type){
		switch (type) {
		case "Berries":	
			return "Total berries found: ";
		case "Travellers":	
			return "Total times travellers found: ";
		case "Candies":	
			return "Total candies found: ";
		default:
			return null;
		}
	}
	
	public static List<GameEventQuery> createFromSqlRows(List<SqlRow> rows){
    	
    	List<GameEventQuery> list = new ArrayList<GameEventQuery>();
    	for(SqlRow row: rows){
    		GameEventQuery uus = new GameEventQuery(msgSwitch((String)row.get("type")), (Long)row.get("count(1)"));
    		list.add(uus);
    	}
    	return list;
    }
	
	public static List<GameEventQuery> getEventsStatistics(){
    	
    	SqlQuery q = Ebean.createSqlQuery("SELECT type, count(1) FROM special_event JOIN tribe ON special_event.tribe_id = tribe.id GROUP BY type;");
    	List<SqlRow> list = q.findList();
    	return createFromSqlRows(list);
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return msg + Long.toString(count);
	}
}
