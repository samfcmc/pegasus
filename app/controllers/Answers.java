package controllers;

import com.avaje.ebean.Ebean;

import models.Answer;
import models.AnswerVote;
import models.Question;
import models.User;
import models.QuestionVote;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.questionShow;
import views.html.helper.form;

public class Answers extends Controller {
	
	@Authenticated(Secured.class)
	public static Result create(long id) {
		Form<Answer> answerForm = new Form<Answer>(Answer.class);
		Answer answer = answerForm.bindFromRequest().get();
		
		if(answerForm.hasErrors()) {
			return redirect(routes.Questions.show(id));
		}
		
		Question question = Question.find.byId(id);
		
		//Get owner
		String username = request().username();
		User user = User.findByUsername(username);
		
		answer.owner = user;
		answer.question = question;
		answer.save();
		
		return redirect(routes.Questions.show(id));
	}
	
	@Authenticated(Secured.class)
	public static Result rate(long id) {
		Answer answer = Answer.find.byId(id);
		
		String username = request().username();
		User user = User.findByUsername(username);
		
		AnswerVote vote = AnswerVote.find.where().eq("user", user)
				.eq("answer", answer).findUnique();
		
		if(vote == null) {
			// User can vote
			Question question = Question.find.byId(answer.question.id);
			vote = new AnswerVote(user, answer);
			vote.save();
			answer.votes.add(vote);
			answer.save();
			
			return redirect(routes.Questions.show(question.id));
		}
		
		else {
			return forbidden();
		}

				
	}
	
}
