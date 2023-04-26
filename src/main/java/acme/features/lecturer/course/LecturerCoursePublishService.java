
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.NatureType.NatureType;
import acme.entities.configuration.Configuration;
import acme.entities.courses.Course;
import acme.entities.lectures.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Course course;
		Lecturer lecturer;

		masterId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(masterId);
		lecturer = course == null ? null : course.getLecturer();
		status = course != null && course.isDraftMode() && super.getRequest().getPrincipal().hasRole(lecturer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

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

		Collection<Lecture> lectures;
		boolean allLecturesArePublished;
		final boolean courseTypesIsNotTheory;

		Configuration conf;

		conf = this.repository.findSystemConfiguration();


		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Course existing;

			existing = this.repository.findOneCourseByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "lecturer.course.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("price")) {
			super.state(object.getPrice().getAmount() >= 0.01, "price", "lecturer.course.form.error.negative-price");
			super.state(object.getPrice().getAmount() <= 1000, "price", "lecturer.course.form.error.max-price");
		}
		if (!super.getBuffer().getErrors().hasErrors("price"))
			super.state(conf.getAcceptedCurrencies().contains(object.getPrice().getCurrency()), "price", "lecturer.course.form.error.currency");


		{
			lectures = this.repository.findManyLecturesByCourseId(object.getId());
			super.state(!lectures.isEmpty(), "*", "lecturer.course.form.error.emptyLectures");

			if (!lectures.isEmpty()) {
				allLecturesArePublished = lectures.stream().allMatch(l -> !l.isDraftMode());
				super.state(allLecturesArePublished, "*", "lecturer.course.form.error.unpublishedLectures");

				courseTypesIsNotTheory = lectures.stream().anyMatch(l -> l.getLectureType().equals(NatureType.HANDS_ON));
				super.state(courseTypesIsNotTheory, "*", "lecturer.course.form.error.allLecturesAreTheory");

			}

		}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		object.setDraftMode(false);
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
