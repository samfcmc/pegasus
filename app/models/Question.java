package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Question extends Model {

	private static final long serialVersionUID = 1L;

	@Id //o ebeans trata, assim, de gerar e gerir IDs automaticamente.
	public Long id;
	
	public String title;
	public String text;
	public Integer rating;
	//public dateTime; TODO ver do Tipo para isto.
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Answer> answers;
	
	public Question(String title, String text) {
		this.title = title;
		this.text = text;
		this.rating = 0;
		//o ID é gerado e actualizado automagicamente pelo Ebeans.
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
	 public boolean equals(Object obj) {
	        if (obj == this) {
	            return true;
	        }
	        if (!(obj instanceof Question)) {
	            return false;
	        }
	        Question other = (Question) obj;
	        return this.id.equals(other.getId());
	    }
	 
	 public int hashCode() {
	        return this.id.hashCode();
	    }
}
