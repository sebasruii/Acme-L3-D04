
package acme.features.auditor.audit;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditPublishService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Audit audit;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(masterId);
		auditor = audit == null ? null : audit.getAuditor();
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Audit object) {
		assert object != null;

		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");
	}

	@Override
	public void validate(final Audit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Audit existing;

			existing = this.repository.findOneAuditByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.audit.form.error.duplicated");

			final List<AuditingRecord> auditRecords = this.repository.findAllAuditingRecordsByAuditId(object.getId());
			super.state(!auditRecords.isEmpty(), "*", "auditor.audit.error.records.noRecords");

			boolean notPublishedRecords = false;
			notPublishedRecords = auditRecords.stream().anyMatch(AuditingRecord::isDraftMode);
			super.state(!notPublishedRecords, "*", "auditor.audit.error.records.notPublished");
		}

	}

	@Override
	public void perform(final Audit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		final List<Course> courses = this.repository.findAllPublishedCourses();
		final SelectChoices choices = SelectChoices.from(courses, "code", object.getCourse());
		final List<String> marks = this.repository.findAllMarksByAuditId(object.getId());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "draftMode");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		if (marks != null && !marks.isEmpty())
			tuple.put("marks", marks.stream().collect(Collectors.joining(", ", "[ ", " ]")));
		else
			tuple.put("marks", "N/A");
		super.getResponse().setData(tuple);
	}

}
