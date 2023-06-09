
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumSessionId;
		Practicum practicum;
		Company company;

		company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumByPracticumSessionId(practicumSessionId);

		status = super.getRequest().getPrincipal().hasRole(practicum.getCompany()) && company == practicum.getCompany() && practicum != null && practicum.getDraftMode();
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
	public void bind(final PracticumSession object) {
		assert object != null;

		super.bind(object, "title", "summary", "startDate", "finishDate", "link", "exceptional");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		if (object.getStartDate() != null && !super.getBuffer().getErrors().hasErrors("startDate")) {
			Date minimumStartDate;
			minimumStartDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getStartDate(), minimumStartDate), "startDate", "company.practicum-session.form.error.start-date");
			if (object.getFinishDate() != null && !super.getBuffer().getErrors().hasErrors("finishDate")) {
				Date minimumEndDate;
				minimumEndDate = MomentHelper.deltaFromMoment(object.getStartDate(), 7, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfterOrEqual(object.getFinishDate(), minimumEndDate), "finishDate", "company.practicum-session.form.error.end-date");
			}
		}

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;
		final Boolean draftMode = true;

		tuple = super.unbind(object, "title", "summary", "startDate", "finishDate", "link", "exceptional");
		tuple.put("draftMode", draftMode);

		super.getResponse().setData(tuple);
	}

}
