package models;

import java.util.Collections;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

@javax.persistence.Entity
public class ChatEvent extends Model{
	
	private static final long serialVersionUID = -7787819843817365549L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
	public String msg;
	
	public ChatEvent(String msg){
		this.msg = msg;
	}
	
	public static Model.Finder<Long,ChatEvent> find = new Model.Finder<Long, ChatEvent>(Long.class, ChatEvent.class);
	
	public static List<ChatEvent> findChatEvents(){
		List<ChatEvent> chatLog = Ebean.find(ChatEvent.class).orderBy("ID DESC").setMaxRows(5).findList();
		Collections.reverse(chatLog);
		return chatLog;
	}
}
