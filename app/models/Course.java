package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Course extends Model {

	//private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Question> questions;
	
	public Course(String name){
		this.name = name;
	}

}

