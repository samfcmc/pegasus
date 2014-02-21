package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Question extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String title;
	public String text;
	public DateTime created;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Answer> answers;

	@ManyToMany(cascade = CascadeType.ALL)
	public List<Tag> tags;

	@ManyToOne(cascade = CascadeType.ALL)
	public User owner;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Vote> votes;

	public static Finder<String, Question> find = new Finder<String, Question>(
			String.class, Question.class);
	
	public Question(String title, String text, List<Tag> tags) {
		this(title, text);
		this.tags = tags;
	}

	public Question(String title, String text, List<Tag> tags, User user) {
		this(title, text, tags);
		this.owner = user;
	}

	public Question(String title, String text) {
		this.title = title;
		this.text = text;
		this.answers = new ArrayList<Answer>();
		this.tags = new ArrayList<Tag>();
		this.votes = new ArrayList<Vote>();
		this.created = DateTime.now();
	}

	public void vote(User user) {
		Vote vote = new Vote(user, this);
		vote.save();
		votes.add(vote);
	}

	public String createdAsString() {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		String str = fmt.print(this.created);
		
		return str;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Question)) {
			return false;
		}
		Question other = (Question) obj;
		return this.id.equals(other.id);
	}

	public int hashCode() {
		return this.id.hashCode();
	}
}
