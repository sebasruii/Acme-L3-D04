
package acme.features.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.lectures.LectureCourse;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseDeleteService extends AbstractService<Lecturer, LectureCourse> {

	@Autowired
	protected LecturerLectureCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int lectureCourseId;
		LectureCourse lectureCourse;

		lectureCourseId = super.getRequest().getData("id", int.class);
		lectureCourse = this.repository.findOneLectureCourseById(lectureCourseId);
		status = lectureCourse != null && lectureCourse.getCourse().isDraftMode() && super.getRequest().getPrincipal().hasRole(lectureCourse.getCourse().getLecturer()) && super.getRequest().getPrincipal().hasRole(lectureCourse.getLecture().getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		LectureCourse object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final LectureCourse object) {
		assert object != null;

	}

	@Override
	public void validate(final LectureCourse object) {
		assert object != null;

		super.state(object.getLecture() != null, "course", "lecturer.lecture-course.form.error.no-lecture-selected");
		if (object.getLecture() != null)
			super.state(object.getCourse().isDraftMode(), "course", "lecturer.lecture-course.form.error.published");
	}

	@Override
	public void perform(final LectureCourse object) {
		assert object != null;

		LectureCourse lectureCourse;

		lectureCourse = this.repository.findOneLectureCourseBy(object.getCourse().getId(), object.getLecture().getId());

		this.repository.delete(lectureCourse);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "lecture.title", "course.code");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<LectureCourse> objects) {
		assert objects != null;
		int id;

		id = super.getRequest().getData("id", int.class);
		super.getResponse().setGlobal("id", id);
	}
}
