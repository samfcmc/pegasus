package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Tag;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.index;

import com.avaje.ebean.Ebean;

public class Populate extends Controller {

	@Authenticated(Secured.class)
	public static Result index() {
		User user = new User("user1", "u1");

		Question question = new Question("pergunta2", "texto da pergunta");
		List<Question> questions = Question.find.all();
		// user.questions.add(question);
		question.owner = user;
		Tag course = new Tag("Maths", "MM", "m1");
		course.add(question);
		course.save();
		user.save();
		question.save();
		//return ok(index.render("Success", "", questions));
		
		
		String username = request().username();
		User userLogged = User.findByUsername(username);
		
		if (!(userLogged == null)){
			userLogged.favouriteTags = new ArrayList<Tag>();
			userLogged.favouriteTags.add(course);
			userLogged.save();
		}
		
		//return ok("olá");
		return Tags.listQuestions();
	}
}
