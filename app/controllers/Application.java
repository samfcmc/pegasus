package controllers;

import models.User;
import fenixedu.FenixEduClientQAFactory;
import play.*;
import play.mvc.*;
import pt.ist.fenixedu.sdk.FenixEduClient;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	String name = "";
    	String username = session("user");
    	
    	String code = request().getQueryString("code");
    	if(code != null && username == null) {
    		User user = User.authenticate(code);
    		name = user.name;
    		session().clear();
    		session("user", user.userName);
    	}
    	else if(username != null) {
    		User user = User.findByUsername(username);
    		if(user == null) {
    			session().clear();
    		}
    		else {
    			name = user.name;
    		}
    		
    	}
        return ok(index.render("Hello world", name));
    }
    
    public static Result questionAsk() {
        return ok(questionAsk.render());
    }
    
    

}