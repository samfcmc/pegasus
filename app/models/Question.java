package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Question extends Model {

	//private static final long serialVersionUID = 1L;
	
	public Question(String title, String text, int rating, long id) {
		this.title = title;
		this.text = text;
		this.rating = rating;
		this.id = id;
	}

	@Id
	public Long id;
	
	public String title;
	public String text;
	public Integer rating;
	//public dateTime; TODO ver do Tipo para isto.
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Answer> answers;
	
}
