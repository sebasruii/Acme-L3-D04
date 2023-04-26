
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import acme.entities.configuration.Configuration;
import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


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
		Course object;
		Lecturer lecturer;

		lecturer = this.repository.findOneLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Course();
		object.setDraftMode(true);
		object.setLecturer(lecturer);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "summary", "price", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		Configuration conf;

		conf = this.repository.findSystemConfiguration();

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null, "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			super.state(object.getPrice().getAmount() >= 0.01, "price", "lecturer.course.form.error.negative-price");
			super.state(object.getPrice().getAmount() <= 1000, "price", "lecturer.course.form.error.max-price");
		}

		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(conf.getAcceptedCurrencies().contains(object.getPrice().getCurrency()), "price", "lecturer.course.form.error.currency");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "title", "summary", "price", "link", "draftMode");

		super.getResponse().setData(tuple);
	}
}
