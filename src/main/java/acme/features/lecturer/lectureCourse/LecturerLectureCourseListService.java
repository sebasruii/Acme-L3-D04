
package acme.features.lecturer.lectureCourse;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.lectures.LectureCourse;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCourseListService extends AbstractService<Lecturer, LectureCourse> {

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
		Collection<LectureCourse> objects;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findLectureCoursesByCourseId(courseId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final LectureCourse object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "lecture.title", "lecture.estimatedLearningTime", "lecture.lectureType");
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
