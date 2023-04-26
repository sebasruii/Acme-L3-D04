
package acme.features.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.entities.lectures.LectureCourse;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseAddService extends AbstractService<Lecturer, LectureCourse> {

	@Autowired
	protected LecturerLectureCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("courseId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(course.getLecturer());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		LectureCourse object;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findOneCourseById(courseId);
		object = new LectureCourse();
		object.setCourse(course);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final LectureCourse object) {
		assert object != null;

		int lectureId;
		Lecture lecture;

		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findOneLectureById(lectureId);

		object.setLecture(lecture);
	}

	@Override
	public void validate(final LectureCourse object) {
		assert object != null;

		int lectureId;
		int courseId;
		LectureCourse lecture = null;

		if (object.getLecture() != null) {
			lectureId = object.getLecture().getId();
			courseId = super.getRequest().getData("courseId", int.class);
			lecture = this.repository.findOneLectureCourseBy(courseId, lectureId);

			super.state(object.getLecture().getLecturer().equals(object.getCourse().getLecturer()), "lecture", "lecturer.lecture-course.form.error.not-your-lecture");
			super.state(lecture == null, "lecture", "lecturer.lecture-course.form.error.already-added");
		}

		super.state(object.getLecture() != null, "lecture", "lecturer.lecture-course.form.error.no-lecture-selected");

	}

	@Override
	public void perform(final LectureCourse object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;

		int lecturerId;
		Collection<Lecture> lectures;
		final Tuple tuple;
		SelectChoices choices;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lectures = this.repository.findLecturesByLecturerId(lecturerId);

		choices = SelectChoices.from(lectures, "title", object.getLecture());

		tuple = new Tuple();
		tuple.put("lecture", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<LectureCourse> objects) {
		assert objects != null;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		super.getResponse().setGlobal("courseId", courseId);
	}

}
