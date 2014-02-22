package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class TagRequest extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public Long id;

	public String tagLabel;
	public String tagDescription;
	
	@ManyToOne()
	public User requester;
	
	public static Finder<Long, TagRequest> find = new Finder<Long, TagRequest>(
			Long.class, TagRequest.class);
	
	
	
	public TagRequest(String tagLabel, String tagDescription) {
		super();
		this.tagLabel = tagLabel;
		this.tagDescription = tagDescription;
	}



	public TagRequest(String tagLabel, String tagDescription, User requester) {
		this(tagLabel, tagDescription);
		this.requester = requester;
	}
	
}
