
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPracticumRepository repository;

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

		int courseId;
		Collection<Practicum> objects;
		courseId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManyPracticumByCourseId(courseId);
		super.getBuffer().setData(objects);
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

	@Override
	public void unbind(final Collection<Practicum> objects) {

		assert objects != null;
		int courseId;
		courseId = super.getRequest().getData("masterId", int.class);
		super.getResponse().setGlobal("masterId", courseId);

	}
}
