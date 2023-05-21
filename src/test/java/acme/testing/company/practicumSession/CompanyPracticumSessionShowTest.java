
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int PracticumRecordIndex, final String reference, final int PracticumSessionRecordIndex, final String title, final String summary, final String startDate, final String finishDate, final String link) {
		// HINT: this test signs in as an employer, lists his or her jobs, selects
		// HINT+ one of them and checks that it's as expected.

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(PracticumRecordIndex);
		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(PracticumSessionRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show a duty of a job that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<Practicum> practicums;
		String param;

		practicums = this.repository.findManyPracticumsByCompanyUsername("Company1");
		for (final Practicum practicum : practicums)
			if (practicum.getDraftMode()) {
				param = String.format("masterId=%d", practicum.getId());

				super.checkLinkExists("Sign in");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();

				super.signIn("administrator1", "administrator1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company2", "company2");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/company/practicum-session/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
