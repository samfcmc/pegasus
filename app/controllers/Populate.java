package controllers;

import models.Question;
import models.Tag;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Populate extends Controller {

	public static Result index() {
		User user = new User("user1", "u1");

		Question question = new Question("pergunta2", "texto da pergunta");
		// user.questions.add(question);
		question.owner = user;
		Tag course = new Tag("Maths", "MM", "m1");
		Tag course2 = new Tag("PADI", "PD", "padi");
		course.add(question);
		course2.add(question);
		course.save();
		course2.save();
		
		user.save();
		question.save();
		return ok(index.render("Success", ""));
	}
}
