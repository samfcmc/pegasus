package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Vote extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade=CascadeType.ALL)
	public User user;
	
	@ManyToOne(cascade=CascadeType.ALL)
	public Question question;
	
	public int value;
	
	public Vote(User user, Question question) {
		this.user = user;
		this.question = question;
		
		//TODO: Different users will have diferent values
		this.value = 1;
	}

}
