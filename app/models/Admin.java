package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Admin extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;
	
	@OneToOne()
	public User user;

	public Admin(User user) {
		super();
		this.user = user;
	}
	
	
		
	
}
