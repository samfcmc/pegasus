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
    	User user = new User("user1", "u1");
    	Tag tag = new Tag("Maths", "MM", "m1");
    	List<Tag> tags = new ArrayList<Tag>();
    	tags.add(tag);  	
    	Question question = new Question("pergunta2","texto da pergunta", tags, user);
    	tag.add(question);
    	Ebean.beginTransaction();
    	try
    	{
    		tag.save();
    		user.save();
    		question.save();
    		return ok(index.render("Success", ""));
    	} catch(Exception e) {
    		return ok(index.render("Error", ""));
    	}
    	finally{
    		Ebean.endTransaction();
    	}
    	
    }
}
