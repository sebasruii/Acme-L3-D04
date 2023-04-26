
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.NatureType.NatureType;
import acme.entities.courses.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseShowService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


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
		Course course;
		Principal principal;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);
		principal = super.getRequest().getPrincipal();

		status = course.getLecturer().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		Tuple tuple;
		NatureType nature;
		int numTheoryLectures;
		int numHandsOnLectures;

		numTheoryLectures = this.repository.numberOfTheoryLecturesPerCourse(object.getId());
		numHandsOnLectures = this.repository.numberOfHandsOnLecturesPerCourse(object.getId());

		if (numTheoryLectures == 0 && numHandsOnLectures == 0)
			nature = null;
		else if (numTheoryLectures > 0 && numHandsOnLectures > 0)
			nature = NatureType.BALANCED;
		else
			nature = numTheoryLectures > numHandsOnLectures ? NatureType.THEORY : NatureType.HANDS_ON;

		tuple = super.unbind(object, "code", "title", "summary", "price", "link", "draftMode");
		tuple.put("courseType", nature);
		super.getResponse().setData(tuple);
	}
}
