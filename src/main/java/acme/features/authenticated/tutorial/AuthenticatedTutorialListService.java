
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorials.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialListService extends AbstractService<Authenticated, Tutorial> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AuthenticatedTutorialRepository repository;


	//AbstractService Interface -------------------------------------------------------------
	@Override
	public void check() {

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Collection<Tutorial> object;
		final int assistantId;
		final int courseId;

		courseId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findManyPracticumByCourseId(courseId);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "summary", "goals", "draftMode", "course");

		super.getResponse().setData(tuple);
	}
}
