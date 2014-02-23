package controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;

import models.Question;
import models.User;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	String name = "";
    	String username = session().get("user");
    	List<Question> questions = Question.find.all();
    	
    	//Order by date and time
    	Collections.sort(questions, new Comparator<Question>() {
    		public int compare(Question question1, Question question2) {
    			if(question1 == null || question2 == null) {
    				return 0;
    			}
    			if(question1.created.isBefore(question2.created)) {
    				return 1;
    			}
    			if(question1.created.isEqual(question2.created)) {
    				return 0;
    			}
    			else {
    				return -1;
    			}
    		}
    	});
    	
    	String code = request().getQueryString("code");
    	if(code != null && username == null) {
    		User user = User.authenticate(code);
    		name = user.name;
    		session().clear();
    		session("user", user.userName);
    	}
    	
    	for(Question question : questions) {
    		User owner = User.find.byId(question.owner.id);
    		question.owner = owner;
    	}
    	
        return ok(index.render("Hello world", name, questions));
    }
    
    public static Result logout() {
    	session().clear();
    	return redirect(routes.Application.index());
    }
    

}

