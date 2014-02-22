package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Vote extends Model {
	
	@ManyToOne(cascade=CascadeType.ALL)
	public User user;
	
	public int value;

	public Vote(User user, int value) {
		super();
		this.user = user;
		this.value = value;
	}

}
