
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerLectureListTest extends TestHarness {

	@Autowired
	protected LecturerLectureRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String estimatedLearningTime, final String lectureType) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("See lectures");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, estimatedLearningTime);
		super.checkColumnHasValue(recordIndex, 2, lectureType);

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/list-all");
		super.checkPanicExists();

		super.signIn("administrator1", "administrator1");
		super.request("/lecturer/lecture/list-all");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/lecturer/lecture/list-all");
		super.checkPanicExists();
		super.signOut();
	}

	@Test
	public void test301Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses) {
			param = String.format("courseId=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/list", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/lecture/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
