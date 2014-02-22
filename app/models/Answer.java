package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import play.db.ebean.Model;

@Entity
public class Answer extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String text;
	
	public DateTime created;
	
	@OneToOne(cascade=CascadeType.ALL) 
	public User owner;

	@ManyToOne(cascade=CascadeType.ALL)
	public Question question;
	
	public Answer(Long id, String text, Integer rating){
		this.id = id;
		this.text = text;
		this.created = DateTime.now();
	}
	
	public String createdAsString() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		String str = fmt.print(this.created);
		
		return str;
	}
}
