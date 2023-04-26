
package acme.features.auditor.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditCreateService extends AbstractService<Auditor, Audit> {

	private final AuditorAuditRepository repository;


	@Autowired
	public AuditorAuditCreateService(final AuditorAuditRepository repository) {
		this.repository = repository;
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean authorized = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(authorized);
	}

	@Override
	public void load() {
		final Audit object = new Audit();
		object.setDraftMode(true);
		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final Audit object) {
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean existing = this.repository.existsAuditWithCode(object.getCode(), object.getId());
			super.state(!existing, "code", "auditor.audit.error.code.duplicated");
		}

		if (object.getCourse() != null && object.getCourse().isDraftMode())
			super.state(!object.getCourse().isDraftMode(), "course", "auditor.audit.error.course.draft");
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		final Auditor auditor = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setAuditor(auditor);
		object.setCourse(course);
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		final List<Course> courses = this.repository.findAllPublishedCourses();
		final SelectChoices choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
