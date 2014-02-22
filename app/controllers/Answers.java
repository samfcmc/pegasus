package controllers;

import models.Answer;
import models.Question;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.questionShow;

public class Answers extends Controller {
	
	@Authenticated(Secured.class)
	public static Result create(long id) {
		Form<Answer> form = new Form<Answer>(Answer.class);
		Answer answer = form.bindFromRequest().get();
		
		Question question = Question.find.byId(id);
		
		//Get owner
		String username = request().username();
		User user = User.findByUsername(username);
		
		answer.owner = user;
		answer.question = question;
		answer.save();
		
		return redirect(routes.Questions.show(id));
	}
	
}
