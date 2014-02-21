package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Question;
import models.Tag;
import models.User;
import models.Vote;

import com.avaje.ebean.Ebean;

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
		
		int rating = rating(question);
		
		if (question == null) {
			return notFound();
		}
		
		//To make owner username available in view
		User owner = Ebean.find(User.class, question.owner.id);
		question.owner = owner;
		
		boolean canVote = canVote(question, user);

		return ok(questionShow.render(question, rating, canVote));
	}
	
	private static int rating(Question question) {
		List<Vote> votes = Ebean.find(Vote.class).where().eq("question", question).findList();
		int result = 0;
		
		for(Vote vote : votes) {
			result += vote.value;
		}
		
		return result;
	}
	
	private static boolean canVote(Question question, User user) {
		Vote vote = Ebean.find(Vote.class).where().eq("user", user)
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
			return ok(questionShow.render(question, rating, false));
		}
		
		return forbidden();

	}

	public static Result ask() {
		return ok(questionAsk.render());
	}

	@Authenticated(Secured.class)
	public static Result create() {
		String username = request().username();
		User user = User.findByUsername(username);

		final Map<String, String[]> values = request().body()
				.asFormUrlEncoded();

		try {

			String title = values.get("title")[0];
			String text = values.get("text")[0];
			String[] tagsText = values.get("tags")[0].replaceAll("\\s+", "")
					.split(",");

			if (tagsText.length == 0
					|| (tagsText.length == 1 && tagsText[0].equals(""))) {
				return ok(index.render("Error: No tag sent", "", null));
			}

			// Check if given tags exist
			List<Tag> tags = new ArrayList<Tag>();
			for (String tag : tagsText) {
				List<Tag> results = Ebean.find(Tag.class).where()
						.eq("label", tag).findList();
				if (results.size() == 0) {
					// TODO bad request - tag doesn't exist
					return ok(index.render("Error: Tag \"" + tag
							+ "\" doesn't exist", "", null));
				} else {
					tags.add(results.get(0));
				}
			}

			Question question = new Question(title, text, tags, user);
			question.save();

			return redirect(controllers.routes.Questions.show(question.id));
		} catch (NullPointerException e) {
			return ok(index.render(
					"Error: NullPointerException - POST paramenters missing",
					"", null));
		}
	}
	
	@Authenticated(Secured.class)
	public static Result edit(long id) {
		String username = request().username();
		User user = User.findByUsername(username);
		
		Question question = Ebean.find(Question.class, id);
		
		if (question == null) {
			return notFound();
		} else if (question.owner.id != user.id) {
			return ok(index.render("Error: You do not own this question!", "", null));
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
			return ok(index.render("Error: You do not own this question!", "", null));
		}
		
		final Map<String, String[]> values = request().body().asFormUrlEncoded();
		
		String[] postTitle = values.get("title");
		String[] postText = values.get("text");
		String[] postTagsText = values.get("tags");
		
		if (postTitle == null || postText == null || postTagsText == null) {
			// FIXME show error page
			return ok("Could not get all needed parameters", "");
		}
		
		String title = postTitle[0];
		String text = postText[0];
		String[] tagsText = postTagsText[0].trim().replaceAll("\\s+", " ").split(" ");
		
		if (tagsText.length == 0 || (tagsText.length == 1 && tagsText[0].equals(""))) {
			return ok(index.render("Error: No tag sent", "", null));
		}
		
		// Check if given tags exist
		List<Tag> tags = new ArrayList<Tag>();
		for (String tag : tagsText) {
			List<Tag> results = Ebean.find(Tag.class).where().eq("label", tag).findList();
			if (results.size() == 0) {
				// TODO bad request - tag doesn't exist
				return ok(index.render("Error: Tag \"" + tag + "\" doesn't exist", "", null));
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
