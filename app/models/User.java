package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class User extends Model {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;
	public String userName;
	
	//@OneToMany(cascade=CascadeType.PERSIST)
	public List<Question> questions;
	
	//@OneToMany(cascade=CascadeType.PERSIST)
	public List<Answer> answers;
	
	//@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Course> courses;
	
	public User(String name, String userName){
		this.name = name;
		this.userName = userName;
		
		questions = new LinkedList<Question>();//
		
	};
	
	
	
	
	
	
}
