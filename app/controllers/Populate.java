package controllers;

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
		// user.questions.add(question);
		question.owner = user;
		Tag course = new Tag("Maths", "MM", "m1");
		course.add(question);
		course.save();

		user.save();
		question.save();
		return ok(index.render("Success", ""));

	}
}
