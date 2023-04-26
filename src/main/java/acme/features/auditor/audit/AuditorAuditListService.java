
package acme.features.auditor.audit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditListService extends AbstractService<Auditor, Audit> {

	@Autowired
	protected AuditorAuditRepository repository;


	@Override
	public void check() {
		final boolean status = true;
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		final List<Audit> objects = this.repository.findAllAuditsByAuditorId(auditorId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "code", "conclusion");
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("course", object.getCourse().getTitle());
		tuple.put("draft", object.isDraftMode() ? "No" : "Yes");
		super.getResponse().setData(tuple);
	}

}
