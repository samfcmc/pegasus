package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class QuestionVote extends Vote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade=CascadeType.ALL)
	public Question question;
	
	public static Finder<Long, QuestionVote> find = new Finder<Long, QuestionVote>(
			Long.class, QuestionVote.class);
	
	public QuestionVote(User user, Question question) {
		super(user, 1);
		this.question = question;
	}

}
