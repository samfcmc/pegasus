package controllers;

import java.util.List;

import models.Question;
import models.User;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	String name = "";
    	String username = session().get("user");
    	List<Question> questions = Question.find.all();
    	
    	String code = request().getQueryString("code");
    	if(code != null && username == null) {
    		User user = User.authenticate(code);
    		name = user.name;
    		session().clear();
    		session("user", user.userName);
    	}
    	
        return ok(index.render("Hello world", name, questions));
    }
    
    public static Result logout() {
    	session().clear();
    	return redirect(routes.Application.index());
    }
    

}

