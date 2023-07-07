
package acme.features.student.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		Activity activity;
		final int activityId;
		Enrolment enrolment;
		int enrolmentId;
		Student student;
		boolean status;
		int userId;

		userId = super.getRequest().getPrincipal().getAccountId();
		enrolmentId = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(enrolmentId);
		enrolment = activity.getEnrolment();
		student = enrolment == null ? null : enrolment.getStudent();
		status = (enrolment != null || super.getRequest().getPrincipal().hasRole(student)) && enrolment.isDraftMode() && enrolment.getStudent().getUserAccount().getId() == userId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int activityId = super.getRequest().getData("id", int.class);
		final Activity activity = this.repository.findActivityById(activityId);

		super.getBuffer().setData(activity);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "summary", "activityType", "startDate", "finishDate", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "activityType", "startDate", "finishDate", "link");

		super.getResponse().setData(tuple);
	}
}
