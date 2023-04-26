
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
public class StudentEnrolmentFinalizeService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService<Student, Enrolment> ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Enrolment object;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		int studentId;
		Student student;
		int courseId;
		Course course;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		student = this.repository.findStudentById(studentId);
		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);

		super.bind(object, "cardHolderName", "cardLowerNibble");
		object.setStudent(student);
		object.setCourse(course);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		final String card = super.getRequest().getData("creditCard", String.class);
		if (!card.matches("^\\d{4}\\/\\d{4}\\/\\d{4}\\/\\d{4}$"))
			throw new IllegalArgumentException("student.enrolment.form.error.card");

		final String cardHolderName = super.getRequest().getData("cardHolderName", String.class);
		if (cardHolderName.isEmpty())
			throw new IllegalArgumentException("student.enrolment.form.error.holder");

		final String cvc = super.getRequest().getData("cvc", String.class);
		if (!cvc.matches("^\\d{3}$"))
			throw new IllegalArgumentException("student.enrolment.form.error.cvc");

		final String expiryDate = super.getRequest().getData("expiryDate", String.class);
		if (!expiryDate.matches("^\\d{2}\\/\\d{2}$"))
			throw new IllegalArgumentException("student.enrolment.form.error.expiryDate");
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);
		final String card = super.getRequest().getData("creditCard", String.class);

		object.setCardLowerNibble(card.substring(card.length() - 4));
		object.setCardHolderName(super.getRequest().getData("cardHolderName", String.class));

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		SelectChoices choices;
		final String creditCard = "";
		final String cvc = "";
		final String expiryDate = "";
		Collection<Course> courses;
		Tuple tuple;

		courses = this.repository.findNotInDraftCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "nameHolder");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("creditCard", creditCard);
		tuple.put("cvc", cvc);
		tuple.put("expiryDate", expiryDate);

		super.getResponse().setData(tuple);
		super.unbind(object);
	}
}
