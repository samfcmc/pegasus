package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Tag extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String label = "";
	public String description = "";
	
	public String nameFenix ="";
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Question> questions;
	
	@ManyToMany(cascade=CascadeType.ALL/*, mappedBy = "favouriteTags"*/)
	public List<User> users;
	
//	public Tag(String label, String description) {
//		this.label = label;
//		this.description = description;
//		this.questions = new ArrayList<Question>();
//		this.users = new ArrayList<User>();
//	}

	public Tag(String labelAcronym, String description, String nameFenix) {
		this.label = labelAcronym;
		this.description = description;
		this.questions = new ArrayList<Question>();
		this.users = new ArrayList<User>();
		this.nameFenix = nameFenix;
	}
	public void add(Question question) {
		this.questions.add(question);
	}
	
	public static Finder<String, Tag> find = new Finder<String, Tag>(
			String.class,
			Tag.class);

	public static Object findByFenixId(String id) {
		return null;
	} 
}

