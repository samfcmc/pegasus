package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;

public class Secured extends Authenticator {

	@Override
	public String getUsername(Context context) {
		return context.session().get("user");
	}
	
	@Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.index());
    }
}
