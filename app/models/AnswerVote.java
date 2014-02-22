package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AnswerVote extends Vote {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade=CascadeType.ALL)
	public Answer answer;
	
	public static Finder<Long, AnswerVote> find = new Finder<Long, AnswerVote>(
			Long.class, AnswerVote.class);

	public AnswerVote(User user, Answer answer) {
		super(user, 1);
		this.answer = answer;
	}

}
