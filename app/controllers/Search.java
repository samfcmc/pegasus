package controllers;

import java.util.List;

import models.Course;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.listQuestions;
import views.html.index;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

public class Search extends Controller {
	public static Result index() {
    	
    	String searchQuery = request().body().asFormUrlEncoded().get("searchString")[0];/*getQueryString("searchString")*/; 
    	String searchTitle = "Results for \"" + searchQuery + " \":";
    	
    	List<Question> results = Ebean.find(Question.class)
			.where().or(
    					Expr.like("title", "%" + searchQuery + "%"), 
    					Expr.like("text", "%" + searchQuery + "%")
    					)
			.findList();
    	
    	
    	return ok(listQuestions.render(searchQuery, results));
    	
//    	Logger.debug(string);
    }

	public static Result populate() {
		User user = new User("user1", "u1");
		// user.save();

		Question question = new Question("pergunta1", "texto da pergunta");
		user.questions.add(question);

		Course course = new Course("Maths");
		course.questions.add(question);
		course.save();

		Ebean.beginTransaction();
		try {
			user.save();
			question.save();
		} finally {
			Ebean.endTransaction();
		}

		return ok(index.render("Hello world", "Test"));
	}
}