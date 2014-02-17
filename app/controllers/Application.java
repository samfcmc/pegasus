package controllers;

import models.User;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	String name = "";
    	String username = session().get("user");
    	
    	String code = request().getQueryString("code");
    	if(code != null && username == null) {
    		User user = User.authenticate(code);
    		name = user.name;
    		session().clear();
    		session("user", user.userName);
    	}
    	
        return ok(index.render("Hello world", name));
    }
    
    public static Result logout() {
    	session().clear();
    	return redirect(routes.Application.index());
    }
    
    public static Result questionAsk() {
        return ok(questionAsk.render());
    }
    
    

}

