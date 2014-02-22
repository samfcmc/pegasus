import com.avaje.ebean.Ebean;

import models.Admin;
import models.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");

		User user = User.findByUsername("ist169350");
		if (user != null) {
			Admin admin = Ebean.find(Admin.class).where().eq("user", user)
					.findUnique();
			if (admin == null) {
				admin = new Admin(user);
				admin.save();
			}
		}
	}
}
