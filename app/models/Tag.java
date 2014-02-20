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
//	public String fenixId;
//	public String acronym;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public List<Question> questions;
	
	@ManyToMany(cascade=CascadeType.ALL/*, mappedBy = "favouriteTags"*/)
	public List<User> users;
	
	public Tag(String name, String acronym, String fenixId) {
//		this.fenixId = fenixId;
//		this.acronym = acronym;
		this.label = name;
		this.questions = new ArrayList<Question>();
	}

	public void add(Question question) {
		this.questions.add(question);
	}
	
	public static Finder<String, Tag> find = new Finder<String, Tag>(
			String.class,
			Tag.class); 
	
	public static Tag findByFenixId(String fenixId){
		return find.where().eq("fenixId", fenixId).findUnique();
	}
}

