package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class User extends Model {

	private static final long serialVersionUID = 1L;

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
		questions = new LinkedList<Question>();
	};
	

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	
	
	
	
	
	
}
