package controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Tag;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.listQuestions;
import views.html.index;
import views.html.searchQuestions;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

public class Search extends Controller {
	public static Result index() {
    	
    	String searchQuery = request().getQueryString("searchString");/*getQueryString("searchString")*/; 
    	//String searchTitle = "Results for \"" + searchQuery + " \":";
    	
    	List<Question> results = Ebean.find(Question.class)
			.where().or(
    					Expr.like("title", "%" + searchQuery + "%"), 
    					Expr.like("text", "%" + searchQuery + "%")
    					)
			.findList();
    	Collections.sort(results, new Question.QuestionComparator());
    	return ok(searchQuestions.render(searchQuery, results));
    }

	public static Result populate() {
		User user = new User("user1", "u1");

		Question question = new Question("pergunta1", "texto da pergunta");
		user.questions.add(question);

		Tag course = new Tag("Maths", "Cadeira de matemática de secundário", "Mathematics.");
		course.questions.add(question);
		course.save();

		Ebean.beginTransaction();
		try {
			user.save();
			question.save();
		} finally {
			Ebean.endTransaction();
		}

		return ok(index.render("Hello world", "Test", null));
	}
}
