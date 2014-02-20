package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Answer extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String text;
	public int rating;
	//TODO datetime
	
	@OneToOne(cascade=CascadeType.ALL) //TODO check if dangerous with "cascade deletes"
	public User owner;

	@ManyToOne(cascade=CascadeType.ALL) //TODO check if dangerous with "cascade deletes"
	public Question question;
	
	public Answer(Long id, String text, Integer rating){
		this.id = id;
		this.text = text;
		this.rating = rating;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
