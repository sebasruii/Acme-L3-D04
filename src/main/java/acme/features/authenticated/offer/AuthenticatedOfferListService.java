
package acme.features.authenticated.offer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedOfferListService extends AbstractService<Authenticated, Offer> {

	//Internal	state ------------------------------------------------------------------------
	@Autowired
	protected AuthenticatedOfferRepository repository;

	//AbstractService Interface -------------------------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Collection<Offer> object;

		object = this.repository.findAllOffers();

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Offer object) {
		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary", "startDate", "finishDate", "price", "link");

		super.getResponse().setData(tuple);
	}

}
