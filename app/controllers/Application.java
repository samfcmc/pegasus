package controllers;

import com.avaje.ebean.Ebean;

import models.Course;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Hello world", "Test"));
    }
    
    public static Result lol() {
    	User user = new User("user1", "u1");
    	//user.save();
    	
    	
    	Question question = new Question("pergunta1","texto da pergunta",2,2);
    	user.questions.add(question);
    	
    	Course course = new Course("Maths");
    	course.questions.add(question);
    	course.save();
    	//user.save();
    	
    	Ebean.
    	beginTransaction
    	();
    	try
    	{
    		user.save();
    		question.save();
//    		   	Ebean.save(user);
//    		   	Ebean.save(user.questions);
    	}
    	finally{
    		Ebean.endTransaction();
    	}
    	
    	//question.save();
    	//question.save();
        return ok(index.render("Hello world", "Test"));
    }

//    public static Result search() {
//        return ok(index.render("clara searches", "Test"));
//    }
}
