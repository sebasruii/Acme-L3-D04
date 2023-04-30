
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
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	// Constants -------------------------------------------------------------

	public static final int						ONE_WEEK	= 1;

	// Internal state ---------------------------------------------------------
	@Autowired
	private CompanyPracticumSessionRepository	repository;

	// AbstractService Interface ----------------------------------------------


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

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession PracticumSession;
		int practicumId;
		Practicum practicum;
		boolean draftMode;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);
		draftMode = practicum.getDraftMode();

		PracticumSession = new PracticumSession();
		PracticumSession.setPracticum(practicum);

		if (!draftMode)
			PracticumSession.setExceptional(true);
		else
			PracticumSession.setExceptional(false);

		super.getBuffer().setData(PracticumSession);
	}

	@Override
	public void bind(final PracticumSession PracticumSession) {
		assert PracticumSession != null;

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(practicumId);

		super.bind(PracticumSession, "title", "summary", "startDate", "finishDate", "link");
		PracticumSession.setPracticum(practicum);

	}

	@Override
	public void validate(final PracticumSession PracticumSession) {
		assert PracticumSession != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate") || !super.getBuffer().getErrors().hasErrors("finishDate")) {
			Date startDate;
			Date finishDate;
			Date inAWeekFromNow;
			Date inAWeekFromStart;

			startDate = PracticumSession.getStartDate();
			finishDate = PracticumSession.getFinishDate();
			inAWeekFromNow = MomentHelper.deltaFromCurrentMoment(CompanyPracticumSessionCreateService.ONE_WEEK, ChronoUnit.WEEKS);
			inAWeekFromStart = MomentHelper.deltaFromMoment(startDate, CompanyPracticumSessionCreateService.ONE_WEEK, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(MomentHelper.isAfter(startDate, inAWeekFromNow), "startDate", "company.session-practicum.error.start-after-now");
			if (!super.getBuffer().getErrors().hasErrors("finishDate"))
				super.state(MomentHelper.isAfter(finishDate, inAWeekFromStart), "finishDate", "company.session-practicum.error.end-after-start");
		}
	}

	@Override
	public void perform(final PracticumSession PracticumSession) {
		assert PracticumSession != null;

		this.repository.save(PracticumSession);
	}

	@Override
	public void unbind(final PracticumSession PracticumSession) {
		assert PracticumSession != null;

		Practicum practicum;
		Tuple tuple;

		practicum = PracticumSession.getPracticum();
		tuple = super.unbind(PracticumSession, "title", "summary", "startDate", "finishDate", "link");
		tuple.put("masterId", practicum.getId());
		tuple.put("draftMode", practicum.getDraftMode());

		super.getResponse().setData(tuple);
	}
}
