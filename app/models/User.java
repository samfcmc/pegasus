package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import fenixedu.FenixEduClientQAFactory;
import play.db.ebean.Model;
import pt.ist.fenixedu.sdk.FenixEduClient;
import pt.ist.fenixedu.sdk.FenixEduUserConfig;
import pt.ist.fenixedu.sdk.beans.FenixPerson;

@Entity
public class User extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	public String name;
	public String userName;
	
	public String accessToken;
	public String refreshToken;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<Question> questions;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	public List<Answer> answers;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Course> courses;
	
	public static Finder<String,User> find = new Finder<String,User>(
	        String.class, User.class); 
	
	
	
	public User(String name, String userName, String accessToken,
			String refreshToken) {
		super();
		this.name = name;
		this.userName = userName;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.questions = new ArrayList<Question>();
		this.answers = new ArrayList<Answer>();
		this.courses = new ArrayList<Course>();
	}

	public FenixEduUserConfig getFenixEduUserConfig() {
		FenixEduUserConfig config = new FenixEduUserConfig(userName, accessToken, refreshToken);
		return config;
	}
	
	public static User authenticate(String code) {
		FenixEduClient client = FenixEduClientQAFactory.getSingleton();
		FenixEduUserConfig userConfig = new FenixEduUserConfig();
		
		client.setCode(code, userConfig);
		
		FenixPerson person = client.getPerson(userConfig);
		
		User user = findByUsername(person.getUsername());
		
		if(user == null) {
			user = new User(person.getName(), person.getUsername(), userConfig.getAccessToken(), userConfig.getRefreshToken());
		}
		else {
			user.accessToken = userConfig.getAccessToken();
			user.accessToken = userConfig.getRefreshToken();
		}
		
		user.save();
		
		return user;		
	}
	
	public static User findByUsername(String username) {
		User user = find.where().eq("userName", username).findUnique();
		
		return user;
	}
	
	
	
	
	
	
}
