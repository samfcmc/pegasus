package controllers;

import com.avaje.ebean.Ebean;

import models.Admin;
import models.User;
import play.mvc.Result;
import play.mvc.Http.Context;
import play.mvc.Security.Authenticator;

public class AdminSecured extends Authenticator {

	@Override
	public String getUsername(Context context) {
		String username = context.session().get("user");
		User user = User.findByUsername(username);
		
		Admin admin = Ebean.find(Admin.class).where().eq("user", user).findUnique();
		
		if(admin != null) {
			return username;
		}
		else {
			return null;
		}
	}
	
	@Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.index());
    }
}
