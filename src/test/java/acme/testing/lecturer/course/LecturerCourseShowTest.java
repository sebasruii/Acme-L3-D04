
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerCourseShowTest extends TestHarness {

	@Autowired
	protected LecturerCourseRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String summary, final String courseType, final String price, final String link) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("courseType", courseType);
		super.checkInputBoxHasValue("price", price);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		final Collection<Course> courses;
		String param;

		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses) {
			param = String.format("id=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/show", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/course/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/show", param);
			super.checkPanicExists();
			super.signOut();
		}

	}
}
