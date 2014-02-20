package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Question;
import models.Tag;
import models.User;
import play.api.libs.json.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.listQuestions;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;

@Authenticated(Secured.class)
public class Tags extends Controller {
	public static Result listQuestions(){
		String username = request().username();
		User userLogged = User.findByUsername(username);
		ArrayList<Question> selectedQuestions = new ArrayList<Question>();
		
		for (Tag t : userLogged.favouriteTags){
			selectedQuestions.addAll(t.questions);
		}
		
		return ok(listQuestions.render("Questions from personal tags: ", selectedQuestions));
	}
	
	public static Result getAllTags() {
		List<Tag> tags = Tag.find.all();
		String result = "";
		
		for(Tag tag : tags) {
			result += tag.label + ",";
		}
		
		return ok(result);
	}
}
