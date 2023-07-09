
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferCreateService extends AbstractService<Administrator, Offer> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AdministratorOfferRepository repository;

	//AbstractService Interface -------------------------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Offer object;
		Date instantiationDate;

		object = new Offer();
		instantiationDate = MomentHelper.getCurrentMoment();
		object.setInstantiation(instantiationDate);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;

		super.bind(object, "instantiation", "heading", "summary", "startDate", "finishDate", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		final Date instantiationDate;
		final Date startDate;
		final Date finishDate;

		instantiationDate = object.getInstantiation();
		startDate = object.getStartDate();
		finishDate = object.getFinishDate();
		//Custom restrictions
		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isLongEnough(instantiationDate, startDate, 1440, ChronoUnit.MINUTES), "startDate", "administrator.offer.error.start-1Day-after-now");
		if (!super.getBuffer().getErrors().hasErrors("finishDate"))
			super.state(MomentHelper.isLongEnough(startDate, finishDate, 10080, ChronoUnit.MINUTES), "finishDate", "administrator.offer.error.end-7Days-after-start");

		assert object != null;
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;
		final Date instantiationDate = MomentHelper.getCurrentMoment();
		object.setInstantiation(instantiationDate);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		Tuple tuple;

		tuple = super.unbind(object, "instantiation", "heading", "summary", "startDate", "finishDate", "price", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
