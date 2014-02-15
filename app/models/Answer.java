package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Answer extends Model {

	
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String text;
	public Integer rating;
	//TODO datetime
}
