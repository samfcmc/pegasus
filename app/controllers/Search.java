package controllers;

import java.util.List;

import models.Course;
import models.Question;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.avaje.ebean.Ebean;

public class Search extends Controller {
	public static Result index() {
    	
    	String searchQuery = request().getQueryString("searchString"); 
    	
    	List<Question> resultsTitles = Ebean.find(Question.class)
    			.where()
    			.like("title", "%" + searchQuery + "%")
    			.findList();
    	
    	List<Question> results = Ebean.find(Question.class)
    			.where()
    			.like("text", "%" + searchQuery + "%")
    			.findList();
    	
    	for (Question q : results){
    		if (! resultsTitles.contains(q))
    		{
    			resultsTitles.add(q);
    		}
    	}
    	
    	System.out.println("results.size = "+ results.size());
    	if (results.size() != 0)
    		{
    			String stringResultsTitle = "Results for \" " + request().getQueryString("searchString") + " \": ";
	      		String stringResults = "";
    			for (Question q : results)
	      		{
	      			stringResults += q.getTitle() +  System.getProperty("line.separator");
	      		}
    		
	      		return ok(index.render(stringResultsTitle, stringResults));
    		}	
    	else
    		return ok(index.render("Results for " + request().getQueryString("searchString"), "No results were found."));
		//return null;
        
    }

	public static Result populate() {
		User user = new User("user1", "u1");
		// user.save();

		Question question = new Question("pergunta1", "texto da pergunta");
		user.questions.add(question);

		Course course = new Course("Maths");
		course.questions.add(question);
		course.save();

		Ebean.beginTransaction();
		try {
			user.save();
			question.save();
		} finally {
			Ebean.endTransaction();
		}

		return ok(index.render("Hello world", "Test"));
	}
}
