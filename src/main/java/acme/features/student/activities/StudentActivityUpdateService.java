
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
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

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
		int userId;
		Enrolment enrolment;
		int enrolmentId;
		boolean status;

		userId = super.getRequest().getPrincipal().getAccountId();
		enrolmentId = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(enrolmentId);
		enrolment = activity.getEnrolment();
		status = !enrolment.isDraftMode() && enrolment.getStudent().getUserAccount().getId() == userId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Activity activity = this.repository.findActivityById(id);

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

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("finishDate")) {
			final boolean validPeriod = MomentHelper.isAfter(object.getFinishDate(), object.getStartDate());
			super.state(validPeriod, "finishDate", "student.workbook.form.error.end-before-start");
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
		Enrolment enrolment;
		enrolment = object.getEnrolment();

		final SelectChoices choices = SelectChoices.from(TypeActivity.class, object.getActivityType());
		tuple = super.unbind(object, "title", "summary", "activityType", "startDate", "finishDate", "link");
		tuple.put("choicesActivityType", choices);
		tuple.put("draftMode", enrolment.isDraftMode());

		super.getResponse().setData(tuple);
	}

}
