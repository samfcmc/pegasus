package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class User extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;
	public String userName;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<Question> questions;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<Answer> answers;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Course> courses;
	
	
	
	
	
	
}
