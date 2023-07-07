
package acme.features.student.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.entities.workbook.TypeActivity;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("enrolmentId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		Enrolment enrolment;
		int enrolmentId;
		Student student;
		boolean status;
		final int userId;

		userId = super.getRequest().getPrincipal().getAccountId();
		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = (enrolment != null || super.getRequest().getPrincipal().hasRole(student)) && enrolment.getStudent().getUserAccount().getId() == userId && enrolment.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Activity activity = new Activity();
		final int enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(enrolmentId);

		activity.setEnrolment(enrolment);
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

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endPeriod")) {
			final boolean validPeriod = MomentHelper.isAfter(object.getFinishDate(), object.getStartDate());
			super.state(validPeriod, "finishDate", "student.activity.form.error.validPeriod");
		}

	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;

		final SelectChoices choices = SelectChoices.from(TypeActivity.class, object.getActivityType());

		tuple = super.unbind(object, "title", "summary", "activityType", "startDate", "finishDate", "link");
		tuple.put("choicesActivityType", choices);

		final int enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		super.getResponse().setGlobal("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}

}
