package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;

import models.Tag;
import models.TagRequest;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import views.html.tagCreate;
import views.html.tagRequestsList;

public class TagRequests extends Controller {

	@Authenticated(Secured.class)
	public static Result create() {
		Form<TagRequest> form = new Form<TagRequest>(TagRequest.class);
		TagRequest tagRequest = form.bindFromRequest().get();

		// Check if the tag already exists or request has already been done
		Tag tag = Tag.find.where().eq("label", tagRequest.tagLabel)
				.findUnique();
		TagRequest tagRequestFind = TagRequest.find.where()
				.eq("tagLabel", tagRequest.tagLabel).findUnique();

		if (tag != null) {
			return forbidden("Tag already exists");
		}

		if (tagRequestFind != null) {
			return forbidden("Tag has already been requested");
		}

		String username = request().username();
		User user = User.findByUsername(username);
		tagRequest.requester = user;
		tagRequest.save();

		return redirect(controllers.routes.Application.index());
	}

	@Authenticated(AdminSecured.class)
	public static Result accept(long id) {
		TagRequest tagRequest = Ebean.find(TagRequest.class, id);
		String tagLabel = tagRequest.tagLabel;
		String tagDescription = tagRequest.tagDescription;
		String tagNameFenix = tagRequest.tagNameFenix;

		Tag tagFind = Tag.find.where().eq("label", tagLabel).findUnique();

		if (tagFind != null) {
			return forbidden("Tag already exists");
		}

		Tag tag = new Tag(tagLabel, tagDescription, tagNameFenix);

		tag.save();
		tagRequest.delete();

		return redirect(controllers.routes.TagRequests.getAllTagRequests());
	}

	@Authenticated(AdminSecured.class)
	public static Result reject(long id) {
		TagRequest tagRequest = Ebean.find(TagRequest.class, id);

		tagRequest.delete();

		return redirect(controllers.routes.TagRequests.getAllTagRequests());
	}

	@Authenticated(Secured.class)
	public static Result requestTag() {
		Form<TagRequest> form = new Form<TagRequest>(TagRequest.class);
		return ok(tagCreate.render(form));
	}

	@Authenticated(AdminSecured.class)
	public static Result getAllTagRequests() {
		List<TagRequest> tagRequests = TagRequest.find.all();

		// Load owners
		for (TagRequest tagRequest : tagRequests) {
			User requester = Ebean.find(User.class, tagRequest.requester.id);
			tagRequest.requester = requester;
		}

		return ok(tagRequestsList.render(tagRequests));
	}
}
