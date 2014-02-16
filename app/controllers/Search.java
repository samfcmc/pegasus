package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;

import models.Question;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Search extends Controller {
    public static Result index() {
        //return ok(index.render("clara search", "Test"));
    	
    	String searchQuery = request().getQueryString("searchstr"); 
    	
    	List<Question> results = Ebean.find(Question.class)
    			.where()
    			.like("title", "%" + searchQuery + "%")
    			.findList();
    	
    	if (results.size() != 0)
    		return ok(index.render("Results for " + request().getQueryString("searchstr"), ((Question)results.get(0)).title));
    	else
    		return ok(index.render("Results for " + request().getQueryString("searchstr"), "Test"));
        
    }
}
