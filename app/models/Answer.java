package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@OneToMany(cascade = CascadeType.ALL)
	public List<AnswerVote> votes;
	
	public static Finder<Long, Answer> find = new Finder<Long, Answer>(
			Long.class, Answer.class);
	
	public Answer(Long id, String text, Integer rating){
		this.id = id;
		this.text = text;
		this.created = DateTime.now();
		this.votes = new ArrayList<AnswerVote>();
	}
	
	public String createdAsString() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		String str = fmt.print(this.created);
		
		return str;
	}
	
	public int rating() {
		votes = AnswerVote.find.where().eq("answer", this).findList();
		int rating = 0;
		
		for(AnswerVote vote : votes) {
			rating += vote.value;
		}
		
		return rating;
		
	}
	
}
