
package acme.testing.lecturer.lectureCourse;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerLectureCourseAddTest extends TestHarness {

	@Autowired
	protected LecturerLectureCourseRepositoryTest repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture-course/add-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseIndex, final int recordIndex, final String lectureTitle, final String courseCode) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("See lectures");
		super.clickOnButton("Add lecture");

		super.checkFormExists();
		super.fillInputBoxIn("lecture", lectureTitle);

		super.clickOnSubmit("Add lecture");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("See lectures");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("lecture.title", lectureTitle);
		super.checkInputBoxHasValue("course.code", courseCode);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture-course/add-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int courseIndex, final int recordIndex, final String lectureTitle, final String courseCode) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseIndex);
		super.clickOnButton("See lectures");
		super.clickOnButton("Add lecture");

		super.checkFormExists();
		super.fillInputBoxIn("lecture", lectureTitle);

		super.clickOnSubmit("Add lecture");

		super.checkErrorsExist();

	}

	@Test
	public void test300Hacking() {
		Collection<Course> courses;
		String param;

		courses = this.repository.findCoursesByLecturer("lecturer1");
		for (final Course course : courses) {
			param = String.format("courseId=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture-course/add", param);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/lecturer/lecture-course/add", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture-course/add", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
