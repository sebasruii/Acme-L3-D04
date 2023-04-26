
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordPublishService extends AbstractService<Auditor, AuditingRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		final AuditingRecord ar = this.repository.findOneAuditingRecordById(super.getRequest().getData("id", int.class));
		status = super.getRequest().getPrincipal().hasRole(ar.getAudit().getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;

		object = this.repository.findOneAuditingRecordById(super.getRequest().getData("id", int.class));
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		final String mark = super.getRequest().getData("mark", String.class);
		super.bind(object, "subject", "assessment", "link", "startDate", "finishDate");
		object.setMark(mark);
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("finishDate"))
			if (!MomentHelper.isBefore(object.getStartDate(), object.getFinishDate()))
				super.state(false, "startDate", "auditor.auditingrecord.error.date.startAfterFinish");
			else
				super.state(!(object.getHoursFromStart() < 1), "startDate", "auditor.auditingrecord.error.date.shortPeriod");

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "link", "mark", "startDate", "finishDate", "draftMode");

		super.getResponse().setData(tuple);
	}
}
