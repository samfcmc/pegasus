package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Tag;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.avaje.ebean.Ebean;

public class Populate extends Controller {
    
    public static Result index() {
    	User user1 = new User("user1", "u1");
    	User user2 = new User("user2", "u2");
    	
    	Question question = new Question("pergunta2","texto da pergunta");
    	question.owner = user1;
    	
    	Tag tagCourse = new Tag("Plataformas...", "PADI", "padi");
//    	Tag tagCourse2 = new Tag("Programacao avançada...", "PA", "pa");
    	
    	user1.favouriteTags.add(tagCourse);
//    	user1.favouriteTags.add(tagCourse2);
//    	user2.favouriteTags.add(tagCourse2);
    	
    	tagCourse.add(question);
    	tagCourse.users.add(user1);
//    	tagCourse2.users.add(user1);
//    	tagCourse2.users.add(user2);
//    	
//    	Ebean.beginTransaction();
//    	try
//    	{
    		tagCourse.save();
//    		tagCourse2.save();
    		user1.save();
    		user2.save();
    		question.save();
//    	}
//    	finally{
//    		Ebean.endTransaction();
//    	}
//    	
        return ok(index.render("", ""));
    }
}
