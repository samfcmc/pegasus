package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Question;
import models.Tag;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.listQuestions;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;


public class Tags extends Controller {
	
	@Authenticated(Secured.class)
	public static Result listQuestions(){
		String username = request().username();
		User userLogged = User.findByUsername(username);
		ArrayList<Question> selectedQuestions = new ArrayList<Question>();
		
		for (Tag t : userLogged.favouriteTags){
			selectedQuestions.addAll(t.questions);
		}
		
		return ok(listQuestions.render("Questions from personal tags: ", selectedQuestions));

	}
	
	public static Result getTags(){
		List<Tag> tags = Ebean.find(Tag.class).findList();
		String stringTags = "";
		for (Tag t : tags){
			stringTags += t.label + ",";
		}
		return ok(stringTags);
	}
}
