
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.NatureType.NatureType;
import acme.entities.lectures.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Lecture Lecture;
		Principal principal;

		id = super.getRequest().getData("id", int.class);
		Lecture = this.repository.findOneLectureById(id);
		principal = super.getRequest().getPrincipal();

		status = Lecture.getLecturer().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(NatureType.class, object.getLectureType());

		tuple = super.unbind(object, "title", "summary", "estimatedLearningTime", "body", "lectureType", "link", "draftMode");
		tuple.put("lectureTypes", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<Lecture> objects) {
		assert objects != null;
		int lectureId;

		lectureId = super.getRequest().getData("id", int.class);
		super.getResponse().setGlobal("lectureId", lectureId);
	}
}
