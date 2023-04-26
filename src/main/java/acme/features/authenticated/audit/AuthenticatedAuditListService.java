
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditListService extends AbstractService<Authenticated, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final Collection<Audit> objects;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		//objects = this.repository.findAllAuditsByCourseId(masterId);
		//super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion");
		super.getResponse().setData(tuple);
	}

}
