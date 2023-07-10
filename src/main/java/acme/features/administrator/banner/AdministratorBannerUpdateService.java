
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

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
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Banner object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findBannerById(id);
		Date instantiationMoment;
		instantiationMoment = MomentHelper.getCurrentMoment();
		object.setInstantiation(instantiationMoment);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiation", "startDate", "finishDate", "imageLink", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		Date instantiationMoment;
		instantiationMoment = MomentHelper.getCurrentMoment();
		object.setInstantiation(instantiationMoment);

		if (!super.getBuffer().getErrors().hasErrors("startDate") || !super.getBuffer().getErrors().hasErrors("finishDate")) {

			Date start;
			Date startCondition;
			Date end;
			Date inAWeekFromStart;

			startCondition = object.getInstantiation();
			start = object.getStartDate();
			end = object.getFinishDate();

			inAWeekFromStart = MomentHelper.deltaFromMoment(start, AdministratorBannerCreateService.oneWeek, ChronoUnit.WEEKS);

			if (!super.getBuffer().getErrors().hasErrors("startDate")) {
				super.state(MomentHelper.isAfter(start, startCondition), "startDate", "administrator.banner.error.start-before-now");
				if (!super.getBuffer().getErrors().hasErrors("finishDate"))
					super.state(MomentHelper.isAfter(end, inAWeekFromStart), "finishDate", "administrator.banner.error.end-after-start");
			}
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "startDate", "finishDate", "imageLink", "slogan", "link");

		super.getResponse().setData(tuple);
	}
}
