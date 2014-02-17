package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Course extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name = "";
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Question> questions = null;
	
	public Course(String name){
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void add(Question question) {
		this.questions.add(question);
	}
}

