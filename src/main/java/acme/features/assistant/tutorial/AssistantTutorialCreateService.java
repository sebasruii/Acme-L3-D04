
package acme.features.assistant.tutorial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AssistantTutorialRepository repository;

	//AbstractService Interface -------------------------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean authorized = super.getRequest().getPrincipal().hasRole(Assistant.class);
		super.getResponse().setAuthorised(authorized);
	}

	@Override
	public void load() {
		final Tutorial object = new Tutorial();
		object.setDraftMode(true);
		object.setEstimatedTotalTime("0");
		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;

			if (!super.getBuffer().getErrors().hasErrors("course")) {
				final Course selectedCourse = object.getCourse();
				super.state(!selectedCourse.isDraftMode(), "course", "assistant.tutorial.error.not-published");
			}

			if (!super.getBuffer().getErrors().hasErrors("code")) {
				existing = this.repository.findTutorialByCode(object.getCode());
				super.state(existing == null, "code", "assistant.tutorial.form.error.duplicated");
			}

		}

	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;

		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "title", "summary", "goals");
		final Assistant assistant = this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setAssistant(assistant);
		object.setCourse(course);
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		final List<Course> courses = this.repository.findAllPublishedCourses();
		final SelectChoices choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "title", "summary", "goals");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
