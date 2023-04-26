
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
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		boolean roleAuthorise;
		final boolean validInstantiationStartDate;
		final boolean validStartFinishDate;
		int id;
		final Offer offer;

		id = super.getRequest().getData("id", int.class);
		offer = this.repository.findOfferById(id);

		//Custom restrictions
		validInstantiationStartDate = MomentHelper.isLongEnough(offer.getInstantiation(), offer.getStartDate(), 1, ChronoUnit.DAYS);
		validStartFinishDate = MomentHelper.isLongEnough(offer.getStartDate(), offer.getFinishDate(), 7, ChronoUnit.DAYS);
		roleAuthorise = super.getRequest().getPrincipal().hasRole(Administrator.class);

		status = validInstantiationStartDate && validStartFinishDate && roleAuthorise;

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Offer object;
		int id;
		Date instantiationDate;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOfferById(id);
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
		assert object != null;
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

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
