
package acme.features.authenticated.tutorial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorials.Tutorial;
import acme.features.assistant.tutorial.AssistantTutorialRepository;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	//Internal	state ------------------------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	//AbstractService Interface -------------------------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final int id = super.getRequest().getData("id", int.class);
		final Tutorial tutorial = this.repository.findTutorialById(id);
		final boolean status = tutorial != null && super.getRequest().getPrincipal().isAuthenticated() && tutorial.isDraftMode() == false;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		final List<Course> courses = this.repository.findAllPublishedCourses();
		final SelectChoices choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "summary", "goals", "draftMode");
		tuple.put("course", object.getCourse().getCode());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);

	}
}
