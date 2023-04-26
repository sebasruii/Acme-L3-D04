
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentShowService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = enrolment != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment enrolment;
		int id;
		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(enrolment);

	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		SelectChoices choices;
		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		Boolean finalized = false;

		if (object.getCardHolderName() != null && object.getCardHolderName() != null)
			finalized = true;
		tuple = super.unbind(object, "code", "motivation", "goals", "cardHolderName", "cardLowerNibble");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);
		tuple.put("finalized", finalized);
		tuple.put("objectId", object.getId());
	}
}
