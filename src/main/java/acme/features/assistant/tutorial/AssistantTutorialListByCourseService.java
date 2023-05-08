
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorials.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialListByCourseService extends AbstractService<Assistant, Tutorial> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AssistantTutorialRepository repository;


	//AbstractService Interface -------------------------------------------------------------
	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Assistant.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Collection<Tutorial> object;
		final int assistantId;
		final int courseId;

		courseId = super.getRequest().getData("masterId", int.class);
		assistantId = super.getRequest().getPrincipal().getActiveRoleId();
		object = this.repository.findManyPracticumByCourseIdAssistantId(courseId, assistantId);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "summary", "goals", "draftMode", "course");

		super.getResponse().setData(tuple);
	}

}
