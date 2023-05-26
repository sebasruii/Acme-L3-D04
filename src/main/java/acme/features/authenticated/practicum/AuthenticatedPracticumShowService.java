
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AuthenticatedPracticumRepository repository;

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
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		status = !practicum.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		super.getBuffer().setData(practicum);
	}

	@Override
	public void unbind(final Practicum practicum) {
		assert practicum != null;

		Tuple tuple;
		tuple = super.unbind(practicum, "code", "title", "summary", "goals", "estimatedTotalTime");
		tuple.put("courseCode", practicum.getCourse().getCode());
		tuple.put("nameCompany", practicum.getCompany().getName());

		super.getResponse().setData(tuple);
	}

}
