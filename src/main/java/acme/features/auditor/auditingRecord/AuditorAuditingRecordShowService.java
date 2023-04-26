
package acme.features.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordShowService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

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
		AuditingRecord ar;

		ar = this.repository.findOneAuditingRecordById(super.getRequest().getData("id", int.class));

		status = ar != null && super.getRequest().getPrincipal().hasRole(ar.getAudit().getAuditor());

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
	public void unbind(final AuditingRecord object) {
		assert object != null;

		final String format = "dd/MM/yyyy hh:mm";

		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "link", "draftMode", "correction");
		tuple.put("startDate", MomentHelper.format(format, object.getStartDate()));
		tuple.put("finishDate", MomentHelper.format(format, object.getFinishDate()));
		tuple.put("hours", object.getHoursFromStart());
		tuple.put("marks", object.getMark());

		super.getResponse().setData(tuple);
	}
}
