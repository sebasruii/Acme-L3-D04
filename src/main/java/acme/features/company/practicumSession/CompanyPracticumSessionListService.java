
package acme.features.company.practicumSession;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;
		Company company;

		company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && company == practicum.getCompany() && practicum != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<PracticumSession> objects;
		int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findPracticumSessionsByPracticumId(practicumId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "startDate", "finishDate");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<PracticumSession> objects) {
		assert objects != null;

		int practicumId;
		Practicum practicum;
		final boolean showCreate;
		final boolean exceptionalCreate;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		showCreate = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && practicum.getDraftMode();
		exceptionalCreate = !practicum.getDraftMode() && objects.stream().filter(x -> x.getExceptional()).collect(Collectors.toList()).isEmpty();

		super.getResponse().setGlobal("masterId", practicumId);
		super.getResponse().setGlobal("showCreate", showCreate);
		super.getResponse().setGlobal("exceptionalCreate", exceptionalCreate);
	}

}
