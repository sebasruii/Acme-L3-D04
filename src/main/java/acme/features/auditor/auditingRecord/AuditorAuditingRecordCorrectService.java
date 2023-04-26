
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCorrectService extends AbstractService<Auditor, AuditingRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		final Audit audit = this.repository.findOneAuditById(super.getRequest().getData("auditId", int.class));
		status = super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && !audit.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;

		object = new AuditingRecord();
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		final int auditId = super.getRequest().getData("auditId", int.class);
		final Audit audit = this.repository.findOneAuditById(auditId);
		final String mark = super.getRequest().getData("mark", String.class);
		super.bind(object, "subject", "assessment", "link", "startDate", "finishDate");
		object.setMark(mark);
		object.setAudit(audit);
		object.setDraftMode(false);
		object.setCorrection(true);
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
		final Boolean confirm = super.getRequest().getData("confirm", boolean.class);
		super.state(confirm, "*", "auditor.auditingrecord.correction.confirmation");

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("startDate"))
			if (!MomentHelper.isBefore(object.getStartDate(), object.getFinishDate()))
				super.state(false, "startDate", "auditor.auditingrecord.error.date.startAfterFinish");
			else
				super.state(!(object.getHoursFromStart() < 1), "startDate", "auditor.auditingrecord.error.date.shortPeriod");

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "link", "mark", "startDate", "finishDate");
		tuple.put("auditId", super.getRequest().getData("auditId", int.class));

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}
}
