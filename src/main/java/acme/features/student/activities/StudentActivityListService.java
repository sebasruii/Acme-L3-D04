
package acme.features.student.activities;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Enrolment;
import acme.entities.workbook.Activity;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

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
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(student) && enrolment.getStudent().getUserAccount().getId() == userId && !enrolment.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Enrolment enrolment;
		Collection<Activity> activities;
		final int enrolmentId = super.getRequest().getData("enrolmentId", int.class);

		enrolment = this.repository.findEnrolmentById(enrolmentId);
		activities = this.repository.findAllActivitiesByEnrolment(enrolmentId);

		super.getResponse().setGlobal("draftMode", enrolment.isDraftMode());
		super.getResponse().setGlobal("enrolmentId", enrolmentId);
		super.getBuffer().setData(activities);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "title", "activityType");
		super.getResponse().setData(tuple);
	}

}
