
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;
import acme.testing.lecturer.lecture.LecturerLectureRepositoryTest;

public class LecturerCourseUpdateTest extends TestHarness {

	@Autowired
	protected LecturerLectureRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String price, final String link) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("price", price);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String summary, final String price, final String link) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("price", price);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses)
			if (course.isDraftMode()) {
				param = String.format("id=%d", course.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/course/update", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/lecturer/course/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/lecturer/course/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}
	@Test
	public void test301Hacking() {
		Collection<Course> courses;
		String param;

		super.signIn("lecturer1", "lecturer1");
		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses)
			if (!course.isDraftMode()) {
				param = String.format("id=%d", course.getId());
				super.request("/lecturer/course/update", param);
				super.checkPanicExists();

			}
		super.signOut();

	}

	@Test
	public void test302Hacking() {
		Collection<Course> courses;
		String param;

		super.signIn("lecturer2", "lecturer2");
		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses) {
			param = String.format("id=%d", course.getId());
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
		}

	}

}
