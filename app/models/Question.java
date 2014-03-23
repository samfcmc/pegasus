package models;

import java.util.ArrayList;
import java.util.Comparator;
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
	public List<QuestionVote> votes;

	public static Finder<Long, Question> find = new Finder<Long, Question>(
			Long.class, Question.class);
	
	public Question(String title, String text, List<Tag> tags) {
		this(title, text);
		this.tags = tags;
	}

	public Question(String title, String text, List<Tag> tags, User user) {
		this(title, text, tags);
		this.owner = user;
	}

	public Question(String title, String text) {
		this();
		this.title = title;
		this.text = text;
	}
	
	public Question() {
		this.answers = new ArrayList<Answer>();
		this.tags = new ArrayList<Tag>();
		this.votes = new ArrayList<QuestionVote>();
		this.created = DateTime.now();
	}

	public void vote(User user) {
		QuestionVote vote = new QuestionVote(user, this);
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
	
	public static class QuestionComparator implements Comparator{
  		public int compare(Question question1, Question question2) {
			if(question1 == null || question2 == null) {
				return 0;
			}
			if(question1.created.isBefore(question2.created)) {
				return 1;
			}
			if(question1.created.isEqual(question2.created)) {
				return 0;
			}
			else {
				return -1;
			}
		}

		@Override
		public int compare(Object o1, Object o2) throws UnsupportedOperationException {
			
			if (o1 instanceof Question && o2 instanceof Question){
				return compare((Question)o1, (Question)o2);
			}
			else throw new UnsupportedOperationException("QuestionComparator: compare: arguments are not of type Question. ");
		}
	}
	
	public int rating() {
		votes = QuestionVote.find.where().eq("question", this).findList();
		int rating = 0;
		
		for(QuestionVote vote : votes) {
			rating += vote.value;
		}
		
		return rating;
		
	}
}
