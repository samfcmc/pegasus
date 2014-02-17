package controllers;

import models.Course;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.avaje.ebean.Ebean;

public class Populate extends Controller {
    
    public static Result index() {
    	User user = new User("user1", "u1");
    	
    	Question question = new Question("pergunta2","texto da pergunta");
    	user.questions.add(question);
    	
    	Course course = new Course("Maths");
    	course.add(question);
    	course.save();
    	
    	Ebean.beginTransaction();
    	try
    	{
    		user.save();
    		question.save();
    	}
    	finally{
    		Ebean.endTransaction();
    	}
    	
        return ok(index.render("", ""));
    }
}
