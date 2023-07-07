
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolment.Enrolment;
import acme.testing.TestHarness;

class StudentEnrolmentDeleteTest extends TestHarness {

	// Internal state -------------------------------------------------------------

	@Autowired
	protected StudentEnrolmentTestRepository repository;

	// Test methods ---------------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	void test100Positive(final int recordIndex, final String code, final String nextCode) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "List of Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.clickOnMenu("Student", "List of Enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, nextCode);
		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature since it's a listing that
		// doesn't involve entering any data into any forms.
	}

	@Test
	void test300Hacking() {
		// HINT: this test tries to delete an enrolment with a role other than "Student",
		// HINT+ or using an student who is not the owner.

		Collection<Enrolment> enrolments;
		String param;

		enrolments = this.repository.findAllEnrolmentsByStudentUsername("student1");
		for (final Enrolment enrolment : enrolments)
			if (enrolment.isDraftMode()) {
				param = String.format("id=%d", enrolment.getId());

				super.checkLinkExists("Sign in");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signIn("administrator1", "administrator1");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();
				super.signIn("auditor1", "auditor1");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();
				super.signIn("company1", "company1");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();
				super.signIn("lecturer1", "lecturer1");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();
				super.signIn("assistant1", "assistant1");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();
				super.signIn("student2", "student2");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();

			} else {
				// HINT: this test tries to delete a finalised enrolment that was registered by the principal.

				param = String.format("id=%d", enrolment.getId());
				super.signIn("student1", "student1");
				super.request("/student/enrolment/delete", param);
				super.checkPanicExists();
				super.signOut();

			}
	}
}
