package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Tag;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.avaje.ebean.Ebean;

public class Populate extends Controller {

	public static Result index() {
		User user = new User("user1", "u1");

		Question question = new Question("pergunta2", "texto da pergunta");
		List<Question> questions = Question.find.all();
		// user.questions.add(question);
		question.owner = user;
		Tag course = new Tag("Maths", "Cadeira de matemática de secundário", "Mathematics.");
		course.add(question);
		course.save();

		user.save();
		question.save();
		return ok(index.render("Success", "", questions));

	}
}
