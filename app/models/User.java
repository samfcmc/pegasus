package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

import play.db.ebean.Model;
import pt.ist.fenixedu.sdk.FenixEduClient;
import pt.ist.fenixedu.sdk.FenixEduUserConfig;
import pt.ist.fenixedu.sdk.beans.FenixPerson;
import pt.ist.fenixedu.sdk.beans.FenixPersonCourses;
import pt.ist.fenixedu.sdk.beans.FenixPersonCourses.FenixCourse;
import pt.ist.fenixedu.sdk.beans.FenixPersonCourses.FenixEnrolment;
import views.html.listQuestions;
import fenixedu.FenixEduClientQAFactory;

@Entity
public class User extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String name;
	public String userName;

	public String accessToken;
	public String refreshToken;

	@OneToMany(cascade=CascadeType.ALL)
	public List<Question> questions;

	@OneToMany(cascade=CascadeType.ALL)
	public List<Answer> answers;

	@ManyToMany(cascade=CascadeType.ALL)
	public List<Tag> favouriteTags;
	
	public static Finder<Long, User> find = new Finder<Long, User>(
			Long.class, User.class);

	public User(String name, String userName, String accessToken,
			String refreshToken) {
		super();
		this.name = name;
		this.userName = userName;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.questions = new ArrayList<Question>();
		this.answers = new ArrayList<Answer>();
		this.favouriteTags = new ArrayList<Tag>();
	}

	public FenixEduUserConfig getFenixEduUserConfig() {
		FenixEduUserConfig config = new FenixEduUserConfig(userName,
				accessToken, refreshToken);
		return config;
	}

	public static User authenticate(String code) {
		FenixEduClient client = FenixEduClientQAFactory.getSingleton();
		FenixEduUserConfig userConfig = new FenixEduUserConfig();

		client.setCode(code, userConfig);

		FenixPerson person = client.getPerson(userConfig);

		User user = findByUsername(person.getUsername());

		if (user == null) {
			user = new User(person.getName(), person.getUsername(),
					userConfig.getAccessToken(), userConfig.getRefreshToken());
		} else {
			user.accessToken = userConfig.getAccessToken();
			user.refreshToken = userConfig.getRefreshToken();
		}
		setUserCoursesFromAPI(user);
		
		user.save();

		return user;
	}

	private static void setUserCoursesFromAPI(User user){
		FenixEduClient client = FenixEduClientQAFactory.getSingleton();
		FenixEduUserConfig userConfig = user.getFenixEduUserConfig();
		
		FenixPersonCourses courses = client.getPersonCourses("2013/2014", userConfig); //FIXME academicTerm
		
		for (FenixEnrolment c : courses.getEnrolments()){
				String acronymFenix = c.getAcronym();
//				acronymFenix = acronymFenix.replaceAll("[0-9]+", "");
				
				String nameFenix = c.getName();
				String descriptionFenix = "Subject at IST. \n" + "Acronym: "+ c.getAcronym() +"\n"+
						"ID: "+ c.getId() +"\n"+ "Name in fénix: "+ c.getName() + "\n"+
						"URL of subject: " + c.getUrl();
				
		
				
				List<Tag> listTagUnderTest = Tag.find.where(Expr.eq("label", acronymFenix)).findList();
				
				Tag newSubject = null;
				//when at least one tag already exists with that label
				if (listTagUnderTest.size() != 0){
					for (Tag t : listTagUnderTest){
//						t.label = t.label + "{" + t.nameFenix + "}";
//						newSubject = new Tag(acronymFenix+ "{" + t.nameFenix + "}", descriptionFenix, nameFenix);
//						t.save();
//						newSubject.save();
					}
				}else{
					newSubject = new Tag(acronymFenix, descriptionFenix, nameFenix);
				}
				
			
				user.favouriteTags.add(newSubject);
		}
		
		for (FenixCourse c : courses.getTeaching()){
			if (Tag.findByFenixId(c.getId()) == null){
				Tag course = new Tag(c.getName(), c.getAcronym(), c.getId());
				user.favouriteTags.add(course);
			}
		}
	}

	public static User findByUsername(String username) {
		User user = find.where().eq("userName", username).findUnique();

		return user;
	}

	public User(String name, String userName) {
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

	public List<Tag> getCourses() {
		return favouriteTags;
	}

	public void setCourses(List<Tag> courses) {
		this.favouriteTags = courses;
	}
}
