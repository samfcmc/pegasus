package controllers;

import models.Question;

import com.avaje.ebean.Ebean;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.questionShow;

public class Questions extends Controller {

	@Authenticated(Secured.class)
	public static Result show(long id) {
		Question question = Ebean.find(Question.class, id);
		
		if(question == null) {
			return notFound();
		}
		
		return ok(questionShow.render(question));
	}
}
