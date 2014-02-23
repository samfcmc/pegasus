package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import models.Answer;
import models.Question;
import models.Tag;
import models.User;
import models.QuestionVote;

import com.avaje.ebean.Ebean;

import play.data.Form;
import play.data.format.Formatters;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.*;

public class Questions extends Controller {

	@Authenticated(Secured.class)
	public static Result show(long id) {
		Question question = Ebean.find(Question.class, id);
		String username = request().username();
		User user = User.findByUsername(username);
		Form<Answer> form = new Form<Answer>(Answer.class);

		int rating = rating(question);

		if (question == null) {	
			return notFound();
		}
		
		Answer topAnswer = null;
		int topRating = 0;
		//Get answers owners and Top answer
		for(Answer answer: question.answers) {
			User owner = User.find.byId(answer.owner.id);
			answer.owner = owner;
			int answerRating = answer.rating();
			
			if(answerRating > topRating) {
				topAnswer = answer;
				topRating = answerRating;
			}
		}
		
		// To make owner username available in view
		User owner = Ebean.find(User.class, question.owner.id);
		question.owner = owner;
		
		

		boolean canVote = canVote(question, user);

		return ok(questionShow.render(question, rating, canVote, form, topAnswer, topRating));
	}
	
	public static int rating(Question question) {
		List<QuestionVote> votes = Ebean.find(QuestionVote.class).where()
				.eq("question", question).findList();
		int result = 0;

		for (QuestionVote vote : votes) {
			result += vote.value;
		}

		return result;
	}

	private static boolean canVote(Question question, User user) {
		QuestionVote vote = Ebean.find(QuestionVote.class).where().eq("user", user)
				.eq("question", question).findUnique();

		return vote == null;
	}

	@Authenticated(Secured.class)
	public static Result rate(long id) {
		Question question = Ebean.find(Question.class, id);
		
		if (question == null) {
			return notFound();
		}

		String username = request().username();
		User user = User.findByUsername(username);

		boolean canVote = canVote(question, user);

		if (canVote) {
			question.vote(user);
			question.save();
			int rating = rating(question);
			return redirect(routes.Questions.show(id));
		}

		return forbidden();

	}

	public static Result ask() {
		return ok(questionAsk.render("","","",""));
	}

	
	
	@Authenticated(Secured.class)
	public static Result create() {
		// Form<Question> newQuestionForm = new Form<Question>(Question.class);
		// Question newQuestion = newQuestionForm.bindFromRequest().get();

		String username = request().username();
		User user = User.findByUsername(username);

		final Map<String, String[]> values = request().body()
				.asFormUrlEncoded();

			String titleForm= "", textForm = "", tagsForm = "";
			
			if (values.get("title") != null){
				titleForm = values.get("title")[0];
				if (titleForm.isEmpty()){
					return ok(questionAsk.render("Error: Title can't be empty", titleForm, textForm, tagsForm));	
				}
			}else{
				return ok(questionAsk.render("Error: Title can't be null", titleForm, textForm, tagsForm));
				
			}
			
			if (values.get("text") != null){
				textForm = values.get("text")[0];
				if (textForm.isEmpty()){
					return ok(questionAsk.render("Error: Text can't be empty", titleForm, textForm, tagsForm));	
				}
			}else{
				return ok(questionAsk.render("Error: Text can't be null", titleForm, textForm, tagsForm));	
			}
			
			String[] tagsText;
			if (values.get("tags") != null){
				tagsText = values.get("tags")[0].replaceAll("\\s+", "")
						.split(",");
				if (tagsText.length == 0 || (tagsText.length == 1 && tagsText[0].equals(""))) {
					return ok(questionAsk.render("Error: Tags can't be empty", titleForm, textForm, tagsForm));	
				}else{
					// Check if given tags exist
					List<Tag> tags = new ArrayList<Tag>();
					for (String tag : tagsText) {
						Tag tagSearched = Ebean.find(Tag.class).where()
								.eq("label", tag).findUnique();
						if (tagSearched == null) {
							// TODO bad request - tag doesn't exist
							return ok(questionAsk.render("Error: Tag \"" + tag
									+ "\" doesn't exist. Try to request that as a new tag.", titleForm, textForm, tagsForm ));
						} else {
							tags.add(tagSearched);
						}
					}
					Question newQuestion = new Question(titleForm, textForm, tags, user);
					newQuestion.save();
					return redirect(controllers.routes.Questions.show(newQuestion.id));
				}
			}
			return ok(questionAsk.render("Error: Tags can't be null", titleForm, textForm, tagsForm ));
	}

	@Authenticated(Secured.class)
	public static Result edit(long id) {
		String username = request().username();
		User user = User.findByUsername(username);

		Question question = Ebean.find(Question.class, id);

		if (question == null) {
			return notFound();
		} else if (question.owner.id != user.id) {
			return ok(index.render("Error: You do not own this question!", "",
					null));
		}

		return ok(questionEdit.render(question));
	}

	@Authenticated(Secured.class)
	public static Result doEdit(long id) {
		String username = request().username();
		User user = User.findByUsername(username);

		Question question = Ebean.find(Question.class, id);

		if (question == null) {
			return notFound();
		} else if (question.owner.id != user.id) {
			return ok(index.render("Error: You do not own this question!", "",
					null));
		}

		final Map<String, String[]> values = request().body()
				.asFormUrlEncoded();

		String[] postTitle = values.get("title");
		String[] postText = values.get("text");
		String[] postTagsText = values.get("tags");

		if (postTitle == null || postText == null || postTagsText == null) {
			// FIXME show error page
			return ok("Could not get all needed parameters", "");
		}

		String title = postTitle[0];
		String text = postText[0];
		String[] tagsText = postTagsText[0].trim().replaceAll("\\s+", " ")
				.split(" ");

		if (tagsText.length == 0
				|| (tagsText.length == 1 && tagsText[0].equals(""))) {
			return ok(index.render("Error: No tag sent", "", null));
		}

		// Check if given tags exist
		List<Tag> tags = new ArrayList<Tag>();
		for (String tag : tagsText) {
			List<Tag> results = Ebean.find(Tag.class).where().eq("label", tag)
					.findList();
			if (results.size() == 0) {
				// TODO bad request - tag doesn't exist
				return ok(index.render("Error: Tag \"" + tag
						+ "\" doesn't exist", "", null));
			} else {
				tags.add(results.get(0));
			}
		}

		question.title = title;
		question.text = text;
		question.tags = tags;

		question.save();

		return redirect(controllers.routes.Questions.show(question.id));
	}

}
