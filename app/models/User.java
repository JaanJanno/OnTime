package models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;

import models.game.Tribe;
import play.db.ebean.*;

@javax.persistence.Entity
public class User extends Model {

	private static final long serialVersionUID = -9127957955088115789L;
	
	@Id
    public String email;
    public String name;
    public String organizationName;
    public String password;
    @OneToOne
    public Tribe tribe;
    
    public User(String email, String name, String organizationName, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
      this.organizationName = organizationName;
    }

    public static Finder<String,User> find = new Finder<String,User>(
        String.class, User.class
    ); 
    
    public static User authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", password).findUnique();
    }
}
