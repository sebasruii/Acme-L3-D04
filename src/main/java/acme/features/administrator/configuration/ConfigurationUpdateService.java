
package acme.features.administrator.configuration;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;

@Service
public class ConfigurationUpdateService extends AbstractService<Administrator, Configuration> {

	@Autowired
	protected ConfigurationRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Configuration object;

		object = this.repository.findConfiguration();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Configuration object) {
		assert object != null;

		super.bind(object, "defaultCurrency", "acceptedCurrencies");
	}

	@Override
	public void validate(final Configuration object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("defaultCurrency")) {
			super.state(object.getAcceptedCurrencies().contains(object.getDefaultCurrency()), "defaultCurrency", "administrator.configuration.form.error.defaultCurrency");
			System.out.println(object.getAcceptedCurrencies().contains(object.getDefaultCurrency()));
		}

		if (!super.getBuffer().getErrors().hasErrors("acceptedCurrencies")) {
			final Set<String> allCurrenciesUsed = this.repository.findManyCurrenciesFromCourses();
			boolean eraseAUsedCurrency = false;
			for (final String currency : allCurrenciesUsed)
				if (!object.getAcceptedCurrencies().contains(currency))
					eraseAUsedCurrency = true;
			super.state(!eraseAUsedCurrency, "acceptedCurrencies", "administrator.configuration.form.error.acceptedCurrencies");
		}
	}

	@Override
	public void perform(final Configuration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Configuration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "defaultCurrency", "acceptedCurrencies");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
