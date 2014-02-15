package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Course extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;
	
	@OneToMany
	public List<Question> questions;
}

