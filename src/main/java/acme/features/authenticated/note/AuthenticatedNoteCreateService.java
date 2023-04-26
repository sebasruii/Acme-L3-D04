
package acme.features.authenticated.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().isAuthenticated();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Note object;

		object = new Note();

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		assert object != null;

		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		super.bind(object, "title", "message", "email", "link");
		object.setAuthor(userAccount.getIdentity().getFullName());
		object.setEmail(userAccount.getIdentity().getEmail());
		object.setInstantiation(MomentHelper.getCurrentMoment());

	}

	@Override
	public void unbind(final Note object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "message", "email", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "authenticated.note.form.confirmation-required");
	}

	@Override
	public void perform(final Note object) {
		assert object != null;

		this.repository.save(object);
	}
}
