
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SystemConfigurationService;
import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	protected SystemConfigurationService		configurationService;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Practicum practicum;
		Company company;
		int practicumSessionId;

		company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumByPracticumSessionId(practicumSessionId);

		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && company.getId() == practicum.getCompany().getId() && practicum != null;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findPracticumSessionById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Tuple tuple;

		final String lang = super.getRequest().getLocale().getLanguage();

		tuple = super.unbind(object, "title", "summary", "startDate", "finishDate", "link");
		tuple.put("exceptional", this.configurationService.booleanTranslated(object.getExceptional(), lang));
		tuple.put("masterId", object.getPracticum().getId());
		tuple.put("draftMode", object.getPracticum().getDraftMode());

		super.getResponse().setData(tuple);
	}

}
