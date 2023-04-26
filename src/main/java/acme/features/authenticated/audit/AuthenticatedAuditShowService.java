
package acme.features.authenticated.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditShowService extends AbstractService<Authenticated, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		//final List<Mark> marks = this.repository.findAllMarksByAuditId(object.getId());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		tuple.put("auditor", object.getAuditor().getProfessionalId());
		tuple.put("courseName", object.getCourse().getTitle());

		/*
		 * if (marks != null && !marks.isEmpty())
		 * tuple.put("marks", marks.stream().map(Mark::toString).collect(Collectors.joining(", ", "[ ", " ]")));
		 * else
		 * tuple.put("marks", "-----");
		 * super.getResponse().setData(tuple);
		 */
	}

}
