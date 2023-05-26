
package acme.features.student.activities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.entities.workbook.TypeActivity;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	//Internal state ---------------------------------------------------------

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
		boolean status;

		final int activityId = super.getRequest().getData("id", int.class);
		final Activity activity = this.repository.findActivityById(activityId);
		final int id = super.getRequest().getPrincipal().getAccountId();

		status = activity.getEnrolment().getStudent().getUserAccount().getId() == id;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity activity;
		Enrolment enrolment;

		final int id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);
		enrolment = activity.getEnrolment();

		super.getResponse().setGlobal("draftMode", enrolment.isDraftMode());
		super.getBuffer().setData(activity);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;
		final SelectChoices choices = SelectChoices.from(TypeActivity.class, object.getActivityType());
		tuple = super.unbind(object, "title", "summary", "activityType", "startDate", "finishDate", "link");
		tuple.put("choicesActivityType", choices);

		super.getResponse().setData(tuple);
	}

}
