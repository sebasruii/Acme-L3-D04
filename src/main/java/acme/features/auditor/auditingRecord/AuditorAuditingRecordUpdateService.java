
package acme.features.auditor.auditingRecord;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	@Autowired
	protected AuditorAuditingRecordRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditingRecordId;
		Audit audit;
		auditingRecordId = super.getRequest().getData("id", int.class);
		audit = this.repository.findOneAuditByAuditingRecordId(auditingRecordId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordById(id);
		super.getBuffer().setData(object);
	}
	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;
		super.bind(object, "subject", "assessment", "startDate", "finishDate", "mark", "link", "correction");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
		final Duration duration = MomentHelper.computeDuration(object.getStartDate(), object.getFinishDate());
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(duration.compareTo(Duration.ofHours(1)) >= 0, "endPeriod", "auditor.auditingRecord.form.error.endPeriod-too-short");
	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		this.repository.save(object);
	}
	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "startDate", "finishDate", "mark", "link", "correction");
		tuple.put("masterId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());
		super.getResponse().setData(tuple);
	}
}
