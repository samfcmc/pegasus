package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Question;
import models.Tag;
import models.User;

import com.avaje.ebean.Ebean;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.*;

public class Questions extends Controller {

	@Authenticated(Secured.class)
	public static Result show(long id) {
		Question question = Ebean.find(Question.class, id);
		
		if(question == null) {
			return notFound();
		}
		//return ok(Json.toJson(question));
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
		
		try {
			
			String title = values.get("title")[0];
			String text = values.get("text")[0];
			String[] tagsText = values.get("tags")[0]
					.replaceAll("\\s+", " ")
					.split(" ");
			
			if (tagsText.length == 0 || (tagsText.length == 1 && tagsText[0].equals(""))) {
				return ok(index.render("Error: No tag sent", ""));
			}
			
			// Check if given tags exist
			List<Tag> tags = new ArrayList<Tag>();
			for (String tag : tagsText) {
				List<Tag> results = Ebean.find(Tag.class)
						.where()
						.eq("label", tag)
						.findList();
				if (results.size() == 0) {
					// TODO bad request - tag doesn't exist
					return ok(index.render("Error: Tag \"" + tag + "\" doesn't exist", ""));
				} else {
					tags.add(results.get(0));
				}
			}
			
			Question question = new Question(title, text, tags, user);
			question.save();
			
			return redirect(controllers.routes.Questions.show(question.id));
		} catch(NullPointerException e) {
			return ok(index.render("Error: NullPointerException - POST paramenters missing", ""));
		}
	}
}
