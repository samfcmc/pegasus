package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Question;
import models.Tag;
import models.User;

import com.avaje.ebean.Ebean;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.*;

public class Questions extends Controller {

	@Authenticated(Secured.class)
	public static Result show(long id) {
		Question question = Ebean.find(Question.class, id);

		if (question == null) {
			return notFound();
		}

		return ok(questionShow.render(question));
	}

	@Authenticated(Secured.class)
	public static Result rate(long id) {
		Question question = Ebean.find(Question.class, id);

		if (question == null) {
			return notFound();
		}

		String username = request().username();
		User user = User.findByUsername(username);

		

		question.vote(user);
		question.save();

		return ok(questionShow.render(question));
	}
	
	public static Result ask() {
        return ok(questionAsk.render());
    }
	
	@Authenticated(Secured.class)
	public static Result create() {
		String username = request().username();
		User user = User.findByUsername(username);
		
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
			return ok(index.render("Error: No tag sent", ""));
		}
		
		// Check if given tags exist
		List<Tag> tags = new ArrayList<Tag>();
		for (String tag : tagsText) {
			List<Tag> results = Ebean.find(Tag.class).where().eq("label", tag).findList();
			if (results.size() == 0) {
				// TODO bad request - tag doesn't exist
				return ok(index.render("Error: Tag \"" + tag + "\" doesn't exist", ""));
			} else { tags.add(results.get(0)); }
		}
		
		Question question = new Question(title, text, tags, user);
		question.save();
		
		return redirect(controllers.routes.Questions.show(question.id));
	}
	
	@Authenticated(Secured.class)
	public static Result edit(long id) {
		String username = request().username();
		User user = User.findByUsername(username);
		
		Question question = Ebean.find(Question.class, id);
		
		if (question == null) {
			return notFound();
		} else if (question.owner.id != user.id) {
			return ok(index.render("Error: You do not own this question!", ""));
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
			return ok(index.render("Error: You do not own this question!", ""));
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
			return ok(index.render("Error: No tag sent", ""));
		}
		
		// Check if given tags exist
		List<Tag> tags = new ArrayList<Tag>();
		for (String tag : tagsText) {
			List<Tag> results = Ebean.find(Tag.class).where().eq("label", tag).findList();
			if (results.size() == 0) {
				// TODO bad request - tag doesn't exist
				return ok(index.render("Error: Tag \"" + tag + "\" doesn't exist", ""));
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