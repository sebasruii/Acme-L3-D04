
package acme.testing.lecturer.lectureCourse;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerLectureCourseListTest extends TestHarness {

	@Autowired
	protected LecturerLectureCourseRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture-course/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseIndex, final int recordIndex, final String title, final String estimatedLearningTime, final String lectureType) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("See lectures");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, estimatedLearningTime);
		super.checkColumnHasValue(recordIndex, 2, lectureType);

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses) {
			param = String.format("courseId=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture-course/list", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/lecture-course/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture-course/list", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
