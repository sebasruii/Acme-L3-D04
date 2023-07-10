
package acme.features.student.enrolment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.enrolment.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinalizeService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentEnrolmentRepository repository;

	// AbstractService<Student, Enrolment> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;
		final int userId;

		userId = super.getRequest().getPrincipal().getAccountId();
		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = (enrolment != null || super.getRequest().getPrincipal().hasRole(student)) && enrolment.isDraftMode() && enrolment.getStudent().getUserAccount().getId() == userId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void load() {
		Enrolment object;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "cardHolderName", "cardLowerNibble");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		final String card = super.getRequest().getData("creditCard", String.class);
		if (!StudentEnrolmentFinalizeService.validateCreditCard(card))
			super.state(false, "creditCard", "student.enrolment.form.error.card");
		if (!super.getBuffer().getErrors().hasErrors("cardHolderName"))
			super.state(!object.getCardHolderName().isEmpty(), "cardHolderName", "student.enrolment.form.error.holder");

		final String cvc = super.getRequest().getData("cvc", String.class);
		if (!cvc.matches("^\\d{3}$"))
			super.state(false, "cvc", "student.enrolment.form.error.cvc");

		final String expiryDate = super.getRequest().getData("expiryDate", String.class);
		final Locale local = super.getRequest().getLocale();
		final String localString = local.equals(Locale.ENGLISH) ? "yy/MM" : "MM/yy";
		final DateFormat formate = new SimpleDateFormat(localString);
		try {
			final Date date = formate.parse(expiryDate);
			final int i = local.equals(Locale.ENGLISH) ? 1 : 0;
			final int month = Integer.parseInt(expiryDate.split("/")[i]);
			if (month < 1 || month > 12)
				super.state(false, "expiryDate", "student.enrolment.form.error.expiryDate.month");
			if (MomentHelper.isBefore(date, MomentHelper.getCurrentMoment()))
				super.state(false, "expiryDate", "student.enrolment.form.error.expiryDate.before");
		} catch (final ParseException e) {
			super.state(false, "expiryDate", "student.enrolment.form.error.expiryDate.pattern");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);
		String card = super.getRequest().getData("creditCard", String.class);
		card = card.replaceAll("\\D", "");
		object.setCardLowerNibble(card.substring(card.length() - 4));
		object.setCardHolderName(super.getRequest().getData("cardHolderName", String.class));

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		SelectChoices choices;
		final String creditCard = "";
		final String cvc = "";
		final String expiryDate = "";
		final Double estimatedTotalTime = 0.0;
		Collection<Course> courses;
		Tuple tuple;

		courses = this.repository.findNotInDraftCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());
		tuple = super.unbind(object, "code", "motivation", "goals", "course", "cardHolderName");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("creditCard", creditCard);
		tuple.put("cvc", cvc);
		tuple.put("expiryDate", expiryDate);
		tuple.put("estimatedTotalTime", estimatedTotalTime);
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}

	public static boolean validateCreditCard(String cardNumber) {
		cardNumber = cardNumber.replaceAll("\\D", "");

		if (!cardNumber.matches("\\d+") || cardNumber.isEmpty())
			return false;

		int sum = 0;
		boolean alternate = false;

		for (int i = cardNumber.length() - 1; i >= 0; i--) {
			int digit = Integer.parseInt(cardNumber.substring(i, i + 1));

			if (alternate) {
				digit *= 2;
				if (digit > 9)
					digit = digit % 10 + 1;
			}

			sum += digit;
			alternate = !alternate;
		}

		return sum % 10 == 0;
	}
}
